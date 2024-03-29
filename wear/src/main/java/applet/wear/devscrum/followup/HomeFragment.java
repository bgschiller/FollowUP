package applet.wear.devscrum.followup;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.support.wearable.view.CardFragment;
import android.support.wearable.view.CardFrame;
import android.support.wearable.view.CardScrollView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;


/**
 * Created by DevScrum on 10/11/14.
 */
public class HomeFragment extends CardFragment {
    final int SPEECH_REQUEST_CODE = 0;
    String spokenText = "";
    private int image;
    private String title;
    private String text;

    public static HomeFragment newInstance(String title, String text, int image){
        Bundle args = new Bundle();
        args.putString("title", title);
        args.putString("text", text);
        args.putInt("image", image);
        HomeFragment hf = new HomeFragment();
        hf.setArguments(args);
        return hf;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        Bundle args = getArguments();
        image = args.getInt("image");
        title = args.getString("title");
        text = args.getString("text");
    }

    @Override
    public View onCreateContentView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        CardScrollView cardScrollView = new CardScrollView(getActivity());
        cardScrollView.setCardGravity(Gravity.BOTTOM);
        CardFrame cf = new CardFrame(getActivity());
        LinearLayout layout = new LinearLayout(getActivity());
        layout.setOrientation(LinearLayout.VERTICAL);

        Button action = new Button(getActivity());
        action.setBackground(getResources().getDrawable(R.drawable.ic_wrap_title));
        LinearLayout.LayoutParams button_params = new LinearLayout.LayoutParams(
                150,
                50);
        button_params.gravity = Gravity.CENTER;
        button_params.setMargins(0, 40, 0, 0);


        action.setLayoutParams(button_params);

        TextView action_desc = new TextView(getActivity());

        LinearLayout.LayoutParams text_params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        text_params.gravity = Gravity.CENTER_HORIZONTAL;
        action_desc.setLayoutParams(text_params);
        action_desc.setText(title);
        action_desc.setTextColor(getResources().getColor(R.color.black));
        action_desc.setTextSize(20);

        layout.addView(action,0);
        layout.addView(action_desc,1);

        cf.addView(layout);
        cardScrollView.addView(cf);
        cardScrollView.setExpansionFactor(1);
        cardScrollView.setBackgroundColor(getResources().getColor(R.color.white));

        View v = cardScrollView;
        v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                displaySpeechRecognizer();
            }
        });

        return v;
    }
    // Create an intent that can start the Speech Recognizer activity
    private void displaySpeechRecognizer() {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
    // Start the activity, the intent will be populated with the speech text
        startActivityForResult(intent, SPEECH_REQUEST_CODE);
    }

    // This callback is invoked when the Speech Recognizer returns.
// This is where you process the intent and extract the speech text from the intent.
    @Override
    public void onActivityResult(int requestCode, int resultCode,Intent data) {

        if (requestCode == SPEECH_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            List<String> results = data.getStringArrayListExtra(
                    RecognizerIntent.EXTRA_RESULTS);
            String spokenText = results.get(0);
            Log.d("text", spokenText);
            this.spokenText = spokenText;
            confirmInputText(spokenText);
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    public void confirmInputText(String text){
        Intent confirm_intent = new Intent(getActivity(), confirmActivity.class);
        confirm_intent.putExtra("body", text);
        startActivity(confirm_intent);
        getActivity().finish();
    }


}
