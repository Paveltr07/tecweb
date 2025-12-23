import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.rmi.RemoteException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import  java.net.URL;
public class Gestor_GUI extends JFrame {
    private final GestorNotas gestorNotas;
    private final String matricula;
    private final String tipoUsuario;

    public Gestor_GUI(GestorNotas gestorNotas, String matricula, String tipoUsuario){
        this.gestorNotas = gestorNotas;
        this.matricula = matricula;
        this.tipoUsuario = tipoUsuario;
        setTitle("Gestor de Notas RMI - " + tipoUsuario.toUpperCase());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(900,600);
        setLocationRelativeTo(null);

        initUI();
    }

    private void initUI(){
        //Panel principal con bordes y fondo
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20,20,20,20));
        mainPanel.setBackground(new Color(240,240,240));

        //Panel para la cabecera
        JPanel headerPanel = new JPanel();
        headerPanel.setBackground(new Color(70, 130, 180));
        JLabel lblBienvenido = new JLabel("Bienvenido " + matricula + " (" + tipoUsuario +")");
        lblBienvenido.setFont(new Font("Arial", Font.BOLD, 18));
        lblBienvenido.setForeground(Color.WHITE);
        headerPanel.add(lblBienvenido);

        //Panel destinado para los botones
        //Se implementa GridLayout para los iconos
        JPanel buttonPanel = new JPanel(new GridLayout(2, 3, 20, 20));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));
        buttonPanel.setBackground(new Color(240, 240, 240));

        //Solo para profesores
        if (tipoUsuario.equalsIgnoreCase("profesor")) {
            crearBtn(buttonPanel, "Registrar Notas", "registrar.png");
            crearBtn(buttonPanel, "Modificar Notas", "modificar.png");
            crearBtn(buttonPanel, "Eliminar Materias", "eliminar.png");
            crearBtn(buttonPanel, "Gestionar Alumnos", "alumnos.png");
            crearBtn(buttonPanel, "Agregar Alumnos", "addAlumn.png");
        }

        //Solo para alumnos
        if(tipoUsuario.equalsIgnoreCase("estudiante")){
            crearBtn(buttonPanel, "Consultar Notas", "notas.png");
            crearBtn(buttonPanel, "Configuración", "config.png");
        }

        //Panel inferior
        JPanel footerPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        footerPanel.setBackground(new Color(240, 240, 240));
        JButton btnLogout = new JButton("Cerrar Sesión");
        btnLogout.addActionListener(e -> cerrarSesion());
        footerPanel.add(btnLogout);

        //Ensamblado de la interfaz
        mainPanel.add(headerPanel,BorderLayout.NORTH);
        mainPanel.add(new JScrollPane(buttonPanel), BorderLayout.CENTER);
        mainPanel.add(footerPanel, BorderLayout.SOUTH);

        add(mainPanel);

    }
    private void crearBtn(JPanel panel, String texto, String iconPath){
        //Boton con una imagen
        ImageIcon icon = null;
        URL imgURL = getClass().getResource("/" + iconPath);
        if (imgURL != null){
            Image img = new ImageIcon(imgURL).getImage();
            //Escalar la imagen al tamaño deseado
            Image scaledImg = img.getScaledInstance(64, 64, Image.SCALE_SMOOTH);
            icon = new ImageIcon(scaledImg);
        }else {
            System.err.println("No se encontró la imagen: " + iconPath);
            icon = new ImageIcon(); //Sin imagen en caso de ocurrir el problema.
        }

        //Estilo para el boton
        JButton button = new JButton(texto, icon);
        button.setVerticalTextPosition(SwingConstants.BOTTOM);
        button.setHorizontalTextPosition(SwingConstants.CENTER);
        button.setFont(new Font("Arial", Font.PLAIN, 14));
        button.setPreferredSize(new Dimension(120, 140));
        button.setBackground(Color.WHITE);
        button.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(200,200,200)),
                BorderFactory.createEmptyBorder(10, 10, 10, 10)
        ));
        button.addActionListener(e -> manejarAccion(texto)); //Accion que se le asigna al boton

        panel.add(button);
    }

    private void manejarAccion(String accion){
        try{
            switch (accion){
                case "Consultar Notas": //Consultar las notas de los estudiantes
                    List<String> notas = gestorNotas.verNotas(matricula);
                    mostrarNotas(notas);
                    break;
                case "Agregar Alumnos": //Agregar alumnos con su matricula y contraseña
                    JTextField nombreField = new JTextField();
                    JTextField matriculaField = new JTextField();

                    Object[] message ={
                            "Nombre: ", nombreField,
                            "Matricula: ", matriculaField
                    };

                    int option = JOptionPane.showConfirmDialog(
                            this,
                            message,
                            "Agregar Nuevo Alumno",
                            JOptionPane.OK_CANCEL_OPTION,
                            JOptionPane.PLAIN_MESSAGE
                    );
                    if (option == JOptionPane.OK_OPTION) {
                        try {
                            String nombre = nombreField.getText().trim();
                            String matricula = matriculaField.getText().trim();

                            //VALIDACION DE CAMPOS VACIOS
                            if (nombre.isEmpty() || matricula.isEmpty()){
                                JOptionPane.showMessageDialog(
                                        this,
                                        "Todos los campos son obligatorios",
                                        "Error de validación",
                                        JOptionPane.ERROR_MESSAGE
                                );
                                return;
                            }
                            //Cuando se intenta agregar un estudiante
                            gestorNotas.agregarEstudiante(nombre, matricula);
                            JOptionPane.showMessageDialog(
                                    this,
                                    "Alumno registrado exitosamente\n" +
                                            "Matrícula: " + matricula + "\n" +
                                            "Contraseña temporal: abcd",
                                    "Registro exitoso",
                                    JOptionPane.INFORMATION_MESSAGE
                            );
                        }catch(IllegalArgumentException e){
                            JOptionPane.showMessageDialog(
                                    this,
                                    e.getMessage(),
                                    "Error de validación",
                                    JOptionPane.ERROR_MESSAGE
                            );
                        }catch (Exception e){
                            JOptionPane.showMessageDialog(
                                    this,
                                    "Error al registrar alumno: " + e.getMessage(),
                                    "Error del sistema",
                                    JOptionPane.ERROR_MESSAGE
                            );
                        }
                    }
                    break;
                case "Registrar Notas": //Asignar las notas, desplega la lista de alumnos inscritos y se asigna la materia y calificacion
                    try{
                        //Obtener materias predefinidas del servidor
                        String[] materias = gestorNotas.obtenerMaterias();
                        List<String> estudiantes = obtenerListaEstudiantes();
                        if (estudiantes.isEmpty()){
                            JOptionPane.showMessageDialog(
                                    this,
                                    "No se han encontrado materias disponibles",
                                    "Error",
                                    JOptionPane.ERROR_MESSAGE
                            );
                            break;
                        }
                        JComboBox<String> comboEstudiantes = new JComboBox<>(estudiantes.toArray(new String[0]));
                        JComboBox<String> comboMaterias = new JComboBox<>(materias);
                        comboMaterias.setEditable(false);
                        JTextField campoNota = new JTextField();


                        try(Connection conn = ConexionBD.conectar()){
                            String sql = "SELECT matricula, nombre FROM estudiantes ORDER BY nombre";
                            PreparedStatement stmt = conn.prepareStatement(sql);
                            ResultSet rs = stmt.executeQuery();
                            while (rs.next()){
                                estudiantes.add(rs.getString("Matricula") + " - " + rs.getString("nombre"));
                            }
                        }
                        if (estudiantes.isEmpty()){
                            JOptionPane.showMessageDialog(
                                    this,
                                    "No hay estudiantes registrados",
                                    "Advertencia",
                                    JOptionPane.WARNING_MESSAGE
                            );
                            break;
                        }

                        JPanel panel = new JPanel(new GridLayout(0, 1));
                        panel.add(new JLabel("Estudiantes:"));
                        panel.add(comboEstudiantes);
                        panel.add(new JLabel("Materia:"));
                        panel.add(comboMaterias);
                        panel.add(new JLabel("Nota (0.0-10.0):"));
                        panel.add(campoNota);

                        int opcion = JOptionPane.showConfirmDialog(
                          this,
                                panel,
                                "Registrar Nueva Nota",
                                JOptionPane.OK_CANCEL_OPTION,
                                JOptionPane.PLAIN_MESSAGE
                        );
                        if (opcion == JOptionPane.OK_OPTION){
                            String seleccion = (String) comboEstudiantes.getSelectedItem();
                            String matricula = seleccion.split(" - ")[0];
                            String materia = (String) comboMaterias.getSelectedItem();
                            double nota = Double.parseDouble(campoNota.getText().trim());

                            if(materia.isEmpty()){
                                JOptionPane.showMessageDialog(
                                        this,
                                        "La materia es obligatoria",
                                        "Error de validación",
                                        JOptionPane.ERROR_MESSAGE
                                );
                                return;
                            }
                            if (nota < 0 || nota > 10){
                                JOptionPane.showMessageDialog(
                                        this,
                                        "La nota debe estar entre 0.0 y 10.0",
                                        "Error de validación",
                                        JOptionPane.ERROR_MESSAGE
                                );
                                return;
                            }
                            gestorNotas.registrarNota(matricula, materia, nota);
                            JOptionPane.showMessageDialog(
                                    this,
                                    "Nota registrada exitosamente",
                                    "Éxito",
                                    JOptionPane.INFORMATION_MESSAGE
                            );
                        }
                    }catch (NumberFormatException e){
                        JOptionPane.showMessageDialog(
                                this,
                                "La nota debe ser un número válido",
                                "Error",
                                JOptionPane.ERROR_MESSAGE
                        );
                    }catch (RemoteException e){
                        JOptionPane.showMessageDialog(
                                this,
                                "Error con la conexión con el servidor: " + e.getMessage(),
                                "Error",
                                JOptionPane.ERROR_MESSAGE
                        );
                    }catch (SQLException e){
                        JOptionPane.showMessageDialog(
                                this,
                                "Error en la conexion a la base de datos : " + e.getMessage(),
                                "Error",
                                JOptionPane.ERROR_MESSAGE
                        );
                        e.printStackTrace();
                    }catch (Exception e){
                        JOptionPane.showMessageDialog(
                                this,
                                "Error Inesperado: " + e.getMessage(),
                                "Error",
                                JOptionPane.ERROR_MESSAGE
                        );
                        e.printStackTrace();
                    }
                    break;
            }
        }catch(RemoteException exception){
            JOptionPane.showMessageDialog(
                    this,
                    "Error de conexion: " + exception.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    //Obtener lista de estudiantes
    private List<String> obtenerListaEstudiantes() throws RemoteException{
        List<String> estudiantes = new ArrayList<>();
        try(Connection conn = ConexionBD.conectar()){
            String sql = "SELECT matricula, nombre FROM estudiantes ORDER BY nombre";
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()){
                String matricula = rs.getString("matricula");
                String nombre = rs.getString("nombre");
                estudiantes.add(matricula + " - " + nombre);
            }
        }catch (SQLException e){
            throw  new RemoteException("Error al obtener estudiantes" + e.getMessage());
        }
        return estudiantes;
    }

    private void mostrarNotas(List<String> notas){
        //Visualización de las notas
        if (notas == null || notas.isEmpty()) {
            JOptionPane.showMessageDialog(
                    this,
                    "No se encontraron notas registradas",
                    "Información",
                    JOptionPane.INFORMATION_MESSAGE
            );
            return;
        }

        // Crear un área de texto para mostrar las notas
        JTextArea textArea = new JTextArea(10, 30);
        textArea.setEditable(false);

        // Agregar todas las notas al área de texto
        for (String nota : notas) {
            textArea.append(nota + "\n");
        }

        // Mostrar en un JScrollPane dentro de un JOptionPane
        JScrollPane scrollPane = new JScrollPane(textArea);
        JOptionPane.showMessageDialog(
                this,
                scrollPane,
                "Notas del Alumno",
                JOptionPane.PLAIN_MESSAGE
        );
    }

    private void cerrarSesion(){
        this.dispose();
        new panel_login().setVisible(true);
    }
}
