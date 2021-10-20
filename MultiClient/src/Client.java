import java.io.*;
import java.net.*;

import java.io.IOException;

public class Client {
    public static void main(String[] args){
        //String hostName = Inet4Address.getLocalHost().getHostAddress();
        int portNumber = 77;
        boolean win = false;
        try{
            String hostName = Inet4Address.getLocalHost().getHostAddress();
            Socket echoSocket = new Socket(hostName, portNumber);
            PrintWriter putInServer = new PrintWriter(echoSocket.getOutputStream(), true);
            ServerConnection serverC = new ServerConnection(echoSocket, win);
            Thread t = new Thread(serverC);
            t.start();
            BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
            System.out.println("Inser your username.");
            putInServer.println(in.readLine());
            String userInput;
            while (!win && (userInput = in.readLine()) != null) {
                putInServer.println(userInput);
            }
            putInServer.close();
            in.close();
        } catch (IOException e) {
            System.out.println("you got banned");
            return;
        }
       
    }
}
