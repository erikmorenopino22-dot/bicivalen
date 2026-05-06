package com.hibernate;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;
import javax.swing.UIManager;
import javax.swing.border.LineBorder;
import javax.swing.border.MatteBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import com.formdev.flatlaf.FlatLightLaf;
import com.hibernate.dao.BicivalenDAO;
import com.hibernate.model.Bicicleta;
import com.hibernate.model.Bono;
import com.hibernate.model.Estacion;
import com.hibernate.model.Ruta;
import com.hibernate.model.Usuario;

public class App {

	private JFrame Admin;
	private JFrame frameLogin;
	private JTextField txtIdU, txtNomU, txtEmailU, txtBonoU;
	private JPasswordField passwordU;
	private JTextField txtIdR, txtDuraciónR;
	private JTextField txtIdE, txtNomE, txtCapacidadE, txtPuestosE, txtUbicacionE;
	private JTextField txtIdB;
	private JTextField txtIdBn, txtNomBn;
	private JTable tableU, tableR, tableE, tableB, tableBn;

	public static void main(String[] args) {
		EventQueue.invokeLater(() -> {
			try {
				FlatLightLaf.setup();
				aplicarEstilo();
			} catch (Exception e) {
				e.printStackTrace();
			}
			try {
				App window = new App(null);
				window.Admin.setVisible(true);
			} catch (Exception e) {
				e.printStackTrace();
			}
		});
	}
	
	private void centrarTabla(JTable table) {
	    DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
	    centerRenderer.setHorizontalAlignment(JLabel.CENTER);

	    for (int i = 0; i < table.getColumnCount(); i++) {
	        table.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
	    }
	}

	private static void aplicarEstilo() {
		UIManager.put("Button.arc", 15);
		UIManager.put("Component.arc", 15);
		UIManager.put("TextComponent.arc", 10);
		UIManager.put("Component.focusWidth", 1);
		UIManager.put("Button.innerFocusWidth", 0);
		UIManager.put("defaultFont", new Font("Segoe UI", Font.PLAIN, 11));
	}

	public App() {
		this(null);
	}

	public App(JFrame loginFrame) {
		this.frameLogin = loginFrame;
		initialize();
	}

	private JButton btnPrimario(String texto) {
		JButton b = new JButton(texto);
		b.setBackground(new Color(41, 128, 185));
		b.setForeground(Color.WHITE);
		b.setFocusPainted(false);
		b.setFont(new Font("Segoe UI", Font.BOLD, 11));
		return b;
	}

	private JButton btnAdd(String texto) {
		JButton b = new JButton(texto);
		b.setBackground(new Color(39, 174, 96));
		b.setForeground(Color.WHITE);
		b.setFocusPainted(false);
		b.setFont(new Font("Segoe UI", Font.BOLD, 11));
		return b;
	}

	private JButton btnDel(String texto) {
		JButton b = new JButton(texto);
		b.setBackground(new Color(192, 57, 43));
		b.setForeground(Color.WHITE);
		b.setFocusPainted(false);
		b.setFont(new Font("Segoe UI", Font.BOLD, 11));
		return b;
	}

	private JLabel lbl(String texto) {
		JLabel l = new JLabel(texto);
		l.setForeground(new Color(60, 80, 120));
		l.setFont(new Font("Segoe UI", Font.BOLD, 11));
		return l;
	}

	private JTextField txt(int cols) {
		JTextField t = new JTextField(cols);
		t.setBorder(new LineBorder(new Color(180, 200, 230), 1, true));
		return t;
	}

	private JTextField txtId() {
		JTextField t = txt(10);
		t.setEditable(false);
		t.setBackground(new Color(240, 245, 255));
		return t;
	}

