package com.viveksb007.otakuworld;

import android.content.Context;
import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    private Context mContext;

    private RelativeLayout mRelativeLayout;
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        generateAuthToken();

        mContext = getApplicationContext();

        mRelativeLayout = (RelativeLayout) findViewById(R.id.activity_main);
        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);

        mLayoutManager = new GridLayoutManager(mContext, 2);
        mRecyclerView.setLayoutManager(mLayoutManager);


        /*
        mAdapter = new AnimeAdapter(mContext);
        mRecyclerView.setAdapter(mAdapter);
        */

    }

    private void generateAuthToken() {
        String URL = "https://anilist.co/api/auth/access_token?grant_type=client_credentials&client_id=viveksb007-gsxam&client_secret=6BmShBiPcqnEHR2HA21ot3noG";
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, URL, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            String access_token = response.getString("access_token");
                            PrefManager manager = new PrefManager(mContext);
                            manager.setAccessToken(access_token);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Snackbar.make(findViewById(R.id.activity_main), "Check internet connection or try again later :P", Snackbar.LENGTH_SHORT).show();
            }
        });
        AppController.getInstance().addToRequestQueue(jsonObjectRequest);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);

        MenuItem searchAnime = menu.findItem(R.id.search_anime);
        SearchView searchView = null;
        if (searchAnime != null) {
            searchView = (SearchView) MenuItemCompat.getActionView(searchAnime);
        }
        EditText searchPlate = (EditText) searchView.findViewById(android.support.v7.appcompat.R.id.search_src_text);
        searchPlate.setHint("Search Anime");
        View searchPlateView = searchView.findViewById(android.support.v7.appcompat.R.id.search_plate);
        searchPlateView.setBackgroundColor(ContextCompat.getColor(this, android.R.color.transparent));

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Intent searchIntent = new Intent(getApplicationContext(), SearchActivity.class);
                searchIntent.putExtra("search_query", query);
                startActivity(searchIntent);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                // use this method for auto complete search process
                return false;
            }
        });
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.search_anime:

                break;
        }
        return true;
    }
}
