package com.isobar.jmann.coffee_app;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.fasterxml.jackson.databind.AnnotationIntrospector;
import com.fasterxml.jackson.databind.JavaType;
import com.isobar.jmann.coffee_app.jackson.JacksonRequest;
import com.isobar.jmann.coffee_app.models.CoffeeList;
import com.isobar.jmann.coffee_app.models.SpecificCoffee;
import com.isobar.jmann.coffee_app.singleton.VolleySingleton;

import java.util.ArrayList;
import java.util.Arrays;


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

        //JavaType listType = Mapper.get().getTypeFactory().constructCollectionType(ArrayList.class, SpecificCoffee.class);
        JacksonRequest<SpecificCoffee[]> jacksonRequest = new JacksonRequest<SpecificCoffee[]>(Request.Method.GET, "https://coffeeapi.percolate.com/api/coffee/?api_key=WuVbkuUsCXHPx3hsQzus4SE",
                SpecificCoffee[].class, null, new Response.Listener<SpecificCoffee[]>() {

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
//
//
//        Map<String, String> params = new HashMap<>();
//        params.put(key, key_value);
//
//        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest("https://coffeeapi.percolate.com/api/coffee/?api_key=WuVbkuUsCXHPx3hsQzus4SE", new Response.Listener<JSONArray>() {
//
//            @Override
//            public void onResponse(JSONArray response) {
//                //Log.i("SplashActivity", "response is " + response);
//
//                String desc;
//                String image_url;
//                String id;
//                String name;
//
//                for (int i = 0; i < response.length(); i++) {
//
//                    try {
//                        desc = (String)((JSONObject)response.get(i)).get("desc");
//
//                        image_url = (String)((JSONObject)response.get(i)).get("image_url");
//                        id = (String)((JSONObject)response.get(i)).get("id");
//                        name = (String)((JSONObject)response.get(i)).get("name");
//                        specificCoffees.add(new SpecificCoffee(desc, image_url, id, name));
//                    } catch (JSONException e) {
//                        e.printStackTrace();
//                    }
//
//                    // proceed to List activity
//
//                    // first check that Splash screen stays up for minimum 3 seconds
//                    long timeNow = System.currentTimeMillis();
//                    if (timeNow - startTime > 3000) {
//                        transitionToList();
//                    } else {
//
//                        Handler handler = new Handler();
//                        handler.postDelayed(new Runnable() {
//                            @Override
//                            public void run() {
//                                transitionToList();
//                            }
//                        }, (3000 - startTime));
//                    }
//
//
//
//                }
//
//
//            }
//        }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                Log.i("SplashActivity", "error is " + error);
//            }
//        });
//
//        VolleySingleton.getInstance(this).getRequestQueue().add(jsonArrayRequest);

    }

    private void transitionToList(ArrayList<SpecificCoffee> specificCoffees) {
        Intent intent = new Intent(SplashActivity.this, CoffeeListActivity.class);
        intent.putExtra(ARRAY_KEY, specificCoffees);

        startActivity(intent);
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);

        finish();
    }

    private void downloadImage(String url) {
        VolleySingleton.getInstance(SplashActivity.this).getImageLoader().get(url, new ImageLoader.ImageListener() {
            @Override
            public void onResponse(ImageLoader.ImageContainer response, boolean isImmediate) {
                Bitmap bitmap = response.getBitmap();
            }

            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
    }
}
