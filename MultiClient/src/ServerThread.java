import java.io.*;
import java.net.*;
import java.util.ArrayList;

public class ServerThread implements Runnable {
    public Socket socket;
    public PrintWriter out;
    private BufferedReader in;
    private ArrayList<ServerThread> clients;

    private String word;
    private String description;
    private int attempts;
    public String userName;

    ServerThread(Socket socket, String s, String ss, ArrayList<ServerThread> clients){
        this.socket = socket;
        word = s;
        description = ss;
        this.clients = clients;
        attempts = 10;
    }

    public void run() {
        try{
            out = new PrintWriter(socket.getOutputStream(), true);
        	in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            userName = in.readLine();
            System.out.println("client-" + Thread.currentThread().getName() + " has set his username in " + userName);
            out.println("welcome " + userName + ", good luck and having fun.\nYou have " + attempts + " attempts.");
            out.println("Use the command /t to chat with the other player");
            out.println("The Word has " + word.length() + " characters, and is " + description);
            newUser(Integer.parseInt(Thread.currentThread().getName()));
            String line;
            synchronized(this){
            while ((line = in.readLine()) != null) {
                if(line.startsWith("/t", 0)){
                    publicChat(Integer.parseInt(Thread.currentThread().getName()), line.substring(3));
                    continue;
                }
                else if(line.startsWith("/report", 0)){
                    reportUser(line);
                }
                else if(line.toUpperCase().equals(word)){
                    String msg = (userName + " has won the game: The words is " + word);
                    alertAll(Integer.parseInt(Thread.currentThread().getName()), msg);
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


        }catch(IOException e){
            System.out.println(userName + " has left the game");
            clients.remove(Integer.parseInt(Thread.currentThread().getName()));
            String msg = (userName + " has left the game\nThere are " + clients.size() + "player left.");
            alertAll((clients.size()+1), msg);

        }
    }

    public void alertAll(int exepted, String msg){
        System.out.println(clients.size());
        int cont = 0;
        for(ServerThread t : clients){
            if(cont != exepted)
                t.out.println(msg);
            cont++;
        }
    }

    public void publicChat(int exepted, String msg){
        int cont = 0;
        for(ServerThread t : clients){
            if(cont != exepted)
                t.out.println("<<" + userName + ">>: " + msg);
            cont++;
        }
    }

    public void newUser(int exepted){
        int cont = 0;
        for(ServerThread t : clients) {
            if(cont != exepted){
                t.out.println("<<Server>>: " + userName + " has joined the game");
                t.out.println("<<Server>>: there are " + clients.size() + " players.");
            }
            cont++;
        }
    }

    public void reportUser(String line) throws IOException{
        line = line.substring(8);
        int cont = 0;
        for(ServerThread t : clients){
            if(t.userName.equalsIgnoreCase(line)){
                t.out.println("<<Server>>: " + userName + " has reported you for toxic behavoir, you got banned.");
                t.socket.close();
                clients.remove(cont);
                String msg = ("<<Server>>: " + userName + " has reported you " + line + " for toxic behavoir, you got banned.");
                publicChat((clients.size()+1), msg);
                return;
            }
            cont++;
        }

    }
}
