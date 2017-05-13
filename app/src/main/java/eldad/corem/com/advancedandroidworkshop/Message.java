package eldad.corem.com.advancedandroidworkshop;
/**
 * Created by The Gate Keeper on 5/13/2017.
 */

public class Message {

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    private String sender, content, timestamp, creationDate;

    public Message(){
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(String date) {
        this.creationDate = date;
    }

    @Override
    public String toString(){
        return getContent() + " by: " + getSender() + " at: " + getCreationDate() + " " + getTimestamp();
    }
}
