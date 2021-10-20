import java.io.*;
import java.net.*;
import java.util.ArrayList;

public class ServerConsole implements Runnable {
    private ArrayList<ServerThread> clients;
    BufferedReader inKey;

    public ServerConsole(ArrayList<ServerThread> s) throws IOException{
        clients = s;
        inKey = new BufferedReader(new InputStreamReader(System.in));
    }

    public void run() {
        while (true) {
            String s;
            try {
                s = inKey.readLine();
                if(s.startsWith("/ban")){
                    s = getNameFromLine(s);
                    banUser(s);
                }
            } catch (IOException e) {
                System.out.println("error");
                e.printStackTrace();
            }
        }
    }

    public String getNameFromLine(String s){
        s = s.substring(5, s.length());
        /*
        for(int i = 0; i < s.length(); i++) {
            if(s.charAt(i) == '/')
            return s.substring(i);
        }
        */
        return s;
        //return "null";
    }

    public void banUser(String s) throws IOException{
        for(ServerThread t : clients){
            if(t.userName.equals(s)){
                t.out.println("you got banned.");
                t.socket.close();
                return;
            }
        }
    }
    
}