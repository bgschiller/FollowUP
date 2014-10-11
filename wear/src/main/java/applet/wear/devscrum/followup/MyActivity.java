package applet.wear.devscrum.followup;

import android.app.Activity;

import android.os.Bundle;

import android.support.wearable.view.GridViewPager;
import android.support.wearable.view.WatchViewStub;
import android.util.Log;
import android.widget.TextView;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.wearable.DataItemBuffer;
import com.google.android.gms.wearable.DataMapItem;
import com.google.android.gms.wearable.Wearable;

public class MyActivity extends Activity {

    private TextView mTextView;
    private GoogleApiClient mGoogleApiClient;
    private String mTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my);
        mGoogleApiClient = new GoogleApiClient.Builder(this).addApi(Wearable.API).build();
        final GridViewPager pager = (GridViewPager) findViewById(R.id.pager);

        PendingResult<DataItemBuffer> results = Wearable.DataApi.getDataItems(mGoogleApiClient);
        results.setResultCallback(new ResultCallback<DataItemBuffer>() {

            @Override
            public void onResult(DataItemBuffer dataItems) {
                if (dataItems.getCount() > 0) {
                    DataMapItem dataMapItem = DataMapItem.fromDataItem(dataItems.get(0));
                    mTitle = dataMapItem.getDataMap().getString("opp_name");
                    dataItems.release();

                }
                    Log.w("logging", mTitle);
                    PagerAdapter adapter = new PagerAdapter(getApplicationContext(), getFragmentManager());
                    adapter.addObject(mTitle);
                    pager.setAdapter(adapter );

            }
        });



    }

    @Override
    protected void onStart() {
        super.onStart();
        mGoogleApiClient.connect();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mGoogleApiClient.disconnect();
    }


}
