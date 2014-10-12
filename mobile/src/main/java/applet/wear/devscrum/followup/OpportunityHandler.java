package applet.wear.devscrum.followup;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;

import com.google.api.client.json.Json;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Array;

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
        String soql_query = URLEncoder.encode("select Id, Name, Amount, latest_wrap_up__c, " +
                    "latest_wrap_up__r.Follow_Up_Items__c, Image_Name__c, " +
                    "( SELECT Contact.Name FROM OpportunityContactRoles where IsPrimary = true limit 1) " +
                    "from Opportunity " +
                    "where latest_wrap_up__c != null or show_in_demo__c = 1");


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

        try {
            if (result.charAt(0) == '['){
                //error
                Log.d("OpportunityHandler","Session expired, going to login screen");
                return null;
            } else {
                JSONObject res = new JSONObject(result);
                return process_json(res);
            }
        } catch (JSONException e){
            e.printStackTrace();
            return null;
        }
    }

    private ArrayList<Opportunity> process_json(JSONObject tlo) throws JSONException {
        ArrayList<Opportunity> retval = new ArrayList<Opportunity>(tlo.getInt("totalSize"));
        JSONArray records = tlo.getJSONArray("records");
        for (int ix=0; ix < records.length(); ix++){
            JSONObject rec = records.getJSONObject(ix);
            Opportunity opp = new Opportunity();
            opp.mTitle = rec.getString("Name");
            opp.oId = rec.getString("Id");
            opp.amount = "$" + String.valueOf(rec.getInt("Amount") / 1000) + "K" ;
            if ( ! rec.has("Follow_Up_Items__c")){
                opp.notes = null;
            } else {
                opp.notes = rec.getString("Follow_Up_Items__c");
            }
            if( rec.isNull("OpportunityContactRoles")){
                opp.contact = "";
            } else {
                opp.contact = rec.getJSONObject("OpportunityContactRoles")
                        .getJSONArray("records")
                        .getJSONObject(0)
                        .getJSONObject("Contact")
                        .getString("Name");
            }
            if ( rec.has("Image_Name__c")){
                opp.imageName = rec.getString("Image_Name__c");
            } else {
                opp.imageName = null;
            }
            retval.add(opp);
        }
        return retval;

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
