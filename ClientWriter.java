import java.lang.Thread;
import java.net.*;
import java.io.*;
import java.util.*;
import java.text.*;

public class ClientWriter extends Thread {
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
      out.writeObject(new Message("NULL",client.getClientName(),"NULL"));
      out.writeObject(message);

      /*do {
        outputLine = console.readLine();
        String[] splitLine = outputLine.split("\\s+");
        if(splitLine[0].equals("tell")) {
          String message = "";
          for(int i = 2; i < splitLine.length; i++) {
            message += splitLine[i] + " ";
          }
          out.writeObject(new Message(splitLine[1],clientName,message));
        }
        else if(splitLine[0].equals("quit")) {
          out.writeObject(new Message("Server",clientName,"quit"));
        }
        else {
          out.writeObject(new Message("Server",clientName,outputLine));
        }

      } while (!outputLine.equals("quit"));*/
      socket.close();
    } catch (IOException e) {
      System.out.println("Error outputing: " + e.getMessage());
      e.printStackTrace();
    }
  }


}
