package visvikis.ioannis.interviewcalculator;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import java.io.IOException;



/*
    Will be used to perform the calculations, the uploads and the API calls
 */
public class TheModel
{
    //will be fed back to the user after the operation is performed

    public String calculate(String[] args) throws IllegalArgumentException
    {
        String result;

        String first = args[0];
        char operation = args[1].charAt(0);
        String second = args[2];

        double a = Double.parseDouble(first);
        double b = Double.parseDouble(second);

        switch (operation) {

            case '+':
                result = Double.toString(a + b);
                break;
            case '-':
                result = Double.toString(a - b);
                break;
            case '*':
                result = Double.toString(a * b);
                break;
            case '/':
                if(b != 0)
                    result = Double.toString(a / b);
                else
                    throw new IllegalArgumentException("Can not divide by zero");
                break;

            default:
                result = "This was not considered";
        }

        return result;
    }



    public Bitmap getTheFlag(Context applicationContext, String flagPath) {

        Bitmap result;

        try{
            //Log.e("FLAG_PATH", flagPath);
            result = BitmapFactory.decodeStream(applicationContext.getAssets().open(flagPath));
        }
        catch (IOException io){
            int resourceCode = (flagPath.contains("48")) ? R.drawable.unknown_48 : R.drawable.unknown_64;
            result = BitmapFactory.decodeResource(applicationContext.getResources(), resourceCode);
        }

        return result;
    }








}
