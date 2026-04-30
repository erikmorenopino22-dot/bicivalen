package com.hibernate;

import java.awt.EventQueue;
import java.awt.Font;

import javax.swing.JFrame;
import javax.swing.JTable;
import javax.swing.JLabel;
import javax.swing.JComboBox;
import javax.swing.JButton;
import javax.swing.JTabbedPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.UIManager;

import com.formdev.flatlaf.FlatLightLaf;

public class App3 {

	private JFrame Usuario;
	private JTable tableBicicletas;
	private JTable tableRutas;
	private JTable tableBonos;
	private JTable tableB;
	private JTextField txtValoraciónB;
	private JTable tableR;
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
					App3 window = new App3();
					window.Usuario.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public App3() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		Usuario = new JFrame();
		Usuario.setTitle("BiciValen-Menu");
		Usuario.setBounds(100, 100, 745, 600);
		Usuario.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Usuario.getContentPane().setLayout(null);

		JButton btnMostrar = new JButton("Mostrar");
		btnMostrar.setVisible(false);
		btnMostrar.setBounds(33, 25, 105, 27);
		Usuario.getContentPane().add(btnMostrar);

		JTabbedPane tabbedPane_1 = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane_1.setBounds(106, 103, 577, 301);
		Usuario.getContentPane().add(tabbedPane_1);

		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane_1.addTab("Gestión Bicis", null, tabbedPane, null);

		JPanel panelAl = new JPanel();
		tabbedPane.addTab("Alquilar", null, panelAl, null);
		panelAl.setLayout(null);

		JLabel lblEnQueEstacin = new JLabel("En que Estación Estas:");
		lblEnQueEstacin.setBounds(12, 12, 141, 17);
		panelAl.add(lblEnQueEstacin);

		JComboBox comboBoxE = new JComboBox();
		comboBoxE.setBounds(171, 7, 280, 26);
		panelAl.add(comboBoxE);

		JLabel lblEligeBici = new JLabel("Selecciona Bici:");
		lblEligeBici.setBounds(12, 41, 91, 17);
		panelAl.add(lblEligeBici);

		tableB = new JTable();
		tableB.setBounds(12, 70, 543, 90);
		panelAl.add(tableB);

		JButton btnAlquilar = new JButton("Alquilar");
		btnAlquilar.setBounds(232, 168, 105, 27);
		panelAl.add(btnAlquilar);

		JPanel panelDevol = new JPanel();
		tabbedPane.addTab("Devolver", null, panelDevol, null);
		panelDevol.setLayout(null);

		JLabel lblEnQueEstacin_1 = new JLabel("En que Estación Estas:");
		lblEnQueEstacin_1.setBounds(12, 12, 131, 17);
		panelDevol.add(lblEnQueEstacin_1);

		JComboBox comboBoxED = new JComboBox();
		comboBoxED.setBounds(171, 7, 280, 26);
		panelDevol.add(comboBoxED);

		JLabel lblValoracin = new JLabel("Valoración:");
		lblValoracin.setBounds(12, 58, 73, 17);
		panelDevol.add(lblValoracin);

		txtValoraciónB = new JTextField();
		txtValoraciónB.setBounds(91, 56, 114, 21);
		panelDevol.add(txtValoraciónB);
		txtValoraciónB.setColumns(10);

		JButton btnDevolver = new JButton("Devolver");
		btnDevolver.setBounds(232, 168, 105, 27);
		panelDevol.add(btnDevolver);

		JPanel panelR = new JPanel();
		tabbedPane_1.addTab("Tus Rutas", null, panelR, null);
		panelR.setLayout(null);

		tableR = new JTable();
		tableR.setBounds(12, 12, 548, 115);
		panelR.add(tableR);

		JPanel panelBn = new JPanel();
		tabbedPane_1.addTab("Comprar Bonos", null, panelBn, null);
		panelBn.setLayout(null);

		tableBn = new JTable();
		tableBn.setBounds(12, 12, 548, 115);
		panelBn.add(tableBn);

		JButton btnComprar = new JButton("Comprar");
		btnComprar.setBounds(51, 166, 105, 27);
		panelBn.add(btnComprar);

		JButton btnMasInfo = new JButton("Mas Info");
		btnMasInfo.setBounds(387, 166, 105, 27);
		panelBn.add(btnMasInfo);

		JPanel panelA = new JPanel();
		tabbedPane_1.addTab("Necesitas Ayuda", null, panelA, null);
		panelA.setLayout(null);

		JLabel lblNecesitoAyudaCon = new JLabel("Necesito Ayuda Con:");
		lblNecesitoAyudaCon.setBounds(12, 12, 122, 17);
		panelA.add(lblNecesitoAyudaCon);

		JButton btnEliminarCuenta = new JButton("Eliminar Cuenta");
		btnEliminarCuenta.setBounds(12, 51, 241, 27);
		panelA.add(btnEliminarCuenta);

		JButton btnCambiarContrasea = new JButton("Cambiar Contraseña");
		btnCambiarContrasea.setBounds(12, 90, 241, 27);
		panelA.add(btnCambiarContrasea);

		JButton btnCambiarCorreoElectronico = new JButton("Cambiar Correo Electronico");
		btnCambiarCorreoElectronico.setBounds(12, 129, 241, 27);
		panelA.add(btnCambiarCorreoElectronico);

		JButton btnCerrarSesin = new JButton("Cerrar Sesión");
		btnCerrarSesin.setBounds(12, 168, 241, 27);
		panelA.add(btnCerrarSesin);

	}

	public JFrame getFrame() {
		return Usuario;
	}
}
