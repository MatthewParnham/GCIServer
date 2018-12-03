import java.io.*;
import java.net.*;
import java.util.*;

public class Client {
    public static void main(String[] args) throws IOException {

        if (args.length != 3) {
            System.err.println(
                "Usage: java Client <host name> <port number> <client name>");
            System.exit(1);
        }

        String hostName = args[0];
        int portNumber = Integer.parseInt(args[1]);
        String clientName = args[2];

        try {
          Socket socket = new Socket(hostName, portNumber);
          PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
          BufferedReader in = new BufferedReader(
              new InputStreamReader(socket.getInputStream()));

            BufferedReader stdIn =
                new BufferedReader(new InputStreamReader(System.in));
            String fromServer;
            String fromUser;
            ArrayList<String> incomingMessages = new ArrayList<String>();

            out.println(clientName);
            System.out.println(in.readLine());
            new ClientListener(socket, clientName, in, out, incomingMessages);

            while (true) {
              System.out.println("Options:\n1. Send Message\n2. Receive Messages\n3. Quit");
              fromUser = stdIn.readLine();
              if(fromUser.equals("1")) {
                System.out.print("Username: ");
                fromUser = stdIn.readLine();
                out.println(fromUser);
                System.out.print("Message: ");
                fromUser = stdIn.readLine();
                out.println(fromUser);
              }
              else if(fromUser.equals("2")) {
                Iterator itr = incomingMessages.iterator();
                while(itr.hasNext()) {
                  System.out.println(itr.next());
                }
              }
                /*if(fromUser.equals("quit")) {
                  socket.close();
                  break;
                }*/
              else {
                socket.close();
                break;
              }
            }
        } catch (UnknownHostException e) {
            System.err.println("Don't know about host " + hostName);
            System.exit(1);
        } catch (IOException e) {
            System.err.println("Couldn't get I/O for the connection to " +
                hostName);
            System.exit(1);
        }
    }
}
