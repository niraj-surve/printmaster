package com.printmaster.app.service;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.printmaster.app.model.PrintRequest;
import com.printmaster.app.repository.PrintRequestRepository;
import com.printmaster.app.utils.PaymentUtils;

@Service
public class PaymentService {

	@Autowired
	private PaymentUtils paymentUtils;

	@Autowired
	private PrintRequestRepository printRequestRepository;

	@Value("${payment.endpoint}")
	private String endpoint;

	@Value("${payment.saltkey}")
	private String saltKey;

	@Value("${pay.url}")
	private String payUrl;

	// Mockup print pricing
	private static final double COLOR_PRINT_COST_PER_COPY = 5.0;
	private static final double BLACK_WHITE_PRINT_COST_PER_COPY = 2.0;

	public ResponseEntity<Map<String, String>> initiatePayment(PrintRequest printRequest)
			throws NoSuchAlgorithmException, IOException {
		double amount = calculateAmount(printRequest.getCopies(), printRequest.getPrintType());
		printRequest.setAmount(amount);

		String ENDPOINT = "/pg/v1/pay";
		String merchantTransactionId = java.util.UUID.randomUUID().toString();
		printRequest.setTransactionId(merchantTransactionId);

		Map<String, Object> mainPayload = new HashMap<>();
		mainPayload.put("merchantId", "PGTESTPAYUAT86");
		mainPayload.put("merchantTransactionId", merchantTransactionId);
		mainPayload.put("merchantUserId", "MUID123");
		mainPayload.put("amount", (int) (amount * 100)); // Amount in paise
		mainPayload.put("redirectUrl", "http://localhost:8080/api/print/paymentsuccess");
		mainPayload.put("redirectMode", "POST");
		mainPayload.put("callbackUrl", "http://localhost:5173/payment/success");
		mainPayload.put("mobileNumber", "9999999999");

		Map<String, String> paymentInstrument = new HashMap<>();
		paymentInstrument.put("type", "PAY_PAGE");
		mainPayload.put("paymentInstrument", paymentInstrument);

		String base64String = paymentUtils.base64Encode(mainPayload);
		String mainString = base64String + ENDPOINT + saltKey;
		String sha256Val = paymentUtils.calculateSHA256String(mainString);
		String checkSum = sha256Val + "###1";

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.set("X-VERIFY", checkSum);
		headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));

		Map<String, String> jsonPayload = new HashMap<>();
		jsonPayload.put("request", base64String);

		HttpEntity<Map<String, String>> requestEntity = new HttpEntity<>(jsonPayload, headers);

		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<String> responseEntity = restTemplate.postForEntity(payUrl, requestEntity, String.class);

		String jsonResponse = responseEntity.getBody();
		String url = paymentUtils.extractURL(jsonResponse);
		System.out.println(jsonResponse);

		// Save the initial print request with the amount and transaction ID
		printRequestRepository.save(printRequest);

		// Return the payment URL in the response body
		Map<String, String> responseBody = new HashMap<>();
		responseBody.put("paymentUrl", url);

		return ResponseEntity.ok(responseBody);
	}

	public ResponseEntity<String> handlePaymentReturn(Map<String, String> formParams) throws NoSuchAlgorithmException {
		String INDEX = "1";
		String transactionId = formParams.get("transactionId");
		String jsonResponse = "";

		if (transactionId != null) {
			String requestUrl = "https://api-preprod.phonepe.com/apis/pg-sandbox/pg/v1/status/PGTESTPAYUAT86/"
					+ transactionId;
			String sha256PayloadString = "/pg/v1/status/PGTESTPAYUAT86/" + transactionId + saltKey;
			String sha256Val = paymentUtils.calculateSHA256String(sha256PayloadString);
			String checksum = sha256Val + "###" + INDEX;

			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);
			headers.set("X-VERIFY", checksum);
			headers.set("X-MERCHANT-ID", transactionId);
			headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
			HttpEntity<String> requestEntity = new HttpEntity<>(headers);
			RestTemplate restTemplate = new RestTemplate();
			ResponseEntity<String> responseEntity = restTemplate.exchange(requestUrl, HttpMethod.GET, requestEntity,
					String.class);
			jsonResponse = responseEntity.getBody();

			// After confirming the payment, update the print request in the database
			PrintRequest printRequest = printRequestRepository.findByTransactionId(transactionId);
			if (printRequest != null && isPaymentSuccessful(jsonResponse)) {
				// Update the status or any other necessary fields
				printRequestRepository.save(printRequest);
			}
		}

		// Redirect to the success page
		HttpHeaders headers = new HttpHeaders();
		headers.add("Location", "http://localhost:5173/printmaster/payment/success");
		return new ResponseEntity<>(headers, HttpStatus.FOUND);
	}

	private double calculateAmount(int copies, String printType) {
		double costPerCopy = "color".equalsIgnoreCase(printType) ? COLOR_PRINT_COST_PER_COPY
				: BLACK_WHITE_PRINT_COST_PER_COPY;
		return costPerCopy * copies;
	}

	private boolean isPaymentSuccessful(String jsonResponse) {
		// Implement logic to check if the payment is successful based on jsonResponse
		return true; // Placeholder
	}
}