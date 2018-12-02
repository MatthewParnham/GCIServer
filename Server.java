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
    HashMap<String, User> users = new HashMap<String, User>();


    while(true) {
      Socket clientSocket = null;
      try {
        clientSocket = serverSocket.accept();
        System.out.println("A client has connected.");

        BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
        //read  in username and create user object in map with info
        String userName = in.readLine();
        if(users.containsKey(userName)) {
          out.println("'" + userName + "' is already taken. Refusing connection.");
        }
        else {
          users.put(userName,new User(userName,clientSocket.getInetAddress(), clientSocket));
          new ClientHandler(clientSocket, userName, in, out, users).start();
        }

      } catch (IOException e) {
        System.out.println("Exception caught when trying to listen on port "
            + portNumber + " or listening for a connection");
        System.out.println(e.getMessage());
      }
    }
  }
}
