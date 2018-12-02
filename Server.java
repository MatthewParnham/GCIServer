import java.net.*;
import java.io.*;
import java.util.*;
import java.text.*;

public class Server {
  public static void main(String[] args) throws IOException {

    if (args.length != 1) {
        System.err.println("Usage: java Server <port number>");
        System.exit(1);
    }

    int portNumber = Integer.parseInt(args[0]);
    ServerSocket serverSocket = new ServerSocket(portNumber);


    while(true) {
      Socket clientSocket = null;
      try {
        clientSocket = serverSocket.accept();
        System.out.println("A client has connected.");

        BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
        new ClientHandler(clientSocket, in, out).start();


      } catch (IOException e) {
        System.out.println("Exception caught when trying to listen on port "
            + portNumber + " or listening for a connection");
        System.out.println(e.getMessage());
      }
    }
  }
}
