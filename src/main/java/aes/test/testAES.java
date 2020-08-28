package aes.test;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;	

import aes.test.AESCommonsImp;

public class testAES {

	private static final String CRYPT_IV = "cgwfvvokixrjmwkm"; //
	private static final String secretKey = "GnnJ+JcmVLE=";

	private static final Logger LOG = LogManager.getLogger(testAES.class);

	public static void main(String[] args) {


		LOG.info("secretKey"+secretKey);
		JSONObject request = JsonUtils.jsonRead("src\\main\\java\\aes\\test\\request.json");
		LOG.info("Original");

		LOG.info(request.toString());
		JSONObject encryptObj = JsonUtils.encryptJson(request, secretKey, CRYPT_IV);
		LOG.info("Cifrado: " + encryptObj.toString());
		JSONObject DencryptObj = JsonUtils.decryptJson(encryptObj, secretKey);


		LOG.info("Descifrado: "+DencryptObj.toString());

		JSONObject response = JsonUtils.jsonRead("src\\main\\java\\aes\\test\\response.json");
		LOG.info("Response Leido: Cifrado");

		LOG.info(response.toString());

		JSONObject decryptObj = JsonUtils.decryptJson(response, secretKey);
		LOG.info("Recuperado: "+decryptObj.toString());

	}

	private static void encryptParams(AESCommonsImp aes, HashMap<String, String> params) {
		System.out.println("*** Request Cifrado ***");
		System.out.println("***********************");
		for (Map.Entry<String, String> entry : params.entrySet()) {
			System.out.println(entry.getKey() + ": " + aes.encryptAES256(entry.getValue(), secretKey));
		}

	}

	private static void decryptParams(AESCommonsImp aes, HashMap<String, String> params) {
		System.out.println("*** Response DesCifrado ***");
		System.out.println("***************************");
		for (Map.Entry<String, String> entry : params.entrySet()) {
			System.out.println(entry.getKey() + ": " + aes.decryptAES256(entry.getValue(), secretKey));
		}
	}

	public static Boolean transactionValidityTime(Date fechaCreacion, Integer minutes) {

		Integer minToMiliSec = 1000 * minutes * 60;
		Long timeSecond = Long.valueOf(minToMiliSec); /// Transforma minutos en milisegundos
		boolean valid = true;
		try {
			TimeUnit.SECONDS.sleep(60);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		Date sysDate = new Date();
		Long diferencia = sysDate.getTime() - fechaCreacion.getTime();

		if (diferencia > minToMiliSec) {
			System.out.println("La transacción Excedio el tiempo de vigencia ");
			valid = false;

		}
		System.out.println("diferencia: ");

		System.out.println(diferencia);

		System.out.println("date: " + fechaCreacion);
		long inputTime = fechaCreacion.getTime();

		System.out.println("minToMiliSec");

		System.out.println(minToMiliSec);

		Date afterAddingTenMins = new Date(inputTime + (timeSecond));
		System.out.println("Despues de agregar " + minutes + " minutos");

		System.out.println(afterAddingTenMins);

		return valid;
	}

}
