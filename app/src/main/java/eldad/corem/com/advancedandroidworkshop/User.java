package eldad.corem.com.advancedandroidworkshop;

/**
 * Created by The Gate Keeper on 5/6/2017.
 */

public class User {

    private boolean isAnonymous;
    private String email;
    private String token;
    private String userName;

    public User(){

    }
    public boolean isAnonymous() {
        return isAnonymous;
    }

    public void setAnonymous(boolean anonymous) {
        isAnonymous = anonymous;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    @Override
    public String toString(){
        return getUserName() + " email: " + getEmail();
    }
}
