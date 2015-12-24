package com.test.webway.webwaytestclient;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;

public class Client {

	private String serverMessage;
	public static String SERVERIP ;
	public static final int SERVERPORT = 4444;
	private OnMessageReceived mMessageListener = null;
	private boolean mRun = false;

	PrintWriter out;
	BufferedReader in;

	public Client(OnMessageReceived listener) {
		mMessageListener = listener;
		
	}

	public void sendMessage(String message) {
		if (out != null && !out.checkError()) {
			out.println(message);
			out.flush();
		}
	}

	public void stopClient() {
		mRun = false;
	}

	public void run() {

		mRun = true;

		try {
			InetAddress serverAddress = InetAddress.getByName(SERVERIP);

			Socket socket = new Socket(serverAddress, SERVERPORT);
			try {

				// send the message to the server
				out = new PrintWriter(new BufferedWriter(
						new OutputStreamWriter(socket.getOutputStream())), true);

				in = new BufferedReader(new InputStreamReader(
						socket.getInputStream()));

				while (mRun) {
					serverMessage = in.readLine();

					if (serverMessage != null && mMessageListener != null) {
						mMessageListener.messageReceived(serverMessage);
					}
					serverMessage = null;

				}
			} catch (Exception e) {

			} finally {
				socket.close();
			}

		} catch (Exception e) {

		}

	}
	public interface OnMessageReceived {
		public void messageReceived(String message);
	}
}