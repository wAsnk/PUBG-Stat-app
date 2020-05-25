package com.example.pubgstatsapp;

import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;


/**
 * A simple {@link Fragment} subclass.
 */
public class StatisticsFrag extends Fragment {
    ListView listView;
    String playerID;
    Player player;


    public StatisticsFrag() {
        // Required empty public constructor
    }

    public static void setListViewHeightBasedOnChildren(ListView listView) {
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) {
            // pre-condition
            return;
        }

        int totalHeight = 0;
        for (int i = 0; i < listAdapter.getCount(); i++) {
            View listItem = listAdapter.getView(i, null, listView);
            listItem.measure(0, 0);
            totalHeight += listItem.getMeasuredHeight();
        }

        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        listView.setLayoutParams(params);
        listView.requestLayout();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_statistics, container, false);



    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        Bundle bundle = getArguments();
        if (bundle != null) {
            playerID = bundle.getString("id");
            player = bundle.getParcelable("player");
        }
        String url = "https://api.pubg.com/shards/steam/players/" + playerID + "/seasons/lifetime";
        GetPlayerAllTimeStats get = new GetPlayerAllTimeStats();
        get.execute(url);


    }


    public class GetPlayerAllTimeStats extends AsyncTask<String, Integer, String> {
        HttpURLConnection conn;
        BufferedReader reader;
        String json_data;

        @Override
        protected String doInBackground(String... strings) {
            if (player.getLifeTimeGameModeStats().size() == 0){
                String json_url = strings[0];
                String data = "";
                try {
                    data = getJSONData(json_url);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return data;
            }
            return "No need of JSON";
        }

        @Override
        protected void onPostExecute(String s) {
            if (player.getLifeTimeGameModeStats().size() == 0){
                JSONDataParse(s);
            }
            doListItems();
        }

        public void doListItems(){
            //ListView)  getView().findViewById(R.id.list1);
            int i = 1;
            for (GameModeStats item  : player.getLifeTimeGameModeStats())
            {
                item.toStatList();
                StatisticsAdapter statisticsAdapter = new StatisticsAdapter(getContext(), item.getStats());
                String actualListID = "list" + i;
                int id = getResources().getIdentifier(actualListID,"id", getActivity().getPackageName());
                ListView l = ((ListView) getView().findViewById(id));
                l.setAdapter(statisticsAdapter);
                setListViewHeightBasedOnChildren(l);
                i++;
            }
            getView().findViewById(R.id.nestedScroll).setVisibility(View.VISIBLE);
        }

        private String getJSONData(String json_url) throws IOException {

            try {
                URL url = new URL(json_url);
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

        private GameModeStats loadStats(String name, JSONObject value){
            GameModeStats g = new GameModeStats(name);

            try {
                g.setAssist(value.getInt("assists"));
                g.setBoosts(value.getInt("boosts"));
                g.setdBNOs(value.getInt("dBNOs"));
                g.setDailyKills(value.getInt("dailyKills"));
                g.setDailyWins(value.getInt("dailyWins"));
                g.setDamageDealt(value.getDouble("damageDealt"));
                g.setDays(value.getInt("days"));
                g.setHeadshotKills(value.getInt("headshotKills"));
                g.setHeals(value.getInt("heals"));
                g.setKillPoints(value.getInt("killPoints"));
                g.setKills(value.getInt("kills"));
                g.setLongestKill(value.getDouble("longestKill"));
                g.setLongestTimeSurvivde(value.getDouble("longestTimeSurvived"));
                g.setLosses(value.getInt("losses"));
                g.setMostSurvivalTime(value.getDouble("mostSurvivalTime"));
                g.setWins(value.getInt("wins"));

            } catch (JSONException e) {
                e.printStackTrace();
            }

            return g;
        }

        private void JSONDataParse(String JSONInput){
            try {
                JSONObject jsonObject = new JSONObject(JSONInput);
                JSONObject gameModeStats = jsonObject.getJSONObject("data").getJSONObject("attributes").getJSONObject("gameModeStats");
                JSONArray names = gameModeStats.names();
                for (int i = 0; i < names.length(); i++){
                    String name = names.getString(i);
                    JSONObject value = gameModeStats.getJSONObject(name);
                    player.addStats(loadStats(name,value));
                }

            }
            catch (JSONException e)
            {
                e.printStackTrace();
            }
        }
    }
}
