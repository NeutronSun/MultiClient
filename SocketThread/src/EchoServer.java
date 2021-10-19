import java.net.*;
import java.io.*;
 
public class EchoServer {
    public static void main(String[] args) throws IOException {
         
        /*if (args.length != 1) {
            System.err.println("Usage: java EchoServer <port number>");
            System.exit(1);
        }*/
         
        int portNumber = 65535;
        //Integer randomNumber = (int)(Math.random()*10)+1;
        int contThreads = 1;
        while(true){
            try (
                ServerSocket serverSocket = new ServerSocket(portNumber);
                //PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);                   
                //BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            ) {
                Socket s = serverSocket.accept();
                ServerThread st= new ServerThread(s, contThreads);
                st.start();
                contThreads++;
                
            } catch (IOException e) {
                System.out.println("Exception caught when trying to listen on port " + portNumber + " or listening for a connection");
                System.out.println(e.getMessage());
            }
        }
    }
}