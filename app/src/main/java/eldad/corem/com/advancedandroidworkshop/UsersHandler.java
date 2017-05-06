package eldad.corem.com.advancedandroidworkshop;

import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * Created by The Gate Keeper on 5/6/2017.
 */

public class UsersHandler {

    public int getLoggedAnonymousUsers() {
        return loggedAnonymousUsers;
    }

    public int getLoggedInUsers() {
        return loggedInUsers;
    }

    private int loggedAnonymousUsers, loggedInUsers;
    private ArrayList<User> users, anonymousUsers;

    public UsersHandler(){
        users = new ArrayList<>();
        anonymousUsers = new ArrayList<>();
        loggedAnonymousUsers=loggedInUsers=0;
    }

    public ArrayList getUsers(){ return users;}
    public ArrayList getAnonymousUsers(){ return users;}

    public void addUser(User user){
        if (user!=null) {
            if (user.isAnonymous()) {
                loggedAnonymousUsers++;
                anonymousUsers.add(user);
            } else {
                loggedInUsers++;
                users.add(user);
            }
        }
    }
}
