package applet.wear.devscrum.followup;

import android.content.Intent;
import android.os.Bundle;
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

/**
 * Created by DevScrum on 10/11/14.
 */
public class HomeFragment extends CardFragment{
    String mTitle;
    String mBody;
    int mIcon;


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
}
