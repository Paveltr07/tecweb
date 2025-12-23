import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class SocketCliente4 {
    public static void main(String args[]) throws IOException {
        Socket socket4 = new Socket("localhost",6666);
        DataInputStream in = new DataInputStream(socket4.getInputStream());
        DataOutputStream out = new DataOutputStream(socket4.getOutputStream());
        out.writeUTF("Hola mundo desde el cliente 4");
        String mensaje = in.readUTF();
        System.out.println(mensaje);
        socket4.close();
    }
}
