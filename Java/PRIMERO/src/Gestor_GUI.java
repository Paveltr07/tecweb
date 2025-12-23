import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.table.DefaultTableModel;
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
        //En esta sección del código, se introducen las funciones que corresponden a cada acción del panel de actividades
        //PRIMERO: se deben clasificar las acciones de profesor y alumno, para que solo puedan ser permitidas según el ROL
        //Panel destinado para los botones
        //Se implementa GridLayout para los iconos
        JPanel buttonPanel = new JPanel(new GridLayout(2, 3, 20, 20));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));
        buttonPanel.setBackground(new Color(240, 240, 240));

        //Solo para profesores
        //Acciones que únicamente son para el ROL DE PROFESOR
        if (tipoUsuario.equalsIgnoreCase("profesor")) {
            crearBtn(buttonPanel, "Registrar Notas", "registrar.png");
            crearBtn(buttonPanel, "Modificar Notas", "modificar.png");
            crearBtn(buttonPanel, "Eliminar Notas", "eliminar.png");
            crearBtn(buttonPanel, "Gestionar Alumnos", "alumnos.png");
            crearBtn(buttonPanel, "Agregar Alumnos", "addAlumn.png");
        }

        //Solo para alumnos
        //Acciones que únicamente son para el ROL DE ALUMNO
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
    //Permite el modelado de los botones, así como sus iconos, descripción de la acción y asiganr la función
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
    //ACCIONES DEL SISTEMA
    //En esta parte del código, se crean todas las funciones que se implementan en el sistema, cada una de ellas se le asigna a un botón
    //
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
                    //Acción del sistema para eliminar las notas con las excepciones correspondientes en caso de error
                case "Eliminar Notas":
                    try{
                        //Se solicita la lista de estudiantes que cuenten con notas en el sistema
                        List<String> estudiantesConNotas = gestorNotas.obtenerEstudiantesConNotas();
                        if (estudiantesConNotas.isEmpty()){
                            JOptionPane.showMessageDialog(
                                    this,
                                    "No se encontraron notas registradas",
                                    "Información",
                                    JOptionPane.INFORMATION_MESSAGE
                            );
                            break;
                        }
                        //Modelado de la tabla que contendrá la información
                        String[] columnNombre = {"Matrícula", "Nombre", "Materia", "Nota"};
                        DefaultTableModel model = new DefaultTableModel(columnNombre, 0) {
                            @Override
                            public boolean isCellEditable(int row, int column) {
                                return false;
                            }
                        };

                        //Llenado de la tabla con la información
                        for (String registro : estudiantesConNotas){
                            String[] partes = registro.split(" - ");
                            if (partes.length >= 4){
                                model.addRow(new Object[]{
                                        partes[0].trim(),
                                        partes[1].trim(),
                                        partes[2].trim(),
                                        partes[3].trim()
                                });
                            }
                        }

                        //Creación de la tabla y el estilo
                        JTable tableNotas = new JTable(model);
                        tableNotas.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
                        tableNotas.setFont(new Font("Arial", Font.PLAIN, 14));
                        tableNotas.getTableHeader().setFont(new Font("Arial", Font.BOLD, 14));
                        tableNotas.setRowHeight(25);

                        //Configuración de los paneles
                        //PANEL PRINCIPAL
                        JPanel panelPrincipal = new JPanel(new BorderLayout());
                        panelPrincipal.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

                        //PANEL SECUNDARIO
                        //INSTRUCCIONES
                        JPanel panelSuperior = new JPanel(new FlowLayout(FlowLayout.LEFT)); //Posiciona los componentes de la fila
                        panelSuperior.add(new JLabel("Seleccione la nota que desea eliminar:"));
                        panelPrincipal.add(panelSuperior, BorderLayout.NORTH);

                        //Implementación del Scroll
                        JScrollPane scrollPane = new JScrollPane(tableNotas);
                        panelPrincipal.add(scrollPane, BorderLayout.CENTER);


                        //Mostrar el dialogo para la selección
                        //Dialogo de confirmación para tener control de las acciones del usuario
                        int confirm =JOptionPane.showConfirmDialog(
                                this,
                                panelPrincipal,
                                "Eliminar Nota",
                                JOptionPane.OK_CANCEL_OPTION,
                                JOptionPane.PLAIN_MESSAGE
                        );

                        //Se procesa la seleccion del usuario
                        if (confirm == JOptionPane.OK_OPTION){
                            int filaSeleccionada = tableNotas.getSelectedRow();
                            if (filaSeleccionada == -1){
                                JOptionPane.showMessageDialog(
                                        this,
                                        "Debe seleccionar una nota para eliminar",
                                        "Advertencia",
                                        JOptionPane.WARNING_MESSAGE
                                );
                                return;
                            }
                            //Se obtienen los valores que solicita a la base de datos
                            String matricula = model.getValueAt(filaSeleccionada, 0).toString().trim();  // Columna 0 = Matrícula
                            String nombre = model.getValueAt(filaSeleccionada, 1).toString().trim();     // Columna 1 = Nombre
                            String materia = model.getValueAt(filaSeleccionada, 2).toString().trim();    // Columna 2 = Materia
                            String nota = model.getValueAt(filaSeleccionada, 3).toString().trim();      //Columna 3 = Nota

                            // DEBUG > Verificar que los datos sean los correctos
                            System.out.println("Datos a eliminar:");
                            System.out.println("Matrícula: '" + matricula + "'");
                            System.out.println("Nombre: '" + nombre + "'");
                            System.out.println("Materia: '" + materia + "'");
                            System.out.println("Nota: '" + nota + "'");

                            //Verificamos los datos a eliminar
                            System.out.println("Intentando eliminar - Matrícula: '" + matricula + "', Materia: '" + materia + "'");

                            //Dialogo para Confirmación final del usuario
                            int confirmacion = JOptionPane.showConfirmDialog(
                                    this,
                                    "¿Estás seguro de eliminar la nota de " + materia +
                                            " del estudiante " + nombre + "?",
                                    "Confirmar Eliminación",
                                    JOptionPane.YES_NO_OPTION,
                                    JOptionPane.WARNING_MESSAGE
                            );
                            if (confirmacion == JOptionPane.YES_OPTION){
                                gestorNotas.eliminarMateria(matricula, materia); //Valores que se pasan para procesar
                                JOptionPane.showMessageDialog(
                                        this,
                                        "Nota eliminada exitosamente",
                                        "Éxito",
                                        JOptionPane.INFORMATION_MESSAGE
                                );
                                estudiantesConNotas = gestorNotas.obtenerEstudiantesConNotas();
                                model.setRowCount(0); //Limpia de la tabla
                                for (String registro : estudiantesConNotas){
                                    String[] partes = registro.split(" - ");
                                    if (partes.length >= 4){
                                        model.addRow(new Object[]{
                                                partes[0].trim(),
                                                partes[1].trim(),
                                                partes[2].trim(),
                                                partes[3].trim(),

                                        });
                                    }
                                }
                            }
                        }
                        /*
                        //Crea el diálogo de selección
                        JComboBox<String> comboNotas = new JComboBox<>(estudiantesConNotas.toArray(new String[0]));
                        comboNotas.setPreferredSize(new Dimension(300,30)); //Se asigna un tamaño

                        //Se ajusta el panel con su borde
                        JPanel panel = new JPanel(new BorderLayout());
                        panel.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));

                        //Se crea una etiqueta para el mensaje de instrucción para el usuario
                        JLabel lblInstr = new JLabel("Seleccione la nota a eliminar: ");
                        panel.add(lblInstr, BorderLayout.NORTH);//Ubicación de los paneles norte y centro
                        panel.add(lblInstr, BorderLayout.CENTER);
                        */
                    }catch(RemoteException e){
                        JOptionPane.showMessageDialog(
                                this,
                                "Error con la conexión con el servidor: " + e.getMessage(),
                                "Error",
                                JOptionPane.ERROR_MESSAGE
                        );
                        e.printStackTrace();
                    }catch (Exception e){
                        JOptionPane.showMessageDialog(
                                this,
                                "Error inesperado: " + e.getMessage(),
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
