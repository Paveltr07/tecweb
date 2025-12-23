import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class SocketCliente {
    public static void main(String args[]) throws IOException {
        Socket socket1 = new Socket("localhost",6666);
        DataInputStream in = new DataInputStream(socket1.getInputStream());
        DataOutputStream out = new DataOutputStream(socket1.getOutputStream());
        out.writeUTF("Hola mundo desde el cliente 1");
        String mensaje = in.readUTF();
        System.out.println(mensaje);
        socket1.close();
    }
}
