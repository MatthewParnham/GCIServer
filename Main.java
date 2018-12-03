import java.net.*;
import java.io.*;
import java.util.*;
import java.text.*;

public class Main {
  public static void main(String[] args) throws IOException {

    if (args.length != 2 && args.length != 4) {
        System.err.println("Usage: java Main server <port number>\nor\nUsage: java Main client <host name> <port number> <client name>");
        System.exit(1);
    }


    //Run Server
    if(args[0].equalsIgnoreCase("server")) {
      int portNumber = Integer.parseInt(args[1]);
      Server server = new Server(portNumber);
      server.start();
    }

    //Run Client
    else if(args[0].equalsIgnoreCase("client")) {
      String hostName = args[1];
      int portNumber = Integer.parseInt(args[2]);
      String clientName = args[3];
      Client client = new Client(hostName, portNumber, clientName);
      client.connect();
    }
    else {
      System.err.println("Usage: java Main server <port number>\nor\nUsage: java Main client <host name> <port number> <client name>");
      System.exit(1);
    }
  }
}
