import java.lang.Thread;
import java.net.*;
import java.io.*;
import java.util.*;
import java.text.*;

public class ClientHandler extends Thread {//handles all connected clients
    protected Socket socket;
    protected ObjectInputStream in;
    protected ObjectOutputStream out;
    protected HashMap<String, User> users;
    protected String userName;

    public ClientHandler(Socket clientSocket, String userName, ObjectInputStream in, ObjectOutputStream out, HashMap<String, User> users) {
        this.socket = clientSocket;
        this.in = in;
        this.out = out;
        this.users = users;
        this.userName = userName;
    }

    @Override
    public void run() {
      Message input, output;
      try {

        if(users.get(userName).getHistory().size() > 0) {//checks if any outstanding messages exist and sends them
          for(int i = 0; i <users.get(userName).getHistory().size();i++) {
            out.writeObject(users.get(userName).getHistory().remove(i));
          }
        }

        while ((input = (Message)in.readObject()) != null) {//reads messages as they come and sends them appropriately
          String receiver = input.getReciever();
          String sender = input.getSender();
          String message = input.getMessage();
          System.out.println("[" + userName + "]: " + message);

          if(message.equalsIgnoreCase("quit")) {
            break;
          }
          if(receiver.equalsIgnoreCase("server")) {
            continue;
          }

          if(users.containsKey(receiver)) {
            if(users.get(receiver).getSocket() != null) {
              ObjectOutputStream messageOut = new ObjectOutputStream(users.get(receiver).getSocket().getOutputStream());
              messageOut.writeObject(input);
            }
            else {
              users.get(receiver).getHistory().add(input);
            }
          }
          else {
            users.put(receiver,new User(receiver, null));
            users.get(receiver).getHistory().add(input);
          }


        }
        socket.close();
        users.get(userName).setSocket(null);
        System.out.println("Client has left.");
      } catch (IOException e) {
        System.out.println("Exception caught when trying to listen on port.");
        System.out.println(e.getMessage());
        users.remove(userName);
      } catch (ClassNotFoundException e) {
        System.out.println("Exception caught when reading object from socket.");
        System.out.println(e.getMessage());
      }




    }
}
