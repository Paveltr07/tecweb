import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class SocketCliente3 {
    public static void main(String args[]) throws IOException {
        Socket socket = new Socket("localhost", 6666);
        PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
        BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        BufferedReader teclado = new BufferedReader(new InputStreamReader(System.in));

        System.out.println("Cliente 3 conectado al servidor");

        String mensaje;
        while (true) {
            // Leer mensaje desde el teclado
            System.out.print("### ");
            mensaje = teclado.readLine();

            if (mensaje.equalsIgnoreCase("salir")) {
                out.println("Cliente 3 desconectado.");
                break;
            }

            out.println(mensaje);
            String respuesta = in.readLine();
            if (respuesta != null) {
                System.out.println(" " + respuesta);
            }
        }

        socket.close();
        System.out.println("Cliente 3 desconectado.");
    }
}