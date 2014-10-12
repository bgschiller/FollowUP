package applet.wear.devscrum.followup;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.wearable.activity.ConfirmationActivity;
import android.support.wearable.view.DelayedConfirmationView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.wearable.MessageApi;
import com.google.android.gms.wearable.Node;
import com.google.android.gms.wearable.NodeApi;
import com.google.android.gms.wearable.Wearable;
import com.google.common.base.Charsets;

import java.nio.charset.Charset;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Created by DevScrum on 10/11/14.
 */
public class confirmActivity extends Activity implements DelayedConfirmationView.DelayedConfirmationListener {
    private DelayedConfirmationView mDelayedView;
    private String body;
    private String nodeId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.confirmation_activity_layout);
        Intent i = getIntent();
        body = i.getStringExtra("body");

        TextView tv = (TextView) findViewById(R.id.parse_voice_text);
        if(body.length() > 50) {
            String tv_body = body.substring(0, 50) + "...";
            tv.setText(tv_body);
        } else { tv.setText(body); }
        tv.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);

        mDelayedView = (DelayedConfirmationView) findViewById(R.id.delayed_confirm);
        mDelayedView.setListener(this);
        // Two seconds to cancel the action
        mDelayedView.setTotalTimeMs(5000);
        // Start the timer
        mDelayedView.start();
    }

    @Override
    public void onTimerFinished(View v) {
        //User didn't cancel
        sendMessage("/text_body", body);

    }

    @Override
    public void onTimerSelected(View v) {
        //User canceled
        Intent back_record = new Intent(this, MainActivity.class);
        startActivity(back_record);
        finish();
    }

    private GoogleApiClient getGoogleApiClient(Context ctx) {
        GoogleApiClient mApiClient = new GoogleApiClient.Builder(ctx)
                .addApi(Wearable.API)
                .build();
       return mApiClient;
    }

    private void sendMessage(final String path, final String text) {
               final GoogleApiClient client = getGoogleApiClient(this);
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            client.blockingConnect();
                            NodeApi.GetConnectedNodesResult result =
                                    Wearable.NodeApi.getConnectedNodes(client).await();
                            List<Node> nodes = result.getNodes();
                            if (nodes.size() > 0) {
                                nodeId = nodes.get(0).getId();
                                Wearable.MessageApi.sendMessage(client, nodeId, path, text.getBytes(Charsets.UTF_8));
                            }
                            client.disconnect();
                        }
                    }).start();

                    backToMain();
    }

    public void backToMain(){
        Intent newIntent = new Intent(this, MainActivity.class);
        startActivity(newIntent);
        this.finish();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }
}

