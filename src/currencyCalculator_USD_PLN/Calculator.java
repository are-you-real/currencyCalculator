package currencyCalculator_USD_PLN;

// GUI libs
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import org.json.JSONArray;
import org.json.JSONObject;

// WEB API libs
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.MalformedURLException;

public class Calculator implements ActionListener {
	
	private JLabel labelSend, labelRecieve;
	private JFrame frame;
	private JPanel panel;
	private JTextField usdField, plnField;
	
	private static HttpURLConnection connection;
	
	public Calculator() {
		frame = new JFrame();
		
		labelSend = new JLabel("You send (USD) ");
		labelRecieve = new JLabel("They recieve (PLN) ");
		
		usdField = new JTextField();
		usdField.addActionListener(this);
		
		plnField = new JTextField();
		plnField.addActionListener(this);
		
		
		panel = new JPanel();
		panel.setBorder(BorderFactory.createEmptyBorder(30, 30, 10, 30));
		panel.setLayout(new GridLayout(0,2));
		panel.add(labelSend);
		panel.add(usdField);
		panel.add(labelRecieve);
		panel.add(plnField);
		
		
		
		frame.add(panel, BorderLayout.CENTER);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setTitle("USD PLN Calculator");
		frame.pack();
		frame.setVisible(true);
	}
	
	
	
	public static void main(String[] args) {
		new Calculator();
	}


	@Override
	public void actionPerformed(ActionEvent e) {
		
		// Connecting with NBP WEB API
		BufferedReader reader;
		String line;
		StringBuffer responseContent = new StringBuffer();
		Double usdRate = 0.0;
		
		try { 
			URL url = new URL("http://api.nbp.pl/api/exchangerates/rates/a/usd/");
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
			
			// get currrency rate
			usdRate = parse(responseContent.toString());
			
			
		} catch (MalformedURLException el) { 
			el.printStackTrace(); 
		} catch (IOException el) {
			el.printStackTrace();
		} finally {
			connection.disconnect();
		}
		
		
		
		// Calculations 
		if (e.getSource()==usdField)
         {
			Double pln = Double.parseDouble(usdField.getText())*usdRate;
			plnField.setText(pln.toString());
         }
     else if (e.getSource()==plnField)
         {
    	 	Double usd = Double.parseDouble(plnField.getText())/usdRate;
			usdField.setText(usd.toString());
         }
		
	}
	
	public static Double parse(String responseBody) {
		JSONObject album = new JSONObject(responseBody);
		JSONArray ratesArr = album.getJSONArray("rates");
		Double rates = ratesArr.getJSONObject(0).getDouble("mid");
		
		return rates;
	}

}
