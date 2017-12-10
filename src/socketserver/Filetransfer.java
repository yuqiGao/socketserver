//package socketserver;
//
//import java.io.BufferedReader;
//import java.io.InputStreamReader;
//import java.io.PrintStream;
//import java.net.Socket;
//import java.net.URLDecoder;
//import java.util.StringTokenizer;
//
///**
// * Created by liuxingyu on 17/12/9.
// */
//public class Filetransfer {
//    public void fileTransfer(){
//        try {
//            Socket client = null;
//            client = server.accept();
//            if (client != null) {
//                try {
//                    System.out.println("连接服务器成功！！...");
//
//                    BufferedReader reader = new BufferedReader(
//                            new InputStreamReader(client.getInputStream()));
//
//                    // GET /test.jpg /HTTP1.1
//                    String line = reader.readLine();
//
//                    System.out.println("line: " + line);
//
//                    String resource = line.substring(line.indexOf('/'),
//                            line.lastIndexOf('/') - 5);
//
//                    System.out.println("the resource you request is: "
//                            + resource);
//
//                    resource = URLDecoder.decode(resource, "UTF-8");
//
//                    String method = new StringTokenizer(line).nextElement()
//                            .toString();
//
//                    System.out.println("the request method you send is: "
//                            + method);
//
//                    while ((line = reader.readLine()) != null) {
//                        if (line.equals("")) {
//                            break;
//                        }
//                        System.out.println("the Http Header is : " + line);
//                    }
//
//                    if ("post".equals(method.toLowerCase())) {
//                        System.out.println("the post request body is: "
//                                + reader.readLine());
//                    }
//
//                    if (resource.endsWith(".mkv")) {
//
//                        transferFileHandle("videos/test.mkv", client);
//                        closeSocket(client);
//                        continue;
//
//                    } else if (resource.endsWith(".jpg")) {
//
//                        transferFileHandle("images/test.jpg", client);
//                        closeSocket(client);
//                        continue;
//
//                    } else if (resource.endsWith(".rmvb")) {
//
//                        transferFileHandle("videos/test.rmvb", client);
//                        closeSocket(client);
//                        continue;
//
//                    } else {
//                        PrintStream writer = new PrintStream(
//                                client.getOutputStream(), true);
//                        writer.println("HTTP/1.0 404 Not found");// 返回应答消息,并结束应答
//                        writer.println();// 根据 HTTP 协议, 空行将结束头信息
//                        writer.close();
//                        closeSocket(client);
//                        continue;
//                    }
//                } catch (Exception e) {
//                    System.out.println("HTTP服务器错误:"
//                            + e.getLocalizedMessage());
//                }
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//}
