package visvikis.ioannis.interviewcalculator;

import android.content.Context;
import android.graphics.Bitmap;

public interface ProjectInterfaces
{

    interface TheViewInteface{

        void askForResult(String[] args);

        void setResponse(String response);

    }


    interface ThePresenterInterface{

        void askAPI(String from, String to, String amount);

        void fetchResultFromModel(String[] args);

        Bitmap fetchFlag(Context applicationContext, String flagPath);
    }

}
