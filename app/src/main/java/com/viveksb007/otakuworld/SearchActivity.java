package com.viveksb007.otakuworld;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.RelativeLayout;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by viveksb007 on 13/12/16.
 */

public class SearchActivity extends Activity {

    private Context mContext;

    private RelativeLayout mRelativeLayout;
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    String BASE_URL = "https://anilist.co/api/";
    private String ACCESS_TOKEN;
    private String query;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mContext = getApplicationContext();

        ACCESS_TOKEN = new PrefManager(mContext).getAccessToken();

        Bundle bundle = getIntent().getExtras();
        query = bundle.getString("search_query");

        mRelativeLayout = (RelativeLayout) findViewById(R.id.activity_main);
        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);

        makeSearchAPIcall();

    }

    private void makeSearchAPIcall() {

        String APIcallURL = BASE_URL + "anime/search/" + query.replace(" ", "%20") + "?access_token=" + ACCESS_TOKEN;
        Log.d("URL", APIcallURL);
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, APIcallURL, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                ResponseObject[] responseObjects = new ResponseObject[response.length()];

                for (int i = 0; i < response.length(); i++) {
                    try {
                        JSONObject object = response.getJSONObject(i);
                        ResponseObject responseObject = new ResponseObject();

                        responseObject.setId(String.valueOf(object.getInt("id")));
                        responseObject.setCover_uri(Uri.parse(object.getString("image_url_lge")));
                        responseObject.setTitle(object.getString("title_english"));
                        responseObject.setYoutube_id(object.getString("youtube_id"));
                        responseObject.setSysnopsis(object.getString("description"));
                      //  responseObject.setGenre((String[]) object.get("genres"));

                        responseObjects[i] = responseObject;

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                LoadViews(responseObjects);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Snackbar.make(findViewById(R.id.activity_main), "Check internet connection or try again later :P", Snackbar.LENGTH_SHORT).show();
            }
        });

        AppController.getInstance().addToRequestQueue(jsonArrayRequest);
    }

    private void LoadViews(ResponseObject[] responseObjects) {
        mLayoutManager = new GridLayoutManager(mContext, 2);
        mRecyclerView.setLayoutManager(mLayoutManager);

        mAdapter = new AnimeAdapter(mContext, responseObjects);
        mRecyclerView.setAdapter(mAdapter);

    }

}
