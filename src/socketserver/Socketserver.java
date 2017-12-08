package socketserver;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

public class Socketserver {
	public static final int PORT = 12345;// 监听的端口号

	public static void main(String[] args) {
		System.out.println("Starting Server...\n");
		Socketserver server = new Socketserver();
		server.init();
	}

	public void init() {
		try {
			ServerSocket serverSocket = new ServerSocket(PORT);
			while (true) {
				Socket client = serverSocket.accept();
				new HandlerThread(client);
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	private class HandlerThread implements Runnable {
		private Socket socket;

		public HandlerThread(Socket client) {
			socket = client;
			new Thread(this).start();
		}

		public void run() {
			try {
				// 读取客户端数据
				DataInputStream input = new DataInputStream(socket.getInputStream());
				String clientInputStr = input.readUTF();
				System.out.println("Message from client:" + clientInputStr);
				DataOutputStream out = new DataOutputStream(socket.getOutputStream());
				out.writeUTF("Server'" + clientInputStr + "'\n");
				String s = new BufferedReader(new InputStreamReader(System.in)).readLine();
				out.writeUTF(s);

				out.close();
				input.close();
			} catch (Exception e) {
				System.out.println(e.getMessage());
			} finally {
				if (socket != null) {
					try {
						socket.close();
					} catch (Exception e) {
						socket = null;
						System.out.println(e.getMessage());
					}
				}
			}
		}
	}
}