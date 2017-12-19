package socketserver;

import java.io.*;
import java.net.Socket;
import java.net.URLDecoder;

import static java.lang.Thread.sleep;

public class Response {
	private static final int BUFFER_SIZE = 1024;
	private static int status = 1;
	Request request;
	OutputStream output;

	public Response(OutputStream output) {
		this.output = output;
	}

	public void setRequest(Request request) {
		this.request = request;
	}

	public void sendStaticResource() throws IOException {
		byte[] bytes = new byte[BUFFER_SIZE];
		FileInputStream fis = null;
		try {
			File file = new File(Socketserver.WEB_ROOT, request.getUri());
			System.out.println("webroot is : " + Socketserver.WEB_ROOT);
			if (file.exists()) {
				System.out.println("filePath is :" + file.getAbsolutePath());
				fis = new FileInputStream(file);
				int ch = fis.read(bytes, 0, BUFFER_SIZE);
				while (ch != -1) {
					output.write(bytes, 0, ch);
					ch = fis.read(bytes, 0, BUFFER_SIZE);
				}
			} else {
				// file not found
				String errorMessage = "HTTP/1.1 404 File Not Found\r\n" + "Content-Type: text/html\r\n"
						+ "Content-Length: 23\r\n" + "\r\n" +

						"<h1>File Not Found</h1>";
				output.write(errorMessage.getBytes());
			}
		} catch (Exception e) {
			// thrown if cannot instantiate a File object
			System.out.println(e.toString());
		} finally {
			if (fis != null)
				fis.close();
		}
	}

	public void downLoadResource(Socket client) throws IOException{
		this.downLoadResource(client,status);
	}

	private void downLoadResource(Socket client,int status2) throws IOException {
		try {
			String resource = request.getUri();
			resource = URLDecoder.decode(resource, "UTF-8");
			System.out.println(resource);
			
			if (resource.endsWith(".txt")) {
				if(status == 1){
					String errorMessage = "HTTP/1.1 404 File Not Found\r\n" + "Content-Type: text/html\r\n"
							+ "Content-Length: 23\r\n" + "\r\n" +

							"<h1>file is downloading2222 !!!</h1>";
					output.write(errorMessage.getBytes());
					System.out.println("hahah 我可以运行到这里");
					status++;
				}else{
					transferFileHandle("webroot/haha.txt", client);
					System.out.println("我还是可以允许到这里");
				}
//				String errorMessage = "HTTP/1.1 404 File Not Found\r\n" + "Content-Type: text/html\r\n"
//						+ "Content-Length: 23\r\n" + "\r\n" +
//
//						"<h1>load server success !!!</h1>";
//				output.write(errorMessage.getBytes());
//				sleep(2*1000);



			}  else if (resource.endsWith(".rmvb")) {
				transferFileHandle("videos/test.rmvb", client);
			} else if (resource.endsWith("index.html")){
				transferFileHandleForHTML("webroot/HTML/index.html", client);
				System.out.println("我还是可以允许到这里wwww");
			} else if(resource.endsWith(".css")){
				transferFileHandleForHTML("webroot/HTML"+resource, client);
			} else if(resource.endsWith(".js")){
				transferFileHandleForHTML("webroot/HTML"+resource, client);
			} else if(resource.endsWith(".jpg")){
				transferFileHandleForHTML("webroot/HTML"+resource, client);
			} else if(resource.endsWith(".json")){
				transferFileHandleForHTML("webroot/HTML"+resource, client);
			}else if(resource.endsWith(".yml")){
				transferFileHandleForHTML("webroot/HTML"+resource, client);
			}else if(resource.endsWith(".map")){
				transferFileHandleForHTML("webroot/HTML"+resource, client);
			}else{
				transferFileHandleForHTML("webroot/HTML"+resource, client);
			}
			
//			else {
//				String errorMessage = "HTTP/1.1 404 File Not Found\r\n" + "Content-Type: text/html\r\n"
//						+ "Content-Length: 23\r\n" + "\r\n" +
//
//						"<h1>File Not Found</h1>";
//				output.write(errorMessage.getBytes());
//			}
		}catch (Exception e) {
			// thrown if cannot instantiate a File object
			System.out.println(e.toString());
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
		System.out.println("path is ~~~~~~" + fileToSend.getAbsolutePath());

		if (fileToSend.exists() && !fileToSend.isDirectory()) {
			try {
				PrintStream writer = new PrintStream(client.getOutputStream());
				writer.println("HTTP/1.0 200 OK");// 返回应答消息,并结束应答
				writer.println("Content-Type:application/binary");
				writer.println("Content-Length:" + fileToSend.length());// 返回内容字节数
				writer.println();// 根据 HTTP 协议, 空行将结束头信息
				String message = "<h1>file is downloading !!!</h1>";
				writer.write(message.getBytes());

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

	private void transferFileHandleForHTML(String path, Socket client) {

		File fileToSend = new File(path);
		System.out.println(path);
		System.out.println("path is ~~~~~~" + fileToSend.getAbsolutePath());

		if (fileToSend.exists() && !fileToSend.isDirectory()) {
			try {
				PrintStream writer = new PrintStream(client.getOutputStream());
				writer.println("HTTP/1.0 200 OK");// 返回应答消息,并结束应答
				writer.println("Content-Type:text/html");
				writer.println("Content-Length:" + fileToSend.length());// 返回内容字节数
				writer.println();// 根据 HTTP 协议, 空行将结束头信息
				FileInputStream fis = new FileInputStream(fileToSend);
				byte[] buf = new byte[fis.available()];
				int ch = fis.read(buf, 0, BUFFER_SIZE);
				while (ch != -1) {
					writer.write(buf, 0, ch);
					ch = fis.read(buf, 0, BUFFER_SIZE);
				}
				
				fis.read();
				writer.write(buf);
				writer.close();
				fis.close();
				
			} catch (IOException e) {
				
				e.printStackTrace();
			}
		}
	}



}
