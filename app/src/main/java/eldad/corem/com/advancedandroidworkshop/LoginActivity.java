package eldad.corem.com.advancedandroidworkshop;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.crashlytics.android.Crashlytics;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.EmailAuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import org.greenrobot.eventbus.EventBus;

import io.fabric.sdk.android.Fabric;

import static com.google.android.gms.internal.zzs.TAG;

/**
 * Created by The Gate Keeper on 3/28/2017.
 */

public class LoginActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private String mCustomToken;
    private EditText Email, Password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fabric.with(this, new Crashlytics());
        setContentView(R.layout.activity_login);
        Bundle bundle = getIntent().getExtras();
        mCustomToken = bundle.getString("uid");
        mAuth = FirebaseAuth.getInstance();
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    // User is signed in
                    Log.d(TAG, "onAuthStateChanged:signed_in:" + user.getUid());
                } else {
                    // User is signed out
                    Log.d(TAG, "onAuthStateChanged:signed_out");
                }
                // ...
            }
        };
        Email = (EditText) findViewById(R.id.email);
        Password = (EditText) findViewById(R.id.password);
        Button signIn = (Button) findViewById(R.id.SignInButton);
        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!Email.getText().toString().equals("") && !Password.getText().toString().equals(""))
                    signIn();
            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
        // ...
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }
        // ...
    }

    public void signIn(){
        mAuth.signInWithCustomToken(mCustomToken)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.d(TAG, "signInWithCustomToken:onComplete:" + task.isSuccessful());

                        // If sign in fails, display a message to the user. If sign in succeeds
                        // the auth state listener will be notified and logic to handle the
                        // signed in user can be handled in the listener.
                        if (!task.isSuccessful()) {
                            Log.w(TAG, "signInWithCustomToken", task.getException());
                            EventBus.getDefault().post(new MyEvent("Authentication Failed"));
                            finish();
                        }
                        String email = Email.getText().toString();
                        String password = Password.getText().toString();
                        AuthCredential credential = EmailAuthProvider.getCredential(email,password);
                        Toast.makeText(LoginActivity.this, "Logging in", Toast.LENGTH_SHORT).show();
                        EventBus.getDefault().post(credential);
                        finish();
                    }
                });
    }

}
