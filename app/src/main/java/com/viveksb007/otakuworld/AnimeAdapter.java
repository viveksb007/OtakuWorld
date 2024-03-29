package com.viveksb007.otakuworld;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;


public class AnimeAdapter extends RecyclerView.Adapter<AnimeAdapter.ViewHolder> {

    private Context mContext;
    private ResponseObject[] responseObjects;
    private int size;


    public AnimeAdapter(Context context, ResponseObject[] responseObjects) {
        this.mContext = context;
        this.size = responseObjects.length;
        this.responseObjects = responseObjects;
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView img;
        public TextView textView;

        public ViewHolder(View v) {
            super(v);
            img = (ImageView) v.findViewById(R.id.img_view);
            textView = (TextView) v.findViewById(R.id.card_text_view);
        }
    }

    @Override
    public AnimeAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.card_layout, parent, false);
        final ViewHolder vh = new ViewHolder(v);
        v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ResponseObject reqObject = responseObjects[vh.getAdapterPosition()];
                Intent detailIntent = new Intent(mContext, DetailActivity.class);

                Bundle bundle = new Bundle();
                bundle.putString("title", reqObject.getTitle());
                bundle.putString("synopsis", reqObject.getSysnopsis());
                bundle.putString("cover_uri", String.valueOf(reqObject.getCover_uri()));

                detailIntent.putExtras(bundle);
                detailIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                mContext.startActivity(detailIntent);
            }
        });

        return vh;
    }

    @Override
    public void onBindViewHolder(AnimeAdapter.ViewHolder holder, int position) {
        Picasso.with(mContext).load(responseObjects[position].getCover_uri()).into(holder.img);
        holder.textView.setText(responseObjects[position].getTitle());
    }

    @Override
    public int getItemCount() {
        return size;
    }
}
