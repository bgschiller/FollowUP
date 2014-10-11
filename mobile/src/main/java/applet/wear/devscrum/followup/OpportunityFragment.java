package applet.wear.devscrum.followup;

import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.wearable.DataApi;
import com.google.android.gms.wearable.PutDataMapRequest;
import com.google.android.gms.wearable.PutDataRequest;
import com.google.android.gms.wearable.Wearable;

import org.w3c.dom.Text;

/**
 * Created by DevScrum on 10/11/14.
 */
public class OpportunityFragment extends Fragment {
    GoogleApiClient mGoogleApiClient;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        GoogleApiClient.Builder builder = new GoogleApiClient.Builder(getActivity());
        mGoogleApiClient = builder.addApi(Wearable.API).build();


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState){
        View v = inflater.inflate(R.layout.opportunity_fragment, parent, false);
        TextView text = (TextView) v.findViewById(R.id.opportunity_title);

        PutDataMapRequest dataMap = PutDataMapRequest.create("/currentOpp");
        dataMap.getDataMap().putString("opp_name", (String) text.getText());

        PutDataRequest request = dataMap.asPutDataRequest();
        PendingResult<DataApi.DataItemResult> pendingResult = Wearable.DataApi
                .putDataItem(mGoogleApiClient, request);

        return v;
    }

    @Override
    public void onStart(){
        super.onStart();
        mGoogleApiClient.connect();
    }

    @Override
    public void onStop() {
        super.onStop();
        mGoogleApiClient.disconnect();
    }

}
