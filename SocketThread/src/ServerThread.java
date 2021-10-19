import java.net.*;
import java.io.*;

public class ServerThread extends Thread{
    String line = null;
    Socket s = null;
    int cont;

    public ServerThread(Socket s, int c){
        this.s=s;
        this.cont = c;
    }

    public void run(){
        try(
            PrintWriter out = new PrintWriter(s.getOutputStream(), true);                   
            BufferedReader in = new BufferedReader(new InputStreamReader(s.getInputStream()));
        ) {
            while((line=in.readLine()).compareTo("QUIT")!=0){
                line = line.toUpperCase();
                out.println(line);
                System.out.println("Response to Client " + cont + " :  "+line);
            }
        } catch(IOException e){

        }
    }
}
