package socketserver;

import java.io.*;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class Socketserver {
	public static final int PORT = 12345;
	public static final String WEB_ROOT = System.getProperty("user.dir") + File.separator + "webroot";
	private static final String SHUTDOWN_COMMAND = "/SHUTDOWN";
	// the shutdown command received
	private boolean shutdown = false;

	public static void main(String[] args) {
		System.out.println("Starting Server...\n");
		Socketserver server = new Socketserver();
		server.init();
	}

	public void init() {
		try {
			// ServerSocket serverSocket = new ServerSocket(PORT);
			ServerSocket serverSocket = new ServerSocket(PORT, 1, InetAddress.getByName("127.0.0.1"));
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
				// DataInputStream input = new
				// DataInputStream(socket.getInputStream());
				// String clientInputStr = input.readUTF();
				// System.out.println("Message from client:" + clientInputStr);
				// DataOutputStream out = new
				// DataOutputStream(socket.getOutputStream());
				// out.writeUTF("Server:" + clientInputStr + "\n");
				// String s = new BufferedReader(new
				// InputStreamReader(System.in)).readLine();
				// out.writeUTF(s);
				//
				// out.close();
				// input.close();
				InputStream input = socket.getInputStream();
				OutputStream output = socket.getOutputStream();

				// create Request object and parse
				Request request = new Request(input);
				request.parse();

				// create Response object
				Response response = new Response(output);
				response.setRequest(request);
//				response.sendStaticResource();
				response.downLoadResource(socket);


				// Close the socket
				socket.close();
				// check if the previous URI is a shutdown command
				shutdown = request.getUri().equals(SHUTDOWN_COMMAND);

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




		private void closeSocket(Socket socket) {
			try {
				socket.close();
			} catch (IOException ex) {
				ex.printStackTrace();
			}
			System.out.println(socket + "离开了HTTP服务器");
		}

		private void transferFileHandle(String path, Socket client) {

			File fileToSend = new File(path);

			if (fileToSend.exists() && !fileToSend.isDirectory()) {
				try {
					PrintStream writer = new PrintStream(client.getOutputStream());
					writer.println("HTTP/1.0 200 OK");// 返回应答消息,并结束应答
					writer.println("Content-Type:application/binary");
					writer.println("Content-Length:" + fileToSend.length());// 返回内容字节数
					writer.println();// 根据 HTTP 协议, 空行将结束头信息

					FileInputStream fis = new FileInputStream(fileToSend);
					byte[] buf = new byte[fis.available()];
					fis.read(buf);
					writer.write(buf);
					writer.close();
					fis.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
}