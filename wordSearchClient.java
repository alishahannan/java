/**
 * Alisha Hannan
 * COP2805C
 * Word Searcher Client Side
 */

package cop2805;

import java.awt.event.*;
import java.awt.*;
import javax.swing.*;
import java.io.IOException;
import java.net.*;
import java.io.*;

class theFrame extends JFrame {
	public JTextField inputField = new JTextField();
	public DefaultListModel<Integer> listModel;
	public JButton transmit = new JButton("Transmit");

	public theFrame() {
		super();
		init();
	}

	public void init() {
		/*
		 * Unable to load GUI
		 */

		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setTitle("Word Search");
		this.setLayout(new GridLayout(3, 2));
		this.add(new JLabel("Word to Search For: "));
		this.add(inputField);
		this.add(new JLabel("Response"));
		this.add(new JList<Integer>(listModel));
		this.add(transmit);
		// transmit.addActionListener(new fieldListener(this));
		this.pack();
		this.setVisible(true);
	}
}

public class wordSearchClient {

	private static void constructGUI() {
		JFrame.setDefaultLookAndFeelDecorated(true);
		theFrame frame = new theFrame();
		frame.setVisible(true);
	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				constructGUI();
			}
		});
	}

}

class fieldListener implements ActionListener {
	theFrame fr;

	public fieldListener(theFrame frame) {
		fr = frame;
	}

	public void actionPerformed(ActionEvent e) {
		JButton btn = (JButton) e.getSource();
		String userInput = fr.inputField.getText();

		/*
		 * Able to connect and send data when using a List Unable to do so with Jlist
		 */

		// List<Integer> listModel = new ArrayList<>();
		try {
			Socket connection = new Socket("127.0.0.1", 1236);
			InputStream input = connection.getInputStream();
			OutputStream output = connection.getOutputStream();

			output.write(userInput.length());
			output.write(userInput.getBytes());

			int n = input.read();

			BufferedReader reader = new BufferedReader(new InputStreamReader(input));
			String response = reader.readLine();
			while (response != null) {
				response = reader.readLine();
				if (response != null) {
					// listModel.add(Integer.parseInt(response));
					fr.listModel.addElement(Integer.parseInt(response));
				}

				response = reader.readLine();
			}
			// listModel.forEach(System.out::println);
			if (!connection.isClosed())
				connection.close();

		} catch (IOException z) {
			z.printStackTrace();
		}
	}
}
