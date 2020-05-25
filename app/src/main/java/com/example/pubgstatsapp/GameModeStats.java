package com.example.pubgstatsapp;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import java.util.ArrayList;

public class GameModeStats implements Parcelable {
    protected GameModeStats(Parcel in) {
        modeName = in.readString();
        assist = in.readInt();
        boosts = in.readInt();
        dBNOs = in.readInt();
        dailyKills = in.readInt();
        dailyWins = in.readInt();
        days = in.readInt();
        headshotKills = in.readInt();
        heals = in.readInt();
        killPoints = in.readInt();
        kills = in.readInt();
        losses = in.readInt();
        wins = in.readInt();
        damageDealt = in.readDouble();
        longestKill = in.readDouble();
        longestTimeSurvivde = in.readDouble();
        mostSurvivalTime = in.readDouble();
    }

    public static final Creator<GameModeStats> CREATOR = new Creator<GameModeStats>() {
        @Override
        public GameModeStats createFromParcel(Parcel in) {
            return new GameModeStats(in);
        }

        @Override
        public GameModeStats[] newArray(int size) {
            return new GameModeStats[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(modeName);
        dest.writeInt(assist);
        dest.writeInt(boosts);
        dest.writeInt(dBNOs);
        dest.writeInt(dailyKills);
        dest.writeInt(dailyWins);
        dest.writeInt(days);
        dest.writeInt(headshotKills);
        dest.writeInt(heals);
        dest.writeInt(killPoints);
        dest.writeInt(kills);
        dest.writeInt(losses);
        dest.writeInt(wins);
        dest.writeDouble(damageDealt);
        dest.writeDouble(longestKill);
        dest.writeDouble(longestTimeSurvivde);
        dest.writeDouble(mostSurvivalTime);
    }

    public class Stats {
        private String name;
        private String value;

        public Stats(String name, String value) {
            this.name = name;
            this.value = value;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }
    }
    private String modeName;

    private int assist, boosts, dBNOs, dailyKills, dailyWins, days, headshotKills, heals, killPoints, kills, losses, wins;
    private double damageDealt, longestKill, longestTimeSurvivde, mostSurvivalTime;

    ArrayList<Stats> stats;

    public GameModeStats(String modeName) {
        this.modeName = modeName;
    }


    public void toStatList(){
        stats = new ArrayList<>();
        stats.add(new Stats("Kills", Integer.toString(this.kills)));
        stats.add(new Stats("Assist", Integer.toString(this.assist)));
        stats.add(new Stats("Losses", Integer.toString(this.losses)));
        stats.add(new Stats("Wins", Integer.toString(this.wins)));
        stats.add(new Stats("Headshot Kills", Integer.toString(this.headshotKills)));
        stats.add(new Stats("Boosts", Integer.toString(this.boosts)));
        stats.add(new Stats("dBNOs", Integer.toString(this.dBNOs)));
        stats.add(new Stats("Daily Kills", Integer.toString(this.dailyKills)));
        stats.add(new Stats("Daily Wins", Integer.toString(this.dailyWins)));
        stats.add(new Stats("Days", Integer.toString(this.days)));
        stats.add(new Stats("Heals", Integer.toString(this.heals)));
        stats.add(new Stats("Kill Points", Integer.toString(this.killPoints)));
        stats.add(new Stats("DamageDealt", Double.toString(this.damageDealt)));
        stats.add(new Stats("Longest Kill", Double.toString(this.longestKill)));
        stats.add(new Stats("Longest Time Survived", Double.toString(this.longestTimeSurvivde)));
        stats.add(new Stats("Most Survival Time", Double.toString(this.mostSurvivalTime)));
    }

    @NonNull
    @Override
    public String toString() {

        return "Wins: " + wins + ", Losses: " + losses + ", Kills: " + kills + ", Assists: " + assist;
    }

    public ArrayList<Stats> getStats() {
        return stats;
    }

    public void setStats(ArrayList<Stats> stats) {
        this.stats = stats;
    }

    public int getWins() {
        return wins;
    }

    public void setWins(int wins) {
        this.wins = wins;
    }

    public String getModeName() {
        return modeName;
    }

    public void setModeName(String modeName) {
        this.modeName = modeName;
    }

    public int getAssist() {
        return assist;
    }

    public void setAssist(int assist) {
        this.assist = assist;
    }

    public int getBoosts() {
        return boosts;
    }

    public void setBoosts(int boosts) {
        this.boosts = boosts;
    }

    public int getdBNOs() {
        return dBNOs;
    }

    public void setdBNOs(int dBNOs) {
        this.dBNOs = dBNOs;
    }

    public int getDailyKills() {
        return dailyKills;
    }

    public void setDailyKills(int dailyKills) {
        this.dailyKills = dailyKills;
    }

    public int getDailyWins() {
        return dailyWins;
    }

    public void setDailyWins(int dailyWins) {
        this.dailyWins = dailyWins;
    }

    public int getDays() {
        return days;
    }

    public void setDays(int days) {
        this.days = days;
    }

    public int getHeadshotKills() {
        return headshotKills;
    }

    public void setHeadshotKills(int headshotKills) {
        this.headshotKills = headshotKills;
    }

    public int getHeals() {
        return heals;
    }

    public void setHeals(int heals) {
        this.heals = heals;
    }

    public int getKillPoints() {
        return killPoints;
    }

    public void setKillPoints(int killPoints) {
        this.killPoints = killPoints;
    }

    public int getKills() {
        return kills;
    }

    public void setKills(int kills) {
        this.kills = kills;
    }

    public int getLosses() {
        return losses;
    }

    public void setLosses(int losses) {
        this.losses = losses;
    }

    public double getDamageDealt() {
        return damageDealt;
    }

    public void setDamageDealt(double damageDealt) {
        this.damageDealt = damageDealt;
    }

    public double getLongestKill() {
        return longestKill;
    }

    public void setLongestKill(double longestKill) {
        this.longestKill = longestKill;
    }

    public double getLongestTimeSurvivde() {
        return longestTimeSurvivde;
    }

    public void setLongestTimeSurvivde(double longestTimeSurvivde) {
        this.longestTimeSurvivde = longestTimeSurvivde;
    }

    public double getMostSurvivalTime() {
        return mostSurvivalTime;
    }

    public void setMostSurvivalTime(double mostSurvivalTime) {
        this.mostSurvivalTime = mostSurvivalTime;
    }
}
