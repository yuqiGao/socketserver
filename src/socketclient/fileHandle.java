package socketclient;

import java.io.*;
import java.util.ArrayList;

/**
 * Created by liuxingyu on 17/12/8.
 */
public class fileHandle {

    public static String filePath = "/Users/liuxingyu/web/message.txt";

    public void appendToTXT(String file, String conent) {
        BufferedWriter out = null;
        try {
            out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file, true)));
            out.write(conent+"\r\n");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
