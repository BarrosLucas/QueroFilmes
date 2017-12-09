package com.example.root.querofilmes.view;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;


import com.example.root.querofilmes.R;

import java.util.List;


public class MenuItemAdapter extends BaseAdapter{
    private final Context context;
    private LayoutInflater inflater;
    private final List<NavDrawerMenuItem> options;

    public MenuItemAdapter(Context context, List<NavDrawerMenuItem> options){
        this.context = context;
        this.options = options;
        this.inflater = (LayoutInflater.from(context));
    }

    @Override
    public int getCount() {
        return options != null? options.size():0;
    }

    @Override
    public Object getItem(int position) {
        return options != null? options.get(position):null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if(convertView == null){
            viewHolder = new ViewHolder();
            convertView = inflater.inflate(R.layout.activity_menu_item_adapter,parent,false);
            convertView.setTag(viewHolder);
            viewHolder.textView = (TextView) convertView.findViewById(R.id.title_option);
            viewHolder.imageView = (ImageView) convertView.findViewById(R.id.icon_option);
        }else{
            viewHolder = (ViewHolder) convertView.getTag();
        }
        NavDrawerMenuItem item = options.get(position);
        viewHolder.textView.setText(item.title);
        viewHolder.imageView.setImageResource(item.imageView);
        if(item.selected){
            convertView.setBackgroundColor(Color.GRAY);
            viewHolder.textView.setTextColor(Color.BLACK);
        }

        return convertView;
    }

    public void setSelected(int position, boolean selected){
        clearSelected();
        options.get(position).selected = selected;
        notifyDataSetChanged();
    }

    public void clearSelected(){
        if(options != null){
            for(NavDrawerMenuItem item: options){
                item.selected = false;
            }
            notifyDataSetChanged();
        }
    }

    static class ViewHolder{
        TextView textView;
        ImageView imageView;
    }
}
