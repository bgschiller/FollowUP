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
            mGoogleApiClient = new GoogleApiClient.Builder(getActivity()).build();

        if(getArguments() != null){
                mTitle = getArguments().getString("title");
                mBody = getArguments().getString("body");
                mIcon = getArguments().getInt("icon");
        }
    }

    @Override
    public View onCreateContentView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        CardScrollView cardScrollView = new CardScrollView(getActivity());
        cardScrollView.setCardGravity(Gravity.BOTTOM);
        CardFrame cf = new CardFrame(getActivity());
        FrameLayout layout = new FrameLayout(getActivity());


        PendingResult<DataItemBuffer> results = Wearable.DataApi.getDataItems(mGoogleApiClient);
        results.setResultCallback(new ResultCallback<DataItemBuffer>() {
            @Override
            public void onResult(DataItemBuffer dataItems) {
                if (dataItems.getCount() != 0) {
                    mTitle = dataItems.get(0).toString();
                }

                dataItems.release();
            }
        });

        Button chng_opp = new Button(getActivity());
        chng_opp.setBackground(getResources().getDrawable(mIcon));


        StringBuilder titleBuilder = new StringBuilder().insert(0, mTitle);
        StringBuilder bodyBuilder = new StringBuilder().insert(0,mBody);
        String titleDisplay = titleBuilder.toString();
        String bodyDisplay = bodyBuilder.toString();
        TextView titleTextView = new TextView(getActivity());
        TextView bodyTextView = new TextView(getActivity());

        titleTextView.setText(titleDisplay);
        bodyTextView.setText(bodyDisplay);

        titleTextView.setTextColor(getResources().getColor(R.color.black));
        bodyTextView.setTextColor(getResources().getColor(R.color.black));

        titleTextView.setAllCaps(true);
        titleTextView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT));
        titleTextView.setPadding(0,10,0,20);
        bodyTextView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT));
        bodyTextView.setPadding(0,50,0,20);

        layout.addView(titleTextView, 0);
        layout.addView(chng_opp, 1);
        layout.addView(bodyTextView, 2);

        cf.addView(layout);
        cardScrollView.addView(cf);
        return cardScrollView;
    }

    @Override
    public void onStart(){
        mGoogleApiClient.connect();
    }

    @Override
    public void onStop(){
        mGoogleApiClient.disconnect();
    }
}
