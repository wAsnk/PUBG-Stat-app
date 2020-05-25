package com.example.pubgstatsapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class PlayersAdapter extends BaseAdapter {

    Context mContext;
    ArrayList<Player> players;
    public PlayersAdapter(Context context, ArrayList<Player> players){
        this.mContext = context;
        this.players = players;
    }

    @Override
    public int getCount() {
        return players.size();
    }

    @Override
    public Object getItem(int position) {
        return players.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null){
            convertView = LayoutInflater.from(mContext).inflate(R.layout.listview_item_player, parent, false);
        }

        Player tempPlayer = (Player) getItem(position);

        TextView nameValue = (TextView) convertView.findViewById(R.id.playerNameData);
        TextView idValue = (TextView) convertView.findViewById(R.id.playerIDData);


        nameValue.setText(tempPlayer.getName());
        idValue.setText(tempPlayer.getId());
        return convertView;
    }
}
