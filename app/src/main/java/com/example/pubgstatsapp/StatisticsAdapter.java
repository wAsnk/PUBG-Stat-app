package com.example.pubgstatsapp;

import android.content.Context;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class StatisticsAdapter extends BaseAdapter {

    Context mContext;
    ArrayList<GameModeStats.Stats> stats;
    public StatisticsAdapter(Context context, ArrayList<GameModeStats.Stats> stats){
        this.mContext = context;
        this.stats = stats;
    }

    @Override
    public int getCount() {
        return stats.size();
    }

    @Override
    public Object getItem(int position) {
        return stats.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null){
            convertView = LayoutInflater.from(mContext).inflate(R.layout.listview_item, parent, false);
        }

        GameModeStats.Stats tempStats = (GameModeStats.Stats) getItem(position);

        TextView name = (TextView) convertView.findViewById(R.id.Name);
        TextView value = (TextView) convertView.findViewById(R.id.Data);

        name.setText(tempStats.getName());
        value.setText(tempStats.getValue());
        return convertView;
    }
}
