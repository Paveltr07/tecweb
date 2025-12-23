import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class SocketCliente6 {
    public static void main(String args[]) throws IOException {
        Socket socket6 = new Socket("localhost",6666);
        DataInputStream in = new DataInputStream(socket6.getInputStream());
        DataOutputStream out = new DataOutputStream(socket6.getOutputStream());
        out.writeUTF("Hola mundo desde el cliente 6");
        String mensaje = in.readUTF();
        System.out.println(mensaje);
        socket6.close();
    }
}
