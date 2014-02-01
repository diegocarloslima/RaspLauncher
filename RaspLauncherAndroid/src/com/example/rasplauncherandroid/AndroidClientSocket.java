package com.example.rasplauncherandroid;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

import android.util.Log;

public class AndroidClientSocket {

	private static final AndroidClientSocket sInstance = new AndroidClientSocket();

	private Socket mSocket;
	private PrintWriter mWriter;

	private AndroidClientSocket() {
	}

	public static AndroidClientSocket getInstance() {
		return sInstance;
	}

	public void open() {
		if(mSocket != null) {
			return;
		}

		try {
			mSocket = new Socket("192.168.1.104", 36305);
		} catch (UnknownHostException e) {
			Log.w("TEST", Log.getStackTraceString(e));
		} catch (IOException e) {
			Log.w("TEST", Log.getStackTraceString(e));
		}

		try {
			mWriter = new PrintWriter(mSocket.getOutputStream(), true);
		} catch (IOException e) {
			Log.w("TEST", Log.getStackTraceString(e));
		}
	}

	public void close() {
		try {
			mWriter.close();
			mWriter = null;
			mSocket.close();
			mSocket = null;
		} catch (IOException e) {
			Log.w("TEST", Log.getStackTraceString(e));
		}
	}
	
	public void send(String message) {
		mWriter.println(message);
	}
}
