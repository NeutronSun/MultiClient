import java.io.*;
import java.net.*;

import java.io.IOException;

public class Client {
    public static void main(String[] args) throws Exception {
        String hostName = "127.0.0.1";
        int portNumber = 77;
        boolean win = false;

            Socket echoSocket = new Socket(hostName, portNumber);
            PrintWriter putInServer = new PrintWriter(echoSocket.getOutputStream(), true);
            ServerConnection serverC = new ServerConnection(echoSocket);
            new Thread(serverC).start();
            BufferedReader in = new BufferedReader(new InputStreamReader(System.in));

            String userInput;
            while (!win && (userInput = in.readLine()) != null) {
                putInServer.println(userInput);
            }
       
    }
}
