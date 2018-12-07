import java.net.*;
import java.io.*;
import java.util.*;
import java.text.*;

public class Server {//main server class
  private int portNumber;
  private ServerSocket serverSocket;
  private HashMap<String, User> users;

  public Server(int portNumber) throws IOException {
    this.portNumber = portNumber;
    this.serverSocket = new ServerSocket(portNumber);//server is started
    this.users = new HashMap<String, User>();
  }

  public void start() throws IOException {
    System.out.println("Server is now running..\nPress ctrl+C to stop.");
    while(true) {
      Socket clientSocket = null;
      try {
        clientSocket = serverSocket.accept();
        System.out.println("A client has connected.");

        ObjectInputStream in = new ObjectInputStream(clientSocket.getInputStream());
        ObjectOutputStream out = new ObjectOutputStream(clientSocket.getOutputStream());
        //read  in username and create user object in map with info
        Message initialMessage = (Message) in.readObject();
        String userName = initialMessage.getSender();
        if(users.containsKey(userName)) {
          out.writeObject(new Message(userName,"server","'" + userName + "' is already taken. Refusing connection."));
        }
        else {
          users.put(userName,new User(userName, clientSocket));//client handling thread created
          new ClientHandler(clientSocket, userName, in, out, users).start();
        }

      } catch (IOException e) {
        System.out.println("Exception caught when trying to listen on port "
            + portNumber + " or listening for a connection");
        System.out.println(e.getMessage());
      } catch (ClassNotFoundException e) {
        System.out.println("Exception caught when reading object from socket.");
        System.out.println(e.getMessage());
      }
    }
  }
}
