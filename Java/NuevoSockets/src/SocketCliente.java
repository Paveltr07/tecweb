import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class SocketCliente {
    public static void main(String args[]) throws IOException {
        Socket socket2 = new Socket("localhost",6666);
        DataInputStream in = new DataInputStream(socket2.getInputStream());
        DataOutputStream out = new DataOutputStream(socket2.getOutputStream());
        out.writeUTF("Hola mundo desde el cliente");
        String mensaje = in.readUTF();
        System.out.println(mensaje);
        socket2.close();
    }
}
