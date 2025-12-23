import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Iterator;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class JsonReading {
    public static void main(String[] args) throws IOException, ParseException{

        String archivoJson = "json.txt";
        //Se desconoce que es un objeto JSON, entonces parse
        JSONParser jsonParser = new JSONParser();
        FileReader fileReader= new FileReader(archivoJson);
        JSONObject jsonObject = (JSONObject) jsonParser.parse(fileReader); //Se lee y va a generar una estructura para que pueda ser leía con JAVA
        String nomSite = (String) jsonObject.get("Nombre del sitio");
        System.out.println("Nombre del sitio: " + nomSite);
        Long livres = (Long) jsonObject.get ("Libros");
        System.out.println("Libros: " + livres);
        String url = (String) jsonObject.get("URL");
        System.out.println("URL: " +  url);
        JSONArray biblioteca = (JSONArray) jsonObject.get("Biblioteca");
        for (Object obra: biblioteca){
            System.out.println(obra);
        }
    }
}