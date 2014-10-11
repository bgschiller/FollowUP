package applet.wear.devscrum.followup;

import android.app.ListFragment;
import android.content.Context;

import java.util.ArrayList;

/**
 * Created by DevScrum on 10/10/14.
 */
public class OpportunityHandler {
    private static OpportunityHandler sOpportunityHandler;
    private Context mAppContext;
    private ArrayList<Opportunity> mOpportunities;

    private OpportunityHandler(Context appContext) {
        mAppContext = appContext;
        mOpportunities = new ArrayList<Opportunity>();
        for(int i = 0; i < 100; i ++){
            Opportunity o = new Opportunity();
            o.setTitle("Opportunity #: " + i);
            mOpportunities.add(o);
        }
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
            if ( o.getTitle().equals(name)){
                return o;
            }
        }
        return null;
    }

}
