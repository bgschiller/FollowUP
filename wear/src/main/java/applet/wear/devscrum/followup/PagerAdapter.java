package applet.wear.devscrum.followup;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.support.wearable.view.CardFragment;
import android.support.wearable.view.FragmentGridPagerAdapter;
import android.view.Gravity;

/**
 * Created by DevScrum on 10/11/14.
 */
public class PagerAdapter extends FragmentGridPagerAdapter {

    private final Context mContext;
    public String mTitle = "";

    public PagerAdapter(Context ctx, FragmentManager fm) {
        super(fm);
        mContext = ctx;
    }

    public PagerAdapter(Context ctx, FragmentManager fm, String title){
        this(ctx, fm);
        mTitle = title;
    }

    public void addObject(String title){
        mTitle = title;
    }

    static final int[] BG_IMAGES = new int[]{
            R.drawable.cloud_background
    };

    // A simple container for static data in each page
    private static class Page {
        int TitleRes;
        int TextRes;
        int IconRes;
        int CardGravity = Gravity.BOTTOM;
        boolean ExpansionEnabled = true;
        float ExpansionFactor = 10;
        int ExpansionDirection = CardFragment.EXPAND_DOWN;

        public Page (int titleRes, int textRes, boolean expansion, int gravity)
        {
            this(titleRes, textRes, 0);
            this.ExpansionEnabled = expansion;
            this.CardGravity = gravity;
        }

        public Page(int titleRes, int textRes, boolean expansion, float expansionFactor, int gravity)
        {
            this(titleRes, textRes, 0);
            this.ExpansionEnabled = expansion;
            this.ExpansionFactor = expansionFactor;
        }

        public Page (int titleRes, int textRes, int iconRes) {
            this.TitleRes = titleRes;
            this.TextRes = textRes;
            this.IconRes = iconRes;
        }

        public Page (int titleRes, int textRes, int iconRes, int gravity)
        {
            this.TitleRes = titleRes;
            this.TextRes = textRes;
            this.IconRes = iconRes;
            this.CardGravity = gravity;
        }
    }


    // Create a static set of pages in a 2D array
    private final Page[][] PAGES = {
            {
            new Page(R.string.opportunity_name, R.string.sample_body, R.drawable.ic_launcher,  Gravity.TOP),
            new Page(R.string.action_phone,  R.string.blank ,R.drawable.action_phone, Gravity.CENTER)
            },
            {
            new Page(R.string.tap_to_speak, R.string.blank, R.drawable.voice_search_sm, Gravity.BOTTOM),
            new Page(R.string.opportunity_name, R.string.sample_response, true, Gravity.BOTTOM),
            new Page(R.string.delete_button, R.string.blank, R.drawable.action_reply, Gravity.CENTER),
            new Page(R.string.keep_button, R.string.blank, R.drawable.action_open, Gravity.CENTER )
            }
    };


    // Obtain the UI fragment at the specified position
    @Override
    public Fragment getFragment(int row, int col) {
        Page page = PAGES[row][col];
        String title = page.TitleRes != 0 ? mContext.getString(page.TitleRes) : "";
        String text = page.TextRes != 0 ? mContext.getString(page.TextRes) : "";

        if (page.CardGravity == Gravity.CENTER) {
            ActionFragment frag = new ActionFragment();
            frag = frag.newInstance(title, text, page.IconRes);
                frag.setCardGravity(page.CardGravity);
                frag.setExpansionEnabled(false);
                return frag;
        }else{

            CardFragment frag;
            if(page.CardGravity == Gravity.CENTER) {
               frag = CardFragment.create(mTitle, text, page.IconRes);
            }else {
               frag = CardFragment.create(title, text, page.IconRes);
            }
                frag.setCardGravity(page.CardGravity);
                frag.setExpansionEnabled(page.ExpansionEnabled);
                frag.setExpansionDirection(page.ExpansionDirection);
                frag.setExpansionFactor(page.ExpansionFactor);
                return frag;
        }
    }

    @Override
    public int getRowCount() {
        return PAGES.length;
    }

    @Override
    public int getColumnCount(int i) {
        return PAGES[i].length;
    }
}
