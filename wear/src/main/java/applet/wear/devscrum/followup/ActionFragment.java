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
        layout.setOrientation(LinearLayout.VERTICAL);

        Button action = new Button(getActivity());
        action.setBackground(getResources().getDrawable(mIcon));
        LinearLayout.LayoutParams button_params = new LinearLayout.LayoutParams(
                200,
                200);
        button_params.gravity = Gravity.CENTER;
        button_params.setMargins(0, -5, 0, -45);

        action.setLayoutParams(button_params);

        TextView action_desc = new TextView(getActivity());
        action_desc.setText(mTitle);
        action_desc.setTextColor(getResources().getColor(R.color.black));

        layout.addView(action,0);
        layout.addView(action_desc,1);


        cf.addView(layout);
        cardScrollView.addView(cf);

        return cardScrollView;
    }
}
