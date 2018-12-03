import java.lang.Thread;
import java.net.*;
import java.io.*;
import java.util.*;
import java.text.*;

public class ClientListener extends Thread {
    protected Socket socket;
    protected BufferedReader in;
    protected PrintWriter out;
    protected String userName;
    protected ArrayList<String> incomingMessages;

    public ClientListener(Socket clientSocket, String userName, BufferedReader in, PrintWriter out, ArrayList<String> incomingMessages) {
        this.socket = clientSocket;
        this.in = in;
        this.out = out;
        this.userName = userName;
        this.incomingMessages = incomingMessages;
    }

    @Override
    public void run() {
      String inputLine, outputLine;
      try {

        while ((inputLine = in.readLine()) != null) {
          incomingMessages.add(inputLine);
        }
      } catch (IOException e) {
        System.out.println("Exception caught when trying to listen on port.");
        System.out.println(e.getMessage());
      }
    }
}
