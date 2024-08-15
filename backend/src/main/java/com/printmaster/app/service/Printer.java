package com.printmaster.app.service;

public interface Printer {
	void printDocument(byte[] documentContent) throws Exception;

	void printCoverPage(String coverPageContent) throws Exception;
}
