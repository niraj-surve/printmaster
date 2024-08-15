package com.printserver.app;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class PrintServer {

	private static final int PORT = 9090; // You can choose any available port

	public static void main(String[] args) {
		try (ServerSocket serverSocket = new ServerSocket(PORT)) {
			System.out.println("Print server is running and listening on port " + PORT);

			while (true) {
				Socket clientSocket = serverSocket.accept();
				System.out.println("Connection established with client: " + clientSocket.getInetAddress());

				// Handle the incoming print request
				try (InputStream inputStream = clientSocket.getInputStream();
						FileOutputStream fileOutputStream = new FileOutputStream("received_print_output.txt", true)) {

					// Read and save the file
					byte[] buffer = new byte[4096];
					int bytesRead;
					while ((bytesRead = inputStream.read(buffer)) != -1) {
						fileOutputStream.write(buffer, 0, bytesRead);
					}

					System.out.println("Print job received and saved to 'received_print_output.txt'");
				}

				clientSocket.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
