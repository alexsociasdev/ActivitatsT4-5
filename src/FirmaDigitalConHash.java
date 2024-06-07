import java.security.*;
import java.util.Base64;
import java.util.Scanner;
import javax.crypto.Cipher;

public class FirmaDigitalConHash {

    public static PublicKey clavePublica;
    public static PrivateKey clavePrivada;

    public static KeyPair GenerarClave() throws NoSuchAlgorithmException {
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
        keyPairGenerator.initialize(2048);
        return keyPairGenerator.generateKeyPair();
    }

    public static byte[] CrearHash(String mensaje) throws NoSuchAlgorithmException {
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        return digest.digest(mensaje.getBytes());
    }

    public static byte[] CifrarHash(byte[] hash, PrivateKey clavePrivada) throws Exception {
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.ENCRYPT_MODE, clavePrivada);
        return cipher.doFinal(hash);
    }

    public static byte[] DescifrarHash(byte[] hashCifrado, PublicKey clavePublica) throws Exception {
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.DECRYPT_MODE, clavePublica);
        return cipher.doFinal(hashCifrado);
    }

    public static boolean VerificarFirma(String mensaje, byte[] firmaAVerificar, PublicKey clavePublica) throws Exception {
        byte[] hashOriginal = CrearHash(mensaje);
        byte[] hashDescifrado = DescifrarHash(firmaAVerificar, clavePublica);
        return MessageDigest.isEqual(hashOriginal, hashDescifrado);
    }

    public static void main(String[] args) throws Exception {
        // Creación de clave pública y privada
        KeyPair clave = GenerarClave();
        clavePublica = clave.getPublic();
        clavePrivada = clave.getPrivate();
        System.out.println("Clave pública: " + clavePublica);
        System.out.println("Clave privada: " + clavePrivada);

        // Mensaje a cifrar
        Scanner src = new Scanner(System.in);
        System.out.println("Introduce el mensaje a cifrar:");
        String mensaje = src.nextLine();

        // Creación del hash y cifrado del mismo
        byte[] hash = CrearHash(mensaje);
        byte[] mensajeFirmado = CifrarHash(hash, clavePrivada);
        System.out.println("Mensaje firmado: " + Base64.getEncoder().encodeToString(mensajeFirmado));

        // Verificación del mensaje firmado
        boolean verificado = VerificarFirma(mensaje, mensajeFirmado, clavePublica);
        if (verificado) {
            System.out.println("La firma ha sido verificada correctamente");
        } else {
            System.out.println("Error en la verificación de la firma");
        }

        src.close();
    }
}
