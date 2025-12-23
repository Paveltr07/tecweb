import java.sql.Connection;
import java.sql.DriverManager;

public class ConexionBD {
    public static Connection conectar() {
        try {
            String url = "jdbc:mysql://192.168.1.72:3306/gestor_notas";
            String user = "root";
            String password = "";
            return DriverManager.getConnection(url, user, password);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}