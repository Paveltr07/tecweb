import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Cliente5 {
    public static void main(String[] args){
        //puerto del servidor
        final int PUERTO_SERVIDOR = 4666;

        //buffer donde se alamacen los mensajes
        byte[] buffer = new byte[256];

        try{
            InetAddress direccionServidor = InetAddress.getByName("localhost");
            DatagramSocket socketUDP = new DatagramSocket();

            Thread.sleep(3000);

            String mensaje = "*¡Hola mundo desde el cliente 5!*";
            buffer = mensaje.getBytes();

            DatagramPacket pregunta = new DatagramPacket(buffer,buffer.length, direccionServidor,PUERTO_SERVIDOR);
            System.out.println("Cliente 5 Envio el datagrama");
            socketUDP.send(pregunta);

            DatagramPacket paqueterecibido = new DatagramPacket(buffer, buffer.length);
            socketUDP.receive(paqueterecibido);
            System.out.println("Cliente 5 Recibe la peticion");

            //Linea del profe
            //mensaje = new String(paqueterecibido.getData());
            mensaje = new String(paqueterecibido.getData(), 0, paqueterecibido.getLength());
            System.out.println(mensaje);

            socketUDP.close();

        } catch (UnknownHostException ex) {
            Logger.getLogger(Cliente.class.getName()).log(Level.SEVERE,null,ex);
        } catch (SocketException ex) {
            Logger.getLogger(Servidor.class.getName()).log(Level.SEVERE,null,ex);
        } catch (IOException ex) {
            Logger.getLogger(Cliente.class.getName()).log(Level.SEVERE,null,ex);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
