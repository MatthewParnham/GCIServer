import java.net.*;
import java.io.*;
import java.util.*;

public class Server {
    public static void main(String[] args) throws IOException {

        if (args.length != 1) {
            System.err.println("Usage: java Server <port number>");
            System.exit(1);
        }

        int portNumber = Integer.parseInt(args[0]);

        try (
            ServerSocket serverSocket = new ServerSocket(portNumber);
            Socket clientSocket = serverSocket.accept();
            PrintWriter out =
                new PrintWriter(clientSocket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(
                new InputStreamReader(clientSocket.getInputStream()));
        ) {
          new EchoThread(clientSocket).start();
            String inputLine, outputLine;
            //Scanner k = new Scanner(System.in);

            // Initiate conversation with client
            outputLine = "Connected to Server.";
            out.println(outputLine);

            while (true) {
              inputLine = in.readLine();
              if(inputLine != null) {
                System.out.println(inputLine);
              }
              if(inputLine.equals("quit")) {
                break;
              }
            }

            /*while ((inputLine = in.readLine()) != null) {
              System.out.println("Client: " + inputLine);
              System.out.println("Message:");
                outputLine = k.nextLine();
                out.println(outputLine);
                if (outputLine.equals("Bye."))
                    break;
            }*/
        } catch (IOException e) {
            System.out.println("Exception caught when trying to listen on port "
                + portNumber + " or listening for a connection");
            System.out.println(e.getMessage());
        }
    }
}
