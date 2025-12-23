import com.ejemplo.tutorial.AdresseProto.LibretaDirecciones;
import com.ejemplo.tutorial.AdresseProto.Persona;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class LecturaProtobuff {

    public static void main(String[] args) throws FileNotFoundException, IOException {
        LibretaDirecciones.Builder libretadirecciones = LibretaDirecciones.newBuilder();
        libretadirecciones.mergeFrom(new FileInputStream("carnet.txt"));
        System.out.println("\nNumero de direcciones : " + libretadirecciones.getPersonaCount());

        for (Persona pers : libretadirecciones.getPersonaList()) {
            System.out.println("\nNombre : " + pers.getNom());
            System.out.println("ID : " + pers.getId());
            System.out.println("Correo : " + pers.getCorreo());

            for (Persona.NumeroTelefono tel : pers.getTelefonoList()) {
                System.out.println("Telefono : " + tel.getNumero());
                System.out.println("Tipo : " + tel.getType());
            }
        }
    }
}