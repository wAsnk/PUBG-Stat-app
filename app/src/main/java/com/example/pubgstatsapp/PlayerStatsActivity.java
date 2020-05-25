package com.example.pubgstatsapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class PlayerStatsActivity extends AppCompatActivity {
    Player player;
    ProgressBar pb;
    ArrayList<LinearLayout> linearLayouts;
    Button getStatsButton;
    FrameLayout frameLayout;
    ArrayList<Player> players;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player_stats);



        pb = (ProgressBar) findViewById(R.id.progressBar);
        linearLayouts = new ArrayList<>();
        linearLayouts.add((LinearLayout) findViewById(R.id.first));
        linearLayouts.add((LinearLayout) findViewById(R.id.second));
        linearLayouts.add((LinearLayout) findViewById(R.id.third));
        getStatsButton = (Button) findViewById(R.id.getStatsButton);
        frameLayout = (FrameLayout) findViewById(R.id.statisticsFrame);

        Intent intent = getIntent();
        String playerName = intent.getStringExtra("playerName");
        if (intent.getParcelableArrayListExtra("playersList") != null)
        {
            players = intent.getParcelableArrayListExtra("playersList");
        }
        // https://api.pubg.com/shards/steam/players?filter[playerNames]=WackyJacky101
        if (playerName != null) {
            new GetPlayerIDAsync().execute(playerName, "https://api.pubg.com/shards/steam/players?filter[playerNames]=");
        }
        else {
            Toast.makeText(this, "No player data", Toast.LENGTH_LONG);
        }


    }

    public void savePlayerStatistics(Player p) throws Exception {
        if (players == null){
            players = new ArrayList<>();
        }
        if (!players.contains(p)){
            players.add(p);
        }
        else {
            throw new Exception("This player was already added.");
        }
    }

    public void ShowSavedPlayers(){
        Intent i = new Intent(this, SavedPlayersActivity.class);
        i.putParcelableArrayListExtra("playersList", players);
        startActivity(i);
        finish();
    }

    public void onSavePlayerButtonClick(View v){
        try {
            savePlayerStatistics(this.player);
        }
        catch (Exception e){
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG);
        }
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

    public void onClick(View view){
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        Bundle bundle = new Bundle();
        bundle.putString("id", player.getId());
        bundle.putParcelable("player", player);
        StatisticsFrag statisticsFrag = new StatisticsFrag();
        statisticsFrag.setArguments(bundle);

        ft.add(R.id.statisticsFrame, statisticsFrag);

        ft.commit();
        findViewById(R.id.getStatsButton).setVisibility(View.GONE);
        findViewById(R.id.SavePlayerButton).setVisibility(View.VISIBLE);
    }

    public class GetPlayerIDAsync extends AsyncTask<String, Integer, String> {
        HttpURLConnection conn;
        BufferedReader reader;
        String json_data;

        @Override
        protected String doInBackground(String... strings) {
            String playerName = strings[0];
            String json_url = strings[1];
            String data = "";
            try {
                data = getJSONData(playerName,json_url);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return data;
        }

        @Override
        protected void onPostExecute(String s) {
            JSONDataParse(s);
            ShowAndHide();
        }

        private void ShowAndHide(){
            ((TextView)findViewById(R.id.pubgName)).setText(player.getName());
            ((TextView)findViewById(R.id.playerID)).setText(player.getId());
            int size = player.getMatches().size();
            ((TextView)findViewById(R.id.numMatches)).setText(Integer.toString(size));

            pb.setVisibility(View.GONE);
            for (LinearLayout l : linearLayouts) {
                l.setVisibility(View.VISIBLE);
            }

            findViewById(R.id.getStatsButton).setVisibility(View.VISIBLE);
            findViewById(R.id.statisticsFrame).setVisibility(View.VISIBLE);

        }

        private String getJSONData(String playerName, String json_url) throws IOException {

            try {
                URL url = new URL(json_url + playerName);
                conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("GET");
                conn.setRequestProperty("Authorization","Bearer " + getResources().getString(R.string.pubg_api_key));
                conn.setRequestProperty("Accept", "application/vnd.api+json");

                InputStream inputStream = conn.getInputStream();
                if (inputStream == null) {
                    return null;
                }
                StringBuffer buffer = new StringBuffer();
                String line = "";

                reader = new BufferedReader(new InputStreamReader(inputStream));

                while ((line = reader.readLine()) != null){
                    buffer.append(line);
                }
                json_data = buffer.toString();

            }
            catch (IOException e){
                e.printStackTrace();
                return null;
            }
            finally {
                if (conn != null){
                    conn.disconnect();
                }
                if (reader != null){

                    try {
                        reader.close();
                    }
                    catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            return json_data;
        }

        private void JSONDataParse(String JSONInput){
           player = new Player();

           try {
               JSONObject jsonObject = new JSONObject(JSONInput);
               JSONArray data = jsonObject.optJSONArray("data");
               for (int i = 0; i< data.length(); i++){
                   JSONObject item = data.getJSONObject(i);
                   player.setId(item.getString("id"));
                   JSONObject attributes = item.getJSONObject("attributes");
                   player.setName(attributes.getString("name"));

                   JSONArray matches = item.getJSONObject("relationships").getJSONObject("matches").optJSONArray("data");
                   for (int j = 0; j < matches.length(); j++){
                       JSONObject match = matches.getJSONObject(j);
                       Match newMatch = new Match(match.getString("type"),match.getString("id"));
                       player.addMatch(newMatch);
                   }
               }
           }
           catch (JSONException e)
           {
               e.printStackTrace();
           }
        }
    }
}
