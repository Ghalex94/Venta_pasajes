package gui;

import java.awt.EventQueue;

import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.awt.event.ActionEvent;
import java.awt.Font;
import java.awt.Image;

import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.SwingConstants;

import clases.Conductor;
import clases.Empresa;
import clases.Vehiculo;
import mysql.Consultas;

import java.awt.Color;
import org.eclipse.wb.swing.FocusTraversalOnArray;
import java.awt.Component;
import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;

public class viDatos1 extends JInternalFrame implements ActionListener, KeyListener {
	private JLabel lblNewLabel;
	private JLabel lblVehiculo;
	private JButton btnContinuar;
	private JComboBox <Empresa> cbEmpresa;
	private JComboBox <Vehiculo> cbVehiculo;
	
	vPrincipal  vp = null;
	ResultSet rs;
	private JButton btnCancelar;
	private JLabel lblPrecioDePasaje;
	private JTextField txtPrePasaje;
	private JLabel label;
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					viDatos1 frame = new viDatos1(null);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public viDatos1(vPrincipal temp) {
		getContentPane().setBackground(Color.LIGHT_GRAY);
		setTitle("SELECCIONE");
		vp = temp;
		setBounds(100, 100, 871, 254);
		getContentPane().setLayout(null);
		
		lblNewLabel = new JLabel("Empresa:");
		lblNewLabel.setHorizontalAlignment(SwingConstants.LEFT);
		lblNewLabel.setFont(new Font("EngraversGothic BT", Font.PLAIN, 25));
		lblNewLabel.setBounds(35, 49, 132, 20);
		getContentPane().add(lblNewLabel);
		
		lblVehiculo = new JLabel("Vehiculo:");
		lblVehiculo.setHorizontalAlignment(SwingConstants.LEFT);
		lblVehiculo.setFont(new Font("EngraversGothic BT", Font.PLAIN, 25));
		lblVehiculo.setBounds(35, 112, 132, 20);
		getContentPane().add(lblVehiculo);
		
		btnContinuar = new JButton("Continuar");
		btnContinuar.setForeground(Color.WHITE);
		btnContinuar.setBackground(Color.DARK_GRAY);
		btnContinuar.setFont(new Font("EngraversGothic BT", Font.BOLD, 25));
		btnContinuar.addActionListener(this);
		btnContinuar.setBounds(590, 158, 212, 31);
		getContentPane().add(btnContinuar);
		
		cbEmpresa = new JComboBox();
		cbEmpresa.setFont(new Font("Segoe UI Semibold", Font.PLAIN, 18));
		cbEmpresa.setBounds(162, 49, 640, 23);
		getContentPane().add(cbEmpresa);
		
		cbVehiculo = new JComboBox();
		cbVehiculo.setFont(new Font("Segoe UI Semibold", Font.PLAIN, 18));
		cbVehiculo.setBounds(162, 109, 640, 25);
		getContentPane().add(cbVehiculo);
		
		btnCancelar = new JButton("Cancelar");
		btnCancelar.addActionListener(this);
		btnCancelar.setForeground(Color.WHITE);
		btnCancelar.setFont(new Font("EngraversGothic BT", Font.BOLD, 25));
		btnCancelar.setBackground(Color.DARK_GRAY);
		btnCancelar.setBounds(390, 158, 190, 31);
		getContentPane().add(btnCancelar);
		
		lblPrecioDePasaje = new JLabel("Precio de pasaje:");
		lblPrecioDePasaje.setHorizontalAlignment(SwingConstants.LEFT);
		lblPrecioDePasaje.setFont(new Font("EngraversGothic BT", Font.PLAIN, 25));
		lblPrecioDePasaje.setBounds(35, 166, 229, 20);
		getContentPane().add(lblPrecioDePasaje);
		
		txtPrePasaje = new JTextField();
		txtPrePasaje.setHorizontalAlignment(SwingConstants.RIGHT);
		txtPrePasaje.addKeyListener(this);
		txtPrePasaje.setText("0");
		txtPrePasaje.setFont(new Font("Segoe UI Semibold", Font.PLAIN, 18));
		txtPrePasaje.setColumns(10);
		txtPrePasaje.setBounds(250, 164, 52, 25);
		getContentPane().add(txtPrePasaje);
		
		label = new JLabel(".00");
		label.setBackground(Color.WHITE);
		label.setFont(new Font("Segoe UI", Font.BOLD, 18));
		label.setBounds(303, 166, 52, 23);
		getContentPane().add(label);
		setFocusTraversalPolicy(new FocusTraversalOnArray(new Component[]{cbEmpresa, cbVehiculo, txtPrePasaje, btnContinuar, btnCancelar}));
		cargar();
	}
	
