import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface GestorNotas extends Remote {
    boolean login(String matricula, String contrasena) throws Exception;
    String obtenerTipoUsuario(String matricula) throws Exception;
    void agregarEstudiante(String nombre, String matricula) throws Exception;
    List<String> verNotas(String matricula) throws Exception;
    void modificarNota(String matricula, String materiaActual, String nuevaMateria, double nuevaNota) throws Exception;
    void eliminarMateria(String matricula, String materia) throws Exception;
    void modificarAlumno(String matriculaActual, String nuevoNombre, String nuevaMatricula) throws Exception;
    void registrarNota(String matricula, String materia, double nota) throws RemoteException;
}