package applet.wear.devscrum.followup;

import android.app.AlertDialog;
import android.app.Notification;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

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
    String nodeId;
    public static String MESSAGE_RECEIVED = "Message Received";

    public class ListenBinder extends Binder {
        ListenService getService() {
            return ListenService.this;
        }
    }

    @Override
    public void onMessageReceived(MessageEvent messageEvent) {
        super.onMessageReceived(messageEvent);
        nodeId = messageEvent.getSourceNodeId();
        Intent intent = new Intent();
        intent.setAction(MESSAGE_RECEIVED);
        intent.putExtra("messageEvent", messageEvent.getData());
        sendBroadcast(intent);

        if (Log.isLoggable(TAG, Log.DEBUG)) {
            Log.d(TAG, "Message: " + messageEvent);
        }
    }

    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }

}


