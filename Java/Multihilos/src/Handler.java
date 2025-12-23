import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Handler extends Thread {
    private final Socket socket;

    // Constructor
    public Handler(Socket socket) {
        super();
        this.socket = socket;
    }

    @Override
    public void run() {
        BufferedReader reader;  // Lectura desde el socket
        PrintWriter writer;     // Escritura hacia el socket
        BufferedReader teclado = new BufferedReader(new InputStreamReader(System.in));

        try {
            reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            writer = new PrintWriter(socket.getOutputStream(), true);

            String line;

            while ((line = reader.readLine()) != null) {
                System.out.println(" " + line);

                if ("hola".equalsIgnoreCase(line)) {
                    writer.println("Hola cliente");
                }

                else if("adios".equalsIgnoreCase(line)){
                    writer.println("Adios cliente");
                }

                else if (line.equalsIgnoreCase("salir")) {
                    writer.println("Cliente desconectado.");
                    break;
                } else {
                    writer.println(" " + line);
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (socket != null && !socket.isClosed()) {
                    socket.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}