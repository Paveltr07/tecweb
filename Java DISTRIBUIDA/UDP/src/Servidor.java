import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Servidor {
    public static void main(String[] args){
        final int port = 4666;
        byte[] buffer = new byte[256];
        byte[] sendbuffer = null;

        try{
            System.out.println("Servidor UDP listo");
            DatagramSocket socketUDP = new DatagramSocket(port);

            while(true){
                DatagramPacket paqueterecibido = new DatagramPacket(buffer, buffer.length);
                socketUDP.receive(paqueterecibido);

                System.out.println("Informacion recibida del cliente");
                System.out.println(new String(paqueterecibido.getData(), 0, paqueterecibido.getLength()));

                Thread.sleep(7000); // 7 segundos de delay

                InetAddress direccion = paqueterecibido.getAddress();
                int puertoCliente = paqueterecibido.getPort();

                String mensaje = "¡Hola mundo desde el servidor!";
                sendbuffer = mensaje.getBytes();

                DatagramPacket respuesta = new DatagramPacket(sendbuffer, sendbuffer.length,direccion,puertoCliente);

                System.out.println("Envio la informacion del cliente");
                socketUDP.send(respuesta);

            }
        } catch (SocketException ex) {
            Logger.getLogger(Servidor.class.getName()).log(Level.SEVERE,null,ex);
        } catch (IOException ex) {
            Logger.getLogger(Servidor.class.getName()).log(Level.SEVERE,null,ex);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
