package applet.wear.devscrum.followup;

import android.app.Fragment;
import android.app.ListFragment;
import android.os.Bundle;

import java.util.ArrayList;

/**
 * Created by DevScrum on 10/10/14.
 */
public class OpportunityListFragment extends ListFragment {

    ArrayList<Opportunity> mOpportunities;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        getActivity().setTitle("Follow Up");
        mOpportunities = OpportunityHandler.get(getActivity()).getOpps();

    }


}
