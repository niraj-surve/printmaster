package com.printmaster.app.service;

public class PrinterFactory {
	public static Printer getPrinter(String printerType, String printerIp, int printerPort) {
		switch (printerType) {
		case "HP":
			return new HPPrinter(printerIp, printerPort);
		case "Epson":
			return new EpsonPrinter(printerIp, printerPort);
		// Add other printer types here
		default:
			throw new IllegalArgumentException("Unknown printer type");
		}
	}
}
