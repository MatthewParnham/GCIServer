import java.lang.Thread;
import java.net.*;
import java.io.*;
import java.util.*;
import java.text.*;

public class ClientHandler extends Thread {
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

        while ((input = (Message)in.readObject()) != null) {
          String receiver = input.getReciever();
          String sender = input.getSender();
          String message = input.getMessage();
          System.out.println("[" + userName + "]: " + message);
          //String[] inputArgs = inputLine.split("\\s+");
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

          /*
          switch (inputArgs[0]) {
            case "tell":
              if(inputArgs.length > 1 && users.containsKey(inputArgs[1])) {
                PrintWriter specialOut = new PrintWriter(users.get(inputArgs[1]).getSocket().getOutputStream(), true);
                String message = "";
                for(int i = 2; i < inputArgs.length; i++) {
                  message += inputArgs[i] + " ";
                }
                specialOut.println("[" + userName + "]: " + message);
              }
              else {
                out.println("User not found.");
              }
              break;
            case "listUsers":
              for (String key : users.keySet()) {
                out.println(key);
              }
              break;
            default:
              out.println("Command not recognized.");
              break;
          }*/


        }
        users.remove(userName);
        socket.close();
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
