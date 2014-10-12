package applet.wear.devscrum.followup;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.wearable.view.GridViewPager;
import android.util.Log;
import android.widget.TextView;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.wearable.DataItemBuffer;
import com.google.android.gms.wearable.DataMapItem;
import com.google.android.gms.wearable.Wearable;

public class MainActivity extends Activity {

    private TextView mTextView;
    private String mTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my);
                Intent i = getIntent();
                String opp_name = i.getStringExtra("opps");
                PagerAdapter adapter = new PagerAdapter(this, getFragmentManager(), opp_name);

        if ( adapter != null ) {
            GridViewPager pager = (GridViewPager) findViewById(R.id.pager);
            pager.setAdapter(adapter);
        }


    }


}
