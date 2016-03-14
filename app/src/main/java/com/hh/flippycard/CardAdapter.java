package com.hh.flippycard;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

/**
 * Created by dev on 02/04/2015.
 */
public class CardAdapter extends BaseAdapter {
    private Integer[] list_result;
    private LayoutInflater inflater;
    private int resource;
    public callbackEvent listener;
    private Context ctx;
    public interface callbackEvent{
        public void onItemSelected(int img);
    }

    public CardAdapter(Context context, int resource, Integer[] objects) {

        this.ctx= context;
        this.inflater = LayoutInflater.from(context);
        this.list_result = objects;
        this.resource = resource;

    }

    @Override
    public int getCount() {
        return list_result.length;
    }

    @Override
    public Integer getItem(int i) {
        return list_result[i];
    }

    public void setListener(callbackEvent listener) {
        this.listener = listener;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        View v = convertView;
        if (v == null) {
            v = inflater.inflate(resource, null);
            holder = new ViewHolder();
            holder.ivCard = (ImageView) v.findViewById(R.id.iv_card);

            v.setTag(holder);
        } else {
            holder = (ViewHolder) v.getTag();
            DecodeTask dt1 = (DecodeTask)holder.ivCard.getTag(R.id.imageView1);
            if(dt1 != null)
                dt1.cancel(true);

        }

        final Integer result = getItem(position);
        holder.ivCard.setImageBitmap(null);
        DecodeTask dt2 = new DecodeTask(ctx,holder.ivCard,result);
        dt2.execute();
        holder.ivCard.setTag(R.id.iv_card, dt2);

            v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onItemSelected(result);
            }
        });
        return v;
    }

    public static class ViewHolder {
        public ImageView ivCard;

    }

}
