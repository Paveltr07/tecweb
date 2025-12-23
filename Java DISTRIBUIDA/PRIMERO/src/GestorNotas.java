import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface GestorNotas extends Remote {
    boolean login(String matricula, String contrasena) throws RemoteException;
    String obtenerTipoUsuario(String matricula) throws RemoteException;
    void agregarEstudiante(String nombre, String matricula) throws RemoteException;
    List<String> verNotas(String matricula) throws RemoteException;
    void modificarNota(String matricula, String materiaActual, String nuevaMateria, double nuevaNota) throws RemoteException;
    void eliminarMateria(String matricula, String materia) throws RemoteException;
    void modificarAlumno(String matriculaActual, String nuevoNombre, String nuevaMatricula) throws RemoteException;
    void registrarNota(String matricula, String materia, double nota) throws RemoteException;
    String[] obtenerMaterias() throws RemoteException;
    List<String> obtenerEstudiantesConNotas()throws RemoteException;

}
