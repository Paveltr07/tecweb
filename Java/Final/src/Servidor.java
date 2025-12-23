import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class Servidor {
    public static void main(String[] args) {
        try {
            GestorNotas gestor = new GestorNotasImpl();
            System.setProperty("java.rmi.server.hostname", "172.31.10.175");
            Registry registry = LocateRegistry.createRegistry(1099);
            //System.setProperty("java.rmi.server.hostname", "192.168.1.69");

            registry.rebind("GestorNotas", gestor);
            System.out.println("Servidor RMI corriendo en puerto 1099...");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}