package eldad.corem.com.advancedandroidworkshop;

/**
 * Created by The Gate Keeper on 3/30/2017.
 */

public class LoginEvent {
    private String email;
    private String password;

    public LoginEvent (String _email, String _password){
        this.email = _email;
        this.password = _password;
    }

    public String getEmail(){return email;}
    public String getPassword(){return password;}
}
