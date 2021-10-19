import java.io.*;
import java.net.*;

public class ServerLog implements Runnable {
    private Socket socket;
    private BufferedReader in;
    private PrintWriter out;

    public ServerLog(Socket s) throws IOException{
        socket = s;
        in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
    }

    public void run() {
        while (true) {
            String s;
            try {
                s = in.readLine();
                System.out.println(s);
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }
}