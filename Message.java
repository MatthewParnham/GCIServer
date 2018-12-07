import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public class Message implements Serializable {
    private String sender;
    private String reciever;
    private String message;

    /*
    Default Constructor
     */
    public Message(){

    }

    /*
    Constructor
    paramters:String s, String r, String m
    sets sender->s,reciver->r,message->m
     */
    public Message(String s,String r,String m){
        sender = s;
        reciever = r;
        message = m;
    }

    /*
    Constructor
    paramters:String s,String r
    sets sender->s,reciever->r
     */
    public Message(String s, String r){
        sender = s;
        reciever = r;
    }

    /*
    GETTERS AND SETTERS
     */
    public String getSender() {
        return sender;
    }

    public String getReciever() {
        return reciever;
    }

    public String getMessage() {
        return message;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public void setReciever(String reciever) {
        this.reciever = reciever;
    }

    public void setMessage(String message) {
        this.message = message;
    }
    /*
    END OF GETTERS AND SENDERS
     */

    public String breakUp(String m){
        int arrSize = (int)(m.length()/35 + 0.5);

        String[] bySpace = m.split(" ",0);
        String realMessage = "";
        int size = 0;
        for(int i = 0; i < bySpace.length;i++){
            size += bySpace[i].length();
            if(size > 35){
                size = 0;
                realMessage += "\n";
            }
            realMessage += bySpace[i];
        }
        return realMessage;
    }

    /*public byte[] byteValue(){
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ObjectOutput out = null;
        byte[] messageAsBytes;
        try {
            out = new ObjectOutputStream(bos);
            out.writeObject(this);
            out.flush();
            messageAsBytes = bos.toByteArray();
        } catch (IOException e) {
          System.out.println("Exception caught when trying to listen on port.");
          System.out.println(e.getMessage());
        } finally {
            try {
                bos.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        return messageAsBytes;
    }*/

    public String toString(){
        String s = "Sender: " + sender + "\nReciever: " + reciever + "\n\n" + breakUp(message);
        return s;
    }

/*
    protected Message(Parcel in) {
        sender = in.readString();
        reciever = in.readString();
        message = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(sender);
        dest.writeString(reciever);
        dest.writeString(message);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<Message> CREATOR = new Parcelable.Creator<Message>() {
        @Override
        public Message createFromParcel(Parcel in) {
            return new Message(in);
        }

        @Override
        public Message[] newArray(int size) {
            return new Message[size];
        }
    };*/
}
