package visvikis.ioannis.interviewcalculator;

import com.google.gson.JsonObject;
import com.google.gson.annotations.SerializedName;



public class ApiResponsePojo
{

    @SerializedName("success")
    private String code;

    @SerializedName("rates")
    private JsonObject results;

    public String getCode()
    {
        return code;
    }

    public JsonObject getResults()
    {
        return results;
    }

}
