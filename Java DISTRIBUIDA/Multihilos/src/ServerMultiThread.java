import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import static java.lang.System.*;

public class ServerMultiThread {
    public static void main(String[] args) {
        ServerSocket serverSocket;
        try {
            serverSocket = new ServerSocket(6666);
            out.println("Servidor iniciado");

            while (true) {
                Socket clientSocket = serverSocket.accept();
                out.println("Cliente conectado: " + clientSocket.getInetAddress());
                new Handler(clientSocket).start();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}