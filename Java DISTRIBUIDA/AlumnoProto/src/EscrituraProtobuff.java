import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import com.ejemplo.tutorial.AdresseProto.ListaAlumnos;
import com.ejemplo.tutorial.AdresseProto.Alumno;
import com.ejemplo.tutorial.AdresseProto.Alumno.Kardex;
import com.ejemplo.tutorial.AdresseProto.Alumno.Kardex.Materia;
import com.ejemplo.tutorial.AdresseProto.Alumno.Seguro;
import com.ejemplo.tutorial.AdresseProto.Alumno.Practica;

public class EscrituraProtobuff {
    /**
     * @param args
     * @throws FileNotFoundException
     * @throws IOException
     */

    public static void main(String[] args) throws FileNotFoundException, IOException {
        ListaAlumnos.Builder listaAlumnos = ListaAlumnos.newBuilder();
        Alumno.Builder alumno1 = Alumno.newBuilder();
        alumno1.setId(1);
        alumno1.setNom("Veronica");
        alumno1.setEdad(22);
        alumno1.setMatricula(202058576);

        Kardex.Builder kardex1 = Kardex.newBuilder();
        kardex1.addMaterias(Materia.newBuilder().setNombre("Matemáticas").setNrc(101).setCreditos(4));
        kardex1.addMaterias(Materia.newBuilder().setNombre("Física").setNrc(102).setCreditos(4));
        kardex1.addMaterias(Materia.newBuilder().setNombre("Algebra").setNrc(103).setCreditos(4));
        kardex1.addMaterias(Materia.newBuilder().setNombre("Programacion").setNrc(104).setCreditos(4));
        kardex1.addMaterias(Materia.newBuilder().setNombre("Circuitos").setNrc(105).setCreditos(4));
        kardex1.addMaterias(Materia.newBuilder().setNombre("Teoria").setNrc(106).setCreditos(4));
        alumno1.setKardex(kardex1);

        Seguro.Builder seguro1 = Seguro.newBuilder();
        seguro1.setDependencia("IMSS");
        seguro1.setNseguro(123456);
        seguro1.setVigencia(20251231);
        alumno1.setSeguros(seguro1);

        Practica.Builder practica1 = Practica.newBuilder();
        practica1.setDuracion(6);
        practica1.setEmpresa("Google");
        practica1.addHorasdia(8);
        alumno1.setPracticas(practica1);
        listaAlumnos.addAlumno(alumno1);

        // 2 Alumno
        /*Alumno.Builder alumno2 = Alumno.newBuilder();
        alumno2.setId(2);
        alumno2.setNom("Jose");
        alumno2.setEdad(25);
        alumno2.setMatricula(202098765);

        Kardex.Builder kardex2 = Kardex.newBuilder();
        kardex2.addMaterias(Materia.newBuilder().setNombre("Química").setNrc(201).setCreditos(4));
        alumno2.setKardex(kardex2);

        Seguro.Builder seguro2 = Seguro.newBuilder();
        seguro2.setDependencia("ISSSTE");
        seguro2.setNseguro(789123);
        seguro2.setVigencia(20261231);
        alumno2.setSeguros(seguro2);

        Practica.Builder practica2 = Practica.newBuilder();
        practica2.setDuracion(3);
        practica2.setEmpresa("Microsoft");
        practica2.addHorasdia(500);
        alumno2.setPracticas(practica2);
        listaAlumnos.addAlumno(alumno2); */

        FileOutputStream output = new FileOutputStream("carnet.txt");
        listaAlumnos.build().writeTo(output);
        output.close();
        System.out.println("Archivo carnet.txt creado");
    }
}
