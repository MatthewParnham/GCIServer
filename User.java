import java.net.*;
import java.io.*;
import java.util.*;
import java.text.*;

public class User {

  private String userName;
  private InetAddress IP;
  private Socket sock = null;

  public User(String userName, InetAddress IP, Socket sock) {
    this.userName = userName;
    this.IP = IP;
    this.sock = sock;
  }

  public Socket getSocket() {
    return this.sock;
  }
  public String getUserName() {
    return this.userName;
  }
  public InetAddress getIP() {
    return this.IP;
  }
}
