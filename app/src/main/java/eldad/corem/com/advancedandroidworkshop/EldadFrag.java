package eldad.corem.com.advancedandroidworkshop;
import android.app.Fragment;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
    private static String text = "";
    private EditText et;

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
    public static EldadFrag newInstance(String param1, String param2, String _text) {
        EldadFrag fragment = new EldadFrag();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        text = _text;
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
        TextView tv = (TextView) view.findViewById(R.id.textView);
        tv.setText(mParam1 + " " + mParam2);
        final EditText editText = (EditText) view.findViewById(R.id.text);
        editText.setText(text);
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                EventBus.getDefault().post(new MyEvent(s.toString()));
            }
        });
        return view;
    }
}