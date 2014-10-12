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
import android.view.Menu;
import android.view.MenuItem;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.IOException;


public class OpportunityDetailActivity extends FragmentActivity {

    ListenService mService;
    boolean mBound = false;
    public Opportunity opp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.opportunity_fragment);
        FragmentManager fm = getFragmentManager();
        Fragment fragment = fm.findFragmentById(R.id.opportunity_frag_container);
        if (fragment == null) {
            fragment = new OpportunityFragment();
            fm.beginTransaction()
                    .add(R.id.opportunity_frag_container, fragment)
                    .commit();
        }

        Intent i = getIntent();
        opp = Opportunity.fromString(i.getStringExtra("OPP"));
        
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.my, menu);
        return true;
    }

    private class No1CurrHttpGet extends AsyncTask {
        @Override
        protected Object doInBackground(Object[] urls) {
            HttpClient httpclient = new DefaultHttpClient();


            try {
                // Execute HTTP Post Request
                for (Object url : urls) {
                    HttpGet revoke_request = new HttpGet((String) url);
                    HttpResponse response = httpclient.execute(revoke_request);
                }
            } catch (ClientProtocolException e) {
                e.printStackTrace();
            } catch (IOException e) {
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

