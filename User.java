import java.net.*;
import java.io.*;
import java.util.*;
import java.text.*;

public class User {

  private String userName;
  private Socket sock = null;
  private ArrayList<Message> history;

  public User(String userName, Socket sock) {
    this.userName = userName;
    this.sock = sock;
    this.history = new ArrayList<Message>();
  }

  public Socket getSocket() {
    return this.sock;
  }
  public String getUserName() {
    return this.userName;
  }
  public ArrayList<Message> getHistory() {
    return this.history;
  }
}
