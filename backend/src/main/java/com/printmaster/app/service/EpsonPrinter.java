package com.printmaster.app.service;

import java.io.OutputStream;
import java.net.Socket;

public class EpsonPrinter implements Printer {

	private String printerIp;
	private int printerPort;

	public EpsonPrinter(String printerIp, int printerPort) {
		this.printerIp = printerIp;
		this.printerPort = printerPort;
	}

	@Override
	public void printDocument(byte[] documentContent) throws Exception {
		try (Socket socket = new Socket(printerIp, printerPort); OutputStream outputStream = socket.getOutputStream()) {

			// Send the document content to the printer
			outputStream.write(documentContent);
			outputStream.flush();
		} catch (Exception e) {
			throw new Exception("Failed to send document to Epson printer", e);
		}
	}

	@Override
	public void printCoverPage(String coverPageContent) throws Exception {
		try (Socket socket = new Socket(printerIp, printerPort); OutputStream outputStream = socket.getOutputStream()) {

			// Send the cover page content to the printer
			outputStream.write(coverPageContent.getBytes());
			outputStream.flush();
		} catch (Exception e) {
			throw new Exception("Failed to send cover page to Epson printer", e);
		}
	}
}
