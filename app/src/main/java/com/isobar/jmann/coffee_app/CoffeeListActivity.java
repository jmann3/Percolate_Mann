package com.isobar.jmann.coffee_app;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.isobar.jmann.coffee_app.models.SpecificCoffee;
import com.isobar.jmann.coffee_app.recyclerview.DividerItemDecoration;
import com.isobar.jmann.coffee_app.recyclerview.RecyclerItemClickListener;
import com.isobar.jmann.coffee_app.singleton.VolleySingleton;
import com.isobar.jmann.coffee_app.widget.FadeInNetworkImageView;

import java.util.ArrayList;
import java.util.List;


public class CoffeeListActivity extends ActionBarActivity {

    private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    private CoffeeAdapter mCoffeeAdapter;

    public static final String DETAIL_DATA = "detail_data";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_coffee_list);

        // format actionbar
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setIcon(R.drawable.drip_white2);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        mRecyclerView = (RecyclerView)findViewById(R.id.recycler_view);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.addItemDecoration(new DividerItemDecoration(CoffeeListActivity.this, DividerItemDecoration.VERTICAL_LIST));

        // use linear layout manager
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        // specify an adapter
        ArrayList<SpecificCoffee> specificCoffees = getIntent().getParcelableArrayListExtra(SplashActivity.ARRAY_KEY);
        mCoffeeAdapter = new CoffeeAdapter(specificCoffees);
        mRecyclerView.setAdapter(mCoffeeAdapter);

        // add click listener for list rows
        mRecyclerView.addOnItemTouchListener(new RecyclerItemClickListener(this, new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {

                // go to Detail View
                transitionToDetail(position);
            }
        }));

    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        MenuItem settingsItem = menu.findItem(R.id.action_settings);
        settingsItem.setVisible(false);
        return false;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_coffee_list, menu);
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

    private void transitionToDetail(int position) {
        Intent intent = new Intent(CoffeeListActivity.this, CoffeeDetailActivity.class);
        intent.putExtra(DETAIL_DATA, mCoffeeAdapter.getItem(position));

        // pass the items details

        startActivity(intent);
        overridePendingTransition(R.anim.pull_in_left, R.anim.push_out_right);
    }

    public class CoffeeAdapter extends RecyclerView.Adapter<CoffeeAdapter.ViewHolder> {

        private List<SpecificCoffee> mCoffees;

        public CoffeeAdapter(List<SpecificCoffee> coffees) {
            mCoffees = coffees;
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
                TextView mTitle;
                TextView mContent;
                FadeInNetworkImageView mImage;
                ImageView mRight_arrow;

            public ViewHolder(View itemView) {
                super(itemView);

                mTitle = (TextView)itemView.findViewById(R.id.title);
                mContent = (TextView)itemView.findViewById(R.id.description);
                mImage = (FadeInNetworkImageView)itemView.findViewById(R.id.coffee_img);
                mRight_arrow = (ImageView)itemView.findViewById(R.id.right_arrow);
            }
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int position) {

            // create a new view
            View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.coffee_row, viewGroup, false);

            return new ViewHolder(v);
        }

        @Override
        public void onBindViewHolder(ViewHolder viewHolder, int position) {
            // use elements from data set to populate row
            SpecificCoffee specificCoffee = mCoffees.get(position);

            viewHolder.mTitle.setText(specificCoffee.getName());
            viewHolder.mContent.setText(specificCoffee.getDesc());

            ImageLoader imageLoader = VolleySingleton.getInstance(CoffeeListActivity.this).getImageLoader();
            viewHolder.mImage.setImageUrl(specificCoffee.getImage_url(), imageLoader);

        }

        @Override
        public int getItemCount() {
            return mCoffees.size();
        }

        public SpecificCoffee getItem(int position) {
            return mCoffees.get(position);
        }
    }
}
