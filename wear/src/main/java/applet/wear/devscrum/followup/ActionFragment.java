package applet.wear.devscrum.followup;

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

import org.w3c.dom.Text;

/**
 * Created by DevScrum on 10/11/14.
 */
public class ActionFragment extends CardFragment {
    String mTitle;
    String mBody;
    int mIcon;

    static ActionFragment newInstance(String title, String mBody, int ic_res) {
        ActionFragment frag = new ActionFragment();
        Bundle args = new Bundle();
        args.putString("title", title);
        args.putInt("icon", ic_res);
        frag.setArguments(args);
        return frag;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(getArguments() != null){
            mTitle = getArguments().getString("title");
            mIcon = getArguments().getInt("icon");
        }
    }

    @Override
    public View onCreateContentView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        CardScrollView cardScrollView = new CardScrollView(getActivity());
        cardScrollView.setCardGravity(Gravity.BOTTOM);
        CardFrame cf = new CardFrame(getActivity());
        LinearLayout layout = new LinearLayout(getActivity());
        layout.setOrientation(LinearLayout.HORIZONTAL);
        LinearLayout.LayoutParams spacer_params = new LinearLayout.LayoutParams(
                0,
                0, 2.0f);
        LinearLayout.LayoutParams button_params = new LinearLayout.LayoutParams(
                0,
                LinearLayout.LayoutParams.WRAP_CONTENT, 6.0f);
        //button_params.setMargins(20,20,20,20);



        View spacerLeft = new View(getActivity());
        View spacerRight = new View(getActivity());
        Button action = new Button(getActivity());
        action.setBackground(getResources().getDrawable(mIcon));

        layout.addView(spacerLeft,0);
        layout.addView(action,1);
        layout.addView(spacerRight, 2);
        spacerLeft.setLayoutParams(spacer_params);
        spacerRight.setLayoutParams(spacer_params);
        action.setLayoutParams(button_params);

        cf.addView(layout);
        cardScrollView.addView(cf);

        return cardScrollView;
    }
}
