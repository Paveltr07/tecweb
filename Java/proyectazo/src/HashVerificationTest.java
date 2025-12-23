public class HashVerificationTest {
    public static void main(String[] args) {
        String password = "Holamundo23";
        String salt = Hash.generarSalt();

        System.out.println("Salt generado: " + salt);

        String hash = Hash.hashPassword(password, salt);
        System.out.println("Hash generado: " + hash);

        boolean verification = Hash.verifyPassword(password, hash, salt);
        System.out.println("Verificación exitosa? " + verification);
    }
}