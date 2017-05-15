package freelancer.testsymphonysolutions.api.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class TeamsResponse {

    @SerializedName("count")
    private long count;

    @SerializedName("teams")
    private ArrayList<Team> teams;

    public long getCount() {
        return count;
    }

    public ArrayList<Team> getTeams() {
        return teams;
    }
}
