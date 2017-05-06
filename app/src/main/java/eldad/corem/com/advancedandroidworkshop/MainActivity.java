package eldad.corem.com.advancedandroidworkshop;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;
import com.crashlytics.android.Crashlytics;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import io.fabric.sdk.android.Fabric;

public class MainActivity extends AppCompatActivity {
    private MainFragment fragment;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Fabric.with(this, new Crashlytics());
        setContentView(R.layout.activity_main);
        fragment = MainFragment.newInstance();
        getSupportFragmentManager().beginTransaction().add(R.id.layout, fragment, "frag1").commitNow();
    }

/*    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(MyEvent event) {
        Fragment fragment;
        switch (event.getCommand()) {
            case 1:
                EldadFrag frag1 = EldadFrag.newInstance("input", "Name");
                fragment = getSupportFragmentManager().findFragmentByTag("inpFrag");
                if (fragment == null) {
                    getSupportFragmentManager().beginTransaction().replace(R.id.layout, frag1, "inputFragment").commitNow();
                    Toast.makeText(this, event.getMessage(), Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this, "input fragment already presence!", Toast.LENGTH_SHORT).show();
                }
                break;
            case 2:
                EldadFrag frag2 = EldadFrag.newInstance("Login","text");
                fragment = getSupportFragmentManager().findFragmentByTag("textFrag");
                if (fragment == null) {

*//*
                    AuthenticationExampleFragment frag2 = AuthenticationExampleFragment.newInstance();
*//*
                    getSupportFragmentManager().beginTransaction().replace(R.id.layout, frag2, "loginFragment").commitNow();
                    Toast.makeText(this, event.getMessage(), Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }*/

    @Override
    public void onStart() {
        super.onStart();
        //EventBus.getDefault().register(this);
    }

    @Override
    public void onStop() {
        //EventBus.getDefault().unregister(this);
        super.onStop();
    }
}