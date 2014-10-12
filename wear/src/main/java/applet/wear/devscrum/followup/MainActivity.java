package applet.wear.devscrum.followup;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;



public class MainActivity extends Activity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_fragment_layout);

        HomeFragment frags = HomeFragment.newInstance("  Tap to Take Note", "", R.drawable.ic_launcher);

        FragmentManager fm = getFragmentManager();
        FragmentTransaction trans = fm.beginTransaction();


        trans.add(R.id.home_frame_layout, frags);
        trans.commit();

    }


}
