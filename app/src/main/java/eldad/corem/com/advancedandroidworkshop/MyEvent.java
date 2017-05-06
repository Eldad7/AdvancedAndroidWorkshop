package eldad.corem.com.advancedandroidworkshop;

import android.support.annotation.IntDef;

import java.lang.annotation.Retention;

import static java.lang.annotation.RetentionPolicy.SOURCE;

/**
 * Created by The Gate Keeper on 3/28/2017.
 */

public class MyEvent {

    private String message;
    private int commandNum;
    public static final int SEND = 1;
    public static final int LOG_IN = 2;
    public static final int NEW_USER = 3;

    public MyEvent(String _message, int command) {
        this.message = _message;
        this.commandNum = command;
    }

    public String getMessage(){
        return message;
    }

    public int getCommand() {
        return commandNum;
    }

    /**
     * Session wrapper id
     */
    @Retention(SOURCE)
    @IntDef({
            SEND,
            LOG_IN,
            NEW_USER
    })
    public @interface FRAGMENT_COMMANDS {
    }
}
