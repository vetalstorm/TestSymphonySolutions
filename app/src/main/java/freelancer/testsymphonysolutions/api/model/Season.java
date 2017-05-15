package freelancer.testsymphonysolutions.api.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Season implements Serializable {

    @SerializedName("id")
    private long id;

    @SerializedName("caption")
    private String caption;

    @SerializedName("league")
    private String league;

    @SerializedName("year")
    private String year;

    @SerializedName("currentMatchday")
    private long currentMatchday;

    @SerializedName("numberOfMatchdays")
    private long numberOfMatchdays;

    @SerializedName("numberOfTeams")
    private long numberOfTeams;

    @SerializedName("numberOfGames")
    private long numberOfGames;

    @SerializedName("lastUpdated")
    private String lastUpdated;

    public long getId() {
        return id;
    }

    public String getCaption() {
        return caption;
    }

    public String getLeague() {
        return league;
    }

    public long getCurrentMatchday() {
        return currentMatchday;
    }

    public long getNumberOfMatchdays() {
        return numberOfMatchdays;
    }

    public long getNumberOfTeams() {
        return numberOfTeams;
    }

    public long getNumberOfGames() {
        return numberOfGames;
    }

    public String getLastUpdated() {
        return lastUpdated;
    }

    public String getYear() {
        return year;
    }
}
