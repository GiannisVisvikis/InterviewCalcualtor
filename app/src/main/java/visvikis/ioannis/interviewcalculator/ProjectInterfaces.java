package visvikis.ioannis.interviewcalculator;

public interface ProjectInterfaces
{

    interface TheViewInteface{

        void askForResult(String[] args);

        void setResponse(String response);

    }


    interface ThePresenterInterface{

        void fetchResultFromModel(String[] args);

    }

}
