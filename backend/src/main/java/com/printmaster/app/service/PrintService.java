package com.printmaster.app.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.printmaster.app.model.PrintRequest;
import com.printmaster.app.repository.PrintRequestRepository;

@Service
public class PrintService {

	@Autowired
	private PrintRequestRepository printRequestRepository;

	@Value("${print.server.ip}")
	private String printServerIp;

	@Value("${print.server.port}")
	private int printServerPort;

	public void processPrintQueue() {
		List<PrintRequest> printRequests = printRequestRepository.findAll();

		for (PrintRequest printRequest : printRequests) {
			try {
				Printer printer = PrinterFactory.getPrinter(PrintRequest.getPrinterType(), printServerIp,
						printServerPort);
				sendPrintJobToPrinter(printer, printRequest);
				System.out.println("Print job sent to printer for user: " + printRequest.getUserName());
				printRequestRepository.delete(printRequest); // Simulate the queue processing
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	private void sendPrintJobToPrinter(Printer printer, PrintRequest printRequest) throws Exception {
		// Add a cover page with user details
		String coverPage = "User: " + printRequest.getUserName() + "\nDepartment: " + printRequest.getDepartment()
				+ "\nClassroom: " + printRequest.getClassroom() + "\n\n";
		printer.printCoverPage(coverPage);

		// Send the actual document bytes to the printer
		printer.printDocument(printRequest.getFileContent());

		// Handle multiple copies if needed
		for (int i = 1; i < printRequest.getCopies(); i++) {
			printer.printDocument(printRequest.getFileContent());
		}
	}
}