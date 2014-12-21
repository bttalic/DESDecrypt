import java.net.*;
import java.util.Scanner;
import java.io.*;
/**
 * Simple client application which conect to a server app and asks the server to
 * add a two numbers
 * @author Benjamin
 *
 */
public class Client {
	public static void main(String[] args) {
		String serverName;
		int port;
		int unknown;
		if (args.length < 3) {
			serverName = "127.0.0.1";
			port = 6066;
			unknown = 4;
		} else {
			serverName = args[0];
			port = Integer.parseInt(args[1]);
			unknown = Integer.parseInt(args[2]);
		}
		try {
			System.out.println("Enter key:");
			String key = TextIO.getln();
			System.out.println("Enter message: ");
			String message = TextIO.getln();
			System.out.println("key: "+key+" message: "+message);

			byte[] textEncrypted = DESEncryption.encrypt(key, message);
			System.out.println(new String(textEncrypted));
			for(int i = 0; i < textEncrypted.length; i++){
				System.out.print(textEncrypted[i]);
			}
			System.out.println();
			
			System.out.println("Connecting to " + serverName + " on port " + port);
			InetAddress adress = InetAddress.getByName(serverName);
			Socket client = new Socket(adress, port);
			System.out.println("Just connected to " + client.getRemoteSocketAddress());
			
			OutputStream outToServer = client.getOutputStream();
			DataOutputStream out = new DataOutputStream(outToServer);
			
			/*out.writeInt(textEncrypted.length);
			out.writeBytes(new String(textEncrypted));
			out.writeInt(key.substring(0, 4).getBytes().length);
			out.writeBytes(key.substring(0, 4));*/
			out.writeUTF(new String(textEncrypted));
			System.out.println(new String(textEncrypted));
			out.writeUTF(key.substring(0, 8-unknown));

			client.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}