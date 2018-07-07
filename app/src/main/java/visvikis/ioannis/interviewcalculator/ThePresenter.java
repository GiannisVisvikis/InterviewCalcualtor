package visvikis.ioannis.interviewcalculator;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.ConnectivityManager;
import android.support.v4.net.ConnectivityManagerCompat;
import android.util.Log;

import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class ThePresenter implements ProjectInterfaces.ThePresenterInterface
{

    private CalculatorView mCalcView;
    private TheModel mTheModel;


    @Override
    public void askAPI(final String from, final String to, final String amount)
    {
        if(!hasInternetConnection())
            mCalcView.setResponse(mCalcView.getResources().getString(R.string.no_internet_response));
        else{

            mCalcView.setResponse(mCalcView.getResources().getString(R.string.convert_message));

            Retrofit retrofit2 = new Retrofit.Builder()
                    .baseUrl(APInterface.BASE_URL).addConverterFactory(GsonConverterFactory.create()).build();

            APInterface api = retrofit2.create(APInterface.class);

            String query = APInterface.BASE_URL + "latest?access_key=" + mCalcView.getResources().getString(R.string.api_key) +
                    "&symbols=" + from + "," + to;

            Log.e("QUERY", query);

            Call<ApiResponsePojo> call = api.getRates(query);
            call.enqueue(new Callback<ApiResponsePojo>()
            {
                @Override
                public void onResponse(Call<ApiResponsePojo> call, Response<ApiResponsePojo> response)
                {
                    if(response.isSuccessful()){

                        ApiResponsePojo pojo = response.body();

                        JsonObject codesObject =  pojo.getResults();

                        String fromValueString = pojo.getResults().get(from).getAsString();
                        String toValueString = pojo.getResults().get(to).getAsString();

                        double fromValue = Double.parseDouble(fromValueString);
                        double toValue = Double.parseDouble(toValueString);
                        double amountValue = Double.parseDouble(amount);

                        double result = (toValue/fromValue) * amountValue;
                        String backToUser = String.format( "%.2f", result);

                        mCalcView.setResponse(backToUser);

                    }
                    else {
                        mCalcView.setResponse(mCalcView.getResources().getString(R.string.api_not_responding));
                    }
                }

                @Override
                public void onFailure(Call<ApiResponsePojo> call, Throwable t)
                {
                    Log.e("FAILURE", t.getMessage());
                    mCalcView.setResponse(mCalcView.getResources().getString(R.string.api_not_responding));
                }
            });
        }


    }



    @Override
    public void fetchResultFromModel(String[] args)
    {

        String modelResponse;

        try {
            modelResponse = mTheModel.calculate(args);

        }
        catch (IllegalArgumentException ie){
            modelResponse = ie.getMessage();
        }

        mCalcView.setResponse(modelResponse);
    }


    @Override
    public Bitmap fetchFlag(Context applicationContext, String flagPath) {

        return mTheModel.getTheFlag(applicationContext, flagPath);
    }


    public ThePresenter(CalculatorView calcView){

        mCalcView = calcView;
        mTheModel = new TheModel();

    }



    private boolean hasInternetConnection(){

        ConnectivityManager conMan = (ConnectivityManager) mCalcView.getSystemService(Context.CONNECTIVITY_SERVICE);

        return conMan.getActiveNetworkInfo() != null && conMan.getActiveNetworkInfo().isConnectedOrConnecting();

    }


}
