package eldad.corem.com.advancedandroidworkshop;

/**
 * Created by The Gate Keeper on 5/4/2017.
 */

import java.util.Arrays;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.firebase.ui.auth.AuthUI;
import com.google.firebase.auth.FirebaseUser;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.w3c.dom.Text;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MainFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MainFragment extends Fragment {
    public static final int RC_SIGN_IN = 234;
    private static final String TAG = MainFragment.class.getSimpleName();
    private static final String DB_NAME = "Messages";
    TextView usernameTv, display_name, status;
    Button buttonLogin;
    UsersHandler usersHandler;
    boolean loggedIn = false;
    ArrayAdapter<String> adapter;
    private ListView listView;

    public MainFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment SomeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MainFragment newInstance() {
        MainFragment fragment = new MainFragment();
        Bundle args = new Bundle();
        // The arguments supplied here will be retained across fragment destroy and creation
        fragment.setArguments(args);
        return fragment;
    }

    /**
     * don't forget to register and unregister in start/stop
     *
     * @param event b
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void newUser(MyEvent event) {
        if (event.getCommand()==3 && !loggedIn)
            status.setText(String.format(getResources().getString(R.string.anonymous_users),String.valueOf(usersHandler.getLoggedInUsers()), String.valueOf(usersHandler.getLoggedAnonymousUsers())));
        if (event.getCommand()==2){}

    }

    @Subscribe(sticky = true, threadMode = ThreadMode.MAIN)
    public void gotSignEvent(CurrentUser event) {
        Log.i(TAG, "got sticky sign " + event.getUserData() + " event");

//        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        FirebaseUser currentUser = event.getUserData();

        if (currentUser == null){buttonLogin.setText(R.string.login);}
        else if (event.isAnonymous()) {
            display_name.setText(String.format(getResources().getString(R.string.display_name), "Anonymous + Uid: " + currentUser.getUid()));
            buttonLogin.setText(R.string.login);
            status.setText(String.format(getResources().getString(R.string.anonymous_users),String.valueOf(usersHandler.getLoggedInUsers()), String.valueOf(usersHandler.getLoggedAnonymousUsers())));
            loggedIn = false;
        } else {
            loggedIn = true;
            display_name.setText(String.format(getResources().getString(R.string.display_name), currentUser.getDisplayName()));
            Log.i(TAG, "user id is " + currentUser.getUid());
            buttonLogin.setText(R.string.logout);
            adapter = new ArrayAdapter<>(this.getContext(), android.R.layout.simple_list_item_1, usersHandler.getUsers());
            listView.setVisibility(View.VISIBLE);
            listView.setAdapter(adapter);
            adapter.notifyDataSetChanged();
            status.setText(String.format(getResources().getString(R.string.logged_in), String.valueOf(usersHandler.getLoggedAnonymousUsers())));
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        usersHandler = new UsersHandler();
        showConnectedUsers();
        display_name = (TextView) view.findViewById(R.id.display_name);
        status = (TextView) view.findViewById(R.id.status);
        status.setText(String.format(getResources().getString(R.string.anonymous_users),String.valueOf(0), String.valueOf(0)));
        listView = (ListView) view.findViewById(R.id.list);
//        MyFirebaseHelper.wireSomeFirebaseTable(DB_NAME, BoardMessage.class);

        /*View buttonSend = view.findViewById(R.id.button_send);
        buttonSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyFirebaseHelper.saveBoardMessageInFirebase(DB_NAME, editText.getText().toString());
            }
        });*/
        buttonLogin = (Button) view.findViewById(R.id.button_login);
        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doSignInOutToggle();
            }
        });

        Log.i(TAG, "onCreateView: postSticky user");
        showConnectedUsers();
        EventBus.getDefault().postSticky(MyFirebaseHelper.getCurrentUser());
        return view;
    }

    private void doSignInOutToggle() {
        if (!MyFirebaseHelper.getCurrentUser().isAnonymous()) {
            // sign out
            AuthUI.getInstance()
                    .signOut(MainFragment.this.getActivity());
            display_name.setText(String.format(getResources().getString(R.string.display_name), "Anonymous"));
            loggedIn = false;
            EventBus.getDefault().post(new MyEvent("Logout", 2));
        } else {

            // open a sign in dialog
            getActivity().startActivityForResult(
                    AuthUI.getInstance()
                            .createSignInIntentBuilder()
                            .setProviders(Arrays.asList(new AuthUI.IdpConfig.Builder(AuthUI.EMAIL_PROVIDER).build(),
                                    new AuthUI.IdpConfig.Builder(AuthUI.GOOGLE_PROVIDER).build()
                            ))
                            .build(),
                    RC_SIGN_IN); // this callback code is not really needed - we're listening to auth changes
        }
        EventBus.getDefault().post(new MyEvent("Login", 2));
    }

    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onStop() {
        EventBus.getDefault().unregister(this);
        super.onStop();
    }

    private void showConnectedUsers(){
        String DB_NAME = "users";
        MyFirebaseHelper.initUsers(DB_NAME, User.class, usersHandler);
    }

}