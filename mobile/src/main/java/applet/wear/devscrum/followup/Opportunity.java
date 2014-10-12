package applet.wear.devscrum.followup;

import java.util.ArrayList;

/**
 * Created by DevScrum on 10/10/14.
 */
public class Opportunity {
    public String oId;
    public String mTitle;
    public String notes;
    public String imageName;
    public String amount; // eg, "$7.4M"
    public String contact;

    public String serialize(){
        return oId +'\n'+
               mTitle + '\n'+
                notes + '\n'+
                imageName + '\n'+
                amount + '\n'+
                contact;
    }
    public static Opportunity fromString(String ser){
        String[] stray = ser.split("\n");
        Opportunity opp = new Opportunity();
        opp.oId = stray[0];
        opp.mTitle = stray[1];
        opp.notes = stray[2];
        opp.imageName = stray[3];
        opp.amount = stray[4];
        opp.contact = stray[5];
        return opp;
    }
}
