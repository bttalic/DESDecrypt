import java.security.spec.InvalidKeySpecException;
import java.util.ArrayList;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;

public class DESEncryption {

	public static byte[] encrypt(String key, String message) {
		try {
			Cipher desCipher = Cipher.getInstance("DES");
			SecretKeyFactory skf = SecretKeyFactory.getInstance("DES");
			DESKeySpec dks = new DESKeySpec(key.getBytes());
			SecretKey myDesKey = skf.generateSecret(dks);
			desCipher.init(Cipher.ENCRYPT_MODE, myDesKey);
			byte[] text = message.getBytes();
			byte[] textEncrypted = desCipher.doFinal(text);
			return textEncrypted;
		} catch (Exception e) {
			System.out.println("Error encodin message.\n" + e.getMessage());
			return new byte[0];
		}
	}
	
	public static boolean decrypt(String key, byte[] textEncrypted){
		try {
			Cipher desCipher = Cipher.getInstance("DES");
			SecretKeyFactory skf = SecretKeyFactory.getInstance("DES");
			DESKeySpec dks = new DESKeySpec(key.getBytes());
			SecretKey myDesKey = skf.generateSecret(dks);
			desCipher.init(Cipher.DECRYPT_MODE, myDesKey);
			byte[] textDecrypted = desCipher.doFinal(textEncrypted);
			
			TextIO.putln("Text Decryted : "
					+ new String(textDecrypted) + " |Key: " + key);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	public static void generateKeys(String prefix, int length, ArrayList<String> list){
		if (prefix.length() < length){
			if( !prefix.equals(""))
			list.add(prefix);
	        for (char c = 'a'; c <= 'z'; c++)
	        	generateKeys(prefix + c, length, list);
		}
	}

}
