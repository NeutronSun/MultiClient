import java.io.*;
import java.net.*;
import java.util.ArrayList;

public class Server {
        public static void main(String[] args) throws IOException {
        int portNumber = 77;
        System.out.println(Inet4Address.getLocalHost().getHostAddress());
        int nToGuess = (int)(Math.random() * 100) + 1;
        ServerSocket serverSocket = new ServerSocket(portNumber);
        int contThread = 0;

        ArrayList<Thread> threads = new ArrayList<Thread>();
        ArrayList<ServerThread> clients = new ArrayList<ServerThread>();
        //ArrayList<ServerLog> logs = new ArrayList<ServerLog>();
        ServerConsole console = new ServerConsole(clients);
        new Thread(console).start();
        System.out.println(nToGuess);
        System.out.println("Waiting for user..." );
        while (true) {
            Socket clientSocket = serverSocket.accept();
            clients.add(new ServerThread(clientSocket, nToGuess, clients));
            //logs.add(new ServerLog(clientSocket));
            //new Thread(logs.get(contThread)).start();
            threads.add(new Thread(clients.get(contThread)));
            threads.get(contThread).setName(String.valueOf(contThread));
            threads.get(contThread).start();
            System.out.println("Connection Accepted with client-" + threads.get(contThread).getName());
            contThread++;
        }
        
    }
}
