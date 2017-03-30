package eldad.corem.com.advancedandroidworkshop;

import android.support.annotation.NonNull;
import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserInfo;
import org.greenrobot.eventbus.EventBus;

/**
 * Created by The Gate Keeper on 3/28/2017.
 */

public class MyFirebaseHelper {
    private static final String TAG = MyFirebaseHelper.class.getSimpleName();
    static String lastUid = null;

    /**
     * caching the most up to date user info
     */
    private static CurrentUser currentUser = null;

    public static CurrentUser getCurrentUser() {
        if (currentUser == null) {
            // first time checking
            currentUser = new CurrentUser();
            setupUserAuth();
        }

        return currentUser;
    }

    /**
     * called only the first time the app runs
     */
    private static void setupUserAuth() {

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        // will be called every time the auth state changes:
        FirebaseAuth.getInstance().addAuthStateListener(new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    currentUser.setUserData(user);
                } else {
                    currentUser.setUserData(null);
                }

                FirebaseUser userData = currentUser.getUserData();
                EventBus.getDefault().postSticky(currentUser);

            }
        });

        if (user == null) {
            // will trigger the listener
            FirebaseAuth.getInstance().signInAnonymously();
        }
    }
}
