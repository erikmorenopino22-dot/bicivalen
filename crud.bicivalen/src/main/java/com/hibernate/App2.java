package com.hibernate;

import java.awt.EventQueue;
import java.awt.Font;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.UIManager;

import com.formdev.flatlaf.FlatLightLaf;

import javax.swing.JPasswordField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class App2 {

	private JFrame InicioSesion;
	private JTextField txtNombre;
	private JPasswordField passwordLogin;
	private JButton btnLogin;

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
					UIManager.put("defaultFont", new Font("Segoe UI", Font.PLAIN, 11));
				} catch (Exception e) {
					e.printStackTrace();
				}
				try {
					App2 window = new App2();
					window.InicioSesion.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public App2() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		InicioSesion = new JFrame();
		InicioSesion.setTitle("Inicio De Sesión");
		InicioSesion.setBounds(100, 100, 445, 503);
		InicioSesion.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		InicioSesion.getContentPane().setLayout(null);

		JLabel lblNombre = new JLabel("Nombre:");
		lblNombre.setBounds(111, 118, 60, 17);
		InicioSesion.getContentPane().add(lblNombre);

		JLabel lblContrasea = new JLabel("Contraseña:");
		lblContrasea.setBounds(111, 192, 91, 17);
		InicioSesion.getContentPane().add(lblContrasea);

		txtNombre = new JTextField();
		txtNombre.setBounds(111, 159, 227, 27);
		InicioSesion.getContentPane().add(txtNombre);
		txtNombre.setColumns(10);

		passwordLogin = new JPasswordField();
		passwordLogin.setBounds(111, 230, 227, 27);
		InicioSesion.getContentPane().add(passwordLogin);

		btnLogin = new JButton("Login");
		btnLogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String usuario = txtNombre.getText();
				String password = new String(passwordLogin.getPassword());
				if (usuario.equals("admin") && password.equals("1234")) {
					App ventanaAdmin = new App();
					ventanaAdmin.getFrame().setVisible(true);
					InicioSesion.dispose();
				}
				else if (usuario.equals("user") && password.equals("1234")) {
					App3 ventanaUser = new App3();
					ventanaUser.getFrame().setVisible(true);
					InicioSesion.dispose();
				}
				else {
					JOptionPane.showMessageDialog(null, "Usuario o contraseña incorrectos");
				}
			}
		});
		btnLogin.setBounds(177, 294, 105, 27);
		InicioSesion.getContentPane().add(btnLogin);

	}
}
