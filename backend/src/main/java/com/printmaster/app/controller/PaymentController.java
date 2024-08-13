package com.printmaster.app.controller;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.printmaster.app.model.PrintRequest;
import com.printmaster.app.service.PaymentService;

@RestController
@RequestMapping("/api/print")
public class PaymentController {

	@Autowired
	private PaymentService paymentService;

	@PostMapping("/pay")
	public ResponseEntity<Map<String, String>> pay(@RequestParam("userName") String userName,
			@RequestParam("department") String department, @RequestParam("classroom") String classroom,
			@RequestParam("copies") int copies, @RequestParam("printType") String printType,
			@RequestParam("paperSize") String paperSize, @RequestParam("file") MultipartFile file)
			throws NoSuchAlgorithmException, IOException {

		PrintRequest printRequest = new PrintRequest();
		printRequest.setUserName(userName);
		printRequest.setDepartment(department);
		printRequest.setClassroom(classroom);
		printRequest.setCopies(copies);
		printRequest.setPrintType(printType);
		printRequest.setPaperSize(paperSize);
		printRequest.setFileContent(file.getBytes());

		return paymentService.initiatePayment(printRequest);
	}

	@PostMapping("/paymentsuccess")
	public ResponseEntity<String> paymentReturn(@RequestParam Map<String, String> formParams)
			throws NoSuchAlgorithmException {
		System.out.println("msg  ---------------------" + formParams);
		return paymentService.handlePaymentReturn(formParams);
	}
}