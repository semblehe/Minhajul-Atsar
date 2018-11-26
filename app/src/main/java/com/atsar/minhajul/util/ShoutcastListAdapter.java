package com.atsar.minhajul.util;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.atsar.minhajul.R;
import com.atsar.minhajul.model.ModelRadio;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ShoutcastListAdapter extends BaseAdapter {

    private Activity activity;

    private List<ModelRadio> shoutcasts;

    public ShoutcastListAdapter(Activity activity, List<ModelRadio> shoutcasts){
        this.activity = activity;
        this.shoutcasts = shoutcasts;
    }

    @Override
    public int getCount() {
        return shoutcasts.size();
    }

    @Override
    public Object getItem(int position) {
        return shoutcasts.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {

        LayoutInflater inflater = activity.getLayoutInflater();

        ViewHolder holder;

        if (view != null) {

            holder = (ViewHolder) view.getTag();

        } else {

            view = inflater.inflate(R.layout.list_item, parent, false);

            holder = new ViewHolder(view);

            view.setTag(holder);

        }

        ModelRadio shoutcast = (ModelRadio) getItem(position);
        if(shoutcast == null){

            return view;

        }

        holder.judul.setText(shoutcast.getJudul());
        holder.pembicara.setText(shoutcast.getPembicara());
        holder.siaran.setText(shoutcast.getSiaran());

        Picasso.with(activity)
                .load("http://alilmu.net/images/"+shoutcast.getGambar())
                .into(holder.gambar);

        if(shoutcast.getSiaran()=="LIVE"){
            holder.siaran.setBackgroundResource(R.drawable.badge_live);
        }else if (shoutcast.getSiaran()=="OFF") {
            holder.siaran.setBackgroundResource(R.drawable.badge_off);
        }else{
            holder.siaran.setBackgroundResource(R.drawable.badge_onair);
        }

        return view;
    }

    static class ViewHolder {

        @BindView(R.id.judul)
        TextView judul;

        @BindView(R.id.pembicara)
        TextView pembicara;

        @BindView(R.id.siaran)
        TextView siaran;
//
        @BindView(R.id.gambar)
        ImageView gambar;

        public ViewHolder(View view) {

            ButterKnife.bind(this, view);

        }
    }
}
