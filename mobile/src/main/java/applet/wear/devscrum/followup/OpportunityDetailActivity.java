package applet.wear.devscrum.followup;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.IBinder;
import android.os.StrictMode;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;


public class OpportunityDetailActivity extends FragmentActivity {

    ListenService mService;
    boolean mBound = false;
    public Opportunity opp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        Intent i = getIntent();
        opp = Opportunity.fromString(i.getStringExtra("OPP"));

        setContentView(R.layout.opportunity_fragment);
        FragmentManager fm = getFragmentManager();
        Fragment fragment = fm.findFragmentById(R.id.opportunity_frag_container);
        if (fragment == null) {
            fragment = new OpportunityFragment();
            fm.beginTransaction()
                    .add(R.id.opportunity_frag_container, fragment)
                    .commit();
            TextView title = (TextView)findViewById(R.id.opportunity_title);
            title.setText(opp.mTitle);
        }


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.my, menu);
        return true;
    }

    private class No1CurrHttpGet extends AsyncTask {
        @Override
        protected Object doInBackground(Object[] data) {
            HttpClient httpclient = new DefaultHttpClient();
            HttpResponse response = null;
            try {
                if (data.length == 1) {
                    // Execute HTTP Get Request
                    HttpGet revoke_request = new HttpGet((String) data[0]);
                    response = httpclient.execute(revoke_request);
                } else if (data.length == 2) {
                    HttpPost post_request = new HttpPost((String) data[0]);
                    post_request.setEntity(new UrlEncodedFormEntity((ArrayList<NameValuePair>) data[1]));
                    response = httpclient.execute((post_request));
                }
                HttpEntity entity = response.getEntity();
                Log.i("No1Curr, but",EntityUtils.toString(entity, "utf-8"))

            } catch (java.io.IOException e) {
                e.printStackTrace();
            }
        return null;
        }

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_logout) {
            SharedPreferences sharedPref = this.getSharedPreferences(
                    getString(R.string.preference_file_key), this.MODE_PRIVATE);

            String token = sharedPref.getString("SF_ACCESS_TOKEN", "");
            if (!token.equals("")) {

                new No1CurrHttpGet().execute("https://login.salesforce.com/services/oauth2/revoke?token=" + token);

                SharedPreferences.Editor editor = sharedPref.edit();
                editor.putString("SF_ACCESS_TOKEN", "");
                editor.commit();

            }
            startActivity(new Intent(getApplicationContext(), LoginActivity.class));
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onStart(){
        super.onStart();
        Intent intent = new Intent(this, ListenService.class);
        bindService(intent, mConnection, Context.BIND_AUTO_CREATE);

    }

    @Override
    protected void onStop(){
        super.onStop();
        if (mBound) {
            unbindService(mConnection);
            mBound = false;
        }
    }

    public void submitNote(String contents){
        //new No1CurrHttpGet().execute//url, //ArrayList<namevaluepair>);
    }

    private ServiceConnection mConnection = new ServiceConnection() {

        @Override
        public void onServiceConnected(ComponentName className,
                                       IBinder service) {
            // We've bound to LocalService, cast the IBinder and get LocalService instance
            ListenService.ListenBinder binder = (ListenService.ListenBinder) service;
            mService = binder.getService();
            mBound = true;
        }

        @Override
        public void onServiceDisconnected(ComponentName arg0) {
            mBound = false;
        }
    };
}

