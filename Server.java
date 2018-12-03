import java.net.*;
import java.io.*;
import java.util.*;
import java.text.*;

public class Server {
  private int portNumber;
  private ServerSocket serverSocket;
  private HashMap<String, User> users;

  public Server(int portNumber) throws IOException {
    this.portNumber = portNumber;
    this.serverSocket = new ServerSocket(portNumber);
    this.users = new HashMap<String, User>();
  }

  public void start() throws IOException {
    System.out.println("Server is now running..\nPress ctrl+C to stop.");
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
