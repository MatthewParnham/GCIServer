import java.io.*;
import java.net.*;
import java.util.*;

public class Client { //main client class

  private String hostName;
  private int portNumber;
  private String clientName;
  private Socket socket;

  public Client(String hostName, int portNumber, String clientName) {
    this.hostName = hostName;
    this.portNumber = portNumber;
    this.clientName = clientName;
  }

  public void sendMessage(String m, String receiver) {
    new ClientWriter(socket, m, receiver, this).start();
  }

  public String getClientName() {
    return this.clientName;
  }

    public void connect() throws IOException {

        try {
          this.socket = new Socket(hostName, portNumber);

          System.out.println("Connected to the server.");

          new ClientListener(socket, clientName, this).start();
          //new ClientWriter(socket, clientName, this).start();

        } catch (UnknownHostException e) {
            System.err.println("Don't know about host " + hostName);
            System.exit(1);
        } catch (IOException e) {
            System.err.println("Couldn't get I/O for the connection to " +
                hostName);
            System.exit(1);
        }
        sendMessage(clientName,clientName);
        while(true) {
        String outputLine;
        Console console = System.console();
        outputLine = console.readLine();
        sendMessage(outputLine,"receiver");
      }
    }
}
