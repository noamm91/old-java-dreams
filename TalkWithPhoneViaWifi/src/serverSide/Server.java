package serverSide;

import java.awt.AWTException;
import java.awt.Robot;
import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

	
	
	public void connectAndSend(int port) {
		ServerSocket server;
		
		try {
			Robot robot = new Robot();
			server = new ServerSocket(port);
			port = server.getLocalPort();
			System.out.println(port);
			Socket socket = server.accept();
			InputStream is = null;
			is = socket.getInputStream();
			int data = -1;
			int x = 0;
			int y;
			int newPos = 0;
			while ((data = is.read()) != -1){
				if (newPos == 0) {
					x = data;
					newPos++;
				}
				if (newPos == 1) {
					newPos = 0;
					y = data;
					//System.out.println(x);
					robot.mouseMove(x, y);	
				}
				
			}
			server.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (AWTException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}	
}
