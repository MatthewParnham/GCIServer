import java.lang.Thread;
import java.net.*;
import java.io.*;
import java.util.*;
import java.text.*;

public class ClientHandler extends Thread {
    protected Socket socket;
    protected BufferedReader in;
    protected PrintWriter out;
    protected HashMap<String, User> users;
    protected String userName;

    public ClientHandler(Socket clientSocket, String userName, BufferedReader in, PrintWriter out, HashMap<String, User> users) {
        this.socket = clientSocket;
        this.in = in;
        this.out = out;
        this.users = users;
        this.userName = userName;
    }

    @Override
    public void run() {
      String inputLine, outputLine;
      try {
        // Initiate conversation with client
        outputLine = "Connected to Server.";
        out.println(outputLine);

        //ObjectOutputStream os = new ObjectOutputStream(socket.getOutputStream());
        //ObjectInputStream is = new ObjectInputStream(socket.getInputStream());
        //inputLine = in.readLine()) != null
        while ((inputLine = in.readLine()) != null) {
          String user = inputLine;
          String message = in.readLine();
          if(users.containsKey(userName)) {
            PrintWriter specialOut = new PrintWriter(users.get(user).getSocket().getOutputStream());
            specialOut.println(userName + ": " + message);
          }
          else {
            out.println("User not found.");
          }
        //  System.out.println(userName + ": " + inputLine);
          /*if(inputLine.equals("quit")) {
            break;
          }*/
        }
        System.out.println("Client has left.");
        users.remove(userName);
      } catch (IOException e) {
        System.out.println("Exception caught when trying to listen on port.");
        System.out.println(e.getMessage());
      }



        /*InputStream inp = null;
        BufferedReader brinp = null;
        DataOutputStream out = null;
        try {
            inp = socket.getInputStream();
            brinp = new BufferedReader(new InputStreamReader(inp));
            out = new DataOutputStream(socket.getOutputStream());
        } catch (IOException e) {
            return;
        }
        String line;
        while (true) {
            try {
                line = brinp.readLine();
                if ((line == null) || line.equalsIgnoreCase("QUIT")) {
                    socket.close();
                    return;
                } else {
                    out.writeBytes(line + "\n\r");
                    out.flush();
                }
            } catch (IOException e) {
                e.printStackTrace();
                return;
            }
        }*/
    }
}
