import java.net.*;
import java.util.ArrayList;
import java.util.Scanner;
import java.io.*;

/**
 * A simple Server class it accepts messages from client applications and
 * responds. The server will add two integers sent by the cliens.
 * The Thread class is extended so the server can deal with multiple
 * clients at once using different threads.
 * 
 * @author Benjamin
 *
 */
public class Server extends Thread {
	private ServerSocket serverSocket;

	public Server(int port) throws IOException {
		serverSocket = new ServerSocket(port);
		// we could use a timeout for unresponsive sockets if needed
		// serverSocket.setSoTimeout(10000);
	}

	/**
	 * Implements the run() method from the Thread class this is where most of
	 * the work happens.
	 */
	public void run() {
		while (true) {
			try {
				
				System.out.println("Waiting for client on port "+ serverSocket.getLocalPort() + "...");
				Socket server = serverSocket.accept();
				System.out.println("Just connected to " + server.getRemoteSocketAddress());
				DataInputStream in = new DataInputStream(server.getInputStream());

				/*int size = in.readInt();
				byte[] encryptedText = new byte[size];
				in.readFully(encryptedText);
				System.out.println(new String(encryptedText));
				for(int i = 0; i < encryptedText.length; i++){
					System.out.print(encryptedText[i]);
				}
				System.out.println();
				size = in.readInt();
				byte[] keyAsByte = new byte[size];
				in.readFully(keyAsByte);
				String firstFour = new String(keyAsByte);
				System.out.println("First four: "+firstFour);*/
				
				String encryptedTextString = in.readUTF();
				byte[] encryptedText = encryptedTextString.getBytes();
				String firstFour = in.readUTF();
				System.out.println("First four: "+firstFour);
				System.out.println(encryptedTextString);
				
				ArrayList<String> list = new ArrayList<String>();
				TextIO.writeUserSelectedFile();
				DESEncryption.generateKeys("",5, list);
				
				for (int i = 0; i < list.size(); i++) {
					DESEncryption.decrypt(firstFour+list.get(i), encryptedText);
				}
				

				server.close();
			} catch (SocketTimeoutException s) {
				System.out.println("Socket timed out!");
				break;
			} catch (IOException e) {
				e.printStackTrace();
				break;
			}
		}
	}

	public static void main(String[] args) {
		int port = Integer.parseInt(args[0]);
		try {
			Thread t = new Server(port);
			t.start();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}