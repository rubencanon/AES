package aes.test;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Set;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class JsonUtils {
	@SuppressWarnings("unchecked")

	public static JSONObject jsonRead(String fileName) {
		// JSON parser object to parse read file
		JSONParser jsonParser = new JSONParser();
		JSONObject jsonObj = null;
		try {
			FileReader reader = new FileReader(fileName);
			// Read JSON file
			jsonObj = (JSONObject) jsonParser.parse(reader);

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return jsonObj;
	}

	private static void parseEmployeeObject(JSONObject employee) {
		// Get employee object within list
		JSONObject employeeObject = (JSONObject) employee.get("employee");

		// Get employee first name
		String firstName = (String) employeeObject.get("firstName");
		System.out.println(firstName);

		// Get employee last name
		String lastName = (String) employeeObject.get("lastName");
		System.out.println(lastName);

		// Get employee website name
		String website = (String) employeeObject.get("website");
		System.out.println(website);
	}

	public static JSONObject encryptJson(JSONObject jsonObj, String secretKey, String CRYPT_IV ) {

		AESCommonsImp aes = new AESCommonsImp(CRYPT_IV);

		Set elementos = jsonObj.keySet();
		for (Object elemento : elementos) {

			String key = (String) elemento;
			String valor = (String) jsonObj.get(key);

			if (!key.equalsIgnoreCase("token") && !key.equalsIgnoreCase("initVector")) {
				String valorCifrado = aes.encryptAES256(valor, secretKey);
				jsonObj.put(key, valorCifrado);

			}
			if (key.equalsIgnoreCase("initVector")) {
				jsonObj.put(key, CRYPT_IV);
			}

		}

		return jsonObj;
	}

	public static JSONObject decryptJson(JSONObject jsonObj, String secretKey) {

		String CRYPT_IV = (String) jsonObj.get("initVector");

		AESCommonsImp aes = new AESCommonsImp(CRYPT_IV);

		Set elementos = jsonObj.keySet();
		for (Object elemento : elementos) {

			String key = (String) elemento;
			String valor = (String) jsonObj.get(key);
			System.out.println(elemento);
			if (valor != null && !key.equalsIgnoreCase("token") && !key.equalsIgnoreCase("initVector")) {
				String valorDescifrado = aes.decryptAES256(valor, secretKey);
				jsonObj.put(key, valorDescifrado);
				System.out.println(valorDescifrado);

			}

		}

		return jsonObj;
	}

}
