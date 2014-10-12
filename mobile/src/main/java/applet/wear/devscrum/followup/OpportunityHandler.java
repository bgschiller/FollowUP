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
        Opportunity salesforce = new Opportunity();
        salesforce.mTitle = "Salesforce";
        salesforce.imageName = "salesforce";
        salesforce.amount = "$1.6M";
        mOpportunities.add(salesforce);
        Opportunity samsung = new Opportunity();
        samsung.mTitle = "Samsung";
        samsung.imageName = "samsung";
        samsung.amount = "$3.4M";
        mOpportunities.add(samsung);
        Opportunity bitbucket = new Opportunity();
        bitbucket.mTitle = "Bitbucket";
        bitbucket.imageName = "bitbucket";
        bitbucket.amount = "$2.7M";
        mOpportunities.add(bitbucket);
        Opportunity microsoft = new Opportunity();
        microsoft.mTitle = "Microsoft";
        microsoft.imageName = "microsoft";
        microsoft.amount = "$1.9M";
        mOpportunities.add(microsoft);
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
