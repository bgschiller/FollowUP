package applet.wear.devscrum.followup;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

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
}
