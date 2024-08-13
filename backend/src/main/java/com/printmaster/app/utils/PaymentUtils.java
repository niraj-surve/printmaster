package com.printmaster.app.utils;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Map;

import org.apache.tomcat.util.codec.binary.Base64;
import org.json.JSONObject;
import org.springframework.stereotype.Component;

import com.google.gson.Gson;

@SuppressWarnings("deprecation")
@Component
public class PaymentUtils {

	public String base64Encode(Map<String, Object> inputMap) {
		String jsonPayload = new Gson().toJson(inputMap);
		byte[] dataBytes = jsonPayload.getBytes(StandardCharsets.UTF_8);
		return Base64.encodeBase64String(dataBytes);
	}

	public String calculateSHA256String(String inputString) throws NoSuchAlgorithmException {
		MessageDigest digest = MessageDigest.getInstance("SHA-256");
		byte[] hashBytes = digest.digest(inputString.getBytes(StandardCharsets.UTF_8));
		StringBuilder hexString = new StringBuilder();

		for (byte hashByte : hashBytes) {
			String hex = Integer.toHexString(0xff & hashByte);
			if (hex.length() == 1)
				hexString.append('0');
			hexString.append(hex);
		}

		return hexString.toString();
	}

	public String extractURL(String jsonResponse) {
		String url = "";

		try {
			// Parse the JSON response
			JSONObject jsonObject = new JSONObject(jsonResponse);

			// Check if 'data' and 'instrumentResponse' objects exist
			if (jsonObject.has("data")) {
				JSONObject dataObject = jsonObject.getJSONObject("data");
				if (dataObject.has("instrumentResponse")) {
					JSONObject instrumentResponseObject = dataObject.getJSONObject("instrumentResponse");
					if (instrumentResponseObject.has("redirectInfo")) {
						JSONObject redirectInfoObject = instrumentResponseObject.getJSONObject("redirectInfo");
						if (redirectInfoObject.has("url")) {
							url = redirectInfoObject.getString("url");
						} else {
							System.out.println("Field 'url' not found in 'redirectInfo'.");
						}
					} else {
						System.out.println("Field 'redirectInfo' not found in 'instrumentResponse'.");
					}
				} else {
					System.out.println("Field 'instrumentResponse' not found in 'data'.");
				}
			} else {
				System.out.println("Field 'data' not found in JSON response.");
			}
		} catch (Exception e) {
			System.out.println("Error parsing JSON response: " + e.getMessage());
		}

		return url;
	}
}
