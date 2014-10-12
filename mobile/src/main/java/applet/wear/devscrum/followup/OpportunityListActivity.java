package applet.wear.devscrum.followup;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
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
        ListView lv = (ListView) findViewById(R.id.listView);

        ListArrayAdapter adapter =
                new ListArrayAdapter(this, R.layout.list_item, mOpportunities);

        lv.setAdapter(adapter);

    }

    private class ListArrayAdapter extends ArrayAdapter<Opportunity> {

        int resource;
        public ListArrayAdapter(Context context, int resource, List<Opportunity> objects) {
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
            image.setImageDrawable(getResources().getDrawable(R.drawable.salesforcereal));
            oppText.setText(opp.mTitle);
            amountText.setText(opp.amount);
            contactText.setText(opp.contact);

            return oppView;

        }
    }
}
