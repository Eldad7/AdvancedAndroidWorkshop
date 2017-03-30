package eldad.corem.com.advancedandroidworkshop;

import android.os.Bundle;
import android.support.annotation.NonNull;
import static com.google.android.gms.internal.zzs.TAG;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.crashlytics.android.Crashlytics;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import io.fabric.sdk.android.Fabric;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public class MainActivity extends AppCompatActivity {
    private EldadFrag fragment;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private String uid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fabric.with(this, new Crashlytics());
        setContentView(R.layout.activity_main);
        mAuth = FirebaseAuth.getInstance();
        fragment = EldadFrag.newInstance("Fragment", "Anonymous");
        getSupportFragmentManager().beginTransaction().add(R.id.layout, fragment, "frag1").commitNow();
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    // User is signed in
                    uid = user.getUid();
                    fragment.setUserId(uid);
                    Log.d(TAG, "onAuthStateChanged:signed_in:" + user.getUid());
                } else {
                    // User is signed out
                    Log.d(TAG, "onAuthStateChanged:signed_out");
                }
                // ...
            }
        };
        mAuth.signInAnonymously()
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.d(TAG, "signInAnonymously:onComplete:" + task.isSuccessful());

                        // If sign in fails, display a message to the user. If sign in succeeds
                        // the auth state listener will be notified and logic to handle the
                        // signed in user can be handled in the listener.
                        if (!task.isSuccessful()) {
                            Log.w(TAG, "signInAnonymously", task.getException());
                            EventBus.getDefault().post(new MyEvent("Authentication Failed"));
                        }

                        // ...
                    }
                });
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(MyEvent event) {
        Toast.makeText(this, event.getMessage(), Toast.LENGTH_SHORT).show();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(AuthCredential credentials) {
        Toast.makeText(this, "on Message Event", Toast.LENGTH_SHORT).show();
        linkAccount(credentials);
    }

    private void linkAccount(AuthCredential credentials) {
        Toast.makeText(this, "Linking account", Toast.LENGTH_SHORT).show();
        mAuth.getCurrentUser().linkWithCredential(credentials)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.d(TAG, "linkWithCredential:onComplete:" + task.isSuccessful());

                        // If sign in fails, display a message to the user. If sign in succeeds
                        // the auth state listener will be notified and logic to handle the
                        // signed in user can be handled in the listener.
                        if (!task.isSuccessful()) {
                            EventBus.getDefault().post("Authentication Failed");
                        }
                    }
                });
    }

    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
        mAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    public void onStop() {
        EventBus.getDefault().unregister(this);
        if (mAuthListener != null)
            mAuth.removeAuthStateListener(mAuthListener);
        super.onStop();
    }
}