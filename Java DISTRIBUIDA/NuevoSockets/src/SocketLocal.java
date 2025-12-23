import java.net.SocketException;
import java.net.*;

public class SocketLocal {
    public static void main(String args[]) throws SocketException{
        InetSocketAddress adresse = new InetSocketAddress("localhost",5555);
        System.out.println(adresse.getPort());
    }
}
