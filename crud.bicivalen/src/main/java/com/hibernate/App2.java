package com.hibernate;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

import com.formdev.flatlaf.FlatLightLaf;
import com.hibernate.dao.BicivalenDAO;
import com.hibernate.model.Usuario;

public class App2 {

	private JFrame InicioSesion;
	private JTextField txtEmail;
	private JPasswordField passwordLogin;

	public static void main(String[] args) {
		EventQueue.invokeLater(() -> {
			try {
				FlatLightLaf.setup();
				aplicarEstilo();
			} catch (Exception e) {
				e.printStackTrace();
			}
			try {
				App2 window = new App2();
				window.InicioSesion.setVisible(true);
			} catch (Exception e) {
				e.printStackTrace();
			}
		});
	}

	private static void aplicarEstilo() {
		UIManager.put("Button.arc", 20);
		UIManager.put("Component.arc", 15);
		UIManager.put("TextComponent.arc", 10);
		UIManager.put("Component.focusWidth", 1);
		UIManager.put("Button.innerFocusWidth", 0);
		UIManager.put("defaultFont", new Font("Segoe UI", Font.PLAIN, 12));
	}

	public App2() {
		initialize();
	}

	private void initialize() {

		BicivalenDAO bDao = new BicivalenDAO();

		InicioSesion = new JFrame("BiciValen – Inicio de Sesión");
		InicioSesion.setBounds(100, 100, 460, 540);
		InicioSesion.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		InicioSesion.setResizable(false);

		JPanel panelFondo = new JPanel() {
			@Override
			protected void paintComponent(Graphics g) {
				super.paintComponent(g);
				Graphics2D g2 = (Graphics2D) g;
				g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
				GradientPaint gp = new GradientPaint(0, 0, new Color(20, 60, 100), 0, getHeight(),
						new Color(10, 30, 60));
				g2.setPaint(gp);
				g2.fillRect(0, 0, getWidth(), getHeight());
			}
		};
		panelFondo.setLayout(null);
		InicioSesion.setContentPane(panelFondo);

		JLabel lblIcono = new JLabel("🚲", SwingConstants.CENTER);
		lblIcono.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 52));
		lblIcono.setBounds(0, 30, 460, 65);
		panelFondo.add(lblIcono);

		JLabel lblTitulo = new JLabel("BiciValen", SwingConstants.CENTER);
		lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 28));
		lblTitulo.setForeground(Color.WHITE);
		lblTitulo.setBounds(0, 100, 460, 36);
		panelFondo.add(lblTitulo);

		JLabel lblSub = new JLabel("Tu bici, tu ciudad", SwingConstants.CENTER);
		lblSub.setFont(new Font("Segoe UI", Font.PLAIN, 13));
		lblSub.setForeground(new Color(180, 210, 240));
		lblSub.setBounds(0, 136, 460, 20);
		panelFondo.add(lblSub);

		JPanel card = new JPanel() {
			@Override
			protected void paintComponent(Graphics g) {
				Graphics2D g2 = (Graphics2D) g;
				g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
				g2.setColor(new Color(255, 255, 255, 235));
				g2.fillRoundRect(0, 0, getWidth(), getHeight(), 25, 25);
			}
		};
		card.setOpaque(false);
		card.setLayout(null);
		card.setBounds(50, 175, 360, 300);
		panelFondo.add(card);

		JLabel lblEmail = new JLabel("Usuario o Email");
		lblEmail.setFont(new Font("Segoe UI", Font.BOLD, 11));
		lblEmail.setForeground(new Color(60, 80, 120));
		lblEmail.setBounds(30, 25, 130, 17);
		card.add(lblEmail);

		txtEmail = new JTextField();
		txtEmail.setFont(new Font("Segoe UI", Font.PLAIN, 13));
		txtEmail.setBorder(new LineBorder(new Color(180, 200, 230), 1, true));
		txtEmail.setBounds(30, 46, 300, 34);
		card.add(txtEmail);

		JLabel lblPwd = new JLabel("Contraseña");
		lblPwd.setFont(new Font("Segoe UI", Font.BOLD, 11));
		lblPwd.setForeground(new Color(60, 80, 120));
		lblPwd.setBounds(30, 95, 100, 17);
		card.add(lblPwd);

		passwordLogin = new JPasswordField();
		passwordLogin.setFont(new Font("Segoe UI", Font.PLAIN, 13));
		passwordLogin.setBorder(new LineBorder(new Color(180, 200, 230), 1, true));
		passwordLogin.setBounds(30, 116, 300, 34);
		card.add(passwordLogin);

		JButton btnLogin = new JButton("Iniciar Sesión");
		btnLogin.setFont(new Font("Segoe UI", Font.BOLD, 13));
		btnLogin.setBackground(new Color(41, 128, 185));
		btnLogin.setForeground(Color.WHITE);
		btnLogin.setFocusPainted(false);
		btnLogin.setBorder(new EmptyBorder(0, 0, 0, 0));
		btnLogin.setBounds(30, 175, 300, 38);
		card.add(btnLogin);
		btnLogin.setToolTipText("Acceder");

		JLabel lblO = new JLabel("── ¿No tienes cuenta? ──", SwingConstants.CENTER);
		lblO.setFont(new Font("Segoe UI", Font.PLAIN, 10));
		lblO.setForeground(new Color(140, 160, 190));
		lblO.setBounds(30, 224, 300, 17);
		card.add(lblO);

		JButton btnRegistrar = new JButton("Registrarse");
		btnRegistrar.setFont(new Font("Segoe UI", Font.BOLD, 12));
		btnRegistrar.setBackground(new Color(39, 174, 96));
		btnRegistrar.setForeground(Color.WHITE);
		btnRegistrar.setFocusPainted(false);
		btnRegistrar.setBorder(new EmptyBorder(0, 0, 0, 0));
		btnRegistrar.setBounds(30, 248, 300, 38);
		card.add(btnRegistrar);
		btnRegistrar.setToolTipText("Crear nuevo usuario");


		ActionListener loginAction = arg0 -> {
			String input = txtEmail.getText().trim();
			String password = new String(passwordLogin.getPassword());

			if (input.equals("admin") && password.equals("1234")) { 
				App ventanaAdmin = new App();
				ventanaAdmin.getFrame().setVisible(true);
				InicioSesion.dispose();
				return;
			}

			List<Usuario> todos = bDao.selectAllUsuario();
			Usuario encontrado = null;
			for (Usuario u : todos) {
				if ((u.getNombre().equalsIgnoreCase(input) || u.getEmail().equalsIgnoreCase(input))
						&& u.getContraseña().equals(password)) {
					encontrado = u;
					break;
				}
			}

			if (encontrado != null) {
				App3 ventanaUser = new App3(encontrado, InicioSesion);
				ventanaUser.getFrame().setVisible(true);
				InicioSesion.setVisible(false);
			} else {
				JOptionPane.showMessageDialog(InicioSesion, "Usuario o contraseña incorrectos.", "Error de acceso",
						JOptionPane.ERROR_MESSAGE);
				passwordLogin.setText("");
			}
		};

		btnLogin.addActionListener(loginAction);
		passwordLogin.addActionListener(loginAction);

		btnRegistrar.addActionListener(e -> {
			JTextField txtRegNombre = new JTextField(15);
			JTextField txtRegEmail = new JTextField(15);
			JPasswordField pwdReg = new JPasswordField(15);
			JPasswordField pwdConf = new JPasswordField(15);

			JPanel regPanel = new JPanel(new java.awt.GridLayout(4, 2, 8, 10));
			regPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
			regPanel.add(new JLabel("Nombre:"));
			regPanel.add(txtRegNombre);
			regPanel.add(new JLabel("Email:"));
			regPanel.add(txtRegEmail);
			regPanel.add(new JLabel("Contraseña:"));
			regPanel.add(pwdReg);
			regPanel.add(new JLabel("Confirmar pwd:"));
			regPanel.add(pwdConf);

			int result = JOptionPane.showConfirmDialog(InicioSesion, regPanel, "Crear nueva cuenta",
					JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

			if (result == JOptionPane.OK_OPTION) {
				String nombre = txtRegNombre.getText().trim();
				String email = txtRegEmail.getText().trim();
				String pwd = new String(pwdReg.getPassword());
				String conf = new String(pwdConf.getPassword());

				if (nombre.isBlank() || email.isBlank() || pwd.isBlank()) {
					JOptionPane.showMessageDialog(InicioSesion, "Todos los campos son obligatorios.");
					return;
				}
				if (!pwd.equals(conf)) {
					JOptionPane.showMessageDialog(InicioSesion, "Las contraseñas no coinciden.");
					return;
				}

				Usuario nuevo = Usuario.builder().nombre(nombre).email(email).contraseña(pwd).build();
				bDao.insertUsuario(nuevo);

				JOptionPane.showMessageDialog(InicioSesion, "¡Cuenta creada correctamente!\nYa puedes iniciar sesión.");
			}
		});
	}

	public JFrame getFrame() {
		return InicioSesion;
	}
}
