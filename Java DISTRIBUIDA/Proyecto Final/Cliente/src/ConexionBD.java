import java.sql.Connection;
import java.sql.DriverManager;

public class ConexionBD {
    public static Connection conectar() {
        try {
            String url = "jdbc:mysql://192.168.1.69:3306/gestor_notas";
            String user = "pavel";
            String password = "1234";
            return DriverManager.getConnection(url, user, password);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}