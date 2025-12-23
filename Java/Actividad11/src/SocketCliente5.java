import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class SocketCliente5 {
    public static void main(String args[]) throws IOException {
        Socket socket5 = new Socket("localhost",6666);
        DataInputStream in = new DataInputStream(socket5.getInputStream());
        DataOutputStream out = new DataOutputStream(socket5.getOutputStream());
        out.writeUTF("Hola mundo desde el cliente 5");
        String mensaje = in.readUTF();
        System.out.println(mensaje);
        socket5.close();
    }
}
