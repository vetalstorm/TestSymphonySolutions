package freelancer.testsymphonysolutions.api.model;

import com.google.gson.annotations.SerializedName;

import java.util.Map;


public class Error {
    @SerializedName("status")
    private int status;

    @SerializedName("errors")
    private Map<String, String> errors;

    public int getStatus() {
        return status;
    }

    public Map<String, String> getErrors() {
        return errors;
    }
}
