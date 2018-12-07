import java.lang.Thread;
import java.net.*;
import java.io.*;
import java.util.*;
import java.text.*;

public class ClientWriter extends Thread {//thread that handles writing to server
  private ObjectOutputStream out;
  private Socket socket;
  private Client client;
  private Message message;

  public ClientWriter(Socket clientSocket, String message, String receiver, Client client) {
      this.socket = clientSocket;
      this.client = client;
      this.message = new Message(client.getClientName(), receiver, message);

      try {
        out = new ObjectOutputStream(socket.getOutputStream());
      } catch (IOException e) {
        System.out.println("Error getting output stream: " + e.getMessage());
        e.printStackTrace();
      }
  }

  @Override
  public void run() {

    try {
      
      out.writeObject(message);

    } catch (IOException e) {
      System.out.println("Error outputing: " + e.getMessage());
      e.printStackTrace();
    }
  }


}
