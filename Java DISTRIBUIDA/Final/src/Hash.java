import javax.crypto.SecretKeyFactory;
import javax.crypto.interfaces.PBEKey;
import javax.crypto.spec.PBEKeySpec;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.util.Base64;

//Se implementa el algoritmo PBKDF2, para base de datos, deriva una clave a partir de una contraseña
//la técnica que aplica es encriptación basada en iteraciones
 // Se implementa el algoritmo HMAC-SHA256, para verificar la integridad y autenticidad de un mensaje
// Se implementa el algorimo SHA-256 genera una huella digital de 256 bits
//Evitamos problemas como: rainbow tables, que es una base de datos que contiene hashes, que permite descrifrar contraseñas
public class Hash {
    //Configurar el algoritmo
    private static final int Iteraciones = 10000; //Es el número de iteraciones, hace el hash más lento y resistente
    private static final int KEY_LENGTH = 256; //Tamaño del hash, 32 bytes
    private static final String Algoritmo = "PBKDF2WithHmacSHA512"; //Combinación de los componentes que describimos al principi

    //Generación del "Salt"  agrega una cadena de caracteres aleatoria que se añade a la contraseña antes de aplicarle
    //el algoritmo hash
    public static String generarSalt() {
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[16];
        random.nextBytes(salt);
        return Base64.getEncoder().encodeToString(salt);
    }
    //hashear la contraseña
    public static String hashPassword(String contrasena, String salt) {
        try {
            byte[] saltBytes = Base64.getDecoder().decode(salt);
            PBEKeySpec spec = new PBEKeySpec(
                 contrasena.toCharArray(),
                    saltBytes,
                    Iteraciones,
                    KEY_LENGTH
            );
            SecretKeyFactory factory = SecretKeyFactory.getInstance(Algoritmo);
            byte[] hash = factory.generateSecret(spec).getEncoded();
            spec.clearPassword(); //limpia el array de la contraseña
            //codificar a Base64 para almacenar
            return Base64.getEncoder().encodeToString(hash);
        } catch (NoSuchAlgorithmException e){
            throw new RuntimeException("Algoritmo no disponible: " + Algoritmo, e);
        }catch (InvalidKeySpecException e){
            throw new RuntimeException("Especificación de clave inválida: ", e);
        }catch (IllegalArgumentException e){
            throw new RuntimeException("Salt invalido (debe ser base 64)", e);
        }
    }
    public static boolean verifyPassword(String inputPassword, String storedHash, String storedSalt) {
        try {
            String inputHash = hashPassword(inputPassword, storedSalt);
            return inputHash.equals(storedHash);
        } catch (RuntimeException e) {
            return false;
        }
    }
}