package serverSide;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.text.BadLocationException;

import org.w3c.dom.css.RGBColor;

import javax.swing.JTextArea;
import javax.swing.JRadioButton;

public class GUI implements ActionListener {

	private JFrame frmServerSide;
	private JTextField tfPort;
	private JButton btnConnectAndSend;
	private JTextArea taControlLog;
	private JTextArea taDataLog;
	private JRadioButton rbFloat;
	private JRadioButton rbObject;
	private ButtonGroup rg;
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					//Server server = new Server();
					GUI window = new GUI();
					window.frmServerSide.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public GUI() {
		
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		
		frmServerSide = new JFrame();
		frmServerSide.setResizable(false);
		frmServerSide.setTitle("Server Side - Listening To Phone");
		frmServerSide.setBounds(100, 100, 443, 300);
		frmServerSide.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmServerSide.getContentPane().setLayout(null);
		
		rbFloat = new JRadioButton("Direct float");
		rbFloat.setEnabled(false);
		rbFloat.setBounds(158, 7, 109, 23);
		frmServerSide.getContentPane().add(rbFloat);
		
		JRadioButton rbObject = new JRadioButton("Object");
		rbObject.setSelected(true);
		rbObject.setBounds(158, 36, 109, 23);
		frmServerSide.getContentPane().add(rbObject);
		
		rg = new ButtonGroup();
		rg.add(rbFloat);
		rg.add(rbObject);
		
		JLabel lblPort = new JLabel("Port:");
		lblPort.setBounds(10, 11, 37, 14);
		frmServerSide.getContentPane().add(lblPort);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 155, 207, 95);
		frmServerSide.getContentPane().add(scrollPane);
		
		taControlLog = new JTextArea();
		taControlLog.setEditable(false);
		scrollPane.setViewportView(taControlLog);
		taControlLog.setWrapStyleWord(true);
		taControlLog.setLineWrap(true);
		
		btnConnectAndSend = new JButton("Listen");
		btnConnectAndSend.addActionListener(this);
		
		btnConnectAndSend.setBounds(10, 36, 122, 23);
		frmServerSide.getContentPane().add(btnConnectAndSend);
		
		tfPort = new JTextField();
		tfPort.setBounds(43, 8, 89, 20);
		frmServerSide.getContentPane().add(tfPort);
		tfPort.setColumns(10);
		
		JLabel lblIP = new JLabel("IP Address:");
		lblIP.setBounds(10, 76, 67, 14);
		frmServerSide.getContentPane().add(lblIP);
		
		JLabel lblControlLog = new JLabel("Control Log:");
		lblControlLog.setBounds(10, 140, 86, 14);
		frmServerSide.getContentPane().add(lblControlLog);
		
		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(233, 156, 194, 95);
		frmServerSide.getContentPane().add(scrollPane_1);
		
		taDataLog = new JTextArea();
		taDataLog.setEditable(false);
		scrollPane_1.setViewportView(taDataLog);
		taDataLog.setWrapStyleWord(true);
		taDataLog.setLineWrap(true);
		
		JLabel lbDataLog = new JLabel("Data Log:");
		lbDataLog.setBounds(233, 140, 67, 14);
		frmServerSide.getContentPane().add(lbDataLog);
		
		
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(e.getSource() == btnConnectAndSend) {
			String mode = (rg.getSelection() == rbFloat.getModel())? "float" : "object" ;
			Server server = new Server(btnConnectAndSend, taControlLog.getDocument(), taDataLog.getDocument(), mode);
			try {
				server.setPort(Integer.parseInt(tfPort.getText().toString())); 
			} catch (NumberFormatException exception) {
				server.setPort(700);
				try {
					taControlLog.getDocument().insertString(taControlLog.getDocument().getLength(), "No port provided, set to default: 700\n", null);
				} catch (BadLocationException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				//System.out.println("No port provided, set to default: 700");
			}
		btnConnectAndSend.setEnabled(false);
		server.execute();
		}
	}
}
