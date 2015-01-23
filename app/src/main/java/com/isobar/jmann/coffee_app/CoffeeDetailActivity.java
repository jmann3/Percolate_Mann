package com.isobar.jmann.coffee_app;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.isobar.jmann.coffee_app.models.SpecificCoffee;
import com.isobar.jmann.coffee_app.singleton.VolleySingleton;
import com.isobar.jmann.coffee_app.widget.FadeInNetworkImageView;


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

        TextView content = (TextView)findViewById(R.id.description);
        content.setText(mSpecificCoffee.getDesc());

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
