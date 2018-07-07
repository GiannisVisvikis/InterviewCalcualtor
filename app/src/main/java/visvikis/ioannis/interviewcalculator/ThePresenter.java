package visvikis.ioannis.interviewcalculator;

import android.content.Context;
import android.graphics.Bitmap;


public class ThePresenter implements ProjectInterfaces.ThePresenterInterface
{
    private ThePresenter instance;

    private CalculatorView mCalcView;
    private TheModel mTheModel;


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


}
