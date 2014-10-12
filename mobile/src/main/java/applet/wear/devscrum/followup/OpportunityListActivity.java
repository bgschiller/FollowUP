package applet.wear.devscrum.followup;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dborstelmann on 10/11/14.
 */
public class OpportunityListActivity extends Activity {
    ArrayList<Opportunity> mOpportunities;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        /*hax*/
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                .permitAll().build();
        StrictMode.setThreadPolicy(policy);


        setContentView(R.layout.opportunity_list);
        mOpportunities = OpportunityHandler.get(this).getOpps();
        if (mOpportunities == null){
            SharedPreferences sharedPref = this.getSharedPreferences(
                    getString(R.string.preference_file_key), this.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPref.edit();
            editor.putString("SF_ACCESS_TOKEN", "");
            editor.commit();
            startActivity(new Intent(this, LoginActivity.class));
        } else {
            ListView lv = (ListView) findViewById(R.id.listView);
            lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position,
                                        long id) {
                    Opportunity opp = (Opportunity) parent.getItemAtPosition(position);
                    Intent intent = new Intent(getApplicationContext(), OpportunityDetailActivity.class);
                    intent.putExtra("OPP", opp.serialize());
                    startActivity(intent);
                }
            });
            ListArrayAdapter adapter =
                    new ListArrayAdapter(this, R.layout.list_item, mOpportunities);
            lv.setAdapter(adapter);
        }

    }

    private class ListArrayAdapter extends ArrayAdapter<Opportunity> {

        int resource;
        public ListArrayAdapter(Context context, int resource, ArrayList<Opportunity> objects) {
            super(context, resource, objects);
            this.resource = resource;
        }

        public View getView(int position, View convertView, ViewGroup parent) {
            RelativeLayout oppView;
            Opportunity opp = getItem(position);
            Log.d("__________-------------", opp.mTitle);
            if (convertView == null) {
                oppView = new RelativeLayout(getContext());
                String inflater = Context.LAYOUT_INFLATER_SERVICE;
                LayoutInflater vi;
                vi = (LayoutInflater)getContext().getSystemService(inflater);
                vi.inflate(resource, oppView, true);
            }
            else {
                oppView = (RelativeLayout) convertView;
            }

            ImageView image = (ImageView)oppView.findViewById(R.id.icon);
            TextView oppText = (TextView)oppView.findViewById(R.id.firstLine);
            TextView amountText = (TextView)oppView.findViewById(R.id.amount);
            TextView contactText = (TextView)oppView.findViewById(R.id.contact);
            if (opp.imageName == null) {
    //pass
            }else if (opp.imageName.equals("salesforce")) {
                image.setImageDrawable(getResources().getDrawable(R.drawable.salesforce));
            }
            else if (opp.imageName.equals("nike")) {
                image.setImageDrawable(getResources().getDrawable(R.drawable.nike));
            }
            else if (opp.imageName.equals("lego")) {
                image.setImageDrawable(getResources().getDrawable(R.drawable.lego));
            }
            else if (opp.imageName.equals("tesla")) {
                image.setImageDrawable(getResources().getDrawable(R.drawable.tesla));
            }
            else if (opp.imageName.equals("sprint")) {
                image.setImageDrawable(getResources().getDrawable(R.drawable.sprint));
            }
            else if (opp.imageName.equals("aperture")) {
                image.setImageDrawable(getResources().getDrawable(R.drawable.aperture));
            }
            else if (opp.imageName.equals("volkswagen")) {
                image.setImageDrawable(getResources().getDrawable(R.drawable.volkswagen));
            }
            oppText.setText(opp.mTitle);
            amountText.setText(opp.amount);
            contactText.setText(opp.contact);

            return oppView;

        }
    }
    @Override
    public void onBackPressed() {
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.my, menu);
        return true;
    }

    public class No1CurrHttpGet extends AsyncTask {
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
                Log.i("No1Curr, but", EntityUtils.toString(entity, "utf-8"));

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
}
