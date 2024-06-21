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

	JFrame frame = new JFrame("Welcome to Java H2 Tunnel.");
	JButton dropSchemaButton;
	JButton createSchemaButton;
	JButton createDBButton;

	public static void main(String[] args) {
		new App().createGUI();
	}

	public void createGUI() {
		final JLabel jl = new JLabel();
		jl.setBounds(50, 50, 1000, 20);
		jl.setForeground(Color.WHITE);
		createDBButton = createDatabaseButton(jl);
		frame.add(createDBButton);
		this.dropSchemaButton = dropSchemaButton(jl);
		dropSchemaButton.setVisible(false);
		this.createSchemaButton = createSchemaButton(jl);
		frame.add(createSchemaButton);
		frame.add(dropSchemaButton);
		frame.add(jl);
		frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		frame.addWindowListener(getWindowListener());
		createBackground(frame);
		frame.setSize(800, 800);
		frame.setLayout(null);
		renderFrame(jl);
		frame.setVisible(true);
	}

	public void renderFrame(JLabel jl) {
		try {
			if (H2Interaction.doesH2DBExists()) {
				createDBButton.setVisible(false);
				if (H2Interaction.getH2Interaction().doesSchemaExists()) {
					dropSchemaButton.setVisible(true);
					createSchemaButton.setVisible(false);
				} else {
					dropSchemaButton.setVisible(false);
					createSchemaButton.setVisible(true);
				}
			} else {
				createDBButton.setVisible(true);
			}

		} catch (Exception e) {
			jl.setText(ErrorMessage.FRAME_RENDERING_FAILED.message);
		}
	}

	public void refreshFrame() {
		// frame.revalidate();
		frame.repaint();
	}

	public JButton createDatabaseButton(JLabel jl) {
		JButton createDBButton = new JButton("Create H2 Database");
		createDBButton.setBounds(50, 100, 150, 30);
		createDBButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (createDatabase(jl)) {
					createSchemaButton.setVisible(true);
					createDBButton.setVisible(false);
				} else {
					createDBButton.setVisible(true);
				}
			}
		});
		return createDBButton;
	}

	public JButton createSchemaButton(JLabel jl) {
		JButton schemaButton = new JButton("Create Database Schema ");
		schemaButton.setBounds(50, 150, 200, 30);
		schemaButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (createSchema(jl)) {
					schemaButton.setVisible(false);
					dropSchemaButton.setVisible(true);
				}
			}
		});
		return schemaButton;
	}

	public JButton dropSchemaButton(JLabel jl) {
		JButton dropSchemaButton = new JButton("Drop Database Schema ");
		dropSchemaButton.setBounds(50, 150, 200, 30);
		dropSchemaButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (dropSchema(jl)) {
					dropSchemaButton.setVisible(false);
					createSchemaButton.setVisible(true);
				}
			}
		});
		return dropSchemaButton;
	}

	public boolean createDatabase(JLabel jl) {
		try {
			H2Interaction.getH2Interaction();
			jl.setText("Database Created at :: " + Properties.H2_PATH);
			return true;
		} catch (Exception exception) {
			jl.setText(ErrorMessage.DB_CREATION_FAILED.message);
			return false;
		}
	}

	public boolean createSchema(JLabel jl) {
		try {
			H2Interaction.getH2Interaction().createSchema();
			jl.setText("Schema Created with file ::" + Properties.H2_SCHEMA);
			return true;
		} catch (Exception exception) {
			jl.setText(ErrorMessage.SCHEMA_CREATION_FAILED.message);
			return false;
		}
	}

	public boolean dropSchema(JLabel jl) {
		try {
			H2Interaction.getH2Interaction().dropSchema();
			jl.setText(Properties.SCHEMA_NAME + " Schema dropped ");
			return true;
		} catch (Exception exception) {
			jl.setText(ErrorMessage.SCHEMA_DROP_FAILED.message);
			return false;
		}
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
				try {
					H2Interaction.getH2Interaction().releaseResources();
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

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
