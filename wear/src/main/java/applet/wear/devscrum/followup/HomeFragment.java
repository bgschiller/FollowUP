package applet.wear.devscrum.followup;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.wearable.view.CardFragment;
import android.support.wearable.view.CardFrame;
import android.support.wearable.view.CardScrollView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.wearable.Asset;
import com.google.android.gms.wearable.DataApi;
import com.google.android.gms.wearable.DataItemAsset;
import com.google.android.gms.wearable.DataItemBuffer;
import com.google.android.gms.wearable.PutDataRequest;
import com.google.android.gms.wearable.Wearable;

import java.util.concurrent.TimeUnit;

/**
 * Created by DevScrum on 10/11/14.
 */
public class HomeFragment extends CardFragment{
    String mTitle;
    String mBody;
    int mIcon;
    private GoogleApiClient mGoogleApiClient;

    static HomeFragment newInstance(String title, String body, int ic_res) {
        HomeFragment frag = new HomeFragment();
        Bundle args = new Bundle();
        args.putString("title", title);
        args.putString("body", body);
        args.putInt("icon", ic_res);
        frag.setArguments(args);
        return frag;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            mGoogleApiClient = new GoogleApiClient.Builder(getActivity()).addApi(Wearable.API).build();

        if(getArguments() != null){
                mTitle = getArguments().getString("title");
                mBody = getArguments().getString("body");
                mIcon = getArguments().getInt("icon");
        }

        PendingResult<DataItemBuffer> results = Wearable.DataApi.getDataItems(mGoogleApiClient);
        results.setResultCallback(new ResultCallback<DataItemBuffer>() {
            @Override
            public void onResult(DataItemBuffer dataItems) {
                if (dataItems.getCount() != 0) {
                    mTitle = dataItems.get(0).toString();
                    Log.w("data", mTitle);
                }

                dataItems.release();
            }
        });
    }


    @Override
    public void onStart(){
        mGoogleApiClient.connect();
    }

    @Override
    public void onStop(){
        mGoogleApiClient.disconnect();
    }

    @Override
    public View onCreateContentView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.home_fragment_layout, container);
        TextView tv = (TextView) view.findViewById(R.id.opportunity_title);
        tv.setText(mTitle);
        return view;
    }
}
