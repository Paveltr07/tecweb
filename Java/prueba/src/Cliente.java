import java.rmi.Naming;

public class Cliente {

    public static void main(String[] args) {
        try {
            // Conectar al servidor RMI
            Calculadora calculadora = (Calculadora) Naming.lookup("rmi://localhost/Calculadora");

            // Llamar al metodo remoto
            int resultado = calculadora.sumar(10, 5);

            // Mostrar el resultado
            System.out.println("El resultado de la suma es: " + resultado);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}