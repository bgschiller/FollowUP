package applet.wear.devscrum.followup;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLDecoder;
import java.util.LinkedHashMap;
import java.util.Map;


public class LoginActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        String reqUrl = "https://login.salesforce.com/services/oauth2/authorize?response_type=token&display=touch";
        String consumerKey = "3MVG9xOCXq4ID1uH.95Vnw29mOTYdinLkKU9e75shLNu.5pQqoOaExNJsipGWBTi8w8qdIAqtNX88iaKgoscR";

        SharedPreferences sharedPref = this.getSharedPreferences(
                getString(R.string.preference_file_key), this.MODE_PRIVATE);
        String access_token = sharedPref.getString("SF_ACCESS_TOKEN","");
        if (access_token.length() != 0){
            startActivity(new Intent(getApplicationContext(), OpportunityListActivity.class));
        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        WebView wv = (WebView) findViewById(R.id.webView);
        wv.setWebViewClient(new WatchForSuccessWebClient());
        wv.getSettings().setJavaScriptEnabled(true);
        wv.loadUrl(reqUrl +
                "&client_id="+consumerKey +
                "&redirect_uri=sfdc://success");

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.login, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        return super.onOptionsItemSelected(item);
    }

    private class WatchForSuccessWebClient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            Log.d("SFDC Login", "Redirect URL:" + url);
            if (url.startsWith("sfdc://success")) {
                Map<String,String> query_params;
                try {
                    query_params = splitQuery(
                            new URL(url.replace("sfdc://success#", "http://google.com?")));
                    Log.d("URL", query_params.toString());

                    Context context = getApplicationContext();
                    SharedPreferences sharedPref = context.getSharedPreferences(
                            getString(R.string.preference_file_key), Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPref.edit();
                    editor.putString("SF_ACCESS_TOKEN",query_params.get("access_token"));
                    editor.putString("SF_REFRESH_TOKEN",query_params.get("refresh_token"));
                    editor.putString("SF_INSTANCE_URL",query_params.get("instance_url"));
                    editor.putString("SF_ID",query_params.get("id"));
                    editor.putString("SF_ISSUED_AT",query_params.get("issued_at"));
                    editor.putString("SF_SIGNATURE",query_params.get("signature"));
                    editor.putString("SF_TOKEN_TYPE",query_params.get("token_type"));
                    editor.commit();

                    Intent send_to_main = new Intent(getApplicationContext(), OpportunityListActivity.class); //also send along the token
                    startActivity(send_to_main);

                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }
                return true;
            }
            return false;
        }
    }
    public static Map<String, String> splitQuery(URL url) throws UnsupportedEncodingException {
        Map<String, String> query_pairs = new LinkedHashMap<String, String>();
        String query = url.getQuery();
        String[] pairs = query.split("&");
        for (String pair : pairs) {
            int idx = pair.indexOf("=");
            query_pairs.put(URLDecoder.decode(pair.substring(0, idx), "UTF-8"), URLDecoder.decode(pair.substring(idx + 1), "UTF-8"));
        }
        return query_pairs;
    }
}

