package currencyCalculator_GBP_PLN;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;



public class GUI implements ActionListener {
	
	private JLabel labelSend, labelRecieve;
	private JFrame frame;
	private JPanel panel;
	private JTextField gbpField, plnField;
	
	public GUI() {
		frame = new JFrame();
		frame.setPreferredSize(new Dimension(400, 100));
		
		labelSend = new JLabel("You send (GBP) ");
		labelRecieve = new JLabel("They recieve (PLN) ");
		
		gbpField = new JTextField();
		gbpField.addActionListener(this);
		
		plnField = new JTextField();
		plnField.addActionListener(this);
		
		
		panel = new JPanel();
		panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		panel.setLayout(new GridLayout(0,2));
		panel.add(labelSend);
		panel.add(gbpField);
		panel.add(labelRecieve);
		panel.add(plnField);
		
		
		
		frame.add(panel, BorderLayout.CENTER);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setTitle("USD PLN Calculator");
		frame.pack();
		frame.setVisible(true);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		
		currencyConverter calculations = new currencyConverter(); 
		 
		// fields changeing
		if (e.getSource()==gbpField)
         {
			Double gbp = Double.parseDouble(gbpField.getText());
			plnField.setText(calculations.anotherCurrency2pln(gbp).toString());
         }
     else if (e.getSource()==plnField)
         {
    	 	Double pln = Double.parseDouble(plnField.getText());
    	 	gbpField.setText(calculations.pln2anotherCurrency(pln).toString());
         }
	}
	
}
