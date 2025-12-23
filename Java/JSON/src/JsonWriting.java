import java.io.FileWriter;
import java.io.IOException;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class JsonWriting{
    public static void main(String[] args){
        String archivoJson = "json.txt";
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("URL", "www.cs.buap.mx");
        jsonObject.put("Nombre del sitio", "Fcc Buap");
        jsonObject.put("Libros", 2);
        JSONArray jsonArray = new JSONArray();
        jsonArray.add("Java distribuido");
        jsonArray.add("Restricciones");
        jsonObject.put("Biblioteca", jsonArray);

        try{
            FileWriter jsonFileWriter = new FileWriter(archivoJson);
            jsonFileWriter.write(jsonObject.toJSONString());
            jsonFileWriter.flush();
            jsonFileWriter.close();
            System.out.print(jsonObject);
        }catch (IOException e){
            e.printStackTrace();
        }
    }
}