package org.example.ex_application_bureau.Controller;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;

public class EncryptionManagerController {

    private static final String AES_ALGORITHM = "AES";


    // Méthode pour crypter une chaîne de caractères
    public static String encrypt(String input, String key) throws Exception {
        SecretKeySpec secretKey = new SecretKeySpec(key.getBytes(), AES_ALGORITHM);
        Cipher cipher = Cipher.getInstance(AES_ALGORITHM);
        cipher.init(Cipher.ENCRYPT_MODE, secretKey);
        byte[] encryptedBytes = cipher.doFinal(input.getBytes());
        return Base64.getEncoder().encodeToString(encryptedBytes);
    }


    // Méthode pour décrypter une chaîne de caractères
    public static String decrypt(String input, String key) throws Exception {
        SecretKeySpec secretKey = new SecretKeySpec(key.getBytes(), AES_ALGORITHM);
        Cipher cipher = Cipher.getInstance(AES_ALGORITHM);
        cipher.init(Cipher.DECRYPT_MODE, secretKey);
        byte[] decryptedBytes = cipher.doFinal(Base64.getDecoder().decode(input));
        return new String(decryptedBytes);
    }

}