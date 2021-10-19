import java.io.*;
import java.net.*;

import java.io.IOException;

public class Client {
    public static void main(String[] args) throws Exception {
        String hostName = Inet4Address.getLocalHost().getHostAddress();
        int portNumber = 77;
        boolean win = false;

            Socket echoSocket = new Socket(hostName, portNumber);
            PrintWriter putInServer = new PrintWriter(echoSocket.getOutputStream(), true);
            ServerConnection serverC = new ServerConnection(echoSocket);
            new Thread(serverC).start();
            BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
            System.out.println("Inser your username.");
            putInServer.println(in.readLine());
            String userInput;
            while (!win && (userInput = in.readLine()) != null) {
                putInServer.println(userInput);
            }
       
    }
}
