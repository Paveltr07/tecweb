import java.net.*;

public class NuevoSockets {
    public static void main(String[] args) throws UnknownHostException{
        InetAddress direccion1 = InetAddress.getLocalHost();
        System.out.println("InetAddress del Local Host : " + direccion1);

        InetAddress direccion2 = InetAddress.getByName("localhost");
        System.out.println("InetAddress del nombre del Host : " + direccion2);

        InetAddress direccion3 = InetAddress.getByName("www.cs.buap.mx");
        System.out.println("InetAddress del nombre del Host : " + direccion3);

        InetAddress direccion4 = InetAddress.getByAddress(new byte[]{(byte)192,(byte)168,(byte)1,(byte)6});
        System.out.println("InetAddress por direccion : " + direccion4);

        InetAddress address = Inet6Address.getByName( "fe80:0:0:0:8810:534d:fd8e:fc65%11");
        System.out.println("Inet6Address de la dirección proporcionada: "+ address);
        InetAddress address2 =Inet6Address.getByName("::1"); // dimección de la computadona local
        System.out.println("Inet6Address de la computadora local: "+ address2);
    }
}
