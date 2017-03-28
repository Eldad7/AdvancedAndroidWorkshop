package eldad.corem.com.advancedandroidworkshop;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.widget.LinearLayout;
import android.widget.Toast;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public class MainActivity extends AppCompatActivity {
    EldadFrag frag1, frag2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        frag1 = EldadFrag.newInstance("Hi", "I'm frag1", "I'm frag 1");
        getSupportFragmentManager().beginTransaction().add(R.id.layout, frag1, "frag1").commitNow();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(MyEvent event) {
        if (frag1.isVisible()) {
            frag2 = EldadFrag.newInstance("Hi", "I'm frag2", event.getMessage());
            Toast.makeText(this, "Message Event1", Toast.LENGTH_SHORT).show();
            getSupportFragmentManager().beginTransaction().remove(frag1).commitNow();
            getSupportFragmentManager().beginTransaction().add(R.id.layout, frag2, "frag2").commitNow();
            Toast.makeText(this, event.getMessage(), Toast.LENGTH_SHORT).show();
        }
        else{
            Toast.makeText(this, "Message Event2", Toast.LENGTH_SHORT).show();
            frag1 = EldadFrag.newInstance("Hi", "I'm frag1", event.getMessage());
            getSupportFragmentManager().beginTransaction().remove(frag2).commitNow();
            getSupportFragmentManager().beginTransaction().add(R.id.layout, frag1, "frag1").commitNow();
            Toast.makeText(this, event.getMessage(), Toast.LENGTH_SHORT).show();
        }
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