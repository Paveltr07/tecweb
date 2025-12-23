import java.awt.image.AreaAveragingScaleFilter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class ordenar {
    public static void main(String[] args) {

        String hola = "PERRA";

        String resultado = switch (hola) {
            case "hola" -> "hola";
            case "PERRA" -> "perritos";
            default -> "joto";
        };

        System.out.println(resultado);

        String resultados = switch (hola) {
            case "hola", "ola", "holi" -> "hola";
            case "PERRAs" -> "perritos";
            default -> {
                System.out.println("gay");
                yield "Default";
            }
        };

        System.out.println(resultados);


        int [][] matriz = {{1,2}, {3,4}};

        for (int[] fila : matriz) {
            System.out.println();

            for (int z : fila) {
                System.out.println(z + " ");
            }
        }

    }

}
