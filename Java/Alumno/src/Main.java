import java.io.*;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class Main {
    public static void main(String[] args) throws IOException, ClassNotFoundException {
        Alumno alumno = new Alumno();

        //Sets
        alumno.setNombre("Pavel Tamanis");
        alumno.setEdad(22);
        alumno.setMatricula(202058576);

        Seguro seguro = new Seguro("IMSS", 12345, 202632);
        Practicas practicas = new Practicas(12, "FFC", 600);

        //archivo escritura
        String archivoJson = "json.txt";
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("Nombre", alumno.getNombre());
        jsonObject.put("Edad", alumno.getEdad());
        jsonObject.put("Matricula", alumno.getMatricula());


        //B Kardex //C Materias
        Kardex kardex = new Kardex();
        kardex.agregarMateria(new Materia("Programación", "NRC123", 5));
        kardex.agregarMateria(new Materia("Cálculo", "NRC234", 6));
        kardex.agregarMateria(new Materia("Bases de Datos", "NRC345", 4));
        kardex.agregarMateria(new Materia("Física", "NRC456", 6));
        kardex.agregarMateria(new Materia("Química", "NRC567", 5));
        kardex.agregarMateria(new Materia("Ética", "NRC678", 2));
        kardex.agregarMateria(new Materia("Ética", "NRC678", 2));
        kardex.agregarMateria(new Materia("Gay", "NRC678", 2));

        JSONArray materiasArray = new JSONArray();
        for (int i = 0; i < kardex.getNumeroMaterias(); i++) {
            Materia m = kardex.getMateria(i);

            JSONObject materiaJson = new JSONObject();
            materiaJson.put("nombre", m.getNombre());
            materiaJson.put("nrc", m.getNrc());
            materiaJson.put("creditos", m.getCreditos());

            materiasArray.add(materiaJson);
        }

        jsonObject.put("Kardex", materiasArray);

        //D Seguro
        jsonObject.put("dependencia", seguro.getDependencia());
        jsonObject.put("numero", seguro.getNumero());
        jsonObject.put("vigencia", seguro.getVigencia());


        // E Practicas
        jsonObject.put("Duracion Meses : ", practicas.getDuracion());
        jsonObject.put("Empresa : ", practicas.getEmpresa());
        jsonObject.put("Horas dia : ", practicas.getHoras_dia());


        try{
            FileWriter jsonFileWriter = new FileWriter(archivoJson);
            jsonFileWriter.write(jsonObject.toJSONString());
            jsonFileWriter.flush();
            jsonFileWriter.close();
            System.out.print(jsonObject);
        }catch (IOException e){
            e.printStackTrace();
        }

        //Serializacion
        /*FileOutputStream FOS = new FileOutputStream("prueba.txt");
        ObjectOutputStream OOS = new ObjectOutputStream(FOS);
        OOS.writeObject(alumno);

        OOS.close();

        FileInputStream FIS = new FileInputStream("prueba.txt");
        ObjectInputStream OIS = new ObjectInputStream(FIS);
        Alumno alumnoenArchivo = (Alumno) OIS.readObject();  //Decodifico
        OIS.close();
        System.out.println(alumnoenArchivo); */

    }
}