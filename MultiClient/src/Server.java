import java.io.*;
import java.net.*;
import java.util.ArrayList;

public class Server {
        public static void main(String[] args) throws IOException {
        int portNumber = 77;
        int nToGuess = (int)(Math.random() * 100) + 1;
        ServerSocket serverSocket = new ServerSocket(portNumber);
        int contThread = 0;

        String userInput;
        ArrayList<Thread> threads = new ArrayList<Thread>();
        System.out.println(nToGuess);
        while (true) {
            Socket clientSocket = serverSocket.accept();
            threads.add(new Thread(new ServerThread(clientSocket, nToGuess)));
            threads.get(contThread).setName(String.valueOf(contThread));
            threads.get(contThread).start();
            System.out.println("Connection Accepted with cliet: " + threads.get(contThread).getName());
            contThread++;
        }
        
    }
}
