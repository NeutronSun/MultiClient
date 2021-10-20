import java.io.*;
import java.net.*;

public class ServerConnection implements Runnable {
    private Socket socket;
    private BufferedReader in;
    private boolean win;
    public ServerConnection(Socket s, boolean w) throws IOException{
        socket = s;
        in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        win = w;
    }
    public void run() {
        while (true) {
            String s;
            try {
                s = in.readLine();
                System.out.println(s);
                if(s.equals("you got banned.")){
                    socket.close();
                    in.close();
                    win = true;
                }
            } catch (IOException e) {
                System.out.println("wegweg");
                return;
            }
        }
    }
    
}
