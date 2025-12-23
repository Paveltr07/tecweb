import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class GestorNotasImpl extends UnicastRemoteObject implements GestorNotas {

    public GestorNotasImpl() throws Exception {
        super();
    }

    @Override
    public boolean login(String matricula, String contrasena) {
        try (Connection conn = ConexionBD.conectar()) {
            PreparedStatement stmt = conn.prepareStatement("SELECT * FROM usuarios WHERE matricula = ? AND contrasena = ?");
            stmt.setString(1, matricula);
            stmt.setString(2, contrasena);
            ResultSet rs = stmt.executeQuery();
            return rs.next();
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
        try (Connection conn = ConexionBD.conectar()) {
            PreparedStatement stmt = conn.prepareStatement("INSERT INTO estudiantes(nombre, matricula) VALUES (?, ?)", Statement.RETURN_GENERATED_KEYS);
            stmt.setString(1, nombre);
            stmt.setString(2, matricula);
            stmt.executeUpdate();

            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                int idEstudiante = rs.getInt(1);
                PreparedStatement userStmt = conn.prepareStatement("INSERT INTO usuarios(nombre, matricula, contrasena, tipo) VALUES (?, ?, ?, ?)");
                userStmt.setString(1, nombre);
                userStmt.setString(2, matricula);
                userStmt.setString(3, "abcd");
                userStmt.setString(4, "estudiante");
                userStmt.executeUpdate();
            }
        } catch (Exception e) {
            e.printStackTrace();
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
    public void eliminarMateria(String matricula, String materia) {
        try (Connection conn = ConexionBD.conectar()) {
            PreparedStatement getIdStmt = conn.prepareStatement("SELECT id FROM estudiantes WHERE matricula = ?");
            getIdStmt.setString(1, matricula);
            ResultSet rs = getIdStmt.executeQuery();
            if (rs.next()) {
                int idEstudiante = rs.getInt("id");
                PreparedStatement deleteStmt = conn.prepareStatement("DELETE FROM notas WHERE id_estudiante = ? AND materia = ?");
                deleteStmt.setInt(1, idEstudiante);
                deleteStmt.setString(2, materia);
                deleteStmt.executeUpdate();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
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
        try (Connection conn = ConexionBD.conectar()) {
            String buscar = "SELECT id FROM estudiantes WHERE matricula = ?";
            PreparedStatement buscarStmt = conn.prepareStatement(buscar);
            buscarStmt.setString(1, matricula);
            ResultSet rs = buscarStmt.executeQuery();
            if (rs.next()) {
                int idEst = rs.getInt("id");
                String insertar = "INSERT INTO notas (id_estudiante, materia, nota) VALUES (?, ?, ?)";
                PreparedStatement stmt = conn.prepareStatement(insertar);
                stmt.setInt(1, idEst);
                stmt.setString(2, materia);
                stmt.setDouble(3, nota);
                stmt.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}