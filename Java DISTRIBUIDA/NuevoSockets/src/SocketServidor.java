import java.io.DataInputStream;
import java.io.DataOutput;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.SocketException;
import java.net.*;

public class SocketServidor {
    public static void main(String args[]) throws IOException {
        ServerSocket serverSocket = new ServerSocket(6666);
        System.out.println("Servidor Listo");
        Socket socketl;

        while(true){
            socketl = serverSocket.accept(); //Espera bloqueada
            DataInputStream in = new DataInputStream(socketl.getInputStream());
            DataOutput out = new DataOutputStream(socketl.getOutputStream());

            String mensaje = in.readUTF();
            System.out.println(mensaje);

            out.writeUTF("Hola desde el lado del servidor");
            socketl.close();
            System.out.println("Cliente conectado");
        }
    }
}
