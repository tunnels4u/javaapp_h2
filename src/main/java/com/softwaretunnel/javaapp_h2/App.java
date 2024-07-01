package com.softwaretunnel.javaapp_h2;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
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
import javax.swing.ScrollPaneLayout;
import javax.swing.WindowConstants;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.plaf.ButtonUI;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;

import com.softwaretunnel.javaapp_h2.persistance.H2Interaction;
import com.softwaretunnel.javaapp_h2.persistance.domain.Employee;

import javafx.scene.layout.Border;

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
	JButton addEmployeeButton;
	JTable jtable;
	JLabel jl;
	ResourceBundle resourceBundle = ResourceBundle.getBundle("system");
	boolean schemaExists = false;

	ArrayList<Employee> employees = new ArrayList<Employee>();

	public static void main(String[] args) {
		new App().createGUI();
	}

	public void createGUI() {
		this.jl = new JLabel();
		this.jl.setBounds(50, 50, 1000, 20);
		this.jl.setForeground(Color.WHITE);

		this.createDBButton = createDatabaseButton();
		this.frame.add(createDBButton);
		this.dropSchemaButton = dropSchemaButton();
		this.createSchemaButton = createSchemaButton();
		this.dropDBButton = dropDBButton();
		this.addEmployeeButton = getAddButton();

		this.jtable = createTable(jl);
		JScrollPane jScrollPane = new JScrollPane(jtable);
		jScrollPane.setBounds(50, 300, 700, 200);
		jScrollPane.setOpaque(false);
		this.frame.add(jScrollPane);

		this.frame.add(addEmployeeButton);
		this.frame.add(createSchemaButton);
		this.frame.add(dropDBButton);
		this.frame.add(dropSchemaButton);
		this.frame.add(jl);
		this.frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		this.frame.addWindowListener(getWindowListener());
		this.frame.add(createBackground());
		this.frame.setSize(800, 800);

		renderFrameAndData();
		this.frame.setVisible(true);
	}

	public void renderFrameAndData() {
		try {
			if (H2Interaction.doesH2DBExists()) {
				createDBButton.setVisible(false);
				dropDBButton.setVisible(true);
				if (H2Interaction.getH2Interaction().doesSchemaExists()) {
					dropSchemaButton.setVisible(true);
					createSchemaButton.setVisible(false);
					addEmployeeButton.setVisible(true);
					schemaExists = true;
					refreshModel();
				} else {
					dropSchemaButton.setVisible(false);
					createSchemaButton.setVisible(true);
					addEmployeeButton.setVisible(false);
				}
			} else {
				createDBButton.setVisible(true);
				dropDBButton.setVisible(false);
				dropSchemaButton.setVisible(false);
				createSchemaButton.setVisible(false);
				addEmployeeButton.setVisible(false);
			}

		} catch (Exception e) {
			jl.setText(ErrorMessage.FRAME_RENDERING_FAILED.message);
		}
	}

	public void refreshFrame() {
		frame.repaint();
	}

	public JButton createDatabaseButton() {
		JButton createDBButton = new JButton("Create H2 Database");
		createDBButton.setBounds(50, 100, 150, 30);
		createDBButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (createDatabase()) {
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

	public JButton createSchemaButton() {
		JButton schemaButton = new JButton("Create Database Schema ");
		schemaButton.setBounds(50, 150, 200, 30);
		schemaButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (createSchema()) {
					schemaButton.setVisible(false);
					dropSchemaButton.setVisible(true);
					addEmployeeButton.setVisible(true);
					schemaExists = true;
				}
			}
		});
		return schemaButton;
	}

	public JButton dropSchemaButton() {
		JButton dropSchemaButton = new JButton("Drop Database Schema ");
		dropSchemaButton.setBounds(50, 150, 200, 30);
		dropSchemaButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (dropSchema()) {
					dropSchemaButton.setVisible(false);
					createSchemaButton.setVisible(true);
					addEmployeeButton.setVisible(false);
					schemaExists = false;
					refreshModel();
				}
			}
		});
		return dropSchemaButton;
	}

	public JButton dropDBButton() {
		JButton dropDBButton = new JButton("Drop Database");
		dropDBButton.setBounds(50, 100, 150, 30);
		dropDBButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (dropDB()) {
					dropDBButton.setVisible(false);
					createDBButton.setVisible(true);
					dropSchemaButton.setVisible(false);
					createSchemaButton.setVisible(false);
					addEmployeeButton.setVisible(false);
					schemaExists = false;
					refreshModel();
				}
			}
		});
		return dropDBButton;
	}

	public JButton getRemoveButton() {
		JButton deleteRowButton = new JButton("Delete");
		deleteRowButton.setOpaque(true);
		deleteRowButton.setContentAreaFilled(true);
		deleteRowButton.setBorderPainted(false);
		deleteRowButton.setFocusPainted(false);
		deleteRowButton.setForeground(Color.RED);
		deleteRowButton.setBackground(Color.GRAY);
		/*
		 * actionListener here won't work since this button is used inside a table with
		 * a cell renderer
		 */
		return deleteRowButton;

	}

	public JButton getSaveButton() {
		JButton deleteRowButton = new JButton("Save");
		deleteRowButton.setOpaque(true);
		deleteRowButton.setContentAreaFilled(true);
		deleteRowButton.setBorderPainted(false);
		deleteRowButton.setFocusPainted(false);
		deleteRowButton.setForeground(Color.GREEN);
		deleteRowButton.setBackground(Color.GRAY);
		/*
		 * actionListener here won't work since this button is used inside a table with
		 * a cell renderer
		 */
		return deleteRowButton;

	}

	public JButton getAddButton() {
		JButton addRowButton = new JButton("Add Employee");
		addRowButton.setForeground(Color.GREEN);
		addRowButton.setBackground(Color.GRAY);
		addRowButton.setBounds(50, 250, 200, 30);
		addRowButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Object[] row = { null, "", "", getSaveButton(), getRemoveButton() };
				((DefaultTableModel) jtable.getModel()).addRow(row);
			}
		});
		return addRowButton;

	}

	public boolean createDatabase() {
		try {
			H2Interaction.getH2Interaction();
			jl.setText("Database Created at :: " + resourceBundle.getString("H2_PATH"));
			return true;
		} catch (Exception exception) {
			jl.setText(ErrorMessage.DB_CREATION_FAILED.message);
			return false;
		}
	}

	public boolean createSchema() {
		try {
			H2Interaction.getH2Interaction().createSchema();
			jl.setText("Schema Created with file ::" + resourceBundle.getString("H2_SCHEMA"));
			return true;
		} catch (Exception exception) {
			jl.setText(ErrorMessage.SCHEMA_CREATION_FAILED.message);
			return false;
		}
	}

	public boolean dropSchema() {
		try {
			H2Interaction.getH2Interaction().dropSchema();
			jl.setText(resourceBundle.getString("SCHEMA_NAME") + " Schema dropped ");
			return true;
		} catch (Exception exception) {
			jl.setText(ErrorMessage.SCHEMA_DROP_FAILED.message);
			return false;
		}
	}

	public boolean dropDB() {
		try {
			H2Interaction.getH2Interaction().dropDatabase();
			jl.setText("Database dropped ");
			return true;
		} catch (Exception exception) {
			jl.setText(ErrorMessage.DATABASE_DROP_FAILED.message);
			return false;
		}
	}

	/**
	 * This code snippet was taken from
	 * https://stackoverflow.com/questions/1466240/how-to-set-an-image-as-a-background-for-frame-in-swing-gui-of-java
	 */
	public JLabel createBackground() {
		ImageIcon background = new ImageIcon(resourceBundle.getString("IMAGES_PATH") + "tunnel.jpg");
		Image img = background.getImage();
		Image temp = img.getScaledInstance(800, 1000, Image.SCALE_SMOOTH);
		background = new ImageIcon(temp);
		JLabel back = new JLabel(background);
		back.setLayout(null);
		back.setBounds(0, 0, 800, 800);
		back.setOpaque(false);
		return back;
	}

	public JTable createTable(JLabel jl) {
		String[] columnNames = { "Employee ID", "First Name", "Last Name", "Perform Save Action",
				"Perform Delete Action" };
		Object[][] data = null;

		DefaultTableModel model = new DefaultTableModel(data, columnNames);
		JTable table = new JTable(model) {
			public boolean isCellEditable(int row, int column) {
				if (column == 0 || column == 3 || column == 4) {
					return false;
				} else {
					return true;
				}
			}
		};

		TableCellRenderer buttonRenderer = new JTableButtonRenderer();
		table.getColumn("Perform Delete Action").setCellRenderer(buttonRenderer);
		table.getColumn("Perform Save Action").setCellRenderer(buttonRenderer);
		table.setBackground(Color.GREEN);
		table.setOpaque(false);

		table.addMouseListener(new java.awt.event.MouseAdapter() {
			@Override
			public void mouseClicked(java.awt.event.MouseEvent evt) {
				int row = table.rowAtPoint(evt.getPoint());
				int col = table.columnAtPoint(evt.getPoint());
				if (col == 4) {
					if (table.getValueAt(row, 0) != null) {
						try {
							H2Interaction.getH2Interaction().deleteEmployeeRecords(employees.get(row));
							refreshModel();
							jl.setText(ErrorMessage.DELETE_SUCCESS.message);
						} catch (Exception e) {
							jl.setText(ErrorMessage.DELETE_FAILED.message);

						}
					} else {
						((DefaultTableModel) table.getModel()).removeRow(row);
					}
				}
				if (col == 3) {
					Employee employee = new Employee();
					employee.setID((Integer) table.getValueAt(row, 0));
					employee.setFirstName((String) table.getValueAt(row, 1));
					employee.setLastName((String) table.getValueAt(row, 2));
					if (table.getValueAt(row, 0) == null) {
						try {
							H2Interaction.getH2Interaction().insertEmployeeRecords(employee);
							refreshModel();
							jl.setText(ErrorMessage.INSERT_SUCCESS.message);
						} catch (Exception e) {
							jl.setText(ErrorMessage.INSERT_FAILED.message);

						}
					} else {
						try {
							H2Interaction.getH2Interaction().updateEmployeeRecords(employee);
							refreshModel();
							jl.setText(ErrorMessage.UPDATE_SUCCESS.message);
						} catch (Exception e) {
							jl.setText(ErrorMessage.UPDATE_FAILED.message);

						}
					}
				}
			}
		});

		return table;

	}

	public Object[][] getFreshModelData() {

		Object[][] data = null;
		try {
			employees.removeAll(employees);
			employees.addAll(H2Interaction.getH2Interaction().getEmployeeRecords());
			if (!employees.isEmpty()) {
				data = new Object[employees.size()][5];
				for (int i = 0; i < employees.size(); i++) {
					data[i][0] = employees.get(i).getID();
					data[i][1] = employees.get(i).getFirstName();
					data[i][2] = employees.get(i).getLastName();
					data[i][3] = getSaveButton();
					data[i][4] = getRemoveButton();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			jl.setText(ErrorMessage.FETCH_FAILED.message);

		}
		return data;
	}

	public void refreshModel() {
		DefaultTableModel model = (DefaultTableModel) jtable.getModel();
		model.setRowCount(0);
		if (schemaExists) {
			Object[][] data = getFreshModelData();
			if (data != null) {
				for (int i = 0; i < data.length; i++) {
					model.addRow(data[i]);
				}
			}
		}
	}

	private static class JTableButtonRenderer implements TableCellRenderer {
		@Override
		public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
				int row, int column) {

			JButton jbutton = (JButton) value;
			return jbutton;
		}

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
