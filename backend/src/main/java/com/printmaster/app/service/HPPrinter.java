package com.printmaster.app.service;

import java.io.OutputStream;
import java.net.Socket;

public class HPPrinter implements Printer {

	private String printerIp;
	private int printerPort;

	public HPPrinter(String printerIp, int printerPort) {
		this.printerIp = printerIp;
		this.printerPort = printerPort;
	}

	@Override
	public void printDocument(byte[] documentContent) throws Exception {
		try (Socket socket = new Socket(printerIp, printerPort); OutputStream outputStream = socket.getOutputStream()) {

			// HP printers often use PCL or PostScript commands.
			// This is a simple example of sending raw data.
			outputStream.write(documentContent);
			outputStream.flush();
		}
	}

	@Override
	public void printCoverPage(String coverPageContent) throws Exception {
		try (Socket socket = new Socket(printerIp, printerPort); OutputStream outputStream = socket.getOutputStream()) {

			// Send cover page content before the main document
			outputStream.write(coverPageContent.getBytes());
			outputStream.flush();
		}
	}
}