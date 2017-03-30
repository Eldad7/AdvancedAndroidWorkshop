package eldad.corem.com.advancedandroidworkshop;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import org.greenrobot.eventbus.EventBus;

import static org.greenrobot.eventbus.EventBus.TAG;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link EldadFrag#newInstance} factory method to
 * create an instance of this fragment.
 */
public class EldadFrag extends android.support.v4.app.Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private TextView tv;

    public EldadFrag() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SomeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static EldadFrag newInstance(String param1, String param2) {
        EldadFrag fragment = new EldadFrag();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        // The arguments supplied here will be retained across fragment destroy and creation
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.frag_eldad, container, false);
        tv = (TextView) view.findViewById(R.id.textView);
        Button button = (Button) view.findViewById(R.id.button2);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), LoginActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("uid", tv.getText().toString());
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
        return view;
    }

    public void setUserId(String text){
        tv.setText(text);
    }
}