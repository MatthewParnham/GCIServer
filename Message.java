import java.net.*;
import java.io.*;

class Message implements Serializable {

  private String toUser;
  private String fromUser;
  private String message;

  public Message(String toUser, String fromUser, String message) {
    this.toUser = toUser;
    this.fromUser = fromUser;
    this.message = message;
  }

  public String getToUser() {
    return this.toUser;
  }

  public String getMessage() {
    return this.message;
  }

}
