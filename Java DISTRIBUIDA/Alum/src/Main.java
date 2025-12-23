import java.io.*;

public class Main {
    public static void main(String[] args) throws IOException, ClassNotFoundException {
        Alumno alumno = new Alumno();
        alumno.setNombre("Pavel");
        alumno.setFacultad("Compu");
        alumno.setMatricula(202058576);
        alumno.setK_alumno(new Kardex(12,2f));

        //Serializacion
        FileOutputStream FOS = new FileOutputStream("prueba.txt");
        ObjectOutputStream OOS = new ObjectOutputStream(FOS);
        OOS.writeObject(alumno);
        OOS.close();

        //Lectura
        FileInputStream FIS = new FileInputStream("prueba.txt");
        ObjectInputStream OIS = new ObjectInputStream(FIS);
        Alumno alumnoenArchivo = (Alumno) OIS.readObject();  //Decodifico
        OIS.close();
        System.out.println(alumnoenArchivo);
    }
}