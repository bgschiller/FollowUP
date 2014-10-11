package applet.wear.devscrum.followup;


import android.os.Bundle;
import android.support.wearable.view.CardFragment;


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
                mBody = getArguments().getString("body");
                mIcon = getArguments().getInt("icon");
        }
    }

}
