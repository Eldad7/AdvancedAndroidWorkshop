package eldad.corem.com.advancedandroidworkshop;

/**
 * Created by The Gate Keeper on 5/4/2017.
 */

import android.util.Log;

import com.google.firebase.database.Exclude;
import com.google.firebase.database.ServerValue;
/*import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;*/

public class BoardMessage {

    private String message;
    private long timestamp;
    private Long creationDate;
    private String sender;
    private static String TAG = "BoardMessage";

    public java.util.Map<String, String> getCreationDate() {
        return ServerValue.TIMESTAMP;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    /*@Override
    public void onTokenRefresh() {
        // Get updated InstanceID token.
        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
        Log.d(TAG, "Refreshed token: " + refreshedToken);

        // If you want to send messages to this application instance or
        // manage this apps subscriptions on the server side, send the
        // Instance ID token to your app server.
        sendRegistrationToServer(refreshedToken);
    }

    private void sendRegistrationToServer(String refreshedToken) {

    }*/

    @Exclude
    public Long getCreationDateLong() {
        return creationDate;
    }

    public void setCreationDate(Long creationDate) {
        this.creationDate = creationDate;
    }


    public BoardMessage() {
        timestamp = System.currentTimeMillis();
    }

    public String getMessage() {
        return message;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public long getTimestamp() {
        return timestamp;
    }
}