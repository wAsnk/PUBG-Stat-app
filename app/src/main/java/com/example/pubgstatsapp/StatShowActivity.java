package com.example.pubgstatsapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import java.util.ArrayList;

public class StatShowActivity extends AppCompatActivity {
    ArrayList<Player> players;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stat_show);

        Intent intent = getIntent();
        if (intent.getParcelableArrayListExtra("playersList") != null)
        {
            players = intent.getParcelableArrayListExtra("playersList");
        }

        Player player = intent.getParcelableExtra("Player");

        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        Bundle bundle = new Bundle();
        bundle.putString("id", player.getId());
        bundle.putParcelable("player", player);
        StatisticsFrag statisticsFrag = new StatisticsFrag();
        statisticsFrag.setArguments(bundle);

        ft.add(R.id.statisticsFrame2, statisticsFrag);
        ft.commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
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
            case R.id.showPlayersMenuItem:
                ShowSavedPlayers();
                return true;
            case R.id.exitMenuItem:
                this.finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void ShowSavedPlayers(){
        Intent i = new Intent(this, SavedPlayersActivity.class);
        i.putParcelableArrayListExtra("playersList", players);
        startActivity(i);
        finish();
    }
}
