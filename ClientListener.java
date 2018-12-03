import java.lang.Thread;
import java.net.*;
import java.io.*;
import java.util.*;
import java.text.*;

public class ClientListener extends Thread {
    protected Socket socket;
    protected BufferedReader in;
    protected PrintWriter out;
    protected String userName;

    public ClientListener(Socket clientSocket, String userName, BufferedReader in, PrintWriter out) {
        this.socket = clientSocket;
        this.in = in;
        this.out = out;
        this.userName = userName;
    }

    @Override
    public void run() {
      String inputLine, outputLine;
      try {

        while (true) {
          System.out.println(in.readLine());
        }
      } catch (IOException e) {
        System.out.println("Exception caught when trying to listen on port.");
        System.out.println(e.getMessage());
      }
    }
}
