import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import com.ejemplo.tutorial.AdresseProto.LibretaDirecciones;
import com.ejemplo.tutorial.AdresseProto.Persona;

public class EscrituraProtobuff {
    /**
     * @param args
     * @throws FileNotFoundException
     * @throws IOException
     */

    public static void main(String[] args) throws FileNotFoundException, IOException {
        LibretaDirecciones.Builder libretadirecciones = LibretaDirecciones.newBuilder();
        Persona.Builder persona = Persona.newBuilder();
        //1 Persona
        persona.setId(1);
        persona.setNom("Veronica");
        persona.setCorreo("Veronica@gmail.com");

        Persona.NumeroTelefono.Builder numeroPersonal = Persona.NumeroTelefono.newBuilder().setNumero("123456");
        Persona.NumeroTelefono.Builder numeroPersonal2 = Persona.NumeroTelefono.newBuilder().setNumero("324523");

        //numeroPersonal.setType(Persona.TipoTelefono.PERSONAL);
        numeroPersonal2.setType(Persona.TipoTelefono.CELULAR);

        persona.addTelefono(numeroPersonal);
        persona.addTelefono(numeroPersonal2);
        libretadirecciones.addPersona(persona); //Si se quita no se agrega

        //2 Persona
        Persona.Builder persona2 = Persona.newBuilder();
        persona2.setId(2);
        persona2.setNom("Jose");

        Persona.NumeroTelefono.Builder numeroPersonal3 = Persona.NumeroTelefono.newBuilder().setNumero("999999");
        numeroPersonal3.setType(Persona.TipoTelefono.PROFESIONAL);

        persona2.addTelefono(numeroPersonal3);
        libretadirecciones.addPersona(persona2);

        libretadirecciones.build().writeTo(new FileOutputStream("carnet.txt"));

        System.out.println("Archivo carnet.txt creado");
    }
}
