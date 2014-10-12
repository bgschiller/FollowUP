package applet.wear.devscrum.followup;

import android.app.ListFragment;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;

/**
 * Created by DevScrum on 10/10/14.
 */
public class OpportunityHandler {
    private static OpportunityHandler sOpportunityHandler;
    private Context mAppContext;
    private ArrayList<Opportunity> mOpportunities;

    public OpportunityHandler(Context appContext) {
        mAppContext = appContext;
        mOpportunities = retrieve_data();
    }



    private ArrayList<Opportunity> retrieve_data(){
        String soql_query = null;
        try {
            soql_query = URLEncoder.encode("select Id, Name, latest_wrap_up__c, Amount " +
                    "from Opportunity " +
                    "where latest_wrap_up__c != null or show_in_demo__c = 1", "utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }


        SharedPreferences sharedPref = mAppContext.getSharedPreferences(
                mAppContext.getString(R.string.preference_file_key), mAppContext.MODE_PRIVATE);
        String access_token = sharedPref.getString("SF_ACCESS_TOKEN","");
        String instance_url = sharedPref.getString("SF_INSTANCE_URL","");
        if (access_token.length() == 0 || instance_url.length() == 0){
            mAppContext.startActivity(new Intent(mAppContext, LoginActivity.class));
        }
        String url = instance_url + "/services/data/v31.0/query/?q=" + soql_query;

        Log.i("OpportunityHandler", "going to check what's up at "+url);
        HttpClient httpclient = new DefaultHttpClient();
        String result = null;
        try {
            HttpGet opp_request = new HttpGet(url);
            opp_request.addHeader("Authorization", "Bearer " + access_token);
            HttpResponse response = httpclient.execute(opp_request);
            HttpEntity entity = response.getEntity();
            result = EntityUtils.toString(entity, "utf-8");
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Log.i("OpportunityHandler", result);

        return null;
    }
    public static OpportunityHandler get(Context c){
        if( sOpportunityHandler == null) {
            sOpportunityHandler = new OpportunityHandler(c.getApplicationContext());
        }
        return sOpportunityHandler;
    }

    public ArrayList<Opportunity> getOpps() {
        return mOpportunities;
    }

    public Opportunity getOpportunity(String name){
        for ( Opportunity o: mOpportunities){
            if ( o.mTitle.equals(name)){
                return o;
            }
        }
        return null;
    }

}
