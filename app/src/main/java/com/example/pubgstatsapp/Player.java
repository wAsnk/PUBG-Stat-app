package com.example.pubgstatsapp;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.HashMap;

public class Player implements Parcelable {
    private String name;
    private String id;
    private ArrayList<Match> matches;
    private ArrayList<GameModeStats> lifeTimeGameModeStats;

    public Player(String name, String id, ArrayList<Match> matches) {
        this.name = name;
        this.id = id;
        this.matches = matches;
        lifeTimeGameModeStats = new ArrayList<>();
    }

    public Player() {
        matches = new ArrayList<>();
        lifeTimeGameModeStats = new ArrayList<>();
    }


    protected Player(Parcel in) {
        name = in.readString();
        id = in.readString();
        matches = in.createTypedArrayList(Match.CREATOR);
        lifeTimeGameModeStats = in.createTypedArrayList(GameModeStats.CREATOR);
    }

    public static final Creator<Player> CREATOR = new Creator<Player>() {
        @Override
        public Player createFromParcel(Parcel in) {
            return new Player(in);
        }

        @Override
        public Player[] newArray(int size) {
            return new Player[size];
        }
    };

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public ArrayList<Match> getMatches() {
        return matches;
    }

    public void setMatches(ArrayList<Match> matches) {
        this.matches = matches;
    }

    public ArrayList<GameModeStats> getLifeTimeGameModeStats() {
        return lifeTimeGameModeStats;
    }

    public void setLifeTimeGameModeStats(ArrayList<GameModeStats> lifeTimeGameModeStats) {
        this.lifeTimeGameModeStats = lifeTimeGameModeStats;
    }

    public void addMatch(Match m){
        if (!this.matches.contains(m))
        {
            this.matches.add(m);
        }
        else {
            throw new IllegalArgumentException("The match is already in the list.");
        }
    }

    public void delMatch(Match m){
        if (this.matches.contains(m)){
            this.matches.remove(m);
        }
        else {
            throw new IllegalArgumentException("There is no such match in the list.");
        }
    }

    public void addStats(GameModeStats g){
        if (this.lifeTimeGameModeStats != null){
            if (!this.lifeTimeGameModeStats.contains(g))
            {
                this.lifeTimeGameModeStats.add(g);
            }
            else {
                throw new IllegalArgumentException("The stats are already added.");
            }
        }
    }

    public void delStats(GameModeStats g){
        if (this.lifeTimeGameModeStats.contains(g)){
            this.lifeTimeGameModeStats.remove(g);
        }
        else {
            throw new IllegalArgumentException("There is no such stats in the list.");
        }
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(id);
        dest.writeTypedList(matches);
        dest.writeTypedList(lifeTimeGameModeStats);
    }
}
