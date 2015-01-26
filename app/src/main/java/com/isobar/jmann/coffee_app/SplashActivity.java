package com.isobar.jmann.coffee_app;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.isobar.jmann.coffee_app.jackson.JacksonRequest;
import com.isobar.jmann.coffee_app.models.SpecificCoffee;
import com.isobar.jmann.coffee_app.singleton.VolleySingleton;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;


public class SplashActivity extends ActionBarActivity {

    //ArrayList<SpecificCoffee> specificCoffees = new ArrayList<>();

    public static final String url = "https://coffeeapi.percolate.com/api/coffee/";
    public static final String key = "api_key";
    public static final String key_value = "WuVbkuUsCXHPx3hsQzus4SE";

    public static final String ARRAY_KEY = "array_key";

    long startTime;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_splash);

        getSupportActionBar().hide();

        startTime = System.currentTimeMillis();

        // kick off JSON request
        retrieveJSONdata();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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

    private void retrieveJSONdata() {

        HashMap<String, String> headers = new HashMap<>();
        headers.put("Authorization", key_value);

        //JavaType listType = Mapper.get().getTypeFactory().constructCollectionType(ArrayList.class, SpecificCoffee.class);
        JacksonRequest<SpecificCoffee[]> jacksonRequest = new JacksonRequest<SpecificCoffee[]>(Request.Method.GET, url,
                SpecificCoffee[].class, headers, new Response.Listener<SpecificCoffee[]>() {

            @Override
            public void onResponse(SpecificCoffee[] response) {
                final ArrayList<SpecificCoffee> specificCoffees = new ArrayList<SpecificCoffee>(Arrays.asList(response));

                // proceed to List activity

                // first check that Splash screen stays up for minimum 3 seconds
                long timeNow = System.currentTimeMillis();
                if (timeNow - startTime > 3000) {
                    transitionToList(specificCoffees);
                } else {

                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            transitionToList(specificCoffees);
                        }
                    }, (3000 - startTime));
                }

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("SplashActivity", "jackson error" + error.toString());
            }
        });

        VolleySingleton.getInstance(SplashActivity.this).getRequestQueue().add(jacksonRequest);


    }

    private void transitionToList(ArrayList<SpecificCoffee> specificCoffees) {
        Intent intent = new Intent(SplashActivity.this, CoffeeListActivity.class);
        intent.putExtra(ARRAY_KEY, specificCoffees);

        startActivity(intent);
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);

        finish();
    }

    private void resizeAndStoreBitmap(Bitmap bitmap, int imageNum) {

        // compress bitmap by factor of 2
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();

        // create matrix for Manipulation
        Matrix matrix = new Matrix();

        // Resize the bitmap
        matrix.postScale((float)width/2, (float)height/2);

        // create new bitmap
        Bitmap resizedBitmap = Bitmap.createBitmap(bitmap, 0, 0, width, height, matrix, false);

        // store bitmap in cache
        //ScaledImageCache.getInstance().getBitmaps().put(imageNum, resizedBitmap);
    }

}
