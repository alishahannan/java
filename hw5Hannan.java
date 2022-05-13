/**
 * Alisha Hannan
 * COP2805C
 * A simple calculator program to show GUI and Swing usage 
 */

package cop2805;

import javax.swing.*;
import java.awt.*;
import javax.swing.event.*;
import java.awt.event.*;
import java.io.*; 

public class hw5Hannan {
	
	private static void constructGUI() 
	{
		/*
	 	JFrame.setDefaultLookAndFeelDecorated(true);
	 	MyFrame frame = new MyFrame();
		 frame.setVisible(true); 
		 */
	}

	public static void main(String[] args) 
	{ /*
		SwingUtilities.invokeLater(new Runnable() 
		{
			public void run() {
				constructGUI();
			}	
		});	*/
	}
}
/*
class fieldListener implements ActionListener 
{
	MyFrame fr;
	public fieldListener(MyFrame frame)
	{
		fr=frame;
	}
	
	public void actionPerformed(ActionEvent e)
	{
		JButton btn = (JButton) e.getSource();
		String firstNum = fr.firstNum.getText();
		String secondNum = fr.secondNum.getText();
		String operator = (fr.comboBox.getSelectedItem().toString());
		
		double result = 0;
		switch(operator)
		{
			case "Add":
				result = Double.parseDouble(firstNum) + Double.parseDouble(secondNum);
				break;
				
			case "Subtract":
				result = Double.parseDouble(firstNum) - Double.parseDouble(secondNum);
				break;
				
			case "Multiply":
				result = Double.parseDouble(firstNum) * Double.parseDouble(secondNum);
				break;
				
			case "Divide":
				result = Double.parseDouble(firstNum) / Double.parseDouble(secondNum);
				break;
		}
		
		fr.results.setText("Result: " + result);
	}
}

class MyFrame extends JFrame 
{
	public JTextField firstNum = new JTextField();
	public JTextField secondNum = new JTextField();
	private String[] selections = { "Add", "Subtract", "Multiply", "Divide"};
	public JComboBox comboBox = new JComboBox(selections);
	
	public JLabel results = new JLabel("Result: ");
	public MyFrame() 
	{
		super();
		init();
	}
	
	private void init() {
		JButton calculate = new JButton("Calculate");
		calculate.addActionListener(new fieldListener(this));
		
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setTitle("Simple Calculator");
		this.setLayout(new GridLayout(5, 2));
		this.add(new JLabel("First Number:"));
		this.add(firstNum);
		this.add(new JLabel("Second Number:"));
		this.add(secondNum);
		this.add(new JLabel());
		this.add(comboBox);
		this.add(new JLabel());
		this.add(calculate);
		this.add(results);
		int frameWidth = 300;
		int frameHeight = 200;
		Dimension screenSize =
			Toolkit.getDefaultToolkit().getScreenSize();
		this.setBounds((int) screenSize.getWidth() - frameWidth, 0,
		frameWidth, frameHeight);
		this.pack();
		this.setVisible(true);
	}	
}*/