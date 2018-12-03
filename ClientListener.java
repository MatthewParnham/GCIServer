import java.lang.Thread;
import java.net.*;
import java.io.*;
import java.util.*;
import java.text.*;

public class ClientListener extends Thread {
    private Socket socket;
    private String clientName;
    private BufferedReader in;
    private Client client;

    public ClientListener(Socket clientSocket, String clientName, Client client) {
        this.socket = clientSocket;
        this.clientName = clientName;
        this.client = client;

        try {
          in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        } catch (IOException e) {
          System.out.println("Error getting input stream: " + e.getMessage());
            e.printStackTrace();
        }
    }

  @Override
  public void run() {
    String inputLine;
    while(true) {
      try {
        inputLine = in.readLine();
        System.out.println("\n" + inputLine);
      } catch (IOException e) {
      System.out.println("Exception caught when trying to listen on port.");
      System.out.println(e.getMessage());
      }
    }
  }
}
