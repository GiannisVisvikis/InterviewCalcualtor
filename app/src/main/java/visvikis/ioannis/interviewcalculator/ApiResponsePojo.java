package visvikis.ioannis.interviewcalculator;

import com.google.gson.JsonObject;
import com.google.gson.annotations.SerializedName;



public class ApiResponsePojo
{

    private boolean success;

    public ErrorObject getError()
    {
        return error;
    }

    private ErrorObject error;

    @SerializedName("rates")
    private JsonObject results;

    public boolean getCode()
    {
        return success;
    }

    public JsonObject getResults()
    {
        return results;
    }

}
