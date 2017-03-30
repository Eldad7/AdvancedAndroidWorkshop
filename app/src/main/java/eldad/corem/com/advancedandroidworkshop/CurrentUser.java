package eldad.corem.com.advancedandroidworkshop;

import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.iid.FirebaseInstanceId;

/**
 * Created by The Gate Keeper on 3/28/2017.
 */

public class CurrentUser {

    public static final String DB_REF = "users";

    private FirebaseUser userData = null;

    public CurrentUser() {
    }

    public String getToken() {
        return FirebaseInstanceId.getInstance().getToken();
    }

    public boolean isAnonymous() {
        if (userData != null) {
            return userData.isAnonymous();
        }
        return true;
    }

    public String getUserName() {
        if (userData != null) {
            return userData.getDisplayName();
        }
        return "-";
    }

    public String getEmail() {
        if (userData != null) {
            return userData.getEmail();
        }
        return "-";
    }

    public FirebaseUser getUserData() {
        return userData;
    }

    public void setUserData(FirebaseUser userData) {
        this.userData = userData;
    }


}
