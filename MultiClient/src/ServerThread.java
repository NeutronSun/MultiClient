import java.io.*;
import java.net.*;
import java.util.ArrayList;

public class ServerThread implements Runnable {
    private Socket socket;
    public PrintWriter out;
    private BufferedReader in;
    private ArrayList<ServerThread> clients;

    private int nToGuess;
    private int attempts;
    private String userName;

    ServerThread(Socket socket, int n, ArrayList<ServerThread> clients){
        this.socket = socket;
        nToGuess = n;
        this.clients = clients;
        attempts = 5;
    }

    public void run() {
        try{
            out = new PrintWriter(socket.getOutputStream(), true);
        	in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            userName = in.readLine();
            newUser(Integer.parseInt(Thread.currentThread().getName()));
            String line;
            synchronized(this){
            while ((line = in.readLine()) != null) {
                if(Integer.parseInt(line) == nToGuess){
                    alertAll(Integer.parseInt(Thread.currentThread().getName()));
                    out.println("You win!!!");
                    System.out.println(userName + ":has won the game.");
                }
                else{
                    attempts--;
                    out.println(attempts + " attempts left.");
                    System.out.println(userName + ": has " + attempts + " attempts left.");
                }
                if(attempts == 0){
                    out.println(attempts + " attempts left. You lost the game.");
                    System.out.println(userName + ": has " + attempts + " attempts left.");
                }
                System.out.println(line);
            }
        }


        }catch(IOException e){}
    }

    public void alertAll(int exepted){
        System.out.println(clients.size());
        int cont = 0;
        for(ServerThread t : clients){
            if(cont != exepted)
                t.out.println(userName + " ha vinto");
            cont++;
        }
    }

    public void newUser(int exepted){
        int cont = 0;
        for(ServerThread t : clients) {
            if(cont != exepted)
                t.out.println(userName + " has joined the game");
            cont++;
        }
    }
}