	private void estilizarTabla(JTable table) {
		table.setRowHeight(24);
		table.setGridColor(new Color(220, 230, 245));
		table.getTableHeader().setBackground(new Color(41, 128, 185));
		table.getTableHeader().setForeground(Color.WHITE);
		table.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 11));
		table.setSelectionBackground(new Color(210, 230, 255));
		table.setSelectionForeground(new Color(20, 40, 80));
		table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
	}

	private JPanel separador(int x, int y, int w) {
		JPanel sep = new JPanel();
		sep.setBackground(new Color(200, 215, 235));
		sep.setBounds(x, y, w, 1);
		return sep;
	}

	private void cerrarSesion() {
		Admin.dispose();
		if (frameLogin != null) {
			frameLogin.setVisible(true);
		} else {
			App2 login = new App2();
			login.getFrame().setVisible(true);
		}
	}

	private void cargarEstaciones(JComboBox<Estacion> combo, BicivalenDAO bDao) {
		combo.removeAllItems();
		bDao.selectAllEstacion().forEach(combo::addItem);
	}

	private void cargarBicicletas(JComboBox<Bicicleta> combo, Estacion estacion) {
		combo.removeAllItems();
		if (estacion != null)
			estacion.getBicicletas().forEach(combo::addItem);
	}

	private void initialize() {

		BicivalenDAO bDao = new BicivalenDAO();

		DefaultTableModel modeloU = new DefaultTableModel(new String[] { "ID", "Nombre", "Email", "Contraseña" }, 0) {
			public boolean isCellEditable(int r, int c) {
				return false;
			}
		};
		DefaultTableModel modeloR = new DefaultTableModel(
				new String[] { "ID", "Usuario", "EstaciónO", "EstaciónD", "Duración", "Valoración" }, 0) {
			public boolean isCellEditable(int r, int c) {
				return false;
			}
		};
		DefaultTableModel modeloE = new DefaultTableModel(
				new String[] { "ID", "Nombre", "Ubicación", "Capacidad", "Puestos Libres" }, 0) {
			public boolean isCellEditable(int r, int c) {
				return false;
			}
		};
		DefaultTableModel modeloB = new DefaultTableModel(new String[] { "ID", "Tipo", "Estado", "Ubicación" }, 0) {
			public boolean isCellEditable(int r, int c) {
				return false;
			}
		};
		DefaultTableModel modeloBono = new DefaultTableModel(new String[] { "ID", "Nombre", "Tipo", "Descripción" },
				0) {
			public boolean isCellEditable(int r, int c) {
				return false;
			}
		};

		Admin = new JFrame("BiciValen – Panel Administrador");
		Admin.setBounds(100, 100, 760, 620);
		Admin.setResizable(false);
		Admin.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		Admin.addWindowListener(new java.awt.event.WindowAdapter() {
			@Override
			public void windowClosing(java.awt.event.WindowEvent we) {
				String[] opciones = { "Cerrar sesión", "Salir de la app", "Cancelar" };
				int resp = JOptionPane.showOptionDialog(Admin, "¿Qué deseas hacer?", "Salir",
						JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, opciones, opciones[2]);
				if (resp == 0)
					cerrarSesion();
				else if (resp == 1)
					System.exit(0);
			}
		});
		Admin.getContentPane().setLayout(null);
		Admin.getContentPane().setBackground(new Color(245, 248, 255));

		JPanel cabecera = new JPanel();
		cabecera.setBackground(new Color(20, 60, 120));
		cabecera.setLayout(null);
		cabecera.setBounds(0, 0, 760, 62);
		Admin.getContentPane().add(cabecera);

		JLabel lblTitulo = new JLabel("BiciValen");
		lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 20));
		lblTitulo.setForeground(Color.WHITE);
		lblTitulo.setBounds(18, 8, 300, 28);
		cabecera.add(lblTitulo);

		JLabel lblSubtitulo = new JLabel("Panel de Administración");
		lblSubtitulo.setFont(new Font("Segoe UI", Font.PLAIN, 12));
		lblSubtitulo.setForeground(new Color(160, 200, 240));
		lblSubtitulo.setBounds(18, 34, 300, 18);
		cabecera.add(lblSubtitulo);

		JButton btnCerrarSesion = new JButton("Cerrar Sesión");
		btnCerrarSesion.setFont(new Font("Segoe UI", Font.BOLD, 11));
		btnCerrarSesion.setBackground(new Color(192, 57, 43));
		btnCerrarSesion.setForeground(Color.WHITE);
		btnCerrarSesion.setFocusPainted(false);
		btnCerrarSesion.setBounds(620, 17, 120, 28);
		btnCerrarSesion.setToolTipText("Crear nuevo usuario");
		cabecera.add(btnCerrarSesion);
		btnCerrarSesion.addActionListener(e -> {
			int confirm = JOptionPane.showConfirmDialog(Admin, "¿Seguro que quieres cerrar sesión?", "Cerrar sesión",
					JOptionPane.YES_NO_OPTION);
			if (confirm == JOptionPane.YES_OPTION)
				cerrarSesion();
		});

		JButton btnMostrar = new JButton("Mostrar");
		btnMostrar.setVisible(false);
		btnMostrar.setBounds(0, 0, 1, 1);
		Admin.getContentPane().add(btnMostrar);
		btnMostrar.addActionListener(e -> {
			modeloE.setRowCount(0);
			for (Estacion est : bDao.selectAllEstacion())
				modeloE.addRow(new Object[] { est.getId(), est.getNombre(), est.getUbicacion(), est.getCapacidad(),
						est.getPuestos() });

			modeloR.setRowCount(0);
			for (Ruta r : bDao.selectAllRuta())
				modeloR.addRow(new Object[] { r.getId(), r.getUsuario(), r.getEstacionI(), r.getEstacionD(),
						r.getDuracion(), r.getValoracion() });

			modeloU.setRowCount(0);
			for (Usuario u : bDao.selectAllUsuario())
				modeloU.addRow(new Object[] { u.getId(), u.getNombre(), u.getEmail(), u.getContraseña() });

			modeloBono.setRowCount(0);
			for (Bono bn : bDao.selectAllBono())
				modeloBono.addRow(new Object[] { bn.getId(), bn.getNombre(), bn.getTipo(), bn.getDescripcion() });

			modeloB.setRowCount(0);
			for (Bicicleta b : bDao.selectAllBicicleta())
				modeloB.addRow(new Object[] { b.getId(), b.getTipo(), b.getEstado(), b.getEstacion() });
		});

		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setFont(new Font("Segoe UI", Font.BOLD, 11));
		tabbedPane.setBounds(10, 72, 730, 490);
		Admin.getContentPane().add(tabbedPane);

		JPanel panelU = new JPanel();
		panelU.setLayout(null);
		panelU.setBackground(Color.WHITE);
		tabbedPane.addTab("Usuario", null, panelU, null);

		tableU = new JTable(modeloU);
		estilizarTabla(tableU);
		JScrollPane scrollU = new JScrollPane(tableU);
		scrollU.setBounds(12, 12, 700, 110);
		scrollU.setBorder(new LineBorder(new Color(180, 210, 240), 1, true));
		panelU.add(scrollU);

		panelU.add(separador(12, 130, 700));

		JLabel lblIdU = lbl("ID:");
		lblIdU.setBounds(20, 142, 40, 17);
		panelU.add(lblIdU);
		JLabel lblNomU = lbl("Nombre:");
		lblNomU.setBounds(20, 172, 60, 17);
		panelU.add(lblNomU);
		JLabel lblEmailU = lbl("Email:");
		lblEmailU.setBounds(20, 202, 50, 17);
		panelU.add(lblEmailU);
		JLabel lblPwdU = lbl("Contraseña:");
		lblPwdU.setBounds(20, 232, 80, 17);
		panelU.add(lblPwdU);

		txtIdU = txtId();
		txtIdU.setBounds(100, 140, 130, 26);
		panelU.add(txtIdU);
		txtNomU = txt(10);
		txtNomU.setBounds(100, 170, 130, 26);
		panelU.add(txtNomU);
		txtEmailU = txt(10);
		txtEmailU.setBounds(100, 200, 130, 26);
		panelU.add(txtEmailU);
		passwordU = new JPasswordField();
		passwordU.setBorder(new LineBorder(new Color(180, 200, 230), 1, true));
		passwordU.setBounds(100, 230, 130, 26);
		panelU.add(passwordU);

		JButton btnAddU = btnAdd("Añadir");
		btnAddU.setBounds(570, 140, 130, 32);
		panelU.add(btnAddU);
		btnAddU.setToolTipText("Crear nuevo usuario");
		JButton btnEditU = btnPrimario("Editar");
		btnEditU.setBounds(570, 185, 130, 32);
		panelU.add(btnEditU);
		btnEditU.setToolTipText("Editar usuario");
		JButton btnDelU = btnDel("Eliminar");
		btnDelU.setBounds(570, 230, 130, 32);
		panelU.add(btnDelU);
		btnDelU.setToolTipText("Eliminar usuario");

		tableU.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				int i = tableU.getSelectedRow();
				txtIdU.setText(modeloU.getValueAt(i, 0).toString());
				txtNomU.setText(modeloU.getValueAt(i, 1).toString());
				txtEmailU.setText(modeloU.getValueAt(i, 2).toString());
				passwordU.setText(modeloU.getValueAt(i, 3).toString());
			}
		});

		btnAddU.addActionListener(e -> {
			Usuario u = Usuario.builder().nombre(txtNomU.getText()).email(txtEmailU.getText())
					.contraseña(passwordU.getText()).build();
			bDao.insertUsuario(u);
			JOptionPane.showMessageDialog(Admin, "Usuario creado correctamente.");
			btnMostrar.doClick();
		});
		btnEditU.addActionListener(e -> {
			int codigo = Integer.parseInt(txtIdU.getText());
			Usuario u = bDao.selectUsuarioById(codigo);
			u.setNombre(txtNomU.getText());
			u.setEmail(txtEmailU.getText());
			u.setContraseña(passwordU.getText());
			bDao.updateUsuario(u);
			JOptionPane.showMessageDialog(Admin, "Usuario actualizado.");
			btnMostrar.doClick();
		});
		btnDelU.addActionListener(e -> {
			int confirm = JOptionPane.showConfirmDialog(Admin, "¿Eliminar este usuario?", "Confirmar",
					JOptionPane.YES_NO_OPTION);
			if (confirm == JOptionPane.YES_OPTION) {
				bDao.deleteUsuario(Integer.parseInt(txtIdU.getText()));
				JOptionPane.showMessageDialog(Admin, "Usuario eliminado.");
				btnMostrar.doClick();
			}
		});

		JPanel panelR = new JPanel();
		panelR.setLayout(null);
		panelR.setBackground(Color.WHITE);
		tabbedPane.addTab("Rutas", null, panelR, null);

		tableR = new JTable(modeloR);
		estilizarTabla(tableR);
		JScrollPane scrollR = new JScrollPane(tableR);
		scrollR.setBounds(12, 12, 700, 110);
		scrollR.setBorder(new LineBorder(new Color(180, 210, 240), 1, true));
		panelR.add(scrollR);

		panelR.add(separador(12, 130, 700));

		JLabel lblIdR = lbl("ID:");
		lblIdR.setBounds(20, 142, 30, 17);
		panelR.add(lblIdR);
		JLabel lblUsuR = lbl("Usuario:");
		lblUsuR.setBounds(20, 172, 60, 17);
		panelR.add(lblUsuR);
		JLabel lblBiciR = lbl("Bicicleta:");
		lblBiciR.setBounds(20, 202, 65, 17);
		panelR.add(lblBiciR);
		JLabel lblEstOR = lbl("Est. Origen:");
		lblEstOR.setBounds(20, 232, 80, 17);
		panelR.add(lblEstOR);
		JLabel lblEstDR = lbl("Est. Destino:");
		lblEstDR.setBounds(20, 262, 85, 17);
		panelR.add(lblEstDR);
		JLabel lblDurR = lbl("Duración:");
		lblDurR.setBounds(280, 142, 70, 17);
		panelR.add(lblDurR);
		JLabel lblValR = lbl("Valoración:");
		lblValR.setBounds(280, 172, 70, 17);
		panelR.add(lblValR);

		txtIdR = txtId();
		txtIdR.setBounds(110, 140, 120, 26);
		panelR.add(txtIdR);
		txtDuraciónR = txt(10);
		txtDuraciónR.setBounds(360, 140, 100, 26);
		panelR.add(txtDuraciónR);

		JComboBox<Usuario> comboUsuR = new JComboBox<>();
		comboUsuR.setBounds(110, 170, 120, 26);
		panelR.add(comboUsuR);
		JComboBox<Bicicleta> comboBiciR = new JComboBox<>();
		comboBiciR.setBounds(110, 200, 120, 26);
		panelR.add(comboBiciR);
		JComboBox<Estacion> comboEstOR = new JComboBox<>();
		comboEstOR.setBounds(110, 230, 120, 26);
		panelR.add(comboEstOR);
		JComboBox<Estacion> comboEstDR = new JComboBox<>();
		comboEstDR.setBounds(110, 260, 120, 26);
		panelR.add(comboEstDR);
		JSpinner spinnerValR = new JSpinner(new SpinnerNumberModel(1, 1, 5, 1));
		spinnerValR.setBounds(360, 170, 70, 26);
		panelR.add(spinnerValR);

		bDao.selectAllUsuario().forEach(comboUsuR::addItem);
		bDao.selectAllBicicleta().forEach(comboBiciR::addItem);
		bDao.selectAllEstacion().forEach(comboEstOR::addItem);
		bDao.selectAllEstacion().forEach(comboEstDR::addItem);

		JButton btnAddR = btnAdd("Añadir");
		btnAddR.setBounds(570, 140, 130, 32);
		panelR.add(btnAddR);
		btnAddR.setToolTipText("Crear nueva Ruta");
		JButton btnEditR = btnPrimario("Editar");
		btnEditR.setBounds(570, 185, 130, 32);
		panelR.add(btnEditR);
		btnEditR.setToolTipText("Editar Ruta");
		JButton btnDelR = btnDel("Eliminar");
		btnDelR.setBounds(570, 230, 130, 32);
		panelR.add(btnDelR);
		btnDelR.setToolTipText("Eliminar Ruta");

		tableR.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				int i = tableR.getSelectedRow();
				txtIdR.setText(modeloR.getValueAt(i, 0).toString());
				txtDuraciónR.setText(modeloR.getValueAt(i, 4).toString());
				Object valObj = modeloR.getValueAt(i, 5);
				try {
					spinnerValR.setValue(Integer.parseInt(valObj.toString()));
				} catch (Exception ex) {
				}
			}
		});

		btnAddR.addActionListener(e -> {
			Ruta r = Ruta.builder().usuario((Usuario) comboUsuR.getSelectedItem())
					.bicicleta((Bicicleta) comboBiciR.getSelectedItem())
					.estacionI((Estacion) comboEstOR.getSelectedItem())
					.estacionD((Estacion) comboEstDR.getSelectedItem())
					.duracion(Integer.parseInt(txtDuraciónR.getText())).valoracion((int) spinnerValR.getValue())
					.build();
			bDao.insertRuta(r);
			JOptionPane.showMessageDialog(Admin, "Ruta creada correctamente.");
			btnMostrar.doClick();
		});
		btnEditR.addActionListener(e -> {
			Ruta r = bDao.selectRutaById(Integer.parseInt(txtIdR.getText()));
			r.setUsuario((Usuario) comboUsuR.getSelectedItem());
			r.setBicicleta((Bicicleta) comboBiciR.getSelectedItem());
			r.setEstacionI((Estacion) comboEstOR.getSelectedItem());
			r.setEstacionD((Estacion) comboEstDR.getSelectedItem());
			r.setDuracion(Integer.parseInt(txtDuraciónR.getText()));
			r.setValoracion((int) spinnerValR.getValue());
			bDao.updateRuta(r);
			JOptionPane.showMessageDialog(Admin, "Ruta actualizada.");
			btnMostrar.doClick();
		});
		btnDelR.addActionListener(e -> {
			int confirm = JOptionPane.showConfirmDialog(Admin, "¿Eliminar esta ruta?", "Confirmar",
					JOptionPane.YES_NO_OPTION);
			if (confirm == JOptionPane.YES_OPTION) {
				bDao.deleteRuta(Integer.parseInt(txtIdR.getText()));
				JOptionPane.showMessageDialog(Admin, "Ruta eliminada.");
				btnMostrar.doClick();
			}
		});

		JPanel panelE = new JPanel();
		panelE.setLayout(null);
		panelE.setBackground(Color.WHITE);
		tabbedPane.addTab("Estación", null, panelE, null);

		tableE = new JTable(modeloE);
		estilizarTabla(tableE);
		JScrollPane scrollE = new JScrollPane(tableE);
		scrollE.setBounds(12, 12, 700, 110);
		scrollE.setBorder(new LineBorder(new Color(180, 210, 240), 1, true));
		panelE.add(scrollE);

		panelE.add(separador(12, 130, 700));

		JLabel lblIdE = lbl("ID:");
		lblIdE.setBounds(20, 142, 30, 17);
		panelE.add(lblIdE);
		JLabel lblNomE = lbl("Nombre:");
		lblNomE.setBounds(20, 172, 60, 17);
		panelE.add(lblNomE);
		JLabel lblUbE = lbl("Ubicación:");
		lblUbE.setBounds(20, 202, 70, 17);
		panelE.add(lblUbE);
		JLabel lblCapE = lbl("Capacidad:");
		lblCapE.setBounds(20, 232, 70, 17);
		panelE.add(lblCapE);
		JLabel lblPusE = lbl("Puestos:");
		lblPusE.setBounds(20, 262, 60, 17);
		panelE.add(lblPusE);
		JLabel lblBicE = lbl("Bicicletas:");
		lblBicE.setBounds(280, 142, 80, 17);
		panelE.add(lblBicE);

		txtIdE = txtId();
		txtIdE.setBounds(105, 140, 130, 26);
		panelE.add(txtIdE);
		txtNomE = txt(10);
		txtNomE.setBounds(105, 170, 130, 26);
		panelE.add(txtNomE);
		txtUbicacionE = txt(10);
		txtUbicacionE.setBounds(105, 200, 130, 26);
		panelE.add(txtUbicacionE);
		txtCapacidadE = txt(10);
		txtCapacidadE.setBounds(105, 230, 130, 26);
		panelE.add(txtCapacidadE);
		txtPuestosE = txt(10);
		txtPuestosE.setBounds(105, 260, 130, 26);
		panelE.add(txtPuestosE);

		JComboBox<Bicicleta> comboBoxBicis = new JComboBox<>();
		comboBoxBicis.setBounds(360, 138, 160, 26);
		panelE.add(comboBoxBicis);

		JButton btnAddE = btnAdd("Añadir");
		btnAddE.setBounds(570, 140, 130, 32);
		panelE.add(btnAddE);
		btnAddE.setToolTipText("Crear nueva Estación");
		JButton btnEditE = btnPrimario("Editar");
		btnEditE.setBounds(570, 185, 130, 32);
		panelE.add(btnEditE);
		btnEditE.setToolTipText("Editar Estación");
		JButton btnDelE = btnDel("Eliminar");
		btnDelE.setBounds(570, 230, 130, 32);
		panelE.add(btnDelE);
		btnDelE.setToolTipText("Eliminar Estación");

		tableE.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				int i = tableE.getSelectedRow();
				txtIdE.setText(modeloE.getValueAt(i, 0).toString());
				txtNomE.setText(modeloE.getValueAt(i, 1).toString());
				txtUbicacionE.setText(modeloE.getValueAt(i, 2).toString());
				txtCapacidadE.setText(modeloE.getValueAt(i, 3).toString());
				txtPuestosE.setText(modeloE.getValueAt(i, 4).toString());
				int idEst = (int) modeloE.getValueAt(i, 0);
				Estacion es = bDao.selectEstacionById(idEst);
				cargarBicicletas(comboBoxBicis, es);
			}
		});

		btnAddE.addActionListener(e -> {
			Estacion est = Estacion.builder().nombre(txtNomE.getText()).ubicacion(txtUbicacionE.getText())
					.capacidad(Integer.parseInt(txtCapacidadE.getText()))
					.puestos(Integer.parseInt(txtPuestosE.getText())).build();
			bDao.insertEstacion(est);
			JOptionPane.showMessageDialog(Admin, "Estación creada correctamente.");
			btnMostrar.doClick();
		});
		btnEditE.addActionListener(e -> {
			int codigo = Integer.parseInt(txtIdE.getText());
			Estacion es = bDao.selectEstacionById(codigo);
			es.setNombre(txtNomE.getText());
			es.setUbicacion(txtUbicacionE.getText());
			es.setCapacidad(Integer.parseInt(txtCapacidadE.getText()));
			es.setPuestos(Integer.parseInt(txtPuestosE.getText()));
			bDao.updateEstacion(es);
			JOptionPane.showMessageDialog(Admin, "Estación actualizada.");
			btnMostrar.doClick();
		});
		btnDelE.addActionListener(e -> {
			int confirm = JOptionPane.showConfirmDialog(Admin, "¿Eliminar esta estación?", "Confirmar",
					JOptionPane.YES_NO_OPTION);
			if (confirm == JOptionPane.YES_OPTION) {
				bDao.deleteEstacion(Integer.parseInt(txtIdE.getText()));
				JOptionPane.showMessageDialog(Admin, "Estación eliminada.");
				btnMostrar.doClick();
			}
		});

		JPanel panelB = new JPanel();
		panelB.setLayout(null);
		panelB.setBackground(Color.WHITE);
		tabbedPane.addTab("Bicicleta", null, panelB, null);

		tableB = new JTable(modeloB);
		estilizarTabla(tableB);
		JScrollPane scrollB = new JScrollPane(tableB);
		scrollB.setBounds(12, 12, 700, 110);
		scrollB.setBorder(new LineBorder(new Color(180, 210, 240), 1, true));
		panelB.add(scrollB);
		panelB.add(separador(12, 130, 700));
		JLabel lblIdB = lbl("ID:");
		lblIdB.setBounds(20, 142, 30, 17);
		panelB.add(lblIdB);
		JLabel lblTipoB = lbl("Tipo:");
		lblTipoB.setBounds(20, 172, 40, 17);
		panelB.add(lblTipoB);
		JLabel lblEstadB = lbl("Estado:");
		lblEstadB.setBounds(20, 202, 55, 17);
		panelB.add(lblEstadB);
		JLabel lblEstacB = lbl("Estación:");
		lblEstacB.setBounds(20, 232, 65, 17);
		panelB.add(lblEstacB);
		JLabel lblDispB = lbl("Disponible:");
		lblDispB.setBounds(20, 262, 75, 17);
		panelB.add(lblDispB);

		txtIdB = txtId();
		txtIdB.setBounds(100, 140, 130, 26);
		panelB.add(txtIdB);

		JComboBox<String> comboTipoB = new JComboBox<>(new String[] { "Electrica", "Basica" });
		comboTipoB.setBounds(100, 170, 130, 26);
		panelB.add(comboTipoB);

		JComboBox<String> comboEstadoB = new JComboBox<>(new String[] { "Genial", "Bueno", "Malo" });
		comboEstadoB.setBounds(100, 200, 130, 26);
		panelB.add(comboEstadoB);

		JComboBox<Estacion> comboEstacionB = new JComboBox<>();
		comboEstacionB.setBounds(100, 230, 130, 26);
		panelB.add(comboEstacionB);

		JCheckBox checkBoxB = new JCheckBox("");
		checkBoxB.setBounds(100, 258, 30, 25);
		panelB.add(checkBoxB);

		cargarEstaciones(comboEstacionB, bDao);

		JButton btnAddB = btnAdd("Añadir");
		btnAddB.setBounds(570, 140, 130, 32);
		panelB.add(btnAddB);
		btnAddB.setToolTipText("Crear nueva Bici");
		JButton btnEditB = btnPrimario("Editar");
		btnEditB.setBounds(570, 185, 130, 32);
		panelB.add(btnEditB);
		btnAddB.setToolTipText("Editar Bici");
		JButton btnDelB = btnDel("Eliminar");
		btnDelB.setBounds(570, 230, 130, 32);
		panelB.add(btnDelB);
		btnAddB.setToolTipText("Eliminar Bici");

		tableB.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				int i = tableB.getSelectedRow();
				txtIdB.setText(modeloB.getValueAt(i, 0).toString());
				comboTipoB.setSelectedItem(modeloB.getValueAt(i, 1).toString());
				comboEstadoB.setSelectedItem(modeloB.getValueAt(i, 2).toString());
				Object estObj = modeloB.getValueAt(i, 3);
				if (estObj instanceof Estacion)
					comboEstacionB.setSelectedItem(estObj);
			}
		});

		btnAddB.addActionListener(e -> {
			Bicicleta b = Bicicleta.builder().tipo(comboTipoB.getSelectedItem().toString())
					.estado(comboEstadoB.getSelectedItem().toString())
					.estacion((Estacion) comboEstacionB.getSelectedItem()).build();
			bDao.insertBicicleta(b);
			JOptionPane.showMessageDialog(Admin, "Bicicleta creada correctamente.");
			btnMostrar.doClick();
		});
		btnEditB.addActionListener(e -> {
			int codigo = Integer.parseInt(txtIdB.getText());
			Bicicleta b = bDao.selectBicicletaById(codigo);
			b.setTipo(comboTipoB.getSelectedItem().toString());
			b.setEstado(comboEstadoB.getSelectedItem().toString());
			b.setEstacion((Estacion) comboEstacionB.getSelectedItem());
			bDao.updateBicicleta(b);
			JOptionPane.showMessageDialog(Admin, "Bicicleta actualizada.");
			btnMostrar.doClick();
		});
		btnDelB.addActionListener(e -> {
			int confirm = JOptionPane.showConfirmDialog(Admin, "¿Eliminar esta bicicleta?", "Confirmar",
					JOptionPane.YES_NO_OPTION);
			if (confirm == JOptionPane.YES_OPTION) {
				bDao.deleteBicicleta(Integer.parseInt(txtIdB.getText()));
				JOptionPane.showMessageDialog(Admin, "Bicicleta eliminada.");
				btnMostrar.doClick();
			}
		});

		JPanel panelBn = new JPanel();
		panelBn.setLayout(null);
		panelBn.setBackground(Color.WHITE);
		tabbedPane.addTab("Bono", null, panelBn, null);

		tableBn = new JTable(modeloBono);
		estilizarTabla(tableBn);
		JScrollPane scrollBn = new JScrollPane(tableBn);
		scrollBn.setBounds(12, 12, 700, 110);
		scrollBn.setBorder(new LineBorder(new Color(180, 210, 240), 1, true));
		panelBn.add(scrollBn);

		panelBn.add(separador(12, 130, 700));

		JLabel lblIdBn = lbl("ID:");
		lblIdBn.setBounds(20, 142, 30, 17);
		panelBn.add(lblIdBn);
		JLabel lblNomBn = lbl("Nombre:");
		lblNomBn.setBounds(20, 172, 60, 17);
		panelBn.add(lblNomBn);
		JLabel lblTipoBn = lbl("Duración:");
		lblTipoBn.setBounds(20, 202, 65, 17);
		panelBn.add(lblTipoBn);
		JLabel lblDescBn = lbl("Descripción:");
		lblDescBn.setBounds(20, 232, 80, 17);
		panelBn.add(lblDescBn);

		txtIdBn = txtId();
		txtIdBn.setBounds(105, 140, 130, 26);
		panelBn.add(txtIdBn);
		txtNomBn = txt(10);
		txtNomBn.setBounds(105, 170, 130, 26);
		panelBn.add(txtNomBn);

		JComboBox<String> comboBoxBn = new JComboBox<>(new String[] { "1 Dia", "1 Mes", "3 Meses", "1 Año" });
		comboBoxBn.setBounds(105, 200, 130, 26);
		panelBn.add(comboBoxBn);

		JTextArea DescripcionBn = new JTextArea();
		DescripcionBn.setFont(new Font("Segoe UI", Font.PLAIN, 11));
		DescripcionBn.setBorder(new LineBorder(new Color(180, 200, 230), 1, true));
		JScrollPane scrollDesc = new JScrollPane(DescripcionBn);
		scrollDesc.setBounds(105, 230, 200, 55);
		panelBn.add(scrollDesc);

		JButton btnAddBn = btnAdd("Añadir");
		btnAddBn.setBounds(570, 140, 130, 32);
		panelBn.add(btnAddBn);
		btnAddBn.setToolTipText("Crear nuevo Bono");
		JButton btnEditBn = btnPrimario("Editar");
		btnEditBn.setBounds(570, 185, 130, 32);
		panelBn.add(btnEditBn);
		btnEditBn.setToolTipText("Editar Bono");
		JButton btnDelBn = btnDel("Eliminar");
		btnDelBn.setBounds(570, 230, 130, 32);
		panelBn.add(btnDelBn);
		btnDelBn.setToolTipText("Eliminar Bono");

		tableBn.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				int i = tableBn.getSelectedRow();
				txtIdBn.setText(modeloBono.getValueAt(i, 0).toString());
				txtNomBn.setText(modeloBono.getValueAt(i, 1).toString());
				comboBoxBn.setSelectedItem(modeloBono.getValueAt(i, 2).toString());
				DescripcionBn.setText(modeloBono.getValueAt(i, 3).toString());
			}
		});

		btnAddBn.addActionListener(e -> {
			Bono bn = Bono.builder().nombre(txtNomBn.getText()).tipo(comboBoxBn.getSelectedItem().toString())
					.descripcion(DescripcionBn.getText()).build();
			bDao.insertBono(bn);
			JOptionPane.showMessageDialog(Admin, "Bono creado correctamente.");
			btnMostrar.doClick();
		});
		btnEditBn.addActionListener(e -> {
			int codigo = Integer.parseInt(txtIdBn.getText());
			Bono bn = bDao.selectBonoById(codigo);
			bn.setNombre(txtNomBn.getText());
			bn.setTipo(comboBoxBn.getSelectedItem().toString());
			bn.setDescripcion(DescripcionBn.getText());
			bDao.updateBono(bn);
			JOptionPane.showMessageDialog(Admin, "Bono actualizado.");
			btnMostrar.doClick();
		});
		btnDelBn.addActionListener(e -> {
			int confirm = JOptionPane.showConfirmDialog(Admin, "¿Eliminar este bono?", "Confirmar",
					JOptionPane.YES_NO_OPTION);
			if (confirm == JOptionPane.YES_OPTION) {
				bDao.deleteBono(Integer.parseInt(txtIdBn.getText()));
				JOptionPane.showMessageDialog(Admin, "Bono eliminado.");
				btnMostrar.doClick();
			}
		});

		btnMostrar.doClick();
		centrarTabla(tableU);
		centrarTabla(tableR);
		centrarTabla(tableE);
		centrarTabla(tableB);
		centrarTabla(tableBn);
	}

	public JFrame getFrame() {
		return Admin;
	}
}