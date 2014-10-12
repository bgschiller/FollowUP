package applet.wear.devscrum.followup;

import android.app.AlertDialog;
import android.app.Notification;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

import com.google.android.gms.wearable.MessageEvent;
import com.google.android.gms.wearable.WearableListenerService;

/**
 * Created by DevScrum on 10/11/14.
 */
public class ListenService extends WearableListenerService {
    private static final String TAG = "DataLayerSample";
    private static final String START_ACTIVITY_PATH = "/start-activity";
    private static final String DATA_ITEM_RECEIVED_PATH = "/data-item-received";
    private final IBinder mBinder = new ListenBinder();
    private AlertDialog dialog;

    public class ListenBinder extends Binder {
        ListenService getService() {
            return ListenService.this;
        }
    }

    @Override
    public void onMessageReceived(MessageEvent messageEvent) {
        super.onMessageReceived(messageEvent);
        if (messageEvent.getPath().equals("/message")) {
            AlertDialog.Builder builder = new AlertDialog.Builder(getApplicationContext());
            builder.setTitle("MessageEvent received: " + messageEvent.getData());
            dialog = builder.create();
            //do work
        }

        if (Log.isLoggable(TAG, Log.DEBUG)) {
            Log.d(TAG, "Message: " + messageEvent);
        }
    }

}


