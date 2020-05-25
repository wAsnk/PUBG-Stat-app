package com.example.pubgstatsapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class SavedPlayersActivity extends AppCompatActivity {
    ArrayList<Player> players;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_saved_players);

        Intent i = getIntent();
        if (i.getParcelableArrayListExtra("playersList") != null)
        {
            players = i.getParcelableArrayListExtra("playersList");
        }

        ListView l = (ListView) findViewById(R.id.savedPlayersList);
        PlayersAdapter arrayAdapter = new PlayersAdapter(this, this.players);
        l.setAdapter(arrayAdapter);
        l.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Player selectedItem = (Player) parent.getItemAtPosition(position);
                Intent intent = new Intent(getApplicationContext(), StatShowActivity.class);
                intent.putExtra("Player", selectedItem);
                if (players != null) {
                    intent.putParcelableArrayListExtra("playersList", players);
                }
                startActivity(intent);
                finish();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        menu.removeItem(R.id.showPlayersMenuItem);

        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.newPlayerMenuItem:
                Intent mainIntent = new Intent(this, MainActivity.class);
                if (players != null) {
                    mainIntent.putParcelableArrayListExtra("playersList", players);
                }
                startActivity(mainIntent);
                this.finish();
                return true;
            case R.id.exitMenuItem:
                this.finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
