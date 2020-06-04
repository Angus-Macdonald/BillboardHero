package ControlPanel.Utility;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

public class HashPassword {

    public static byte[] hashPassword(String pass) throws NoSuchAlgorithmException {
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[16];
        random.nextBytes(salt);
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        md.update(salt);
        byte[] returnPassword = new byte[16];
        for(int i = 0; i < 2; i++){
            returnPassword = md.digest(pass.getBytes(StandardCharsets.UTF_8));
        }
        return returnPassword;
    }

    public static boolean inputPassHashCheck(String inputPass, String hashPass){
        if(inputPass != hashPass){
            return true;
        }
        else{
            return false;
        }
    }
}
