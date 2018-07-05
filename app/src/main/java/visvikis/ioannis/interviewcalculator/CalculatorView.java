package visvikis.ioannis.interviewcalculator;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.Toolbar;
import android.view.View;

public class CalculatorView extends AppCompatActivity
{

    private final String FIRST_ARG_TAG = "FIRST_ARG";
    private final String SECOND_ARG_TAG = "SECOND_ARG";
    private final String THIRD_ARG_TAG = "THIRD_ARG";

    //A three position array of strings. Will hold the first number, the operation to be performed and the second number
    private String[] args;

    private AppCompatTextView displayTxtView;

    private AppCompatButton clearAllButton;
    private AppCompatButton clearLastButton;
    private AppCompatButton plusMinusButton;
    private AppCompatButton equalsButton;
    private AppCompatButton convertButton;

    

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculator_view);

        if(savedInstanceState == null){
            args = new String[3];
        }
        else{
            args[0] = savedInstanceState.getString(FIRST_ARG_TAG);
            args[1] = savedInstanceState.getString(SECOND_ARG_TAG);
            args[2] = savedInstanceState.getString(THIRD_ARG_TAG);
        }


        Toolbar theToolbar = findViewById(R.id.theToolbar);
        setSupportActionBar(theToolbar);

        displayTxtView = findViewById(R.id.textview);

        clearAllButton = findViewById(R.id.clear_all);
        clearAllButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) //clear all
            {
                args[0] = null;
                args[1] = null;
                args[2] = null;

                displayTxtView.setText("0");
            }
        });


        clearLastButton = findViewById(R.id.clear_last);
        clearLastButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) //if there is entry, remove it. Else if there is symbol selected, cancel it
            {

                boolean hasEntry = !displayTxtView.getText().toString().equalsIgnoreCase("0");

                if(hasEntry)
                    displayTxtView.setText("0");
                else if(args[1] != null)
                    args[1] = null;  //cancels operation that might have been selected
            }
        });


    }



    @Override
    protected void onSaveInstanceState(Bundle outState)
    {
        super.onSaveInstanceState(outState);

        outState.putString(FIRST_ARG_TAG, args[0]);
        outState.putString(SECOND_ARG_TAG, args[1]);
        outState.putString(THIRD_ARG_TAG, args[2]);

    }


    /**
     * Added as a listener to buttons in layout resource file
     * @param view
     */
    public void addDigit(View view)
    {
        String current = displayTxtView.getText().toString();
        String toAdd = ((AppCompatButton) view).getText().toString();

        current += toAdd;
        displayTxtView.setText(current);
    }


    /**
     * Handles clicks on +,-,* and / buttons, registered in layout xml file
     * @param view
     */
    public void handleSymbol(View view)
    {
        String current = displayTxtView.getText().toString();

        args[0] = current;

        displayTxtView.setText("0");

        args[1] = ((AppCompatButton) view).getText().toString();
    }


}
