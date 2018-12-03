import java.lang.Thread;
import java.net.*;
import java.io.*;
import java.util.*;
import java.text.*;

public class ClientWriter extends Thread {
  private PrintWriter out;
  private Socket socket;
  private String clientName;
  private Client client;

  public ClientWriter(Socket clientSocket, String clientName, Client client) {
      this.socket = clientSocket;
      this.clientName = clientName;
      this.client = client;

      try {
        out = new PrintWriter(socket.getOutputStream(), true);
      } catch (IOException e) {
        System.out.println("Error getting output stream: " + e.getMessage());
        e.printStackTrace();
      }
  }

  @Override
  public void run() {
    String outputLine;
    Console console = System.console();
    try {
      out.println(clientName);

      do {
        outputLine = console.readLine();
        out.println(outputLine);
      } while (!outputLine.equals("quit"));
      socket.close();
    } catch (IOException e) {
      System.out.println("Error outputing: " + e.getMessage());
      e.printStackTrace();
    }
  }


}
