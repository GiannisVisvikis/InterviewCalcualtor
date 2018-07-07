package visvikis.ioannis.interviewcalculator;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Url;

public interface APInterface {

    public static final String BASE_URL = "http://data.fixer.io/api/";


    @GET
    Call<ApiResponsePojo> getRates(@Url String url);


}
