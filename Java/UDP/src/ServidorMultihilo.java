import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ServidorMultihilo {
    public static void main(String[] args){
        final int port = 4666;
        final byte[] buffer = new byte[256];

        try {
            System.out.println("Servidor UDP MULTIHILO listo");
            DatagramSocket socketUDP = new DatagramSocket(port);

            while (true) {
                DatagramPacket paqueterecibido = new DatagramPacket(buffer, buffer.length);
                socketUDP.receive(paqueterecibido);

                // Nuevo hilo
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        System.out.println("Informacion recibida del cliente");
                        System.out.println(new String(paqueterecibido.getData(), 0, paqueterecibido.getLength()));

                        try {
                            Thread.sleep(7000); // 7 segundos de delay
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }

                        InetAddress direccion = paqueterecibido.getAddress();
                        int puertoCliente = paqueterecibido.getPort();

                        String mensaje = "¡Hola mundo desde el servidor!";
                        byte[] sendbuffer = mensaje.getBytes();

                        DatagramPacket respuesta = new DatagramPacket(sendbuffer, sendbuffer.length, direccion, puertoCliente);

                        System.out.println("Envio la informacion del cliente");
                        try {
                            socketUDP.send(respuesta);
                        } catch (IOException e) {
                            Logger.getLogger(ServidorMultihilo.class.getName()).log(Level.SEVERE, null, e);
                        }
                    }
                }).start();
            }
        } catch (SocketException ex) {
            Logger.getLogger(Servidor.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Servidor.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}