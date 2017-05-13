package eldad.corem.com.advancedandroidworkshop;

import java.util.Arrays;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/**
 * Created by The Gate Keeper on 5/13/2017.
 */

public class ChatFragment extends Fragment{

    private EditText chatBoard;
    private ListView chat;
    private Button send;
    ArrayAdapter<String> adapter;
    private static final String TAG = ChatFragment.class.getSimpleName();
    private static final String DB_NAME = "Messages";
    private ChatHandler chatHandler;

    public ChatFragment(){}

    public static ChatFragment newInstance() {
        ChatFragment fragment = new ChatFragment();
        Bundle args = new Bundle();
        // The arguments supplied here will be retained across fragment destroy and creation
        fragment.setArguments(args);
        return fragment;
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void gotNewBoardMessage(BoardMessage event) {
        Toast.makeText(this.getContext(), "New message!", Toast.LENGTH_SHORT).show();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
        public void gotNewMessage(MyEvent event){
            adapter = new ArrayAdapter<>(this.getContext(), android.R.layout.simple_list_item_1, chatHandler.getAllMessages());
            chat.setVisibility(View.VISIBLE);
            chat.setAdapter(adapter);
            adapter.notifyDataSetChanged();
        }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_chat, container, false);
        //MyFirebaseHelper.wireSomeFirebaseTable(DB_NAME, BoardMessage.class);
        chatHandler = new ChatHandler();
        chatBoard = (EditText) view.findViewById(R.id.message_text);
        chat = (ListView) view.findViewById(R.id.chat);
        adapter = new ArrayAdapter<>(this.getContext(), android.R.layout.simple_list_item_1,chatHandler.getAllMessages());
        chat.setAdapter(adapter);
        send = (Button) view.findViewById(R.id.send_button);
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyFirebaseHelper.saveBoardMessageInFirebase(DB_NAME, chatBoard.getText().toString());
            }
        });
        Log.i(TAG, "onCreateView: postSticky user");
        MyFirebaseHelper.initChat(DB_NAME, User.class, chatHandler);
        return view;
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

}
