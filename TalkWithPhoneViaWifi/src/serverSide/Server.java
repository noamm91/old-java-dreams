package serverSide;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.InputEvent;
import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
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

import com.example.noamm_000.talkwithcompviawifi.CommonServerAndClient.DataObject;

public class Server extends SwingWorker<Void, String> {

	private int port;
	private String mode;
	//private JTextArea taLog;
	private Document taControlDoc;
	private Document taDataDoc;
	private JButton restartBtn;
	
	public Server(JButton btn, Document cDoc, Document dDoc, String mode) {
		restartBtn = btn;
		taControlDoc = cDoc;
		taDataDoc = dDoc;
		this.mode = mode;
		try {
			taControlDoc.remove(0, taControlDoc.getLength());
			taDataDoc.remove(0, taDataDoc.getLength());
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
			ObjectInputStream objectInputStream = new ObjectInputStream(is);
			int x = 700;
			int y = 500;
			byte[] floatInByteArr = new byte[4];
			int bytesRecived = 0; 
			if (mode.equals("float")) {
				while ((is.read(floatInByteArr)) == 4) {	
					float value = ByteBuffer.wrap(floatInByteArr).order(ByteOrder.BIG_ENDIAN).getFloat();
					bytesRecived += 4;
					publish(Integer.toString(bytesRecived), Float.toString(value));
					//publish("D", Float.toString(value));
					if(value == 1) {
						int mask = InputEvent.BUTTON1_DOWN_MASK;
						robot.mousePress(mask);
						robot.mouseRelease(mask);
					}
					else if(value == 2) {
						int mask = InputEvent.BUTTON3_DOWN_MASK;
						robot.mousePress(mask);
						robot.mouseRelease(mask);
					}
					else {
						y += value;
						//publish("D", Float.toString(value));
						robot.mouseMove(x, y);			
					}
				}
				server.close();
			}
			else if(mode.equals("object")) {
				DataObject dataObject;
				Object obj;
				while((obj = objectInputStream.readObject()) != null) {
					dataObject = (DataObject) obj;
					y += dataObject.getyDelta();
					x += dataObject.getxDelta();
					robot.mouseMove(x, y);
					if(dataObject.isClickedUp()) {
						robot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
						robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
					}
					else if(dataObject.isClickedDown()) {
						robot.mousePress(InputEvent.BUTTON3_DOWN_MASK);
						robot.mouseRelease(InputEvent.BUTTON3_DOWN_MASK);
					}
					bytesRecived += 4;
					publish(Integer.toString(bytesRecived), "New position is: (" + Integer.toString(x) + " , " + Integer.toString(y) + ")\n");
					
				}
			
			}
			//server.close();
		} catch(EOFException e) {
			publish("\nNo more objects from client");
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
		if(chunks.size() == 1) {
			String ctrlMsg = chunks.get(0);
			try {
				taControlDoc.insertString(taControlDoc.getLength(), ctrlMsg + " " , null);
			} catch (BadLocationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		else if(chunks.size() > 1) {
			String ctrlMsg = chunks.get(0);
			String dataMsg = chunks.get(1);
			try {
				taControlDoc.insertString(taControlDoc.getLength(), ctrlMsg + " " , null);
				taDataDoc.insertString(taDataDoc.getLength(), dataMsg + " " , null);
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