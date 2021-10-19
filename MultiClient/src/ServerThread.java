import java.io.*;
import java.net.*;
import java.util.ArrayList;

public class ServerThread implements Runnable {
    private Socket socket;
    private int nToGuess;
    public PrintWriter out;
    private BufferedReader in;
    private ArrayList<ServerThread> clients;
    private static int winner;
    private static boolean won = false;

    ServerThread(Socket socket, int n, ArrayList<ServerThread> clients){
        this.socket = socket;
        nToGuess = n;
        this.clients = clients;   
    }

    public void run() {
        try{

            out = new PrintWriter(socket.getOutputStream(), true);
        	in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            String line;
            synchronized(this){
            while ((line = in.readLine()) != null) {
                if(Integer.parseInt(line) == nToGuess){
                    winner = Integer.parseInt(Thread.currentThread().getName());
                    won = true;
                    alertAll(Integer.parseInt(Thread.currentThread().getName()));
                    out.println("hai vinto brutto frocio");
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
                t.out.println(exepted + " ha vinto");
            cont++;
        }
    }
}
