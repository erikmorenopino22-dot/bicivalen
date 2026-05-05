package com.hibernate;

import java.awt.EventQueue;
import java.awt.Font;

import javax.swing.JFrame;
import javax.swing.JTabbedPane;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.table.DefaultTableModel;

import com.formdev.flatlaf.FlatLightLaf;
///import com.github.lgooddatepicker.components.TimePicker;
import com.hibernate.dao.BicivalenDAO;
import com.hibernate.model.Bicicleta;
import com.hibernate.model.Bono;
import com.hibernate.model.Estacion;
import com.hibernate.model.Ruta;
import com.hibernate.model.Usuario;

import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JComboBox;
import javax.swing.JTextArea;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.DefaultComboBoxModel;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;
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
	private JTextField txtUbicacionE;

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

		BicivalenDAO bDao = new BicivalenDAO();

		DefaultTableModel modeloU = new DefaultTableModel();
		modeloU.addColumn("ID");
		modeloU.addColumn("Nombre");
		modeloU.addColumn("Email");
		modeloU.addColumn("Contraseña");

		DefaultTableModel modeloR = new DefaultTableModel();
		modeloR.addColumn("ID");
		modeloR.addColumn("Usuario");
		modeloR.addColumn("EstaciónO");
		modeloR.addColumn("EstaciónD");
		modeloR.addColumn("Duración");
		modeloR.addColumn("Reseña");

		DefaultTableModel modeloE = new DefaultTableModel();
		modeloE.addColumn("ID");
		modeloE.addColumn("Nombre");
		modeloE.addColumn("Ubicación");
		modeloE.addColumn("Capacidad");
		modeloE.addColumn("Puestos Libres");
		modeloE.addColumn("Bicicletas");

		DefaultTableModel modeloB = new DefaultTableModel();
		modeloB.addColumn("ID");
		modeloB.addColumn("Tipo");
		modeloB.addColumn("Estado");
		modeloB.addColumn("Ubicación");

		DefaultTableModel modeloBn = new DefaultTableModel();
		modeloBn.addColumn("ID");
		modeloBn.addColumn("Nombre");
		modeloBn.addColumn("Duración");
		modeloBn.addColumn("Descripción");

		Admin = new JFrame();
		Admin.setTitle("BiciValen-Admin");
		Admin.setBounds(100, 100, 745, 600);
		Admin.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Admin.getContentPane().setLayout(null);

		JButton btnMostrar = new JButton("Mostrar");
		btnMostrar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				modeloE.setRowCount(0);
				List<Estacion> listaEstacion = bDao.selectAllEstacion();
				for (Estacion est : listaEstacion) {
				    Object[] fila = { est.getId(), est.getNombre(), est.getUbicacion(), est.getCapacidad(), est.getPuestos() };
				    modeloE.addRow(fila);
				}
				modeloR.setRowCount(0);
				List<Ruta> listaRuta = bDao.selectAllRuta();
				for (Ruta r : listaRuta) {
				    Object[] fila = { r.getId(), r.getUsuario(), r.getEstacionI(), r.getEstacionD(), r.getDuración(), r.getValoracion() };
				    modeloR.addRow(fila);
				}
				modeloU.setRowCount(0);
				List<Usuario> listaUsuario = bDao.selectAllUsuario();
				for (Usuario u : listaUsuario) {
				    Object[] fila = { u.getId(), u.getNombre(), u.getEmail(), u.getContraseña() };
				    modeloU.addRow(fila);
				}
				modeloBn.setRowCount(0);
				List<Bono> listaBono = bDao.selectAllBono();
				for (Bono b : listaBono) {
				    Object[] fila = { b.getId(), b.getNombre(), b.getDuracion(), b.getDescripcion() };
				    modeloBn.addRow(fila);
				}
				modeloB.setRowCount(0);
				List<Bicicleta> listaBicicleta = bDao.selectAllBicicleta();
				for (Bicicleta b : listaBicicleta) {
				    Object[] fila = { b.getId(), b.getTipo(), b.getEstado() };
				    modeloB.addRow(fila);
				}
			}
		});
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

		tableU = new JTable(modeloU);
		tableU.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int index = tableU.getSelectedRow();
				txtIdU.setText(modeloU.getValueAt(index, 0).toString());
				txtNomU.setText(modeloU.getValueAt(index, 1).toString());
				txtEmailU.setText(modeloU.getValueAt(index, 2).toString());
				passwordU.setText(modeloU.getValueAt(index, 3).toString());
			}
		});
		tableU.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
		JScrollPane scrollPane = new JScrollPane(tableU);
		scrollPane.setBounds(12, 12, 548, 99);
		panelU.add(scrollPane);

		JButton btnAddU = new JButton("Add");
		btnAddU.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String nombre = txtNomU.getText();
				String email = txtEmailU.getText();
				String contraseña = passwordU.getText();
				Usuario u = Usuario.builder().nombre(nombre).email(email).contraseña(contraseña).build();
				bDao.insertUsuario(u);
				javax.swing.JOptionPane.showMessageDialog(null, "Usuario Creado");
				btnMostrar.doClick();
			}
		});
		btnAddU.setBounds(455, 118, 105, 27);
		panelU.add(btnAddU);

		JButton btnEditU = new JButton("Edit");
		btnEditU.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int codigo = Integer.parseInt(txtIdU.getText());
				String nombre = txtNomU.getText();
				String email = txtEmailU.getText();
				String contraseña = passwordU.getText();
				Usuario u = bDao.selectUsuarioById(codigo);
				u.setNombre(nombre);
				u.setEmail(email);
				u.setContraseña(contraseña);
				bDao.updateUsuario(u);
				javax.swing.JOptionPane.showMessageDialog(null, "Usuario Editado");
				btnMostrar.doClick();
			}
		});
		btnEditU.setBounds(455, 176, 105, 27);
		panelU.add(btnEditU);

		JButton btnDelU = new JButton("Del");
		btnDelU.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int codigo = Integer.parseInt(txtIdU.getText());
				bDao.deleteUsuario(codigo);
				javax.swing.JOptionPane.showMessageDialog(null, "Usuario Eliminado");
				btnMostrar.doClick();
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

		tableR = new JTable(modeloR);
		tableR.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int index = tableR.getSelectedRow();
				txtIdR.setText(modeloR.getValueAt(index, 0).toString());
				txtUsuarioR.setText(modeloR.getValueAt(index, 1).toString());
				txtEstaciónOR.setText(modeloR.getValueAt(index, 2).toString());
				txtEstaciónDR.setText(modeloR.getValueAt(index, 3).toString());
				txtDuraciónR.setText(modeloR.getValueAt(index, 4).toString());
				txtReseñaR.setText(modeloR.getValueAt(index, 5).toString());
			}
		});
		tableR.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
		JScrollPane scrollPane2 = new JScrollPane(tableR);
		scrollPane2.setBounds(12, 12, 548, 99);
		panelR.add(scrollPane2);

		JButton btnAddR = new JButton("Add");
		btnAddR.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String usuario = txtUsuarioR.getText();
				String email = txtEmailU.getText();
				String contraseña = passwordU.getText();
				Usuario u = Usuario.builder().nombre(nombre).email(email).contraseña(contraseña).build();
				bDao.insertUsuario(u);
				javax.swing.JOptionPane.showMessageDialog(null, "Usuario Creado");
				btnMostrar.doClick();
			}
		});
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
		
		JLabel lblNewLabel_1 = new JLabel("Ubicación:");
		lblNewLabel_1.setBounds(230, 124, 60, 14);
		panelE.add(lblNewLabel_1);

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
		
		txtUbicacionE = new JTextField();
		txtUbicacionE.setBounds(301, 121, 114, 20);
		panelE.add(txtUbicacionE);
		txtUbicacionE.setColumns(10);

		JLabel lblDisponible_1 = new JLabel("Disponible:");
		lblDisponible_1.setBounds(230, 152, 77, 17);
		panelE.add(lblDisponible_1);

		JCheckBox checkBoxE = new JCheckBox("");
		checkBoxE.setBounds(301, 144, 114, 25);
		panelE.add(checkBoxE);

		tableE = new JTable(modeloE);
		tableE.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int index = tableE.getSelectedRow();
				txtIdE.setText(modeloE.getValueAt(index, 0).toString());
				txtNomE.setText(modeloE.getValueAt(index, 1).toString());
				txtUbicacionE.setText(modeloE.getValueAt(index, 2).toString());
				txtCapacidadE.setText(modeloE.getValueAt(index, 3).toString());
				comboBoxBicis.setSelectedItem(modeloE.getValueAt(index, 3).toString());
				txtPuestosE.setText(modeloE.getValueAt(index, 5).toString());
			}
		});
		tableE.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
		JScrollPane scrollPane3 = new JScrollPane(tableE);
		scrollPane3.setBounds(12, 12, 548, 99);
		panelE.add(scrollPane3);

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
		comboBoxTipoB.setModel(new DefaultComboBoxModel(new String[] { "Electrica", "Basica" }));
		comboBoxTipoB.setBounds(90, 141, 114, 26);
		panelB.add(comboBoxTipoB);

		JComboBox comboBoxEstadoB = new JComboBox();
		comboBoxEstadoB.setModel(new DefaultComboBoxModel(new String[] { "Genial", "Bueno", "Malo" }));
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

		tableB = new JTable(modeloB);
		tableB.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int index = tableB.getSelectedRow();
				txtIdB.setText(modeloB.getValueAt(index, 0).toString());
				comboBoxTipoB.setSelectedItem(modeloB.getValueAt(index, 1).toString());
				comboBoxEstadoB.setSelectedItem(modeloB.getValueAt(index, 2).toString());
				txtidEstacionB.setText(modeloB.getValueAt(index, 3).toString());
			}
		});
		tableB.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
		JScrollPane scrollPane4 = new JScrollPane(tableB);
		scrollPane4.setBounds(12, 12, 548, 99);
		panelB.add(scrollPane4);

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

		//TimePicker horaSalida = new TimePicker();
		//horaSalida.setBounds(95, 181, 114, 21);
		//panelBn.add(horaSalida);

		tableBn = new JTable(modeloBn);
		tableBn.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int index = tableBn.getSelectedRow();
				txtIdBn.setText(modeloBn.getValueAt(index, 0).toString());
				txtNomBn.setText(modeloBn.getValueAt(index, 1).toString());
				//horaSalida.setText(modeloBn.getValueAt(index, 2).toString());
				DescripcionBn.setText(modeloBn.getValueAt(index, 2).toString());
			}
		});
		tableBn.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
		JScrollPane scrollPane5 = new JScrollPane(tableBn);
		scrollPane5.setBounds(12, 12, 548, 99);
		panelBn.add(scrollPane5);

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