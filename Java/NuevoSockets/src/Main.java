import java.net.InetAddress;
import java.net.UnknownHostException;
import java.net.*;

public class Main {
    public static void main(String[] args) throws UnknownHostException {
        InetAddress direccion1 = InetAddress.getLocalHost();
        System.out.println("InetAddress del Local Host : " + direccion1);

        InetAddress direccion2 = InetAddress.getByName("localhost");
        System.out.println("InetAddress del nombre del Host : " + direccion2);

        InetAddress direccion3 = InetAddress.getByName("www.cs.buap.mx");
        System.out.println("InetAddress del nombre del Host : " + direccion3);

    }
}