package serverSide;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.JTextArea;

public class GUI {

	private JFrame frmServerSide;
	private JTextField tfPort;

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
		frmServerSide.setBounds(100, 100, 450, 300);
		frmServerSide.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmServerSide.getContentPane().setLayout(null);
		
		JLabel lblPort = new JLabel("Port:");
		lblPort.setBounds(10, 11, 37, 14);
		frmServerSide.getContentPane().add(lblPort);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 155, 414, 95);
		frmServerSide.getContentPane().add(scrollPane);
		
		JTextArea taLog = new JTextArea();
		scrollPane.setViewportView(taLog);
		taLog.setWrapStyleWord(true);
		taLog.setLineWrap(true);
		
		JButton btnConnectAndSend = new JButton("Listen");
		
		btnConnectAndSend.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Server server = new Server(btnConnectAndSend, taLog.getDocument());
				try {
					server.setPort(Integer.parseInt(tfPort.getText().toString())); 
				
				} catch (NumberFormatException exception) {
				
					server.setPort(700);
					System.out.println("No port provided, set to default: 700");
					
				}
				btnConnectAndSend.setEnabled(false);
				server.execute();
			}
			}
		);
		
		btnConnectAndSend.setBounds(10, 36, 165, 23);
		frmServerSide.getContentPane().add(btnConnectAndSend);
		
		tfPort = new JTextField();
		tfPort.setBounds(43, 8, 86, 20);
		frmServerSide.getContentPane().add(tfPort);
		tfPort.setColumns(10);
		
		JLabel lblIP = new JLabel("IP Address:");
		lblIP.setBounds(10, 76, 67, 14);
		frmServerSide.getContentPane().add(lblIP);
		
		/*
		JLabel lbIPValue = new JLabel(server.getIP());
		lbIPValue.setBounds(83, 76, 137, 14);
		frmServerSide.getContentPane().add(lbIPValue);
		*/
		JLabel lblLog = new JLabel("Log:");
		lblLog.setBounds(10, 140, 46, 14);
		frmServerSide.getContentPane().add(lblLog);
		
	}
}
