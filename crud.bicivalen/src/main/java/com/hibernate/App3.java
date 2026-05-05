package com.hibernate;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

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
		for (Bicicleta b : estFull.getBicicletas()) {
			modeloBicis.addRow(new Object[] { b.getId(), b.getTipo(), b.getEstado() });
		}
	}

	private void cargarRutasUsuario() {
		modeloRutas.setRowCount(0);
		for (Ruta r : bDao.selectAllRuta()) {
			if (r.getUsuario() != null && r.getUsuario().getId() == usuarioActual.getId()) {
				modeloRutas
						.addRow(new Object[] { r.getId(), r.getEstacionI() != null ? r.getEstacionI().getNombre() : "-",
								r.getEstacionD() != null ? r.getEstacionD().getNombre() : "-", r.getDuracion() + " min",
								r.getValoracion() });
			}
		}
	}

	private void cargarBonos() {
		modeloBonos.setRowCount(0);
		for (Bono bn : bDao.selectAllBono()) {
			modeloBonos.addRow(new Object[] { bn.getId(), bn.getNombre(), bn.getTipo(), bn.getDescripcion() });
		}
	}

	private void cerrarSesion() {
		frame.dispose();
		if (frameLogin != null) {
			frameLogin.setVisible(true);
		} else {
			App2 login = new App2();
			login.getFrame().setVisible(true);
		}
	}

	private void initialize() {

		frame = new JFrame("BiciValen – " + usuarioActual.getNombre());
		frame.setBounds(100, 100, 745, 600);
		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		frame.addWindowListener(new java.awt.event.WindowAdapter() {
			@Override
			public void windowClosing(java.awt.event.WindowEvent we) {
				String[] opciones = { "Cerrar sesión", "Salir de la app", "Cancelar" };
				int resp = JOptionPane.showOptionDialog(frame, "¿Qué deseas hacer?", "Salir",
						JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, opciones, opciones[2]);
				if (resp == 0)
					cerrarSesion();
				else if (resp == 1)
					System.exit(0);
			}
		});

		frame.getContentPane().setLayout(null);

		JLabel lblBienvenida = new JLabel("BiciValen   |   " + usuarioActual.getNombre());
		lblBienvenida.setFont(new Font("Segoe UI", Font.BOLD, 17));
		lblBienvenida.setForeground(new Color(20, 60, 120));
		lblBienvenida.setBounds(18, 14, 550, 28);
		frame.getContentPane().add(lblBienvenida);

		JLabel lblSub = new JLabel("Panel de usuario");
		lblSub.setFont(new Font("Segoe UI", Font.PLAIN, 11));
		lblSub.setForeground(Color.GRAY);
		lblSub.setBounds(18, 40, 300, 17);
		frame.getContentPane().add(lblSub);

		JTabbedPane tabbedPrincipal = new JTabbedPane(JTabbedPane.TOP);
		tabbedPrincipal.setBounds(10, 70, 710, 470);
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
		modeloBonos = new DefaultTableModel(new String[] { "ID", "Nombre", "Tipo", "Descripción" }, 0) {
			public boolean isCellEditable(int r, int c) {
				return false;
			}
		};
		JTabbedPane tabbedBicis = new JTabbedPane(JTabbedPane.TOP);
		tabbedPrincipal.addTab("Gestión Bicis", null, tabbedBicis, null);

		JPanel panelAl = new JPanel();
		panelAl.setLayout(null);
		tabbedBicis.addTab("Alquilar", null, panelAl, null);

		JLabel lblEstActual = new JLabel("¿En qué estación estás?");
		lblEstActual.setBounds(12, 12, 160, 17);
		panelAl.add(lblEstActual);

		JComboBox<Estacion> comboEstAl = new JComboBox<>();
		comboEstAl.setBounds(180, 8, 280, 26);
		panelAl.add(comboEstAl);

		JLabel lblEligeBici = new JLabel("Bicicletas disponibles:");
		lblEligeBici.setBounds(12, 45, 160, 17);
		panelAl.add(lblEligeBici);

		tableBicis = new JTable(modeloBicis);
		tableBicis.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
		JScrollPane scrollBicis = new JScrollPane(tableBicis);
		scrollBicis.setBounds(12, 65, 540, 100);
		panelAl.add(scrollBicis);

		JLabel lblAvisoAl = new JLabel(" ");
		lblAvisoAl.setForeground(new Color(200, 50, 50));
		lblAvisoAl.setBounds(12, 172, 450, 17);
		panelAl.add(lblAvisoAl);

		JButton btnAlquilar = new JButton("Alquilar");
		btnAlquilar.setBackground(new Color(41, 128, 185));
		btnAlquilar.setForeground(Color.WHITE);
		btnAlquilar.setBounds(220, 196, 120, 32);
		panelAl.add(btnAlquilar);

		comboEstAl.addActionListener(e -> {
			cargarBicicletasDeEstacion((Estacion) comboEstAl.getSelectedItem());
			lblAvisoAl.setText(" ");
		});

		btnAlquilar.addActionListener(e -> {
			if (biciAlquilada != null) {
				lblAvisoAl.setText("Ya tienes una bici alquilada. Ve a Devolver primero.");
				return;
			}
			int fila = tableBicis.getSelectedRow();
			if (fila < 0) {
				lblAvisoAl.setText("Selecciona una bicicleta de la tabla.");
				return;
			}
			int idBici = (int) modeloBicis.getValueAt(fila, 0);
			biciAlquilada = bDao.selectBicicletaById(idBici);
			estacionOrigen = (Estacion) comboEstAl.getSelectedItem();
			horaInicio = LocalDateTime.now();

			JOptionPane.showMessageDialog(frame,
					"¡Buen viaje!\nBicicleta: " + biciAlquilada + "\nDesde: " + estacionOrigen.getNombre());
			lblAvisoAl.setText(" ");
		});

		JPanel panelDev = new JPanel();
		panelDev.setLayout(null);
		tabbedBicis.addTab("Devolver", null, panelDev, null);

		JLabel lblEstDest = new JLabel("¿En qué estación devuelves?");
		lblEstDest.setBounds(12, 12, 190, 17);
		panelDev.add(lblEstDest);

		JComboBox<Estacion> comboEstDev = new JComboBox<>();
		comboEstDev.setBounds(205, 8, 250, 26);
		panelDev.add(comboEstDev);

		JLabel lblBiciActual = new JLabel("Bici alquilada: ninguna");
		lblBiciActual.setForeground(Color.GRAY);
		lblBiciActual.setBounds(12, 48, 500, 17);
		panelDev.add(lblBiciActual);

		JLabel lblValoracion = new JLabel("Valoración (1-5):");
		lblValoracion.setBounds(12, 78, 120, 17);
		panelDev.add(lblValoracion);

		JSpinner spinnerVal = new JSpinner(new SpinnerNumberModel(5, 1, 5, 1));
		spinnerVal.setBounds(135, 74, 70, 26);
		panelDev.add(spinnerVal);

		JLabel lblAvisoD = new JLabel(" ");
		lblAvisoD.setForeground(new Color(200, 50, 50));
		lblAvisoD.setBounds(12, 112, 450, 17);
		panelDev.add(lblAvisoD);

		JButton btnDevolver = new JButton("Devolver");
		btnDevolver.setBackground(new Color(39, 174, 96));
		btnDevolver.setForeground(Color.WHITE);
		btnDevolver.setBounds(220, 140, 120, 32);
		panelDev.add(btnDevolver);

		tabbedBicis.addChangeListener(ev -> {
			if (tabbedBicis.getSelectedIndex() == 1) {
				if (biciAlquilada != null) {
					lblBiciActual
							.setText("Bici: " + biciAlquilada + "  |  Inicio: " + horaInicio.toLocalTime().withNano(0));
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
			long duracionMin = ChronoUnit.MINUTES.between(horaInicio, LocalDateTime.now());
			int valoracion = (int) spinnerVal.getValue();

			Ruta ruta = Ruta.builder().usuario(usuarioActual).bicicleta(biciAlquilada).estacionI(estacionOrigen)
					.estacionD(estDest).duracion((int) duracionMin).valoracion(valoracion).build();
			bDao.insertRuta(ruta);

			biciAlquilada.setEstacion(estDest);
			bDao.updateBicicleta(biciAlquilada);

			JOptionPane.showMessageDialog(frame,
					"¡Gracias!\nDuración: " + duracionMin + " min  |  Valoración: " + valoracion);

			biciAlquilada = null;
			estacionOrigen = null;
			horaInicio = null;

			lblBiciActual.setText("No tienes ninguna bici alquilada.");
			lblBiciActual.setForeground(Color.GRAY);
			lblAvisoD.setText(" ");
			cargarRutasUsuario();
		});

		JPanel panelRutas = new JPanel();
		panelRutas.setLayout(null);
		tabbedPrincipal.addTab("Tus Rutas", null, panelRutas, null);

		tableRutas = new JTable(modeloRutas);
		tableRutas.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
		JScrollPane scrollRutas = new JScrollPane(tableRutas);
		scrollRutas.setBounds(12, 12, 678, 350);
		panelRutas.add(scrollRutas);

		JButton btnRefreshRutas = new JButton("Actualizar");
		btnRefreshRutas.setBounds(12, 370, 130, 28);
		panelRutas.add(btnRefreshRutas);
		btnRefreshRutas.addActionListener(e -> cargarRutasUsuario());

		JPanel panelBonos = new JPanel();
		panelBonos.setLayout(null);
		tabbedPrincipal.addTab("Comprar Bonos", null, panelBonos, null);

		JLabel lblBonoSel = new JLabel("Selecciona un bono y pulsa Comprar:");
		lblBonoSel.setBounds(12, 12, 300, 17);
		panelBonos.add(lblBonoSel);

		tableBonos = new JTable(modeloBonos);
		tableBonos.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
		tableBonos.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
		JScrollPane scrollBonos = new JScrollPane(tableBonos);
		scrollBonos.setBounds(12, 35, 678, 280);
		panelBonos.add(scrollBonos);

		JLabel lblInfoBono = new JLabel(" ");
		lblInfoBono.setForeground(new Color(100, 100, 100));
		lblInfoBono.setBounds(12, 323, 550, 17);
		panelBonos.add(lblInfoBono);

		JButton btnComprar = new JButton("Comprar");
		btnComprar.setBackground(new Color(41, 128, 185));
		btnComprar.setForeground(Color.WHITE);
		btnComprar.setBounds(12, 348, 120, 32);
		panelBonos.add(btnComprar);

		JButton btnMasInfo = new JButton("Más Info");
		btnMasInfo.setBounds(145, 348, 120, 32);
		panelBonos.add(btnMasInfo);

		tableBonos.getSelectionModel().addListSelectionListener(e -> {
			int fila = tableBonos.getSelectedRow();
			if (fila >= 0)
				lblInfoBono.setText("Descripción: " + modeloBonos.getValueAt(fila, 3));
		});

		btnComprar.addActionListener(e -> {
			int fila = tableBonos.getSelectedRow();
			if (fila < 0) {
				JOptionPane.showMessageDialog(frame, "Selecciona un bono primero.");
				return;
			}
			JOptionPane.showMessageDialog(frame, "¡Bono comprado!\n" + modeloBonos.getValueAt(fila, 1) + " ("
					+ modeloBonos.getValueAt(fila, 2) + ")\nSe ha añadido a tu cuenta.");
		});

		btnMasInfo.addActionListener(e -> {
			int fila = tableBonos.getSelectedRow();
			if (fila < 0) {
				JOptionPane.showMessageDialog(frame, "Selecciona un bono primero.");
				return;
			}
			JOptionPane.showMessageDialog(frame, "Nombre: " + modeloBonos.getValueAt(fila, 1) + "\n" + "Tipo: "
					+ modeloBonos.getValueAt(fila, 2) + "\n" + "Descripción: " + modeloBonos.getValueAt(fila, 3));
		});

		JPanel panelAyuda = new JPanel();
		panelAyuda.setLayout(null);
		tabbedPrincipal.addTab("Necesitas Ayuda", null, panelAyuda, null);

		JLabel lblAyudaTitulo = new JLabel("¿Con qué necesitas ayuda?");
		lblAyudaTitulo.setFont(new Font("Segoe UI", Font.BOLD, 13));
		lblAyudaTitulo.setBounds(12, 12, 300, 20);
		panelAyuda.add(lblAyudaTitulo);

		JButton btnEliminarCuenta = new JButton("Eliminar Cuenta");
		btnEliminarCuenta.setBounds(12, 48, 220, 32);
		panelAyuda.add(btnEliminarCuenta);
		btnEliminarCuenta.addActionListener(e -> {
			int confirm = JOptionPane.showConfirmDialog(frame,
					"¿Seguro que quieres eliminar tu cuenta?\nEsta acción no se puede deshacer.",
					"Confirmar eliminación", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
			if (confirm == JOptionPane.YES_OPTION) {
				bDao.deleteUsuario(usuarioActual.getId());
				JOptionPane.showMessageDialog(frame, "Cuenta eliminada. ¡Hasta pronto!");
				cerrarSesion();
			}
		});

		JButton btnCambiarPwd = new JButton("Cambiar Contraseña");
		btnCambiarPwd.setBounds(12, 92, 220, 32);
		panelAyuda.add(btnCambiarPwd);
		btnCambiarPwd.addActionListener(e -> {
			JPasswordField pwdActual = new JPasswordField(15);
			JPasswordField pwdNueva = new JPasswordField(15);
			JPasswordField pwdConf = new JPasswordField(15);

			JPanel p = new JPanel(new java.awt.GridLayout(3, 2, 5, 8));
			p.add(new JLabel("Contraseña actual:"));
			p.add(pwdActual);
			p.add(new JLabel("Nueva contraseña:"));
			p.add(pwdNueva);
			p.add(new JLabel("Confirmar nueva:"));
			p.add(pwdConf);

			int res = JOptionPane.showConfirmDialog(frame, p, "Cambiar contraseña", JOptionPane.OK_CANCEL_OPTION);
			if (res == JOptionPane.OK_OPTION) {
				String actual = new String(pwdActual.getPassword());
				String nueva = new String(pwdNueva.getPassword());
				String conf = new String(pwdConf.getPassword());

				if (!actual.equals(usuarioActual.getContraseña())) {
					JOptionPane.showMessageDialog(frame, "La contraseña actual no es correcta.");
				} else if (!nueva.equals(conf)) {
					JOptionPane.showMessageDialog(frame, "Las nuevas contraseñas no coinciden.");
				} else if (nueva.isBlank()) {
					JOptionPane.showMessageDialog(frame, "La nueva contraseña no puede estar vacía.");
				} else {
					usuarioActual.setContraseña(nueva);
					bDao.updateUsuario(usuarioActual);
					JOptionPane.showMessageDialog(frame, "Contraseña actualizada correctamente.");
				}
			}
		});

		JButton btnCambiarEmail = new JButton("Cambiar Correo Electrónico");
		btnCambiarEmail.setBounds(12, 136, 220, 32);
		panelAyuda.add(btnCambiarEmail);
		btnCambiarEmail.addActionListener(e -> {
			String nuevoEmail = JOptionPane.showInputDialog(frame, "Introduce el nuevo correo electrónico:",
					usuarioActual.getEmail());
			if (nuevoEmail != null && !nuevoEmail.isBlank()) {
				usuarioActual.setEmail(nuevoEmail);
				bDao.updateUsuario(usuarioActual);
				JOptionPane.showMessageDialog(frame, "Correo actualizado a: " + nuevoEmail);
			}
		});

		JButton btnCerrarSesion = new JButton("Cerrar Sesión");
		btnCerrarSesion.setBounds(12, 180, 220, 32);
		btnCerrarSesion.setBackground(new Color(192, 57, 43));
		btnCerrarSesion.setForeground(Color.WHITE);
		panelAyuda.add(btnCerrarSesion);
		btnCerrarSesion.addActionListener(e -> {
			int confirm = JOptionPane.showConfirmDialog(frame, "¿Seguro que quieres cerrar sesión?", "Cerrar sesión",
					JOptionPane.YES_NO_OPTION);
			if (confirm == JOptionPane.YES_OPTION) {
				cerrarSesion();
			}
		});

		cargarEstacionesEnCombo(comboEstAl);
		cargarEstacionesEnCombo(comboEstDev);
		if (comboEstAl.getItemCount() > 0)
			cargarBicicletasDeEstacion((Estacion) comboEstAl.getSelectedItem());
		cargarRutasUsuario();
		cargarBonos();
	}

	public JFrame getFrame() {
		return frame;
	}
}
