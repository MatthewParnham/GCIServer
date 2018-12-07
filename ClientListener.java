import java.lang.Thread;
import java.net.*;
import java.io.*;
import java.util.*;
import java.text.*;

public class ClientListener extends Thread {//handles all listening on the client end
    private Socket socket;
    private String clientName;
    private ObjectInputStream in;
    private Client client;

    public ClientListener(Socket clientSocket, String clientName, Client client) {
        this.socket = clientSocket;
        this.clientName = clientName;
        this.client = client;

        try {
          in = new ObjectInputStream(socket.getInputStream());
        } catch (IOException e) {
          System.out.println("Error getting input stream: " + e.getMessage());
            e.printStackTrace();
        }
    }

  @Override
  public void run() {
    while(true) {
      try {
        Message inputMessage = (Message) in.readObject();
        String message = inputMessage.getMessage();
        String sender = inputMessage.getSender();
        System.out.println("\n" + "[" + sender + "]: " + message);
      } catch (IOException e) {
      System.out.println("Exception caught when trying to listen on port.");
      System.out.println(e.getMessage());
      break;
      } catch (ClassNotFoundException e) {
        System.out.println("Exception caught when reading object from socket.");
        System.out.println(e.getMessage());
      }
    }
  }
}
