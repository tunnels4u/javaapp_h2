package com.softwaretunnel.javaapp_h2;

import java.awt.Color;
import java.awt.Component;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.ResourceBundle;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.WindowConstants;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.plaf.ButtonUI;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;

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
	JButton dropDBButton;
	ResourceBundle resourceBundle = ResourceBundle.getBundle("system");

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
		this.createSchemaButton = createSchemaButton(jl);
		this.dropDBButton = dropDBButton(jl);
		frame.add(createSchemaButton);
		frame.add(dropDBButton);
		frame.add(dropSchemaButton);

		JTable jtable = createTable();
		frame.setContentPane(new JScrollPane(jtable));

		frame.add(jl);
		frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		frame.addWindowListener(getWindowListener());
		createBackground(frame);
		frame.setSize(800, 800);
		// frame.setLayout(null);
		renderFrame(jl);
		frame.setVisible(true);
	}

	public void renderFrame(JLabel jl) {
		try {
			if (H2Interaction.doesH2DBExists()) {
				createDBButton.setVisible(false);
				dropDBButton.setVisible(true);
				if (H2Interaction.getH2Interaction().doesSchemaExists()) {
					dropSchemaButton.setVisible(true);
					createSchemaButton.setVisible(false);
				} else {
					dropSchemaButton.setVisible(false);
					createSchemaButton.setVisible(true);
				}
			} else {
				createDBButton.setVisible(true);
				dropDBButton.setVisible(false);
				dropSchemaButton.setVisible(false);
				createSchemaButton.setVisible(false);
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
					dropDBButton.setVisible(true);
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

	public JButton dropDBButton(JLabel jl) {
		JButton dropDBButton = new JButton("Drop Database");
		dropDBButton.setBounds(50, 100, 150, 30);
		dropDBButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (dropDB(jl)) {
					dropDBButton.setVisible(false);
					createDBButton.setVisible(true);
					dropSchemaButton.setVisible(false);
					createSchemaButton.setVisible(false);

				}
			}
		});
		return dropDBButton;
	}

	public boolean createDatabase(JLabel jl) {
		try {
			H2Interaction.getH2Interaction();
			jl.setText("Database Created at :: " + resourceBundle.getString("H2_PATH"));
			return true;
		} catch (Exception exception) {
			jl.setText(ErrorMessage.DB_CREATION_FAILED.message);
			return false;
		}
	}

	public boolean createSchema(JLabel jl) {
		try {
			H2Interaction.getH2Interaction().createSchema();
			jl.setText("Schema Created with file ::" + resourceBundle.getString("H2_SCHEMA"));
			return true;
		} catch (Exception exception) {
			jl.setText(ErrorMessage.SCHEMA_CREATION_FAILED.message);
			return false;
		}
	}

	public boolean dropSchema(JLabel jl) {
		try {
			H2Interaction.getH2Interaction().dropSchema();
			jl.setText(resourceBundle.getString("SCHEMA_NAME") + " Schema dropped ");
			return true;
		} catch (Exception exception) {
			jl.setText(ErrorMessage.SCHEMA_DROP_FAILED.message);
			return false;
		}
	}

	public boolean dropDB(JLabel jl) {
		try {
			H2Interaction.getH2Interaction().dropDatabase();
			jl.setText("Database dropped ");
			return true;
		} catch (Exception exception) {
			jl.setText(ErrorMessage.DATABASE_DROP_FAILED.message);
			return false;
		}
	}

	public void createBackground(JFrame jf) {
		ImageIcon background = new ImageIcon(resourceBundle.getString("IMAGES_PATH") + "tunnel.jpg");
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

	public JTable createTestTable() {
		String[] columnNames = { "First Name", "Last Name", "Test1" };
		Object[][] data = { { "Homer", "Simpson", getRemoveButton() }, { "Madge", "Simpson", getRemoveButton() },
				{ "Bart", "Simpson", getRemoveButton() }, { "Lisa", "Simpson", getRemoveButton() }, };

		DefaultTableModel model = new DefaultTableModel(data, columnNames);
		/*
		 * {
		 * 
		 * public int getColumnCount() { return 3; }
		 * 
		 * public int getRowCount() { return 4; }
		 * 
		 * public Object getValueAt(int row, int col) { return new Integer(row * col); }
		 * 
		 * public boolean isCellEditable(int row, int col) { return true; } };
		 */
		JTable table = new JTable(model) {
			public boolean isCellEditable(int row, int column) {
				if (column == 2) {
					return false;
				} else {
					return true;
				}
			}
		};
		TableCellRenderer buttonRenderer = new JTableButtonRenderer();
		table.getColumn("Test1").setCellRenderer(buttonRenderer);
		// table.setEditingColumn(3);
		table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
		return table;

	}
	
	public JTable createTable() {
		String[] columnNames = { "First Name", "Last Name", "Test1" };
		Object[][] data = { { "Homer", "Simpson", getRemoveButton() }, { "Madge", "Simpson", getRemoveButton() },
				{ "Bart", "Simpson", getRemoveButton() }, { "Lisa", "Simpson", getRemoveButton() }, };

	    JTable table = new JTable(data, columnNames){
			public boolean isCellEditable(int row, int column) {
				if (column == 2) {
					return false;
				} else {
					return true;
				}
			}
		};
		
		TableCellRenderer buttonRenderer = new JTableButtonRenderer();
		table.getColumn("Test1").setCellRenderer(buttonRenderer);
		
		table.addMouseListener(new java.awt.event.MouseAdapter() {
		    @Override
		    public void mouseClicked(java.awt.event.MouseEvent evt) {
		        int row = table.rowAtPoint(evt.getPoint());
		        int col = table.columnAtPoint(evt.getPoint());
		        if (col==2) {
		           System.out.println("I am here"+ table.getValueAt(row,0));
		           
		        }
		    }
		});
//		table.setCellSelectionEnabled(true);
//
//	    ListSelectionModel cellSelectionModel = table.getSelectionModel();
//	    cellSelectionModel.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
//
//	    cellSelectionModel.addListSelectionListener(new ListSelectionListener() {
//	    	
//	      public void valueChanged(ListSelectionEvent e) {
//	        String selectedData = null;
//
//	        int[] selectedRow = table.getSelectedRows();
//	        int[] selectedColumns = table.getSelectedColumns();
//
//	        for (int i = 0; i < selectedRow.length; i++) {
//	          for (int j = 0; j < selectedColumns.length; j++) {
//	            selectedData = (String) table.getValueAt(selectedRow[i], selectedColumns[j]);
//	          }
//	        }
//	        System.out.println("Selected: " + selectedData);
//	      }
//
//	    });
//
//	    frame.add(new JScrollPane(table));
		return table;

	}

	public JButton getRemoveButton() {
		JButton deleteRowButton = new JButton("Delete");
		deleteRowButton.setOpaque(true);
		deleteRowButton.setContentAreaFilled(true);
		deleteRowButton.setBorderPainted(false);
		deleteRowButton.setFocusPainted(false);
		deleteRowButton.setForeground(Color.RED);
		deleteRowButton.setBackground(Color.GRAY);
//		deleteRowButton.addActionListener(new ActionListener() {
//			public void actionPerformed(ActionEvent actionEvent) {
//				JTable employeeTable = (JTable) actionEvent.getSource();
//				int modelRow = Integer.valueOf(actionEvent.getActionCommand());
//				((DefaultTableModel) employeeTable.getModel()).removeRow(modelRow);
//				System.out.println("button");
//			}
//		});
//		deleteRowButton.setOpaque(true);
		return deleteRowButton;

	}

	private static class JTableButtonRenderer implements TableCellRenderer {
		@Override
		public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
				int row, int column) {

			JButton jbutton = (JButton) value;
			//jbutton.setBackground(Color.RED);
			

			// deleteRowButton.setOpaque(true);
			return jbutton;
		}

	}
}
