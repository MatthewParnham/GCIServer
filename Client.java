import java.io.*;
import java.net.*;

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

        try (
            Socket kkSocket = new Socket(hostName, portNumber);
            PrintWriter out = new PrintWriter(kkSocket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(
                new InputStreamReader(kkSocket.getInputStream()));
        ) {
            BufferedReader stdIn =
                new BufferedReader(new InputStreamReader(System.in));
            String fromServer;
            String fromUser;

            out.println(clientName + " has connected.");

            while (true) {
              fromServer = in.readLine();
              if(fromServer != null) {
                System.out.println(fromServer);
              }
                fromUser = stdIn.readLine();
                if (fromUser != null) {
                    out.println(fromUser);
                }
                if(fromUser.equals("quit")) {
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
