package com.softwaretunnel.javaapp_h2;

import java.awt.Color;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.WindowConstants;

import com.softwaretunnel.javaapp_h2.persistance.H2Interaction;

/**
 * Data Tunnel App
 *
 */
public class App {
	
	private H2Interaction h2Interaction= null;
	
	public static void main(String[] args) {
		new App().createGUI();
	}

	public void createGUI() {
		JFrame f = new JFrame("Welcome to Java H2 Tunnel.");
		final JLabel jl = new JLabel();
		jl.setBounds(50, 50, 1000, 20);
		jl.setForeground(Color.WHITE);
		JButton createDBButton=createDatabaseButton(jl);
		f.add(createDBButton);
		JButton createSchemaButton=createSchemaButton(jl);
		f.add(createSchemaButton);
		f.add(jl);
		f.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		createBackground(f);
		f.setSize(800, 800);
		f.setLayout(null);
		f.setVisible(true);
	}
	
	public JButton createDatabaseButton(JLabel jl) {
		JButton b = new JButton("Create H2 Database");
		b.setBounds(50, 100, 150, 30);
		b.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				createDatabase(jl);
			}
		});
		return b;
	}
	
	public JButton createSchemaButton(JLabel jl) {
		JButton b = new JButton("Create Database Schema ");
		b.setBounds(50, 150, 200, 30);
		b.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				createSchema(jl);
			}
		});
		return b;
	}

	public void createDatabase(JLabel jl) {
		try {
			h2Interaction = H2Interaction.getH2Interaction();
		} catch (Exception exception) {
			jl.setText(ErrorMessage.CONNECTION_FAILED.toString());
		}
		jl.setText("Database Created at :: "+Properties.H2_PATH);
	}
	
	public void createSchema(JLabel jl) {
		try {
			h2Interaction.createSchema();;
		} catch (Exception exception) {
			jl.setText(ErrorMessage.CONNECTION_FAILED.toString());
		}
		jl.setText("Schema Created");
	}
	
	public  void createBackground(JFrame jf) {
		ImageIcon background=new ImageIcon(Properties.IMAGES_PATH+"tunnel.jpg");
	    Image img=background.getImage();
	    Image temp=img.getScaledInstance(800,1000,Image.SCALE_SMOOTH);
	    background=new ImageIcon(temp);
	    JLabel back=new JLabel(background);
	    back.setLayout(null);
	    back.setBounds(0,0,800,1000);
	    back.setOpaque(false);
	    jf.add(back);
	}
}
