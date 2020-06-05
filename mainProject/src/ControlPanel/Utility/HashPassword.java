package ControlPanel.Utility;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

public class HashPassword {

    /**
     * This function was developed to salt and hash a password but was never implemented.
     * @param pass
     * @return
     * @throws NoSuchAlgorithmException
     */
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

    /**
     * This function was developed to two parameters where not of equal, to show that the password was hashed.
     * @param inputPass
     * @param hashPass
     * @return
     */
    public static boolean inputPassHashCheck(String inputPass, String hashPass){
        if(inputPass != hashPass){
            return true;
        }
        else{
            return false;
        }
    }
}
