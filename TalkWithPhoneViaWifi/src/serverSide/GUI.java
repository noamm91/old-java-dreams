package serverSide;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JTextField;

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
					Server server = new Server();
					GUI window = new GUI(server);
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
	public GUI(Server server) {
		
		initialize(server);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize(Server server) {
		frmServerSide = new JFrame();
		frmServerSide.setTitle("Server Side - Listening To Phone");
		frmServerSide.setBounds(100, 100, 450, 300);
		frmServerSide.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmServerSide.getContentPane().setLayout(null);
		
		JLabel lblPort = new JLabel("Port:");
		lblPort.setBounds(10, 11, 37, 14);
		frmServerSide.getContentPane().add(lblPort);
		
		JButton btnConnectAndSend = new JButton("Connect And Listen");
		btnConnectAndSend.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				server.connectAndSend( Integer.parseInt(tfPort.getText().toString()));
			}
		});
		btnConnectAndSend.setBounds(10, 36, 165, 23);
		frmServerSide.getContentPane().add(btnConnectAndSend);
		
		tfPort = new JTextField();
		tfPort.setBounds(43, 8, 86, 20);
		frmServerSide.getContentPane().add(tfPort);
		tfPort.setColumns(10);
	}
}
