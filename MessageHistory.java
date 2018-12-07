import java.io.Serializable;
import java.util.ArrayList;
import java.util.Objects;

public class MessageHistory implements Serializable {
    private ArrayList<Message> messages;
    private static String sender = null;
    private static String reciever = null;

    public MessageHistory(){

    }

    public MessageHistory(String sender, String reciever){

    }

    public MessageHistory(ArrayList<Message> M){
        messages = M;
    }

    //START OF GETTERS AND SETTERS
    public ArrayList<Message> getMessages() {
        return messages;
    }

    public void setMessages(ArrayList<Message> messages) {
        this.messages = messages;
        if(sender == null && reciever == null){
            setSender(messages.get(0).getSender());
            setReciever(messages.get(0).getReciever());
        }
    }

    public static String getSender() {
        return sender;
    }

    public static void setSender(String sender) {
        MessageHistory.sender = sender;
    }

    public static String getReciever() {
        return reciever;
    }

    public static void setReciever(String reciever) {
        MessageHistory.reciever = reciever;

    }
    //END OF GETTERS AND SETTERS


    public String addMessage(Message m){
        messages.add(m);
        if(sender == null && reciever == null){
            setSender(m.getSender());
            setReciever(m.getReciever());
        }
        return m.getMessage();
    }

    /*@Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MessageHistory that = (MessageHistory) o;
        if(Objects.equals(messages, that.getMessages()) && Objects.equals(sender, that.getSender()) && Object.equals(reciever, that.getReciever())){
            return true;
        }
        else{
            return false;
        }
    }*/

    @Override
    public int hashCode() {

        return Objects.hash(messages);
    }
}
