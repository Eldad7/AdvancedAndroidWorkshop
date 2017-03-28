package eldad.corem.com.advancedandroidworkshop;

/**
 * Created by The Gate Keeper on 3/28/2017.
 */

public class MyEvent {

    private String message;

    public MyEvent(String _message) {
        this.message = _message;
    }

    public String getMessage(){
        return message;
    }
}
