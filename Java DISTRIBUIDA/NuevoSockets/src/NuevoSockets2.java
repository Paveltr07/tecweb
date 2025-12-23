import java.util.*;
import java.net.*;


public class NuevoSockets2 {
    public static void main(String args[]) throws SocketException {
        Enumeration<NetworkInterface> nets = NetworkInterface.getNetworkInterfaces();
        for (NetworkInterface netint : Collections.list(nets))
            displayInterfaceInformation(netint);
    }

    static void displayInterfaceInformation(NetworkInterface netint) throws SocketException {
        System.out.println("Mostrar nombre : " + netint.getDisplayName());
        System.out.println("Nombre : " + netint.getName());
        Enumeration<InetAddress> inetAddresses = netint.getInetAddresses();
        for (InetAddress inetAddress : Collections.list(inetAddresses)) {
            System.out.println("InetAddress : " + inetAddress);
        }
        System.out.printf("\n");

        System.out.println("Descripción : " + netint.getDisplayName());
        System.out.println("Nombre:" + netint. getName());
        //Enumeration<InetAddress> inetAddresses = netint.getInetAddresses();
        for (InetAddress inetAddress : Collections.list(inetAddresses)) {
            System.out.println("Dirección de internet: " + inetAddress);
            System.out.println("Conmiendo" + netint.isUp());
            System.out.println("Cielo hacia atras? " + netint. isLoopback());
            System.out.println("Punto a Punto? " + netint.isPointToPoint());
            System.out.println("Soporte multicast? " + netint.supportsMulticast());
            System.out.println("Virtual? " + netint.isVirtual());
            byte[] adresseMaterielle = netint.getHardwareAddress();
            System.out.println("Dirección material : " + Arrays. toString (adresseMaterielle));
            System.out.println("Unidad máxima de transmisión (MTU) : " + netint. getMTU());
            System.out.println("\n");
        }
    }
}
