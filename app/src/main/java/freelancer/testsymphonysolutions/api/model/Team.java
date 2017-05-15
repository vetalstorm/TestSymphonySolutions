package freelancer.testsymphonysolutions.api.model;

import com.google.gson.annotations.SerializedName;

public class Team {

    @SerializedName("id")
    private long id;

    @SerializedName("name")
    private String name;

    @SerializedName("shortName")
    private String shortName;

    @SerializedName("squadMarketValue")
    private String squadMarketValue;

    @SerializedName("crestUrl")
    private String crestUrl;

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getShortName() {
        return shortName;
    }

    public String getSquadMarketValue() {
        return squadMarketValue;
    }

    public String getCrestUrl() {
        return crestUrl;
    }
}
