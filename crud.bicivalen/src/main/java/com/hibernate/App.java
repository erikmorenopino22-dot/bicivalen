package com.hibernate;

import java.awt.EventQueue;
import java.awt.Font;

import javax.swing.JFrame;
import javax.swing.JTabbedPane;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.UIManager;

import com.formdev.flatlaf.FlatLightLaf;
import com.github.lgooddatepicker.components.TimePicker;

import javax.swing.JPasswordField;
import javax.swing.JTable;
import javax.swing.JComboBox;
import javax.swing.JTextArea;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.DefaultComboBoxModel;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;


public class App {

	private JFrame Admin;
	private JTextField txtIdU;
	private JTextField txtNomU;
	private JTextField txtEmailU;
	private JTextField txtBonoU;
	private JPasswordField passwordU;
	private JTable tableU;
	private JTextField txtIdR;
	private JTextField txtUsuarioR;
	private JTextField txtEstaciónOR;
	private JTextField txtEstaciónDR;
	private JTextField txtDuraciónR;
	private JTextField txtReseñaR;
	private JTable tableR;
	private JTextField txtIdE;
	private JTextField txtNomE;
	private JTextField txtCapacidadE;
	private JTextField txtPuestosE;
	private JTable tableE;
	private JTextField txtIdB;
	private JTextField txtidEstacionB;
	private JTable tableB;
	private JTextField txtIdBn;
	private JTextField txtNomBn;
	private JTable tableBn;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				
				try {
					FlatLightLaf.setup();
					UIManager.put("Button.arc", 15);
					UIManager.put("Component.arc", 15);
					UIManager.put("TextComponent.arc", 10);
					UIManager.put("Component.focusWidth", 1);
					UIManager.put("Button.innerFocusWidth", 0);
					UIManager.put("Button.arc", 20);
					UIManager.put("defaultFont", new Font("Segoe UI", Font.PLAIN, 14));
				} catch (Exception e) {
					e.printStackTrace();
				}
				try {
					App window = new App();
					window.Admin.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public App() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		Admin = new JFrame();
		Admin.setTitle("BiciValen-Admin");
		Admin.setBounds(100, 100, 745, 600);
		Admin.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Admin.getContentPane().setLayout(null);
		
		JButton btnMostrar = new JButton("Mostrar");
		btnMostrar.setVisible(false);
		btnMostrar.setBounds(35, 25, 105, 27);
		Admin.getContentPane().add(btnMostrar);
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setBounds(106, 103, 577, 301);
		Admin.getContentPane().add(tabbedPane);
		
		JPanel panelU = new JPanel();
		tabbedPane.addTab("Usuario", null, panelU, null);
		panelU.setLayout(null);
		
		JLabel lblId = new JLabel("Id:");
		lblId.setBounds(12, 123, 16, 17);
		panelU.add(lblId);
		
		JLabel lblNom = new JLabel("Nom:");
		lblNom.setBounds(12, 152, 60, 17);
		panelU.add(lblNom);
		
		JLabel lblEmail = new JLabel("Email:");
		lblEmail.setBounds(12, 181, 60, 17);
		panelU.add(lblEmail);
		
		JLabel lblPwd = new JLabel("Pw:");
		lblPwd.setBounds(12, 210, 44, 17);
		panelU.add(lblPwd);
		
		JLabel lblBono = new JLabel("Bono:");
		lblBono.setBounds(12, 243, 60, 17);
		panelU.add(lblBono);
		
		txtIdU = new JTextField();
		txtIdU.setEditable(false);
		txtIdU.setBounds(67, 121, 114, 21);
		panelU.add(txtIdU);
		txtIdU.setColumns(10);
		
		txtNomU = new JTextField();
		txtNomU.setBounds(67, 150, 114, 21);
		panelU.add(txtNomU);
		txtNomU.setColumns(10);
		
		txtEmailU = new JTextField();
		txtEmailU.setBounds(67, 179, 114, 21);
		panelU.add(txtEmailU);
		txtEmailU.setColumns(10);
		
		txtBonoU = new JTextField();
		txtBonoU.setBounds(67, 241, 114, 21);
		panelU.add(txtBonoU);
		txtBonoU.setColumns(10);
		
		passwordU = new JPasswordField();
		passwordU.setBounds(67, 208, 114, 21);
		panelU.add(passwordU);
		
		tableU = new JTable();
		tableU.setBounds(12, 12, 548, 99);
		panelU.add(tableU);
		
		JButton btnAddU = new JButton("Add");
		btnAddU.setBounds(455, 118, 105, 27);
		panelU.add(btnAddU);
		
		JButton btnEditU = new JButton("Edit");
		btnEditU.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnEditU.setBounds(455, 176, 105, 27);
		panelU.add(btnEditU);
		
		JButton btnDelU = new JButton("Del");
		btnDelU.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnDelU.setBounds(455, 233, 105, 27);
		panelU.add(btnDelU);
		
		JPanel panelR = new JPanel();
		tabbedPane.addTab("Rutas", null, panelR, null);
		panelR.setLayout(null);
		
		JLabel lblId_1 = new JLabel("Id:");
		lblId_1.setBounds(12, 146, 16, 17);
		panelR.add(lblId_1);
		
		JLabel lblUsuario = new JLabel("Usuario:");
		lblUsuario.setBounds(12, 173, 60, 17);
		panelR.add(lblUsuario);
		
		JLabel lblEstacino = new JLabel("EstaciónO:");
		lblEstacino.setBounds(12, 200, 72, 17);
		panelR.add(lblEstacino);
		
		JLabel lblEstacind = new JLabel("EstaciónD:");
		lblEstacind.setBounds(12, 225, 72, 17);
		panelR.add(lblEstacind);
		
		JLabel lblDuracin = new JLabel("Duración:");
		lblDuracin.setBounds(218, 146, 60, 17);
		panelR.add(lblDuracin);
		
		JLabel lblResea = new JLabel("Reseña:");
		lblResea.setBounds(218, 173, 60, 17);
		panelR.add(lblResea);
		
		txtIdR = new JTextField();
		txtIdR.setEditable(false);
		txtIdR.setBounds(90, 144, 114, 21);
		panelR.add(txtIdR);
		txtIdR.setColumns(10);
		
		txtUsuarioR = new JTextField();
		txtUsuarioR.setBounds(90, 171, 114, 21);
		panelR.add(txtUsuarioR);
		txtUsuarioR.setColumns(10);
		
		txtEstaciónOR = new JTextField();
		txtEstaciónOR.setBounds(90, 198, 114, 21);
		panelR.add(txtEstaciónOR);
		txtEstaciónOR.setColumns(10);
		
		txtEstaciónDR = new JTextField();
		txtEstaciónDR.setBounds(90, 223, 114, 21);
		panelR.add(txtEstaciónDR);
		txtEstaciónDR.setColumns(10);
		
		txtDuraciónR = new JTextField();
		txtDuraciónR.setBounds(285, 144, 114, 21);
		panelR.add(txtDuraciónR);
		txtDuraciónR.setColumns(10);
		
		txtReseñaR = new JTextField();
		txtReseñaR.setBounds(285, 171, 114, 21);
		panelR.add(txtReseñaR);
		txtReseñaR.setColumns(10);
		
		
		
		tableR = new JTable();
		tableR.setBounds(12, 12, 548, 99);
		panelR.add(tableR);
		
		JButton btnAddR = new JButton("Add");
		btnAddR.setBounds(455, 118, 105, 27);
		panelR.add(btnAddR);
		
		JButton btnEditR = new JButton("Edit");
		btnEditR.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnEditR.setBounds(455, 176, 105, 27);
		panelR.add(btnEditR);
		
		JButton btnDelR = new JButton("Del");
		btnDelR.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnDelR.setBounds(455, 233, 105, 27);
		panelR.add(btnDelR);
		
		JPanel panelE = new JPanel();
		tabbedPane.addTab("Estación", null, panelE, null);
		panelE.setLayout(null);
		
		JLabel lblId_2 = new JLabel("Id:");
		lblId_2.setBounds(12, 123, 60, 17);
		panelE.add(lblId_2);
		
		JLabel lblNewLabel = new JLabel("Nom:");
		lblNewLabel.setBounds(12, 152, 60, 17);
		panelE.add(lblNewLabel);
		
		JLabel lblCapacidad = new JLabel("Capacidad:");
		lblCapacidad.setBounds(12, 181, 77, 17);
		panelE.add(lblCapacidad);
		
		JLabel lblPuestos = new JLabel("Puestos:");
		lblPuestos.setBounds(12, 210, 60, 17);
		panelE.add(lblPuestos);
		
		JLabel lblBicicletas = new JLabel("Bicicletas:");
		lblBicicletas.setBounds(12, 239, 60, 17);
		panelE.add(lblBicicletas);
		
		txtIdE = new JTextField();
		txtIdE.setEditable(false);
		txtIdE.setBounds(106, 121, 114, 21);
		panelE.add(txtIdE);
		txtIdE.setColumns(10);
		
		txtNomE = new JTextField();
		txtNomE.setBounds(106, 150, 114, 21);
		panelE.add(txtNomE);
		txtNomE.setColumns(10);
		
		txtCapacidadE = new JTextField();
		txtCapacidadE.setBounds(107, 179, 114, 21);
		panelE.add(txtCapacidadE);
		txtCapacidadE.setColumns(10);
		
		txtPuestosE = new JTextField();
		txtPuestosE.setBounds(106, 208, 114, 21);
		panelE.add(txtPuestosE);
		txtPuestosE.setColumns(10);
		
		JComboBox comboBoxBicis = new JComboBox();
		comboBoxBicis.setBounds(106, 234, 114, 26);
		panelE.add(comboBoxBicis);
		
		JLabel lblDisponible_1 = new JLabel("Disponible:");
		lblDisponible_1.setBounds(238, 123, 77, 17);
		panelE.add(lblDisponible_1);
		
		JCheckBox checkBoxE = new JCheckBox("");
		checkBoxE.setBounds(308, 119, 114, 25);
		panelE.add(checkBoxE);
		
		tableE = new JTable();
		tableE.setBounds(12, 12, 548, 99);
		panelE.add(tableE);
		
		JButton btnAddE = new JButton("Add");
		btnAddE.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			}
		});
		btnAddE.setBounds(455, 118, 105, 27);
		panelE.add(btnAddE);
		
		JButton btnEditE = new JButton("Edit");
		btnEditE.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnEditE.setBounds(455, 176, 105, 27);
		panelE.add(btnEditE);
		
		JButton btnDelE = new JButton("Del");
		btnDelE.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnDelE.setBounds(455, 233, 105, 27);
		panelE.add(btnDelE);
		
		JPanel panelB = new JPanel();
		tabbedPane.addTab("Bicicleta", null, panelB, null);
		panelB.setLayout(null);
		
		JLabel lblId_3 = new JLabel("Id:");
		lblId_3.setBounds(12, 115, 60, 17);
		panelB.add(lblId_3);
		
		JLabel lblTipo = new JLabel("Tipo:");
		lblTipo.setBounds(12, 146, 60, 17);
		panelB.add(lblTipo);
		
		JLabel lblEstado = new JLabel("Estado:");
		lblEstado.setBounds(12, 177, 60, 17);
		panelB.add(lblEstado);
		
		JLabel lblDondeEsta = new JLabel("Donde esta:");
		lblDondeEsta.setBounds(12, 206, 84, 17);
		panelB.add(lblDondeEsta);
		
		txtIdB = new JTextField();
		txtIdB.setEditable(false);
		txtIdB.setBounds(90, 113, 114, 21);
		panelB.add(txtIdB);
		txtIdB.setColumns(10);
		
		JComboBox comboBoxTipoB = new JComboBox();
		comboBoxTipoB.setModel(new DefaultComboBoxModel(new String[] {"Electrica", "Basica"}));
		comboBoxTipoB.setBounds(90, 141, 114, 26);
		panelB.add(comboBoxTipoB);
		
		JComboBox comboBoxEstadoB = new JComboBox();
		comboBoxEstadoB.setModel(new DefaultComboBoxModel(new String[] {"Genial", "Bueno", "Malo"}));
		comboBoxEstadoB.setBounds(90, 172, 114, 26);
		panelB.add(comboBoxEstadoB);
		
		txtidEstacionB = new JTextField();
		txtidEstacionB.setBounds(90, 204, 114, 21);
		panelB.add(txtidEstacionB);
		txtidEstacionB.setColumns(10);
		
		JLabel lblDisponible = new JLabel("Disponible:");
		lblDisponible.setBounds(12, 235, 68, 17);
		panelB.add(lblDisponible);
		
		JCheckBox checkBoxB = new JCheckBox("");
		checkBoxB.setBounds(90, 231, 114, 25);
		panelB.add(checkBoxB);
		
		tableB = new JTable();
		tableB.setBounds(12, 12, 548, 99);
		panelB.add(tableB);
		
		JButton btnAddB = new JButton("Add");
		btnAddB.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnAddB.setBounds(455, 118, 105, 27);
		panelB.add(btnAddB);
		
		JButton btnEditB = new JButton("Edit");
		btnEditB.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnEditB.setBounds(455, 176, 105, 27);
		panelB.add(btnEditB);
		
		JButton btnDelB = new JButton("Del");
		btnDelB.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnDelB.setBounds(455, 233, 105, 27);
		panelB.add(btnDelB);
		
		JPanel panelBn = new JPanel();
		tabbedPane.addTab("Bono", null, panelBn, null);
		panelBn.setLayout(null);
		
		JLabel lblId_4 = new JLabel("Id:");
		lblId_4.setBounds(12, 125, 60, 17);
		panelBn.add(lblId_4);
		
		JLabel lblNombre = new JLabel("Nombre:");
		lblNombre.setBounds(12, 154, 60, 17);
		panelBn.add(lblNombre);
		
		JLabel lblDuracin_1 = new JLabel("Duración:");
		lblDuracin_1.setBounds(12, 183, 60, 17);
		panelBn.add(lblDuracin_1);
		
		JLabel lblDescripcin = new JLabel("Descripción:");
		lblDescripcin.setBounds(12, 212, 73, 17);
		panelBn.add(lblDescripcin);
		
		txtIdBn = new JTextField();
		txtIdBn.setEditable(false);
		txtIdBn.setBounds(95, 123, 114, 21);
		panelBn.add(txtIdBn);
		txtIdBn.setColumns(10);
		
		txtNomBn = new JTextField();
		txtNomBn.setBounds(95, 152, 114, 21);
		panelBn.add(txtNomBn);
		txtNomBn.setColumns(10);
		
		JTextArea DescripcionBn = new JTextArea();
		DescripcionBn.setBounds(95, 212, 225, 48);
		panelBn.add(DescripcionBn);
		
		TimePicker horaSalida = new TimePicker();
		horaSalida.setBounds(95, 181, 114, 21);
		panelBn.add(horaSalida);
		
		tableBn = new JTable();
		tableBn.setBounds(12, 12, 548, 99);
		panelBn.add(tableBn);
		
		JButton btnAddBn = new JButton("Add");
		btnAddBn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnAddBn.setBounds(455, 118, 105, 27);
		panelBn.add(btnAddBn);
		
		JButton btnDelBn = new JButton("Del");
		btnDelBn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnDelBn.setBounds(455, 233, 105, 27);
		panelBn.add(btnDelBn);
		
		JButton btnEditBn = new JButton("Edit");
		btnEditBn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnEditBn.setBounds(455, 176, 105, 27);
		panelBn.add(btnEditBn);
		
		
	}
	public JFrame getFrame() {
	    return Admin;
	}
}