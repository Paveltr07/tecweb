import javax.swing.*;
import java.awt.image.AreaAveragingScaleFilter;
import java.util.*;
import java.util.Scanner;

//mezclar 2 en 1 arreglos
//desplazar posicoon 4 a la 0 y los demas
//insertar un numero arreglo
//separar pares e impares


public class Main {
    public static void main(String[] args) {
        Scanner entrada = new Scanner(System.in);

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (j == 2) break; // rompe SOLO este bucle (el interno)
                System.out.println("i=" + i + " j=" + j);
            }
        }
        System.out.println("Fin");

        System.out.println(" ");
        outer: // etiqueta del bucle externo
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (j == 2) break outer; // rompe el bucle externo directamente
                System.out.println("i=" + i + " j=" + j);
            }
        }
        System.out.println("Fin");






    }
}


