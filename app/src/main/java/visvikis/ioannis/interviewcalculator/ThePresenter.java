package visvikis.ioannis.interviewcalculator;

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


    public ThePresenter(CalculatorView calcView){

        mCalcView = calcView;
        mTheModel = new TheModel();

    }


}
