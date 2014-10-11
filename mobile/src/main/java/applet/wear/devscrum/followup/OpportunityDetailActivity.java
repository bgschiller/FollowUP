package applet.wear.devscrum.followup;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.google.android.gms.wearable.PutDataMapRequest;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class OpportunityDetailActivity extends FragmentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.opportunity_fragment);
        FragmentManager fm = getFragmentManager();
        Fragment fragment = fm.findFragmentById(R.id.opportunity_frag_container);
        if(fragment == null) {
            fragment = new OpportunityFragment();
            fm.beginTransaction()
                    .add(R.id.opportunity_frag_container, fragment)
                    .commit();
        }

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                .permitAll().build();
        StrictMode.setThreadPolicy(policy);


    }




    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.my, menu);
        return true;
    }

    private class DontCareHttpGet extends AsyncTask {
        @Override
        protected Object doInBackground(Object[] urls) {
            HttpClient httpclient = new DefaultHttpClient();


            try {
                // Execute HTTP Post Request
                for(Object url: urls) {
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

            String token = sharedPref.getString("SF_ACCESS_TOKEN","");
            if (! token.equals("")){

                new DontCareHttpGet().execute("https://login.salesforce.com/services/oauth2/revoke?token=" + token);

                SharedPreferences.Editor editor = sharedPref.edit();
                editor.putString("SF_ACCESS_TOKEN", "");
                editor.commit();

            }
            startActivity(new Intent(getApplicationContext(), LoginActivity.class));
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
