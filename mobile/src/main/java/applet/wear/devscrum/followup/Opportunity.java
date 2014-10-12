package applet.wear.devscrum.followup;

import java.util.ArrayList;

/**
 * Created by DevScrum on 10/10/14.
 */
public class Opportunity {
    public String mTitle;
    public ArrayList<String> notes;
    public String imageName;
    public String amount;

    public String getNote(int id){
        return notes.get(id);
    }

    public void addNote(String title){
        notes.add(title);
    }
}
