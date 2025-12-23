import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.List;
import java.util.Scanner;

public class Cliente {
    public static void main(String[] args) {
        try {
            Registry registry = LocateRegistry.getRegistry("192.168.1.72", 1099);
            GestorNotas gestor = (GestorNotas) registry.lookup("GestorNotas");

            Scanner sc = new Scanner(System.in);
            System.out.print("Matrícula: ");
            String matricula = sc.nextLine();
            System.out.print("Contraseña: ");
            String contrasena = sc.nextLine();

            if (gestor.login(matricula, contrasena)) {
                String tipo = gestor.obtenerTipoUsuario(matricula);
                System.out.println("Bienvenido, " + tipo);
                if (tipo.equals("estudiante")) {
                    List<String> notas = gestor.verNotas(matricula);
                    System.out.println("Tus notas:");
                    for (String n : notas) System.out.println(n);
                } else if (tipo.equals("profesor")) {
                    while (true) {
                        System.out.println("""
                                1. Agregar estudiante
                                2. Ver notas de estudiante
                                3. Modificar nota
                                4. Eliminar materia
                                5. Modificar alumno
                                6. Registrar nota
                                7. Salir""");

                        int op = Integer.parseInt(sc.nextLine());
                        switch (op) {
                            case 1 -> {
                                System.out.print("Nombre: ");
                                String nombre = sc.nextLine();
                                System.out.print("Matrícula: ");
                                String mat = sc.nextLine();
                                gestor.agregarEstudiante(nombre, mat);
                            }
                            case 2 -> {
                                System.out.print("Matrícula: ");
                                String mat = sc.nextLine();
                                List<String> notas = gestor.verNotas(mat);
                                for (String n : notas) System.out.println(n);
                            }
                            case 3 -> {
                                System.out.print("Matrícula: ");
                                String mat = sc.nextLine();
                                System.out.print("Materia actual: ");
                                String matActual = sc.nextLine();
                                System.out.print("Nueva materia: ");
                                String nuevaMat = sc.nextLine();
                                System.out.print("Nueva nota: ");
                                double nota = Double.parseDouble(sc.nextLine());
                                gestor.modificarNota(mat, matActual, nuevaMat, nota);
                            }
                            case 4 -> {
                                System.out.print("Matrícula: ");
                                String mat = sc.nextLine();
                                System.out.print("Materia a eliminar: ");
                                String matDel = sc.nextLine();
                                gestor.eliminarMateria(mat, matDel);
                            }
                            case 5 -> {
                                System.out.print("Matrícula actual: ");
                                String mat = sc.nextLine();
                                System.out.print("Nuevo nombre: ");
                                String nuevoNombre = sc.nextLine();
                                System.out.print("Nueva matrícula: ");
                                String nuevaMat = sc.nextLine();
                                gestor.modificarAlumno(mat, nuevoNombre, nuevaMat);
                            }

                            case 6 -> {
                                System.out.print("Matrícula estudiante: ");
                                String mat = sc.nextLine();
                                System.out.print("Materia: ");
                                String materia = sc.nextLine();
                                System.out.print("Nota: ");
                                double nota = Double.parseDouble(sc.nextLine());
                                gestor.registrarNota(mat, materia, nota);
                                System.out.println("Nota registrada.");
                            }

                            case 7 -> {
                                System.out.println("Saliendo...");
                                return;
                            }
                        }
                    }
                }
            } else {
                System.out.println("Credenciales incorrectas");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}