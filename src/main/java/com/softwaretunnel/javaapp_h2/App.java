package com.softwaretunnel.javaapp_h2;

import java.awt.Color;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

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

	private H2Interaction h2Interaction = null;
	JFrame frame = new JFrame("Welcome to Java H2 Tunnel.");

	public static void main(String[] args) {
		new App().createGUI();
	}

	public void createGUI() {
		final JLabel jl = new JLabel();
		jl.setBounds(50, 50, 1000, 20);
		jl.setForeground(Color.WHITE);
		JButton createDBButton = createDatabaseButton(jl);
		frame.add(createDBButton);
		JButton createSchemaButton = createSchemaButton(jl);
		frame.add(createSchemaButton);
		frame.add(jl);
		frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		frame.addWindowListener(getWindowListener());
		createBackground(frame);
		frame.setSize(800, 800);
		frame.setLayout(null);
		frame.setVisible(true);
	}
	
	public void refreshFrame() {
		//frame.revalidate();
		frame.repaint();
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
		JButton schemaButton = new JButton("Create Database Schema ");
		schemaButton.setBounds(50, 150, 200, 30);
		schemaButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (createSchema(jl)) {
					frame.remove(schemaButton);
					frame.add(dropSchemaButton(jl));
					refreshFrame();
				}
			}
		});
		return schemaButton;
	}

	public JButton dropSchemaButton(JLabel jl) {
		JButton b = new JButton("Drop Database Schema ");
		b.setBounds(50, 150, 200, 30);
		b.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dropSchema(jl);
			}
		});
		return b;
	}

	public void createDatabase(JLabel jl) {
		try {
			h2Interaction = H2Interaction.getH2Interaction();
			jl.setText("Database Created at :: " + Properties.H2_PATH);
		} catch (Exception exception) {
			jl.setText(ErrorMessage.DB_CREATION_FAILED.message);
		}
	}

	public boolean createSchema(JLabel jl) {
		try {
			h2Interaction.createSchema();
			jl.setText("Schema Created with file ::" + Properties.H2_SCHEMA);
			return true;
		} catch (Exception exception) {
			jl.setText(ErrorMessage.SCHEMA_CREATION_FAILED.message);
			return false;
		}
	}

	public void dropSchema(JLabel jl) {
		try {
			h2Interaction.dropSchema();
			;
		} catch (Exception exception) {
			jl.setText(ErrorMessage.SCHEMA_DROP_FAILED.message);
		}
		jl.setText("Schema dropped with file ::" + Properties.H2_SCHEMA);
	}

	public void createBackground(JFrame jf) {
		ImageIcon background = new ImageIcon(Properties.IMAGES_PATH + "tunnel.jpg");
		Image img = background.getImage();
		Image temp = img.getScaledInstance(800, 1000, Image.SCALE_SMOOTH);
		background = new ImageIcon(temp);
		JLabel back = new JLabel(background);
		back.setLayout(null);
		back.setBounds(0, 0, 800, 1000);
		back.setOpaque(false);
		jf.add(back);
	}

	public WindowListener getWindowListener() {

		return new WindowListener() {

			@Override
			public void windowClosed(WindowEvent e) {
				h2Interaction.releaseResources();

			}

			@Override
			public void windowOpened(WindowEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void windowClosing(WindowEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void windowIconified(WindowEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void windowDeiconified(WindowEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void windowActivated(WindowEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void windowDeactivated(WindowEvent e) {
				// TODO Auto-generated method stub

			}

		};
	}
}
