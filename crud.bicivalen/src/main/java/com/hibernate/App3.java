package com.hibernate;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTable;
import javax.swing.JTabbedPane;
import javax.swing.SpinnerNumberModel;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import com.formdev.flatlaf.FlatLightLaf;
import com.hibernate.dao.BicivalenDAO;
import com.hibernate.model.Bicicleta;
import com.hibernate.model.Bono;
import com.hibernate.model.Estacion;
import com.hibernate.model.Ruta;
import com.hibernate.model.Usuario;

public class App3 {

	private JFrame frame;
	private JFrame frameLogin;
	private BicivalenDAO bDao = new BicivalenDAO();
	private Usuario usuarioActual;
	private Bicicleta biciAlquilada = null;
	private Estacion estacionOrigen = null;
	private LocalDateTime horaInicio = null;
	private DefaultTableModel modeloBicis;
	private DefaultTableModel modeloRutas;
	private DefaultTableModel modeloBonos;
	private JTable tableBicis;
	private JTable tableRutas;
	private JTable tableBonos;
	
	private void centrarTabla(JTable table) {
	    DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
	    centerRenderer.setHorizontalAlignment(JLabel.CENTER);

	    for (int i = 0; i < table.getColumnCount(); i++) {
	        table.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
	    }
	}

	private JLabel lblEstadoBono;

	public static void main(String[] args) {
		EventQueue.invokeLater(() -> {
			try {
				FlatLightLaf.setup();
				UIManager.put("Button.arc", 15);
				UIManager.put("Component.arc", 15);
				UIManager.put("TextComponent.arc", 10);
				UIManager.put("Component.focusWidth", 1);
				UIManager.put("Button.innerFocusWidth", 0);
				UIManager.put("defaultFont", new Font("Segoe UI", Font.PLAIN, 11));
				BicivalenDAO dao = new BicivalenDAO();
				Usuario u = dao.selectUsuarioById(1);
				App3 window = new App3(u, null);
				window.frame.setVisible(true);
			} catch (Exception e) {
				e.printStackTrace();
			}
		});
	}

	public App3(Usuario usuario, JFrame loginFrame) {
		this.usuarioActual = usuario;
		this.frameLogin = loginFrame;
		initialize();
	}

	private boolean tieneBonoActivo() {
		usuarioActual = bDao.selectUsuarioById(usuarioActual.getId());
		return usuarioActual.getBonoActual() != null && usuarioActual.getBonoExpiracion() != null
				&& !usuarioActual.getBonoExpiracion().isBefore(LocalDate.now());
	}

	private LocalDate calcularExpiracion(String tipoBono) {
		LocalDate hoy = LocalDate.now();
		switch (tipoBono) {
		case "1 Dia":
			return hoy.plusDays(1);
		case "1 Mes":
			return hoy.plusMonths(1);
		case "3 Meses":
			return hoy.plusMonths(3);
		case "1 Año":
			return hoy.plusYears(1);
		default:
			return hoy.plusDays(1);
		}
	}

	private void actualizarEstadoBono() {
		usuarioActual = bDao.selectUsuarioById(usuarioActual.getId());
		if (usuarioActual.getBonoActual() == null || usuarioActual.getBonoExpiracion() == null) {
			lblEstadoBono.setText("Sin bono activo – no puedes alquilar");
			lblEstadoBono.setForeground(new Color(192, 57, 43));
		} else if (usuarioActual.getBonoExpiracion().isBefore(LocalDate.now())) {
			lblEstadoBono.setText("Bono caducado el " + usuarioActual.getBonoExpiracion() + " – renuévalo");
			lblEstadoBono.setForeground(new Color(192, 57, 43));
		} else {
			lblEstadoBono.setText("Bono activo: " + usuarioActual.getBonoActual().getNombre() + "  ·  Caduca: "
					+ usuarioActual.getBonoExpiracion());
			lblEstadoBono.setForeground(new Color(39, 174, 96));
		}
	}

	private void cargarEstacionesEnCombo(JComboBox<Estacion> combo) {
		combo.removeAllItems();
		bDao.selectAllEstacion().forEach(combo::addItem);
	}

