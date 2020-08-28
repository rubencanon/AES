package aes.test;


import java.nio.charset.StandardCharsets;
import java.security.spec.KeySpec;
import java.util.Base64;
import java.util.Random;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;


public class AESCommonsImp {

	private static String salt = "1a3v8r9i8l489623";
	private String cryptIv;

	public AESCommonsImp(String cryptIv) {
		super();
		this.cryptIv = cryptIv;
	}

	public String encryptAES128(String text, String secretKey) {
		String cifrado = null;
		try {
			IvParameterSpec iv = new IvParameterSpec(getCryptIV().getBytes(StandardCharsets.UTF_8));
			SecretKeySpec skeySpec = new SecretKeySpec(secretKey.getBytes(StandardCharsets.UTF_8), "AES");

			Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
			cipher.init(Cipher.ENCRYPT_MODE, skeySpec, iv);

			byte[] encrypted = cipher.doFinal(text.getBytes());
			cifrado = new String(Base64.getEncoder().encode(encrypted), StandardCharsets.UTF_8);
		} catch (Exception e) {
			System.out.println("Error while encrypting: " + e.toString());
		}

		return cifrado;
	}

	public String decryptAES1282(String text, String secretKey) {
		String cifrado = null;
		try {
			IvParameterSpec iv = new IvParameterSpec(getCryptIV().getBytes(StandardCharsets.UTF_8));
			SecretKeySpec skeySpec = new SecretKeySpec(secretKey.getBytes(StandardCharsets.UTF_8), "AES");

			Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
			cipher.init(Cipher.DECRYPT_MODE, skeySpec, iv);

			byte[] encrypted = cipher.doFinal(text.getBytes());
			cifrado = new String(Base64.getEncoder().encode(encrypted), StandardCharsets.UTF_8);
		} catch (Exception e) {
			System.out.println("Error while encrypting: " + e.toString());
		}

		return cifrado;
	}

	public String decryptAES128(String strToDecrypt, String key) {
		String descifrado = null;
		try {
			IvParameterSpec iv = new IvParameterSpec(getCryptIV().getBytes(StandardCharsets.UTF_8));

			SecretKey skey = new SecretKeySpec(key.getBytes(StandardCharsets.UTF_8), "AES");
			Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
			cipher.init(Cipher.DECRYPT_MODE, skey, iv);
			byte[] decodedBytes = Base64.getDecoder().decode(strToDecrypt);

			descifrado = new String(cipher.doFinal(decodedBytes), StandardCharsets.UTF_8);

		} catch (Exception e) {
			System.out.println("Error while decrypting: " + e.toString());
		}
		return descifrado;
	}

	public  String encryptAES256(String strToEncrypt, String key) {
		try {
			IvParameterSpec ivspec = new IvParameterSpec(getCryptIV().getBytes(StandardCharsets.UTF_8));

			SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
			KeySpec spec = new PBEKeySpec(key.toCharArray(), salt.getBytes(), 8, 256);
			SecretKeySpec secretKey = new SecretKeySpec(factory.generateSecret(spec).getEncoded(), "AES");

			Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
			cipher.init(Cipher.ENCRYPT_MODE, secretKey, ivspec);
			return Base64.getEncoder().encodeToString(cipher.doFinal(strToEncrypt.getBytes(StandardCharsets.UTF_8)));
		} catch (Exception e) {
			System.out.println("Error while encrypting: " + e.toString());
		}
		return null;
	}

	public String decryptAES256(String strToDecrypt, String key) {
		try {
			byte[] iv = new byte[16];
			IvParameterSpec ivspec = new IvParameterSpec(getCryptIV().getBytes(StandardCharsets.UTF_8));

			SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
			KeySpec spec = new PBEKeySpec(key.toCharArray(), salt.getBytes(), 8, 256);
			SecretKeySpec secretKey = new SecretKeySpec(factory.generateSecret(spec).getEncoded(), "AES");
			Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
			cipher.init(Cipher.DECRYPT_MODE, secretKey, ivspec);
			return new String(cipher.doFinal(Base64.getDecoder().decode(strToDecrypt)));
		} catch (Exception e) {
			System.out.println("Error while decrypting: " +strToDecrypt+ e.toString());
		}
		return null;
	}

	public static String generateCryptIV() {
		char[] chars = "abcdefghijklmnopqrstuvwxyz".toCharArray();
		StringBuilder sb = new StringBuilder(16);
		Random random = new Random();
		for (int i = 0; i < 16; i++) {
			char c = chars[random.nextInt(chars.length)];
			sb.append(c);
		}
		return sb.toString();

	}

	public String getCryptIV() {
		return cryptIv;
	}

}
