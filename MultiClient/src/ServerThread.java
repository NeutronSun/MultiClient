import java.io.*;
import java.net.*;

public class ServerThread implements Runnable {
    private Socket socket;
    private int nToGuess;
    private static int winner;
    private static boolean won = false;

    ServerThread(Socket socket, int n){
        this.socket = socket;
        nToGuess = n;   
    }

    public void run() {
        PrintWriter out = null;
        BufferedReader in = null;
        try{

            out = new PrintWriter(socket.getOutputStream(), true);
        	in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            String line;
            synchronized(this){
            while ((line = in.readLine()) != null) {
                if(Integer.parseInt(line) == nToGuess){
                    winner = Integer.parseInt(Thread.currentThread().getName());
                    won = true;
                    notifyAll();
                }
                if(won){
                    if(winner == Integer.parseInt(Thread.currentThread().getName()))
                    out.println("Hai vinto brutto gay");
                    else
                    out.println("Ha vinto " + winner + " brutto gay");
                }
                out.println("hai sbagliato");
                System.out.println(line);
            }
        }


        }catch(IOException e){}
    }
}
