import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.rmi.Naming;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class panel_login extends JFrame {

    private JPanel LoginForm;
    private JPasswordField contrasena;
    private JTextField matricula;
    private JLabel label_contrasena, label_matricula;
    private JButton btn_login;
    private GestorNotas gestorNotas;

    public panel_login() {

        try {
            Registry registry = LocateRegistry.getRegistry("192.168.1.69", 1099);
            gestorNotas = (GestorNotas) registry.lookup("GestorNotas");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error al conectar con el servidor RMI", "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
            System.exit(1);
        }

        setTitle("Gestor de Notas RMI");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        //Color para el fondo del panel
        getContentPane().setBackground(new Color(223, 248, 235));
        setLayout(new BorderLayout());
        // Panel principal con márgenes
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBorder(BorderFactory.createEmptyBorder(50, 100, 50, 100));
        mainPanel.setBackground(new Color(58, 85, 120, 255)); //Cambiar el color el panel de fondo

        // Panel de formulario con GridBagLayout
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBackground(new Color(251, 254, 249)); //Fondo blanco para el formulario
        formPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(163, 247, 181)), // Borde azul
                BorderFactory.createEmptyBorder(20, 20, 20, 20)
        ));

        //Titulo del panel
        TitledBorder border = BorderFactory.createTitledBorder(
          BorderFactory.createEmptyBorder(),
                "Acceso al Sistema",
                TitledBorder.LEFT,
                TitledBorder.TOP,
                new Font("Italic", Font.BOLD, 32),
                //Color azul para el texto
                Color.BLACK
        );

        formPanel.setBorder(border);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(8, 8, 8, 8); // Espaciado interno

        // Configuración para labels
        Font labelFont = new Font("Arial", Font.BOLD, 14);
        Dimension labelSize = new Dimension(120, 30); // Tamaño fijo para labels

        // Configuración para campos de texto
        Font fieldFont = new Font("Arial", Font.PLAIN, 14);
        Dimension fieldSize = new Dimension(200, 35); // Tamaño fijo para campos

        //Color para los labels
        Color labelColor = new Color(209, 209, 209); //Gris claro

        //Etiqueta para indicar que se escribe la matrícula
        label_matricula = new JLabel("Matrícula:");
        label_matricula.setFont(labelFont);
        label_matricula.setPreferredSize(labelSize);
        label_matricula.setForeground(new Color(0, 0, 0, 255)); //Color de texto
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 0.3;
        formPanel.add(label_matricula, gbc);

        matricula = new JTextField();
        matricula.setFont(fieldFont);
        matricula.setPreferredSize(fieldSize);
        gbc.gridx = 1;
        gbc.weightx = 0.7;
        formPanel.add(matricula, gbc);

        //Etiqueta para indicar que se escribe la contraseña
        label_contrasena = new JLabel("Contraseña:");
        label_contrasena.setFont(labelFont);
        label_contrasena.setPreferredSize(labelSize);
        label_contrasena.setForeground(new Color(0,0,0, 255));
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weightx = 0.3;
        formPanel.add(label_contrasena, gbc);

        contrasena = new JPasswordField();
        contrasena.setFont(fieldFont);
        contrasena.setPreferredSize(fieldSize);
        contrasena.setBackground(Color.WHITE);
        contrasena.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(209, 209, 209)),
                BorderFactory.createEmptyBorder(5,5,5,5)
        ));
        gbc.gridx = 1;
        gbc.weightx = 0.7;
        formPanel.add(contrasena, gbc);

        //Botón para iniciar sesión ubicado en la fila 2
        btn_login = new JButton("Iniciar Sesión");
        btn_login.setFont(new Font("Arial", Font.BOLD, 14));
        btn_login.setPreferredSize(new Dimension(150, 40));
        //Estilo para botón Login
        btn_login.setBackground(new Color(37, 67, 135)); //Fondo azul
        btn_login.setForeground(Color.WHITE); //Texto blanco
        btn_login.setBorder(BorderFactory.createEmptyBorder(5,15,5,15));
        btn_login.setFocusPainted(false); //Elimina el borde de enfoque
        //Acción al botón de Login
        btn_login.addActionListener(e -> {
            String mat = matricula.getText().trim();
            String pass = new String(contrasena.getPassword()).trim();

            // Validación de campos vacíos
            if (mat.isEmpty() || pass.isEmpty()) {
                JOptionPane.showMessageDialog(
                        this,
                        "Matrícula y contraseña son obligatorios",
                        "Validación",
                        JOptionPane.WARNING_MESSAGE
                );
                return;
            }

            try {
                if (gestorNotas.login(mat, pass)) {
                    //Obtener el tipo de usuario
                    String tipo = gestorNotas.obtenerTipoUsuario(mat);
                    //Mensaje de bienvenida para el usuario
                    JOptionPane.showMessageDialog(this, "Bienvenido, tipo de usuario: " + tipo);
                    //Abrir el gestor
                    SwingUtilities.invokeLater(() ->{
                        new Gestor_GUI(gestorNotas, mat, tipo).setVisible(true);
                    });
                    dispose();

                } else {
                    JOptionPane.showMessageDialog(this, "Matrícula o contraseña incorrectos", "Login fallido", JOptionPane.ERROR_MESSAGE);
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error en la autenticación", "Error", JOptionPane.ERROR_MESSAGE);
                ex.printStackTrace();
            }
        });


        //Animacion del botón (Hover)
        btn_login.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btn_login.setBackground(new Color(37, 67, 135)); // Azul más claro
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btn_login.setBackground(new Color(70, 130, 180, 231)); // Volver al color original
            }
        });

        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        gbc.weightx = 1.0;
        gbc.fill = GridBagConstraints.NONE;
        formPanel.add(btn_login, gbc);

        // Añadir panel de formulario al centro
        mainPanel.add(formPanel, BorderLayout.CENTER);
        add(mainPanel, BorderLayout.CENTER);

        setMinimumSize(new Dimension(500, 400));
        pack();
        setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new panel_login());
        //Quitar la documenración en caso de que se tenga que migrar
        // contraseñas que sean en texto plano
        /*
        try{
            GestorNotasImpl gestor = new GestorNotasImpl();
            gestor.migrarContrasena();
            System.out.println("Migración completada exitosamente");
        }catch (Exception e){
            System.err.println("Error durante migración: " +  e.getMessage());
            e.printStackTrace();
        }
        */
    }

}