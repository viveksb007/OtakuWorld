package com.viveksb007.otakuworld;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.widget.TextView;

import com.flaviofaria.kenburnsview.KenBurnsView;
import com.squareup.picasso.Picasso;

/**
 * Created by viveksb007 on 1/1/17.
 */

public class DetailActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail_activity);
        Bundle bundle = getIntent().getExtras();

        KenBurnsView kbv = (KenBurnsView) findViewById(R.id.cover_img);
        TextView title = (TextView) findViewById(R.id.tv_title);
        TextView synopsis = (TextView) findViewById(R.id.tv_synopsis);
        Picasso.with(this).load(Uri.parse(bundle.getString("cover_uri"))).into(kbv);
        title.setText(bundle.getString("title"));
        synopsis.setText(bundle.getString("synopsis"));
    }
}
