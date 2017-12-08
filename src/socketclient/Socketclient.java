package socketclient;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class Socketclient {
	public static final String IP_ADDR = "localhost";
	public static final int PORT = 12345;

	public static void main(String[] args) {
		System.out.println("Starting...");
		// System.out.println("当接收到服务器端字符为 \"Stop Server\" 的时候, 客户端将终止\n");
		while (true) {
			Socket socket = null;
			try {
				socket = new Socket(IP_ADDR, PORT);

				DataInputStream input = new DataInputStream(socket.getInputStream());
				DataOutputStream out = new DataOutputStream(socket.getOutputStream());
				System.out.print("Please Input Message: \t");
				String str = new BufferedReader(new InputStreamReader(System.in)).readLine();
				out.writeUTF(str);

				String ret = input.readUTF();
				System.out.println("Return message from server: " + ret);
				// 如接收到 "OK" 则断开连接
				if ("Server:Stop Server\n".equals(ret)) {
					System.out.println("Closing");
					Thread.sleep(50);
					break;
				}

				out.close();
				input.close();
			} catch (Exception e) {
				System.out.println(e.getMessage());
			} finally {
				if (socket != null) {
					try {
						socket.close();
					} catch (IOException e) {
						socket = null;
						System.out.println(e.getMessage());
					}
				}
			}
		}
	}
}