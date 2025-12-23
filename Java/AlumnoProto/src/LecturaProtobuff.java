import com.ejemplo.tutorial.AdresseProto.ListaAlumnos;
import com.ejemplo.tutorial.AdresseProto.Alumno;
import com.ejemplo.tutorial.AdresseProto.Alumno.Kardex.Materia;
import java.io.FileInputStream;
import java.io.IOException;

public class LecturaProtobuff {
    public static void main(String[] args) throws IOException {
        ListaAlumnos.Builder listaAlumnos = ListaAlumnos.newBuilder();
        listaAlumnos.mergeFrom(new FileInputStream("carnet.txt"));

        System.out.println("\nNúmero de alumnos: " + listaAlumnos.getAlumnoCount());

        for (Alumno alumno : listaAlumnos.getAlumnoList()) {
            System.out.println("\nNombre: " + alumno.getNom());
            System.out.println("ID: " + alumno.getId());
            System.out.println("Edad: " + alumno.getEdad());
            System.out.println("Matrícula: " + alumno.getMatricula());

            System.out.println("\n\tKardex\n");

            for (Materia materia : alumno.getKardex().getMateriasList()) {
                System.out.println("Materia: " + materia.getNombre());
                System.out.println("NRC: " + materia.getNrc());
                System.out.println("Créditos: " + materia.getCreditos() + "\n");
            }

            System.out.println("\n\tSeguro");
            System.out.println("Dependencia: " + alumno.getSeguros().getDependencia());
            System.out.println("N° Seguro: " + alumno.getSeguros().getNseguro());
            System.out.println("Vigencia: " + alumno.getSeguros().getVigencia());

            System.out.println("\n\tPrácticas");
            System.out.println("Empresa: " + alumno.getPracticas().getEmpresa());
            System.out.println("Duración (meses): " + alumno.getPracticas().getDuracion());
            System.out.print("Horas por día: ");

            for (int horas : alumno.getPracticas().getHorasdiaList()) {
                System.out.print(horas);
            }
            System.out.println();
        }
    }
}