package currencyCalculator_GBP_PLN;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import org.json.JSONArray;
import org.json.JSONObject;

public class webAPI {
	BufferedReader reader;
	String line;
	StringBuffer responseContent = new StringBuffer();
	private static HttpURLConnection connection;
	
	
	public Double getRate() {
		try { 
			URL url = new URL("http://api.nbp.pl/api/exchangerates/rates/a/GBP/");
			connection = (HttpURLConnection) url.openConnection();
			
			//Request setup
			connection.setRequestMethod("GET");
			connection.setConnectTimeout(5000);
			connection.setReadTimeout(5000);
			
			int status = connection.getResponseCode();
			
			if (status > 299) {
				reader = new BufferedReader(new InputStreamReader(connection.getErrorStream()));
				while((line = reader.readLine()) != null) {
					responseContent.append(line);
				}
				reader.close();
			} else {
				reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
				while((line = reader.readLine()) != null) {
					responseContent.append(line);
				}
				reader.close();
			}
			return parse(responseContent.toString());
			
			
		} catch (MalformedURLException el) { 
			el.printStackTrace();
			return 0.0;
		} catch (IOException el) {
			el.printStackTrace();
			return 0.0;
		} finally {
			connection.disconnect();
		}
	}
	
	public static Double parse(String responseBody) {
		JSONObject album = new JSONObject(responseBody);
		JSONArray ratesArr = album.getJSONArray("rates");
		Double rates = ratesArr.getJSONObject(0).getDouble("mid");
		
		return rates;
	}
	

}
