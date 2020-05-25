package com.example.pubgstatsapp;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ArrayList<Player> players;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent i = getIntent();
        if (i.getParcelableArrayListExtra("playersList") != null)
        {
            players = i.getParcelableArrayListExtra("playersList");
        }

        Spinner spinner = (Spinner) findViewById(R.id.spinnerPubgPreset);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,R.array.pubgPresetNames, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        String asd = spinner.getSelectedItem().toString();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        menu.removeItem(R.id.newPlayerMenuItem);
        menu.removeItem(R.id.showPlayersMenuItem);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.exitMenuItem:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void onSend(View view){
        Spinner spinner = (Spinner) findViewById(R.id.spinnerPubgPreset);
        EditText editText = (EditText) findViewById(R.id.customPlayerName);
        if (editText.getText().toString().equals("")){
            Intent intent = new Intent(this, PlayerStatsActivity.class);
            if (players != null) {
                intent.putParcelableArrayListExtra("playersList", players);
            }
            intent.putExtra("playerName", spinner.getSelectedItem().toString());
            startActivity(intent);
            finish();
        }
        else if(!editText.getText().toString().equals("")) {
            Intent intent = new Intent(this, PlayerStatsActivity.class);
            if (players != null) {
                intent.putParcelableArrayListExtra("playersList", players);
            }
            intent.putExtra("playerName", editText.getText().toString());
            startActivity(intent);
            finish();
        }
    }
}
