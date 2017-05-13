package eldad.corem.com.advancedandroidworkshop;

import java.util.ArrayList;

/**
 * Created by The Gate Keeper on 5/13/2017.
 */

public class ChatHandler {

    private ArrayList<Message> chat;

    public ChatHandler(){
        chat = new ArrayList<>();
    }

    public ArrayList getAllMessages(){ return chat;}

    public void addMessage(Message message){
        if (message!=null) {
            chat.add(message);
        }
    }
}
