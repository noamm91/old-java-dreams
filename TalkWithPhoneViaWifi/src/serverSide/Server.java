package serverSide;

import java.awt.AWTException;
import java.awt.Robot;
import java.io.IOException;
import java.io.InputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.List;
import javax.swing.JButton;
import javax.swing.SwingWorker;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;

public class Server extends SwingWorker<Void, String> {

	private int port;
	//private JTextArea taLog;
	private Document taDoc;
	private JButton restartBtn;
	
	public Server(JButton btn, Document doc) {
		restartBtn = btn;
		taDoc = doc;
		try {
			taDoc.remove(0, taDoc.getLength());
		} catch (BadLocationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void setPort(int portFromUser) {
		port = portFromUser;
	}

	public int getPort() {
		return this.port;
	}
	
	public String getIP() {
		try {
			String ia = InetAddress.getLocalHost().getHostAddress();
			return ia;
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	@Override
	protected Void doInBackground() throws Exception {
		ServerSocket server = null;
		try {
			Robot robot = new Robot();
			server = new ServerSocket(port);
			port = server.getLocalPort();
			System.out.println(port);
			publish("Waiting for client to connect at port " + port + ":\n");
			server.setSoTimeout(10000);
			Socket socket = server.accept();
			publish("Client connected!\n Bytes recived:");
			InputStream is = null;
			is = socket.getInputStream();
			int x = 700;
			int y = 500;
			byte[] floatInByteArr = new byte[4];
			int bytesRecived = 0; 
			while ((is.read(floatInByteArr)) == 4) {	
				float value = ByteBuffer.wrap(floatInByteArr).order(ByteOrder.BIG_ENDIAN).getFloat();
				y += value;
				robot.mouseMove(x, y);		
				bytesRecived += 4;
				publish(Integer.toString(bytesRecived));
				}
			server.close();
		} catch (SocketTimeoutException e) {
			if(null != server) server.close();
			publish("Timeout: no client connected for 10 seconds");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (AWTException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		return null;
	}

	@Override
	protected void process(List<String> chunks) {
		// TODO Auto-generated method stub
		super.process(chunks);
		for(String publishedData : chunks) {
			//Document doc = tpLog.getDocument();
			try {
				taDoc.insertString(taDoc.getLength(), publishedData + " " , null);
			} catch (BadLocationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	@Override
	protected void done() {
		// TODO Auto-generated method stub
		super.done();
		restartBtn.setEnabled(true);
	}
}	