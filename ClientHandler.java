import java.lang.Thread;
import java.net.*;
import java.io.*;
import java.util.*;
import java.text.*;

public class ClientHandler extends Thread {
    protected Socket socket;
    protected BufferedReader in;
    protected PrintWriter out;

    public ClientHandler(Socket clientSocket, BufferedReader in, PrintWriter out) {
        this.socket = clientSocket;
        this.in = in;
        this.out = out;
    }

    @Override
    public void run() {
      String inputLine, outputLine;
      try {
        // Initiate conversation with client
        outputLine = "Connected to Server.";
        out.println(outputLine);

        while ((inputLine = in.readLine()) != null) {
          System.out.println("Client: " + inputLine);
          if(inputLine.equals("quit")) {
            break;
          }
        }
        System.out.println("Client has left.");
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
