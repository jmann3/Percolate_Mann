package com.isobar.jmann.coffee_app;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.JsonObjectRequest;
import com.isobar.jmann.coffee_app.models.SpecificCoffee;
import com.isobar.jmann.coffee_app.singleton.VolleySingleton;
import com.isobar.jmann.coffee_app.widget.FadeInNetworkImageView;

import org.json.JSONException;
import org.json.JSONObject;


public class CoffeeDetailActivity extends ActionBarActivity {

    SpecificCoffee mSpecificCoffee;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        overridePendingTransition(R.anim.pull_in_right, R.anim.push_out_left);
        setContentView(R.layout.activity_coffee_detail);

        // retrieve data for views
        mSpecificCoffee = getIntent().getParcelableExtra(CoffeeListActivity.DETAIL_DATA);

        // populate views
        TextView title = (TextView)findViewById(R.id.title);
        title.setText(mSpecificCoffee.getName());

        final TextView content = (TextView)findViewById(R.id.description);
        final TextView lastUpdate = (TextView)findViewById(R.id.last_update);

        // perform network request for detailed description and date
        StringBuilder detailUrl = new StringBuilder();
        detailUrl.append(SplashActivity.url);
        detailUrl.append(mSpecificCoffee.getId());
        detailUrl.append("/");
        detailUrl.append("?");
        detailUrl.append(SplashActivity.key);
        detailUrl.append("=");
        detailUrl.append(SplashActivity.key_value);

        // retrieve the full description
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, detailUrl.toString(), null, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                try {
                    content.setText(response.getString("desc"));
                    lastUpdate.setText(response.getString("last_updated_at"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        VolleySingleton.getInstance(CoffeeDetailActivity.this).getRequestQueue().add(jsonObjectRequest);

        FadeInNetworkImageView image = (FadeInNetworkImageView)findViewById(R.id.large_image);
        ImageLoader imageLoader = VolleySingleton.getInstance(CoffeeDetailActivity.this).getImageLoader();
        image.setImageUrl(mSpecificCoffee.getImage_url(), imageLoader);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_coffee_detail, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        overridePendingTransition(R.anim.pull_in_left, R.anim.push_out_right);
    }
}
