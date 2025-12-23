import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileReader;
import java.io.IOException;

public class JsonReading {
    public static void main(String[] args) {
        String archivoJson = "json.txt";

        try {
            JSONParser jsonParser = new JSONParser();
            FileReader fileReader = new FileReader(archivoJson);
            JSONObject jsonObject = (JSONObject) jsonParser.parse(fileReader);

            String nombre = (String) jsonObject.get("Nombre");

            Long edad = (Long) jsonObject.get("Edad");  // En JSON Simple, los números se leen como Long
            Long matricula = (Long) jsonObject.get("Matricula");

            System.out.println("Nombre: " + nombre);
            System.out.println("Edad: " + edad);
            System.out.println("Matricula: " + matricula);


            JSONArray kardexArray = (JSONArray) jsonObject.get("Kardex");
            System.out.println("\n--- Kárdex de materias ---");
            for (Object obj : kardexArray) {
                JSONObject materiaJson = (JSONObject) obj;
                String matNombre = (String) materiaJson.get("nombre");
                String matNrc = (String) materiaJson.get("nrc");
                Long matCreditos = (Long) materiaJson.get("creditos");

                System.out.println("Materia:");
                System.out.println("   Nombre   : " + matNombre);
                System.out.println("   NRC      : " + matNrc);
                System.out.println("   Créditos : " + matCreditos);
            }

            String dependencia = (String) jsonObject.get("dependencia");
            Long numero = (Long) jsonObject.get("numero");
            Long vigencia = (Long) jsonObject.get("vigencia");

            System.out.println("\n--- Seguro ---");
            System.out.println("Dependencia: " + dependencia);
            System.out.println("Número     : " + numero);
            System.out.println("Vigencia   : " + vigencia);

            Long duracionMeses = (Long) jsonObject.get("Duracion Meses : ");
            String empresa = (String) jsonObject.get("Empresa : ");
            Long horasDia = (Long) jsonObject.get("Horas dia : ");

            System.out.println("\n--- Prácticas ---");
            System.out.println("Duración (Meses): " + duracionMeses);
            System.out.println("Empresa         : " + empresa);
            System.out.println("Horas al día    : " + horasDia);

        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
}