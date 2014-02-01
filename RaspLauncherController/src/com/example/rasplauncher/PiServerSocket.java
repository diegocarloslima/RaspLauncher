package com.example.rasplauncher;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

public class PiServerSocket {
	
	public static void main(String[] args) {
		try {
			final PiController piController = new PiController();
			
			final ServerSocket serverSocket = new ServerSocket(36305);
			System.out.println("Starting server on port:" + serverSocket.getLocalPort());

			final Socket socket = serverSocket.accept();
			System.out.println("Client connected" + socket.getRemoteSocketAddress());

			final BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			
			String message;
			while ((message = br.readLine()) != null) {
				System.out.println("Received message:" + message);
				if(message.contains("move")) {
					int distance = Integer.valueOf(message.split(":")[1]);
					piController.move(distance);
				} else if(message.contains("launch")) {
					int power = Integer.valueOf(message.split(":")[1]);
					piController.launch(power);
				} else if (message.equals("bye")) {
			        break;
				}
			}
			
			System.out.println("Finishing...");
		    br.close();
		    socket.close();
		    serverSocket.close();
		    System.out.println("Complete");

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
