package com.example.rasplauncherandroid;

import java.net.HttpURLConnection;
import java.net.URL;

import android.util.Log;

public class NetworkUtils {
	public static final String BASE_URL = "http://192.168.1.104:8080/things/";
	
	public static void performRequest(String url) {
		try {
		
		final URL parsedUrl = new URL(url);
		final HttpURLConnection connection = (HttpURLConnection) parsedUrl.openConnection();
		// connection.setRequestMethod("GET");
		final int responseCode = connection.getResponseCode();
		Log.d("TEST", "Request:" + url + " Response Code:" + responseCode);
		
		} catch (Exception e) {
			Log.w("TEST", Log.getStackTraceString(e));
		}
	}
}
