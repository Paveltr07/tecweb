import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class SocketCliente3 {
    public static void main(String args[]) throws IOException {
        Socket socket3 = new Socket("localhost",6666);
        DataInputStream in = new DataInputStream(socket3.getInputStream());
        DataOutputStream out = new DataOutputStream(socket3.getOutputStream());
        out.writeUTF("Hola mundo desde el cliente 3");
        String mensaje = in.readUTF();
        System.out.println(mensaje);
        socket3.close();
    }
}
