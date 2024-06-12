package com.softwaretunnel.javaapp_h2;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JTextField;

import com.softwaretunnel.javaapp_h2.persistance.H2Interaction;

/**
 * Hello world!
 *
 */
public class App {
	public static void main(String[] args) {
		createGUI();
	}

	public static void createGUI() {
		JFrame f = new JFrame("Welcome to Java H2 Tunnel.");
		final JTextField tf = new JTextField();
		tf.setBounds(50, 50, 150, 20);
		JButton b = new JButton("Create H2 Database");
		b.setBounds(50, 100, 95, 30);
		b.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				createDatabase(tf);
			}
		});
		f.add(b);
		f.add(tf);
		f.setSize(400, 400);
		f.setLayout(null);
		f.setVisible(true);
	}

	public static void createDatabase(JTextField tf) {
		H2Interaction h2Interaction = null;
		try {
			h2Interaction = H2Interaction.getH2Interaction();
		} catch (Exception exception) {
			tf.setText(ErrorMessage.CONNECTION_FAILED.toString());
		}
		tf.setText("Database Created at :: "+h2Interaction.DB_SOURCE);
	}
}