	private void cargarBicicletasDeEstacion(Estacion est) {
		modeloBicis.setRowCount(0);
		if (est == null)
			return;
		Estacion estFull = bDao.selectEstacionById(est.getId());
		if (estFull == null)
			return;
		for (Bicicleta b : estFull.getBicicletas())
			modeloBicis.addRow(new Object[] { b.getId(), b.getTipo(), b.getEstado() });
	}

	private void cargarRutasUsuario() {
		modeloRutas.setRowCount(0);
		for (Ruta r : bDao.selectAllRuta())
			if (r.getUsuario() != null && r.getUsuario().getId() == usuarioActual.getId())
				modeloRutas
						.addRow(new Object[] { r.getId(), r.getEstacionI() != null ? r.getEstacionI().getNombre() : "-",
								r.getEstacionD() != null ? r.getEstacionD().getNombre() : "-", r.getDuracion() + " min",
								r.getValoracion() });
	}

	private void cargarBonos() {
		modeloBonos.setRowCount(0);
		for (Bono bn : bDao.selectAllBono())
			modeloBonos.addRow(new Object[] { bn.getId(), bn.getNombre(), bn.getTipo(), bn.getDescripcion() });
	}

	private void estilizarTabla(JTable t) {
		t.setRowHeight(24);
		t.setGridColor(new Color(220, 230, 245));
		t.getTableHeader().setBackground(new Color(41, 128, 185));
		t.getTableHeader().setForeground(Color.WHITE);
		t.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 11));
		t.setSelectionBackground(new Color(210, 230, 255));
		t.setSelectionForeground(new Color(20, 40, 80));
		t.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
	}

	private JButton btnAzul(String txt) {
		JButton b = new JButton(txt);
		b.setBackground(new Color(41, 128, 185));
		b.setForeground(Color.WHITE);
		b.setFocusPainted(false);
		b.setFont(new Font("Segoe UI", Font.BOLD, 11));
		return b;
	}

	private JButton btnVerde(String txt) {
		JButton b = new JButton(txt);
		b.setBackground(new Color(39, 174, 96));
		b.setForeground(Color.WHITE);
		b.setFocusPainted(false);
		b.setFont(new Font("Segoe UI", Font.BOLD, 11));
		return b;
	}

	private JButton btnRojo(String txt) {
		JButton b = new JButton(txt);
		b.setBackground(new Color(192, 57, 43));
		b.setForeground(Color.WHITE);
		b.setFocusPainted(false);
		b.setFont(new Font("Segoe UI", Font.BOLD, 11));
		return b;
	}

	private void cerrarSesion() {
		frame.dispose();
		if (frameLogin != null)
			frameLogin.setVisible(true);
		else {
			App2 l = new App2();
			l.getFrame().setVisible(true);
		}
	}

	private void initialize() {

		frame = new JFrame("BiciValen – " + usuarioActual.getNombre());
		frame.setBounds(100, 100, 760, 640);
		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		frame.getContentPane().setBackground(new Color(245, 248, 255));

		frame.addWindowListener(new java.awt.event.WindowAdapter() {
			public void windowClosing(java.awt.event.WindowEvent we) {
				String[] ops = { "Cerrar sesión", "Salir de la app", "Cancelar" };
				int r = JOptionPane.showOptionDialog(frame, "¿Qué deseas hacer?", "Salir", JOptionPane.DEFAULT_OPTION,
						JOptionPane.QUESTION_MESSAGE, null, ops, ops[2]);
				if (r == 0)
					cerrarSesion();
				else if (r == 1)
					System.exit(0);
			}
		});

		JLabel lblTitulo = new JLabel("BiciValen  ·  " + usuarioActual.getNombre());
		lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 18));
		lblTitulo.setForeground(new Color(20, 60, 120));
		lblTitulo.setBounds(18, 12, 500, 28);
		frame.getContentPane().add(lblTitulo);

		JLabel lblSub = new JLabel("Panel de usuario");
		lblSub.setFont(new Font("Segoe UI", Font.PLAIN, 11));
		lblSub.setForeground(Color.GRAY);
		lblSub.setBounds(18, 38, 250, 16);
		frame.getContentPane().add(lblSub);

		lblEstadoBono = new JLabel("");
		lblEstadoBono.setFont(new Font("Segoe UI", Font.BOLD, 11));
		lblEstadoBono.setBounds(18, 56, 580, 16);
		frame.getContentPane().add(lblEstadoBono);

		JButton btnCerrarTop = btnRojo("Cerrar Sesión");
		btnCerrarTop.setBounds(620, 20, 120, 28);
		frame.getContentPane().add(btnCerrarTop);
		btnCerrarTop.addActionListener(e -> {
			int c = JOptionPane.showConfirmDialog(frame, "¿Seguro que quieres cerrar sesión?", "Cerrar sesión",
					JOptionPane.YES_NO_OPTION);
			if (c == JOptionPane.YES_OPTION)
				cerrarSesion();
		});
		btnCerrarTop.setToolTipText("Cerrar Cuenta");

		JTabbedPane tabbedPrincipal = new JTabbedPane(JTabbedPane.TOP);
		tabbedPrincipal.setFont(new Font("Segoe UI", Font.BOLD, 11));
		tabbedPrincipal.setBounds(10, 80, 730, 510);
		frame.getContentPane().add(tabbedPrincipal);

		modeloBicis = new DefaultTableModel(new String[] { "ID", "Tipo", "Estado" }, 0) {
			public boolean isCellEditable(int r, int c) {
				return false;
			}
		};
		modeloRutas = new DefaultTableModel(new String[] { "ID", "Origen", "Destino", "Duración", "Valoración" }, 0) {
			public boolean isCellEditable(int r, int c) {
				return false;
			}
		};
		modeloBonos = new DefaultTableModel(new String[] { "ID", "Nombre", "Duración", "Descripción" }, 0) {
			public boolean isCellEditable(int r, int c) {
				return false;
			}
		};

		JTabbedPane tabbedBicis = new JTabbedPane();
		tabbedPrincipal.addTab("Gestión Bicis", null, tabbedBicis, null);

		JPanel panelAl = new JPanel(null);
		panelAl.setBackground(Color.WHITE);
		tabbedBicis.addTab("Alquilar", null, panelAl, null);

		JLabel lblEstAl = new JLabel("¿En qué estación estás?");
		lblEstAl.setFont(new Font("Segoe UI", Font.BOLD, 11));
		lblEstAl.setForeground(new Color(60, 80, 120));
		lblEstAl.setBounds(12, 14, 160, 17);
		panelAl.add(lblEstAl);

		JComboBox<Estacion> comboEstAl = new JComboBox<>();
		comboEstAl.setBounds(180, 10, 280, 26);
		panelAl.add(comboEstAl);

		JLabel lblBiciDisp = new JLabel("Bicicletas disponibles:");
		lblBiciDisp.setFont(new Font("Segoe UI", Font.BOLD, 11));
		lblBiciDisp.setForeground(new Color(60, 80, 120));
		lblBiciDisp.setBounds(12, 46, 160, 17);
		panelAl.add(lblBiciDisp);

		tableBicis = new JTable(modeloBicis);
		estilizarTabla(tableBicis);
		tableBicis.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
		JScrollPane scrollBicis = new JScrollPane(tableBicis);
		scrollBicis.setBounds(12, 66, 695, 105);
		scrollBicis.setBorder(new LineBorder(new Color(180, 210, 240), 1, true));
		panelAl.add(scrollBicis);

		JPanel panelAvisoBono = new JPanel(null);
		panelAvisoBono.setBackground(new Color(255, 243, 230));
		panelAvisoBono.setBorder(new LineBorder(new Color(230, 140, 60), 1, true));
		panelAvisoBono.setBounds(12, 178, 695, 44);
		panelAvisoBono.setVisible(false);
		panelAl.add(panelAvisoBono);

		JLabel lblAvisoBono = new JLabel("Necesitas un bono activo para alquilar. Ve a 'Comprar Bonos'.");
		lblAvisoBono.setFont(new Font("Segoe UI", Font.BOLD, 11));
		lblAvisoBono.setForeground(new Color(180, 80, 20));
		lblAvisoBono.setBounds(10, 6, 650, 17);
		panelAvisoBono.add(lblAvisoBono);

		JLabel lblAvisoFecha = new JLabel("");
		lblAvisoFecha.setFont(new Font("Segoe UI", Font.PLAIN, 10));
		lblAvisoFecha.setForeground(new Color(150, 70, 20));
		lblAvisoFecha.setBounds(10, 24, 650, 14);
		panelAvisoBono.add(lblAvisoFecha);

		JLabel lblAvisoAl = new JLabel(" ");
		lblAvisoAl.setForeground(new Color(192, 57, 43));
		lblAvisoAl.setBounds(12, 228, 500, 17);
		panelAl.add(lblAvisoAl);

		JButton btnAlquilar = btnAzul("Alquilar");
		btnAlquilar.setFont(new Font("Segoe UI", Font.BOLD, 12));
		btnAlquilar.setBounds(295, 250, 120, 34);
		panelAl.add(btnAlquilar);
		btnAlquilar.setToolTipText("Alquilar Bicicleta");

		comboEstAl.addActionListener(e -> {
			cargarBicicletasDeEstacion((Estacion) comboEstAl.getSelectedItem());
			lblAvisoAl.setText(" ");
		});

		tabbedBicis.addChangeListener(ev -> {
			if (tabbedBicis.getSelectedIndex() == 0) {
				boolean activo = tieneBonoActivo();
				panelAvisoBono.setVisible(!activo);
				if (!activo) {
					usuarioActual = bDao.selectUsuarioById(usuarioActual.getId());
					if (usuarioActual.getBonoActual() != null && usuarioActual.getBonoExpiracion() != null
							&& usuarioActual.getBonoExpiracion().isBefore(LocalDate.now())) {
						lblAvisoBono.setText(
								"Tu bono caducó el " + usuarioActual.getBonoExpiracion() + ". Compra uno nuevo.");
						lblAvisoFecha.setText("No puedes alquilar hasta renovar tu bono.");
					} else {
						lblAvisoBono.setText("Necesitas un bono activo para alquilar. Ve a 'Comprar Bonos'.");
						lblAvisoFecha.setText("");
					}
				}
				actualizarEstadoBono();
			}
		});

		btnAlquilar.addActionListener(e -> {
			if (!tieneBonoActivo()) {
				usuarioActual = bDao.selectUsuarioById(usuarioActual.getId());
				if (usuarioActual.getBonoActual() != null && usuarioActual.getBonoExpiracion() != null
						&& usuarioActual.getBonoExpiracion().isBefore(LocalDate.now()))
					JOptionPane.showMessageDialog(frame, "Tu bono caducó el " + usuarioActual.getBonoExpiracion()
							+ ".\nRenuévalo en 'Comprar Bonos'.", "Bono caducado", JOptionPane.WARNING_MESSAGE);
				else
					JOptionPane.showMessageDialog(frame,
							"No tienes ningún bono activo.\nVe a 'Comprar Bonos' para adquirir uno.", "Sin bono",
							JOptionPane.WARNING_MESSAGE);
				return;
			}
			if (biciAlquilada != null) {
				lblAvisoAl.setText("Ya tienes una bici alquilada. Ve a Devolver primero.");
				return;
			}
			int fila = tableBicis.getSelectedRow();
			if (fila < 0) {
				lblAvisoAl.setText("Selecciona una bicicleta de la tabla.");
				return;
			}
			biciAlquilada = bDao.selectBicicletaById((int) modeloBicis.getValueAt(fila, 0));
			estacionOrigen = (Estacion) comboEstAl.getSelectedItem();
			horaInicio = LocalDateTime.now();
			JOptionPane.showMessageDialog(frame, "¡Buen viaje!\nBicicleta: " + biciAlquilada + "\nDesde: "
					+ estacionOrigen.getNombre() + "\nBono activo hasta: " + usuarioActual.getBonoExpiracion());
			lblAvisoAl.setText(" ");
		});

		JPanel panelDev = new JPanel(null);
		panelDev.setBackground(Color.WHITE);
		tabbedBicis.addTab("Devolver", null, panelDev, null);

		JLabel lblEstDev = new JLabel("¿En qué estación devuelves?");
		lblEstDev.setFont(new Font("Segoe UI", Font.BOLD, 11));
		lblEstDev.setForeground(new Color(60, 80, 120));
		lblEstDev.setBounds(12, 14, 190, 17);
		panelDev.add(lblEstDev);

		JComboBox<Estacion> comboEstDev = new JComboBox<>();
		comboEstDev.setBounds(210, 10, 250, 26);
		panelDev.add(comboEstDev);

		JLabel lblBiciActual = new JLabel("Bici alquilada: ninguna");
		lblBiciActual.setForeground(Color.GRAY);
		lblBiciActual.setBounds(12, 50, 500, 17);
		panelDev.add(lblBiciActual);

		JLabel lblVal = new JLabel("Valoración (1-5):");
		lblVal.setFont(new Font("Segoe UI", Font.BOLD, 11));
		lblVal.setForeground(new Color(60, 80, 120));
		lblVal.setBounds(12, 80, 120, 17);
		panelDev.add(lblVal);

		JSpinner spinnerVal = new JSpinner(new SpinnerNumberModel(5, 1, 5, 1));
		spinnerVal.setBounds(135, 76, 70, 26);
		panelDev.add(spinnerVal);

		JLabel lblAvisoD = new JLabel(" ");
		lblAvisoD.setForeground(new Color(192, 57, 43));
		lblAvisoD.setBounds(12, 114, 450, 17);
		panelDev.add(lblAvisoD);

		JButton btnDevolver = btnVerde("Devolver");
		btnDevolver.setFont(new Font("Segoe UI", Font.BOLD, 12));
		btnDevolver.setBounds(295, 136, 120, 34);
		panelDev.add(btnDevolver);
		btnDevolver.setToolTipText("Alquilar Bicicleta");

		tabbedBicis.addChangeListener(ev -> {
			if (tabbedBicis.getSelectedIndex() == 1) {
				if (biciAlquilada != null) {
					lblBiciActual
							.setText("Bici: " + biciAlquilada + "  ·  Inicio: " + horaInicio.toLocalTime().withNano(0));
					lblBiciActual.setForeground(new Color(41, 128, 185));
				} else {
					lblBiciActual.setText("No tienes ninguna bici alquilada.");
					lblBiciActual.setForeground(Color.GRAY);
				}
			}
		});

		btnDevolver.addActionListener(e -> {
			if (biciAlquilada == null) {
				lblAvisoD.setText("No tienes ninguna bicicleta alquilada.");
				return;
			}
			Estacion estDest = (Estacion) comboEstDev.getSelectedItem();
			if (estDest == null) {
				lblAvisoD.setText("Selecciona la estación de destino.");
				return;
			}
			long durMin = ChronoUnit.MINUTES.between(horaInicio, LocalDateTime.now());
			int val = (int) spinnerVal.getValue();
			bDao.insertRuta(Ruta.builder().usuario(usuarioActual).bicicleta(biciAlquilada).estacionI(estacionOrigen)
					.estacionD(estDest).duracion((int) durMin).valoracion(val).build());
			biciAlquilada.setEstacion(estDest);
			bDao.updateBicicleta(biciAlquilada);
			JOptionPane.showMessageDialog(frame,
					"¡Gracias!\nDuración: " + durMin + " min  ·  Valoración: " + val + " ★");
			biciAlquilada = null;
			estacionOrigen = null;
			horaInicio = null;
			lblBiciActual.setText("No tienes ninguna bici alquilada.");
			lblBiciActual.setForeground(Color.GRAY);
			lblAvisoD.setText(" ");
			cargarRutasUsuario();
		});

		JPanel panelRutas = new JPanel(null);
		panelRutas.setBackground(Color.WHITE);
		tabbedPrincipal.addTab("Tus Rutas", null, panelRutas, null);

		tableRutas = new JTable(modeloRutas);
		estilizarTabla(tableRutas);
		JScrollPane scrollRutas = new JScrollPane(tableRutas);
		scrollRutas.setBounds(12, 12, 700, 390);
		scrollRutas.setBorder(new LineBorder(new Color(180, 210, 240), 1, true));
		panelRutas.add(scrollRutas);

		JButton btnRefresh = btnAzul("Actualizar");
		btnRefresh.setBounds(12, 410, 130, 28);
		panelRutas.add(btnRefresh);
		btnRefresh.addActionListener(e -> cargarRutasUsuario());
		btnRefresh.setToolTipText("Actualizar Tabla");

		JPanel panelBonos = new JPanel(null);
		panelBonos.setBackground(Color.WHITE);
		tabbedPrincipal.addTab("Comprar Bonos", null, panelBonos, null);

		JPanel panelBonoActual = new JPanel(null);
		panelBonoActual.setBounds(12, 12, 700, 36);
		panelBonoActual.setBorder(new LineBorder(new Color(180, 210, 240), 1, true));
		panelBonoActual.setBackground(new Color(235, 245, 255));
		panelBonos.add(panelBonoActual);

		JLabel lblBonoTxt = new JLabel("Tu bono:");
		lblBonoTxt.setFont(new Font("Segoe UI", Font.BOLD, 11));
		lblBonoTxt.setForeground(new Color(20, 60, 120));
		lblBonoTxt.setBounds(10, 10, 70, 17);
		panelBonoActual.add(lblBonoTxt);

		JLabel lblBonoVal = new JLabel("—");
		lblBonoVal.setFont(new Font("Segoe UI", Font.PLAIN, 11));
		lblBonoVal.setForeground(new Color(60, 80, 120));
		lblBonoVal.setBounds(85, 10, 580, 17);
		panelBonoActual.add(lblBonoVal);

		JLabel lblBonos = new JLabel("Bonos disponibles:");
		lblBonos.setFont(new Font("Segoe UI", Font.BOLD, 11));
		lblBonos.setForeground(new Color(60, 80, 120));
		lblBonos.setBounds(12, 58, 180, 17);
		panelBonos.add(lblBonos);

		tableBonos = new JTable(modeloBonos);
		estilizarTabla(tableBonos);
		tableBonos.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
		JScrollPane scrollBonos = new JScrollPane(tableBonos);
		scrollBonos.setBounds(12, 78, 700, 255);
		scrollBonos.setBorder(new LineBorder(new Color(180, 210, 240), 1, true));
		panelBonos.add(scrollBonos);

		JLabel lblDescBono = new JLabel(" ");
		lblDescBono.setForeground(Color.GRAY);
		lblDescBono.setBounds(12, 340, 560, 17);
		panelBonos.add(lblDescBono);

		JButton btnComprar = btnVerde("Comprar");
		btnComprar.setFont(new Font("Segoe UI", Font.BOLD, 12));
		btnComprar.setBounds(12, 362, 130, 34);
		panelBonos.add(btnComprar);
		btnComprar.setToolTipText("Comprar Bono");

		JButton btnMasInfo = btnAzul("Más Info");
		btnMasInfo.setBounds(155, 362, 110, 34);
		panelBonos.add(btnMasInfo);
		btnMasInfo.setToolTipText("Mas Información");

		tabbedPrincipal.addChangeListener(ev -> {
			if (tabbedPrincipal.getSelectedIndex() == 2) {
				usuarioActual = bDao.selectUsuarioById(usuarioActual.getId());
				if (usuarioActual.getBonoActual() == null) {
					lblBonoVal.setText("Sin bono  –  Compra uno para poder alquilar bicis.");
					lblBonoVal.setForeground(new Color(192, 57, 43));
				} else if (usuarioActual.getBonoExpiracion().isBefore(LocalDate.now())) {
					lblBonoVal.setText(usuarioActual.getBonoActual().getNombre() + "  (Caducado el "
							+ usuarioActual.getBonoExpiracion() + ")");
					lblBonoVal.setForeground(new Color(192, 57, 43));
				} else {
					lblBonoVal.setText(usuarioActual.getBonoActual().getNombre() + "  –  Válido hasta: "
							+ usuarioActual.getBonoExpiracion());
					lblBonoVal.setForeground(new Color(39, 174, 96));
				}
				actualizarEstadoBono();
			}
		});

		tableBonos.getSelectionModel().addListSelectionListener(e -> {
			int f = tableBonos.getSelectedRow();
			if (f >= 0)
				lblDescBono.setText("Descripción: " + modeloBonos.getValueAt(f, 3));
		});

		btnComprar.addActionListener(e -> {
			int fila = tableBonos.getSelectedRow();
			if (fila < 0) {
				JOptionPane.showMessageDialog(frame, "Selecciona un bono primero.");
				return;
			}
			int idBono = (int) modeloBonos.getValueAt(fila, 0);
			String nombre = (String) modeloBonos.getValueAt(fila, 1);
			String tipo = (String) modeloBonos.getValueAt(fila, 2);

			if (tieneBonoActivo()) {
				int c = JOptionPane.showConfirmDialog(frame,
						"Ya tienes el bono \"" + usuarioActual.getBonoActual().getNombre() + "\" activo hasta "
								+ usuarioActual.getBonoExpiracion() + ".\n" + "¿Quieres reemplazarlo por \"" + nombre
								+ "\"?",
						"Bono activo", JOptionPane.YES_NO_OPTION);
				if (c != JOptionPane.YES_OPTION)
					return;
			}

			LocalDate exp = calcularExpiracion(tipo);
			Bono bonoElegido = bDao.selectBonoById(idBono);
			usuarioActual = bDao.selectUsuarioById(usuarioActual.getId());
			usuarioActual.setBonoActual(bonoElegido);
			usuarioActual.setBonoExpiracion(exp);
			bDao.updateUsuario(usuarioActual);

			lblBonoVal.setText(nombre + "  –  Válido hasta: " + exp);
			lblBonoVal.setForeground(new Color(39, 174, 96));
			actualizarEstadoBono();

			JOptionPane.showMessageDialog(frame,
					"¡Bono activado!\n" + nombre + " (" + tipo + ")\nVálido hasta: " + exp);
		});

		btnMasInfo.addActionListener(e -> {
			int f = tableBonos.getSelectedRow();
			if (f < 0) {
				JOptionPane.showMessageDialog(frame, "Selecciona un bono primero.");
				return;
			}
			JOptionPane.showMessageDialog(frame, "Nombre: " + modeloBonos.getValueAt(f, 1) + "\n" + "Duración: "
					+ modeloBonos.getValueAt(f, 2) + "\n" + "Descripción: " + modeloBonos.getValueAt(f, 3));
		});

		JPanel panelAyuda = new JPanel(null);
		panelAyuda.setBackground(Color.WHITE);
		tabbedPrincipal.addTab("Necesitas Ayuda", null, panelAyuda, null);

		JLabel lblAyuda = new JLabel("¿Con qué necesitas ayuda?");
		lblAyuda.setFont(new Font("Segoe UI", Font.BOLD, 13));
		lblAyuda.setForeground(new Color(20, 60, 120));
		lblAyuda.setBounds(12, 12, 300, 20);
		panelAyuda.add(lblAyuda);

		JButton btnEliminar = btnRojo("Eliminar Cuenta");
		btnEliminar.setBounds(12, 48, 220, 32);
		panelAyuda.add(btnEliminar);
		btnEliminar.addActionListener(e -> {
			int c = JOptionPane.showConfirmDialog(frame,
					"¿Seguro que quieres eliminar tu cuenta?\nEsta acción no se puede deshacer.", "Confirmar",
					JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
			if (c == JOptionPane.YES_OPTION) {
				bDao.deleteUsuario(usuarioActual.getId());
				JOptionPane.showMessageDialog(frame, "Cuenta eliminada. ¡Hasta pronto!");
				cerrarSesion();
			}
		});
		btnEliminar.setToolTipText("Eliminar Cuenta");
		
		JButton btnCambiarPwd = new JButton("Cambiar Contraseña");
		btnCambiarPwd.setFocusPainted(false);
		btnCambiarPwd.setBounds(12, 92, 220, 32);
		panelAyuda.add(btnCambiarPwd);
		btnCambiarPwd.setToolTipText("Cambio de Contraseña");
		btnCambiarPwd.addActionListener(e -> {
			JPasswordField pA = new JPasswordField(15), pN = new JPasswordField(15), pC = new JPasswordField(15);
			JPanel p = new JPanel(new java.awt.GridLayout(3, 2, 5, 8));
			p.add(new JLabel("Contraseña actual:"));
			p.add(pA);
			p.add(new JLabel("Nueva contraseña:"));
			p.add(pN);
			p.add(new JLabel("Confirmar nueva:"));
			p.add(pC);
			if (JOptionPane.showConfirmDialog(frame, p, "Cambiar contraseña",
					JOptionPane.OK_CANCEL_OPTION) == JOptionPane.OK_OPTION) {
				String actual = new String(pA.getPassword()), nueva = new String(pN.getPassword()),
						conf = new String(pC.getPassword());
				if (!actual.equals(usuarioActual.getContraseña()))
					JOptionPane.showMessageDialog(frame, "La contraseña actual no es correcta.");
				else if (!nueva.equals(conf))
					JOptionPane.showMessageDialog(frame, "Las nuevas contraseñas no coinciden.");
				else if (nueva.isBlank())
					JOptionPane.showMessageDialog(frame, "La nueva contraseña no puede estar vacía.");
				else {
					usuarioActual.setContraseña(nueva);
					bDao.updateUsuario(usuarioActual);
					JOptionPane.showMessageDialog(frame, "Contraseña actualizada.");
				}
			}
		});

		JButton btnCambiarEmail = new JButton("Cambiar Correo Electrónico");
		btnCambiarEmail.setFocusPainted(false);
		btnCambiarEmail.setBounds(12, 136, 220, 32);
		panelAyuda.add(btnCambiarEmail);
		btnCambiarEmail.addActionListener(e -> {
			String nuevo = JOptionPane.showInputDialog(frame, "Nuevo correo electrónico:", usuarioActual.getEmail());
			if (nuevo != null && !nuevo.isBlank()) {
				usuarioActual.setEmail(nuevo);
				bDao.updateUsuario(usuarioActual);
				JOptionPane.showMessageDialog(frame, "Correo actualizado a: " + nuevo);
			}
		});
		btnCambiarEmail.setToolTipText("Cambiar Correo");
		
		JButton btnCerrarAyuda = btnRojo("Cerrar Sesión");
		btnCerrarAyuda.setBounds(12, 180, 220, 32);
		panelAyuda.add(btnCerrarAyuda);
		btnCerrarAyuda.addActionListener(e -> {
			int c = JOptionPane.showConfirmDialog(frame, "¿Seguro que quieres cerrar sesión?", "Cerrar sesión",
					JOptionPane.YES_NO_OPTION);
			if (c == JOptionPane.YES_OPTION)
				cerrarSesion();
		});
		btnCerrarAyuda.setToolTipText("Cerrar Sesion");
		
		cargarEstacionesEnCombo(comboEstAl);
		cargarEstacionesEnCombo(comboEstDev);
		if (comboEstAl.getItemCount() > 0)
			cargarBicicletasDeEstacion((Estacion) comboEstAl.getSelectedItem());
		cargarRutasUsuario();
		cargarBonos();
		actualizarEstadoBono();

		if (!tieneBonoActivo()) {
			panelAvisoBono.setVisible(true);
			usuarioActual = bDao.selectUsuarioById(usuarioActual.getId());
			if (usuarioActual.getBonoActual() != null)
				lblAvisoBono.setText("Tu bono caducó el " + usuarioActual.getBonoExpiracion() + ". Compra uno nuevo.");
		}
		centrarTabla(tableBicis);
		centrarTabla(tableRutas);
		centrarTabla(tableBonos);
	}

	public JFrame getFrame() {
		return frame;
	}
}