	public void cargar(){
		Vehiculo vehiculo = new Vehiculo();
		vehiculo.cargarVehiculo(cbVehiculo);
		
		Empresa empresa = new Empresa();
		empresa.cargarEmpresas(cbEmpresa);
	}
	
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == btnCancelar) {
			actionPerformedBtnCancelar(e);
		}
		if (e.getSource() == btnContinuar) {
			actionPerformedBtnContinuar(e);
		}
	}
	
	protected void actionPerformedBtnCancelar(ActionEvent e){		
		this.setVisible(false);
		vp.esconderVentanas();
		vp.cerrarVentanas();
	}
	
	protected void actionPerformedBtnContinuar(ActionEvent e) {
		if(txtPrePasaje.getText().length() <=0)
			JOptionPane.showMessageDialog(null, "Ingrese el precio del pasaje");
		else{
			vp.esconderVentanas();
			vp.cerrarVentanas();
			int empresa = 0;
			empresa = cbEmpresa.getItemAt(cbEmpresa.getSelectedIndex()).getIdempresa(); //1MERMA  2SIGUEL
			//JOptionPane.showMessageDialog(null, ""+empresa);
			int dniconductor = cbVehiculo.getItemAt(cbVehiculo.getSelectedIndex()).getDniconductor();
			String placa = cbVehiculo.getItemAt(cbVehiculo.getSelectedIndex()).getPlaca();
			int idmodelovh = cbVehiculo.getItemAt(cbVehiculo.getSelectedIndex()).getIdmodelo();
			String modelovh = cbVehiculo.getItemAt(cbVehiculo.getSelectedIndex()).getModelo();
			float prepasaje = Float.parseFloat(txtPrePasaje.getText());
			Consultas consulta = new Consultas();
			consulta.actualizarVentaTemporal01(1, empresa, dniconductor, placa, idmodelovh, prepasaje);
			vp.mntmCrearNuevaSalida.setEnabled(false);
			vp.mntmContinuarPreparacion.setEnabled(true);
			vp.mntmCancelarSalida.setEnabled(true);
			vp.mnFormatos.setEnabled(true);
			
			switch(idmodelovh){
			case 1:
				vp.sa1 = new viSeleccionAsientos1(vp);		// Mercedes Sprinter 413 19+1 Asientos
				vp.desktopPane.add(vp.sa1);
				vp.sa1.show();
				vp.sa1.txtTitulo.setText(modelovh);
				try{
					vp.sa1.setMaximum(true);
				}catch(Exception f){}
				break;
			case 2:
				vp.sa1 = new viSeleccionAsientos1(vp);     // Mercedes sprinter 515 19+1 				
				vp.desktopPane.add(vp.sa1);
				vp.sa1.show();
				vp.sa1.txtTitulo.setText(modelovh);
				Image imBanner = new ImageIcon(this.getClass().getResource("/mv02.png")).getImage();
				vp.sa1.lblBanner.setIcon(new ImageIcon(imBanner));
				try{
					vp.sa1.setMaximum(true);
				}catch(Exception f){}
				break;
			case 3:
				vp.sa2 = new viSeleccionAsientos2(vp);     // Mercedes sprinter 515 20+1				
				vp.desktopPane.add(vp.sa2);
				vp.sa2.show();
				vp.sa2.txtTitulo.setText(modelovh);
				try{
					vp.sa2.setMaximum(true);
				}catch(Exception f){}
				break;
			case 4:
				vp.sa3 = new viSeleccionAsientos3(vp);    // Renault 2012 15
				vp.desktopPane.add(vp.sa3);
				vp.sa3.show();
				vp.sa3.txtTitulo.setText(modelovh);
				try{
					vp.sa3.setMaximum(true);
				}catch(Exception f){}
				break;
			case 5:
				vp.sa4 = new viSeleccionAsientos4(vp);     // Renault master moderna
				vp.desktopPane.add(vp.sa4);
				vp.sa4.show();
				vp.sa4.txtTitulo.setText(modelovh);
				try{
					vp.sa4.setMaximum(true);
				}catch(Exception f){}
				break;
			case 6:
				vp.sa2 = new viSeleccionAsientos2(vp);     // Wolskwagen Crafter
				vp.desktopPane.add(vp.sa2);
				vp.sa2.show();
				vp.sa2.txtTitulo.setText(modelovh);
				Image imBanner2 = new ImageIcon(this.getClass().getResource("/mv06.png")).getImage();
				vp.sa2.lblBanner.setIcon(new ImageIcon(imBanner2));
				try{
					vp.sa2.setMaximum(true);
				}catch(Exception f){}
				break;
			}
			
		}
	}
	public void keyPressed(KeyEvent arg0) {
	}
	public void keyReleased(KeyEvent arg0) {
	}
	public void keyTyped(KeyEvent arg0) {
		if (arg0.getSource() == txtPrePasaje) {
			keyTypedTxtPrePasaje(arg0);
		}
	}
	protected void keyTypedTxtPrePasaje(KeyEvent arg0) {
		char c = arg0.getKeyChar();
		if ((c<'0' || c>'9') && (c!=(char)KeyEvent.VK_DELETE) && (c!=(char)KeyEvent.VK_BACK_SPACE)){
			arg0.consume();
		}
		if (txtPrePasaje.getText().length() == 4){
			arg0.consume();
		}
	}
}
