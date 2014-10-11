package applet.wear.devscrum.followup;

import java.util.ArrayList;

/**
 * Created by DevScrum on 10/10/14.
 */
public class Opportunity {
    private String mTitle;
    private ArrayList<String> notes;

    public String getTitle() {
        return mTitle;
    }

    public String getNote(int id){
        return notes.get(id);
    }

    public void setTitle(String name) { mTitle = name;}
    public void addNote(String title){
        notes.add(title);
    }
}
