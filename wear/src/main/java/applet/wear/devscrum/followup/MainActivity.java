package applet.wear.devscrum.followup;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.wearable.view.CardFragment;
import android.support.wearable.view.DelayedConfirmationView;
import android.widget.TextView;


public class MainActivity extends Activity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_fragment_layout);

        HomeFragment frags = HomeFragment.newInstance("wrapUp", "tap to save notes", R.drawable.ic_launcher);

        FragmentManager fm = getFragmentManager();
        FragmentTransaction trans = fm.beginTransaction();


        trans.add(R.id.home_frame_layout, frags);
        trans.commit();

    }


}
