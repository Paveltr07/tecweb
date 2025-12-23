import com.sun.source.tree.ReturnTree;

import javax.sound.midi.SysexMessage;
import java.awt.*;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class GestorNotasImpl extends UnicastRemoteObject implements GestorNotas {

    public GestorNotasImpl() throws Exception {
        super();
    }
    //Para asignar materias disponibles en el programa del colegio.
    private static final String[] MATERIAS_PREDEFINIDAS = {
            "Matemáticas", "Español", "Ciencias Naturales",
            "Historia", "Geografía", "Física",
            "Cálculo", "Circuitos Eléctricos", "Inglés", "Programación Distribuida y Aplicada", "Programación I"
    };

    //Método para obetener las materias predefinidas
    @Override
    public String[] obtenerMaterias() throws RemoteException{
        return MATERIAS_PREDEFINIDAS;
    }

    @Override
    public boolean login(String matricula, String contrasena) {
        try (Connection conn = ConexionBD.conectar()) {
            // 1. Primero intentar con el sistema nuevo (hash + salt)
            PreparedStatement stmt = conn.prepareStatement(
                    "SELECT contrasena, salt FROM usuarios WHERE matricula = ?");
            stmt.setString(1, matricula);
            ResultSet rs = stmt.executeQuery();

            if (!rs.next()) return false;

            String storedHash = rs.getString("contrasena");
            String salt = rs.getString("salt");

            // 2. Si tiene salt, usar verificación con hash
            if (salt != null && !salt.isEmpty()) {
                return Hash.verifyPassword(contrasena, storedHash, salt);
            }
            // 3. Si no tiene salt, comparación directa (solo durante transición)
            else {
                return storedHash.equals(contrasena);
            }

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public String obtenerTipoUsuario(String matricula) {
        try (Connection conn = ConexionBD.conectar()) {
            PreparedStatement stmt = conn.prepareStatement("SELECT tipo FROM usuarios WHERE matricula = ?");
            stmt.setString(1, matricula);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) return rs.getString("tipo");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void agregarEstudiante(String nombre, String matricula) {
        //Validacion de los campos que no estén vacios
        if (nombre == null || nombre.trim().isEmpty() || matricula == null || matricula.trim().isEmpty()){
            throw new IllegalArgumentException("Campos Nombre y Matricula obligatorios");
        }

        try (Connection conn = ConexionBD.conectar()) {
            System.out.println("Conexion establecida correctamente");
            //Verificacion si existe matricula igual
            if (matriculaExiste(conn, matricula, "estudiantes")){
                throw new IllegalStateException("Esta matricula ya existe. Por favor, agrega una matricula diferente");
            }
            //Verificamos si la matricula existe en usuarios
            if (matriculaExiste(conn, matricula, "usuarios")){
                throw new IllegalStateException("Esta matricula ya existe. Por favor, agrega una matricula diferente");
            }
            //Generar el sal y hash para las contraseñas por defecto (cuando se crean lo usuarios)
            String salt = Hash.generarSalt();
            String contrasenaHash = Hash.hashPassword("abcd", salt);
            //Comenzar la transacción
            conn.setAutoCommit(false);
            try{
                //Insertar en la tabla estudiantes
                PreparedStatement stmt = conn.prepareStatement(
                        "INSERT INTO estudiantes(nombre, matricula) VALUES (?, ?)",
                        Statement.RETURN_GENERATED_KEYS);
                stmt.setString(1, nombre.trim());
                stmt.setString(2, matricula.trim());
                stmt.executeUpdate();
                //Insertar en la tabla usuarios
                PreparedStatement userStmt = conn.prepareStatement(
                        "\nINSERT INTO usuarios(nombre, matricula, contrasena, tipo, salt) VALUES (?, ?, ?, ?, ?)"
                );
                userStmt.setString(1, nombre.trim());
                userStmt.setString(2, matricula.trim());
                userStmt.setString(3, contrasenaHash);
                userStmt.setString(4, "estudiante");
                userStmt.setString(5, salt);
                userStmt.executeUpdate();
                conn.commit();//Verificar la transacción
            }catch (SQLException e){
                //Revertir la transacción en caso de algun error
                conn.rollback();
                throw new RuntimeException("Error al agregar estudiante: " + e.getMessage(), e);
            }
        } catch (SQLException e) {
            System.err.println("Error SQL:");
            e.printStackTrace();
            throw new RuntimeException("Error detallado: " + e.getMessage(), e);
        }
    }
    //Método auxiliar para verificar la matricula
    private boolean matriculaExiste(Connection conn, String matricula, String tabla) throws SQLException{
        String query = String.format("SELECT COUNT(*) AS count FROM %s WHERE matricula = ?", tabla);
        try (PreparedStatement stmt = conn.prepareStatement(query)){
            stmt.setString(1, matricula.trim());
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt("count") > 0;
            }
            return false;
        }
    }

    @Override
    public List<String> verNotas(String matricula) {
        List<String> notas = new ArrayList<>();
        try (Connection conn = ConexionBD.conectar()) {
            PreparedStatement stmt = conn.prepareStatement("SELECT n.materia, n.nota FROM notas n JOIN estudiantes e ON n.id_estudiante = e.id WHERE e.matricula = ?");
            stmt.setString(1, matricula);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                notas.add(rs.getString("materia") + ": " + rs.getDouble("nota"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return notas;
    }

    @Override
    public void modificarNota(String matricula, String materiaActual, String nuevaMateria, double nuevaNota) {
        try (Connection conn = ConexionBD.conectar()) {
            PreparedStatement getIdStmt = conn.prepareStatement("SELECT id FROM estudiantes WHERE matricula = ?");
            getIdStmt.setString(1, matricula);
            ResultSet rs = getIdStmt.executeQuery();
            if (rs.next()) {
                int idEstudiante = rs.getInt("id");
                PreparedStatement updateStmt = conn.prepareStatement("UPDATE notas SET materia = ?, nota = ? WHERE id_estudiante = ? AND materia = ?");
                updateStmt.setString(1, nuevaMateria);
                updateStmt.setDouble(2, nuevaNota);
                updateStmt.setInt(3, idEstudiante);
                updateStmt.setString(4, materiaActual);
                updateStmt.executeUpdate();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void eliminarMateria(String matricula, String materia) throws RemoteException{
        try (Connection conn = ConexionBD.conectar()) {
            //Verifica si el estudiante existe
            String verificarEst = "SELECT id FROM estudiantes WHERE matricula = ?";
            PreparedStatement verificarEstStmt = conn.prepareStatement(verificarEst);
            verificarEstStmt.setString(1, matricula);
            ResultSet rsEst = verificarEstStmt.executeQuery();

            if (!rsEst.next()) {
                throw new RemoteException("Estudiante no encontrado");
            }
            int idEstudiante = rsEst.getInt("id");

            //Verificamos si existe la nota a eliminar
            String verificarNota = "SELECT COUNT(*) FROM notas WHERE id_estudiante = ? AND materia = ?";
            PreparedStatement verificarNotaStmt = conn.prepareStatement(verificarNota);
            verificarNotaStmt.setInt(1, idEstudiante);
            verificarNotaStmt.setString(2, materia);
            ResultSet rsNota = verificarNotaStmt.executeQuery();

            if (rsNota.next() && rsNota.getInt(1) == 0){
                throw new RemoteException("No existe una nota registrada para esta materia");
            }

            //Eliminar nota en caso de no cumplir con las excepciones
            String deleteSql = "DELETE FROM notas WHERE id_estudiante = ? AND materia = ?";
            try (PreparedStatement deleteStmt = conn.prepareStatement(deleteSql)){
                deleteStmt.setInt(1, idEstudiante);
                deleteStmt.setString(2, materia);
                int columnAfectada = deleteStmt.executeUpdate();
                if (columnAfectada == 0){
                    throw new RemoteException("No se pudo eliminar la nota");
                }
            }
        } catch (SQLException e) {
            throw new RemoteException("Error en la base de datos: " + e.getMessage());
        }
    }
    @Override
    public List<String > obtenerEstudiantesConNotas() throws RemoteException{
        List<String> resultados = new ArrayList<>();
        try(Connection conn = ConexionBD.conectar()){
            String sql = "SELECT e.matricula, e.nombre, n.materia, n.nota " +
                    "FROM estudiantes e JOIN notas n ON e.id = n.id_estudiante " +
                    "ORDER BY e.nombre, n.materia";
            try (PreparedStatement stmt = conn.prepareStatement(sql)){
                ResultSet rs = stmt.executeQuery();
                while (rs.next()){
                    String line = String.format(
                            "%s - %s - %s - %.1f",
                            rs.getString("matricula"),
                            rs.getString("nombre"),
                            rs.getString("materia"),
                            rs.getDouble("nota"));
                    resultados.add(line);
                }
            }
        }catch (SQLException e){
            throw new RemoteException("Error al obtener estudiantes con notas: " + e.getMessage());
        }
        return resultados;
    }

    @Override
    public void modificarAlumno(String matriculaActual, String nuevoNombre, String nuevaMatricula) {
        try (Connection conn = ConexionBD.conectar()) {
            PreparedStatement updateEstudiante = conn.prepareStatement("UPDATE estudiantes SET nombre = ?, matricula = ? WHERE matricula = ?");
            updateEstudiante.setString(1, nuevoNombre);
            updateEstudiante.setString(2, nuevaMatricula);
            updateEstudiante.setString(3, matriculaActual);
            updateEstudiante.executeUpdate();

            PreparedStatement updateUsuario = conn.prepareStatement("UPDATE usuarios SET nombre = ?, matricula = ? WHERE matricula = ?");
            updateUsuario.setString(1, nuevoNombre);
            updateUsuario.setString(2, nuevaMatricula);
            updateUsuario.setString(3, matriculaActual);
            updateUsuario.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void registrarNota(String matricula, String materia, double nota) throws RemoteException {
        //Validemos la nota, para evitar problemas con el  numero de nota
        if (nota < 0 || nota > 10){
            throw new RemoteException("La nota debe estar entre 0.0 y 10.0");
        }
        //Validamos que la materia pertenezca a las predefinidas
        boolean materiaValida = false;
        for (String m : MATERIAS_PREDEFINIDAS){
            if (m.equalsIgnoreCase(materia)){
                materiaValida = true;
                break;
            }
        }
        if (!materiaValida){
            throw new RemoteException("Materia no valida. Seleccione una de la lista");
        }


        try (Connection conn = ConexionBD.conectar()) {
            //Se realiza la consulta para buscar a los estudiantes en la base de datos
            String buscar = "SELECT id FROM estudiantes WHERE matricula = ?";
            PreparedStatement buscarStmt = conn.prepareStatement(buscar);
            buscarStmt.setString(1, matricula);
            ResultSet rs = buscarStmt.executeQuery();
            if (!rs.next()) {
                throw new RemoteException("Estudiante no encontrado");
            }
            int idEst = rs.getInt("id");
            //Verificar que ya existe una nota para la materia
            String verificar = "SELECT COUNT(*) FROM notas WHERE id_estudiante = ? AND materia = ?";
            PreparedStatement verificarStmt = conn.prepareStatement(verificar);
            verificarStmt.setInt(1, idEst);
            verificarStmt.setString(2, materia);
            ResultSet rsVerificar = verificarStmt.executeQuery();
            if (rsVerificar.next() && rsVerificar.getInt(1)>0){
                throw new RemoteException("Ya existe una nota registrata para esta materia.");
            }

            //Insertar la nueva nota, en caso contrario de que no extista la nota
            String insert = "INSERT INTO notas (id_estudiante, materia, nota) VALUES (?, ?, ?)";
            try (PreparedStatement stmt = conn.prepareStatement(insert)){
                stmt.setInt(1, idEst);
                stmt.setString(2, materia);
                stmt.setDouble(3, nota);
                stmt.executeUpdate();
            }
        } catch (SQLException e) {
            throw new RemoteException("Error en la base de datos: " + e.getMessage());
        }
    }
}