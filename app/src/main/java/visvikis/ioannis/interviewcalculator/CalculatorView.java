package visvikis.ioannis.interviewcalculator;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatSpinner;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class CalculatorView extends AppCompatActivity implements ProjectInterfaces.TheViewInteface
{

    private final String FIRST_ARG_TAG = "FIRST_ARG";
    private final String SECOND_ARG_TAG = "SECOND_ARG";
    private final String THIRD_ARG_TAG = "THIRD_ARG";
    private final String DISPLAYED_VALUE_TAG = "DISPLAYED_ARG";
    private final String RESULT_IN_TAG = "RESULT_IN";
    private final String FROM_SPINNER_INDEX = "FROM_SPINNER_INDEX";
    private final String TO_SPINNER_INDEX = "TO_SPINNER_INDEX";

    private boolean resultIn = false;

    private ThePresenter mPresenter;

    //A three position array of strings. Will hold the first number, the operation to be performed and the second number
    private String[] args;

    private AppCompatTextView displayTxtView;



    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculator_view);

        displayTxtView = findViewById(R.id.textview);

        //initialize the spinners for conversion

        AppCompatSpinner fromSpinner = findViewById(R.id.from_spinner);
        AppCompatSpinner toSpinner = findViewById(R.id.to_spinner);

        ArrayList<CurrencyInfo> spinnerObjects = initializeSpinnerObjects();

        ConvertOptionsAdapter conversionAdapter = new ConvertOptionsAdapter(getApplicationContext(), R.layout.spinner_row, spinnerObjects);

        fromSpinner.setAdapter(conversionAdapter);
        toSpinner.setAdapter(conversionAdapter);


        //Check prior state
        if(savedInstanceState == null){
            args = new String[3];
            fromSpinner.setSelection(43);
            toSpinner.setSelection(43);
        }
        else{
            args[0] = savedInstanceState.getString(FIRST_ARG_TAG);
            args[1] = savedInstanceState.getString(SECOND_ARG_TAG);
            args[2] = savedInstanceState.getString(THIRD_ARG_TAG);
            displayTxtView.setText(savedInstanceState.getString(DISPLAYED_VALUE_TAG));
            resultIn = savedInstanceState.getBoolean(RESULT_IN_TAG);
            fromSpinner.setSelection(savedInstanceState.getInt(FROM_SPINNER_INDEX));
            toSpinner.setSelection(savedInstanceState.getInt(TO_SPINNER_INDEX));
        }


        Toolbar theToolbar = findViewById(R.id.the_toolbar);
        setSupportActionBar(theToolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(true);

        mPresenter = new ThePresenter(this);

        setDigitButtons();

        setClearButtons();

        setSymbolButtons();

        //set plus or minus button and functionality
        AppCompatButton plusMinusButton = findViewById(R.id.plus_minus);
        plusMinusButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                String txtToConvert = displayTxtView.getText().toString();
                String txtToAdd;

                char first = txtToConvert.charAt(0);

                if(Character.isDigit(first))
                    txtToAdd = '-' + txtToConvert;
                else
                    txtToAdd = txtToConvert.substring(1);

                displayTxtView.setText(txtToAdd);

            }
        });

        //set equals button
        AppCompatButton equalsButton = findViewById(R.id.equals);
        equalsButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {

                if(args[1] != null){
                    String second = displayTxtView.getText().toString();

                    if(second.equalsIgnoreCase("") && args[1] != null){

                        paizei malakia edw otan patas dia diairei me miden

                        if(args[1] == "/" || args[1] == "*")
                            args[2] = "1";
                        else
                            args[2] = "0";
                    }
                    else{
                        args[2] = second;
                    }

                    askForResult(args);
                }
            }
        });



        //Log.e("CLASS", toSpinner.getItemAtPosition(43).getClass().toString()); //Returns CurrencyInfo object
        //TODO Set the convert button asshole!!

    }



    @Override
    protected void onSaveInstanceState(Bundle outState)
    {
        super.onSaveInstanceState(outState);

        outState.putString(FIRST_ARG_TAG, args[0]);
        outState.putString(SECOND_ARG_TAG, args[1]);
        outState.putString(THIRD_ARG_TAG, args[2]);
        outState.putString(DISPLAYED_VALUE_TAG, displayTxtView.getText().toString());
        outState.putBoolean(RESULT_IN_TAG, resultIn);
    }




    private void setSymbolButtons()
    {
        SymbolListener symbolListener = new SymbolListener();

        findViewById(R.id.plus).setOnClickListener(symbolListener);
        findViewById(R.id.minus).setOnClickListener(symbolListener);
        findViewById(R.id.multiplication).setOnClickListener(symbolListener);
        findViewById(R.id.division).setOnClickListener(symbolListener);
    }


    private void setDigitButtons()
    {
        DigitListener digitListener = new DigitListener();

        findViewById(R.id.zero).setOnClickListener(digitListener);
        findViewById(R.id.one).setOnClickListener(digitListener);
        findViewById(R.id.two).setOnClickListener(digitListener);
        findViewById(R.id.three).setOnClickListener(digitListener);
        findViewById(R.id.four).setOnClickListener(digitListener);
        findViewById(R.id.five).setOnClickListener(digitListener);
        findViewById(R.id.six).setOnClickListener(digitListener);
        findViewById(R.id.seven).setOnClickListener(digitListener);
        findViewById(R.id.eight).setOnClickListener(digitListener);
        findViewById(R.id.nine).setOnClickListener(digitListener);
        findViewById(R.id.dot).setOnClickListener(digitListener);
    }



    private void setClearButtons()
    {

        findViewById(R.id.clear_all).setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) //clear all
            {
                args[0] = null;
                args[1] = null;
                args[2] = null;

                displayTxtView.setText("");

                resultIn = false;
            }
        });

        
        //set button and functionality that clears last entry
        findViewById(R.id.clear_last).setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) //if there is entry, remove it. Else if there is symbol selected, cancel it
            {
                if(resultIn){
                    displayTxtView.setText("");
                    resultIn = false;
                }

                boolean hasEntry = !displayTxtView.getText().toString().equalsIgnoreCase("");

                if (hasEntry) //delete last digit from the display
                    displayTxtView.setText(displayTxtView.getText().toString().substring(0, displayTxtView.getText().toString().length() -1));
            }
        });
    }



    @Override
    public void askForResult(String[] args)
    {
        mPresenter.fetchResultFromModel(args);
    }

    @Override
    public void setResponse(String response)
    {
        displayTxtView.setText(response);

        //re initialize for next operation
        args[0] = null;
        args[1] = null;
        args[2] = null;

        resultIn = true;
    }



    private ArrayList<CurrencyInfo> initializeSpinnerObjects() {

        ArrayList<CurrencyInfo> curObjects = new ArrayList<>();

        String[] codes = getResources().getStringArray(R.array.coin_codes_sorted);

        String highOrLowRes = (android.os.Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) ? "48" : "64";

        for(String code : codes){

            CurrencyInfo curInfo = new CurrencyInfo(code, highOrLowRes);
            curObjects.add(curInfo);
        }

        return curObjects;
    }







    //LISTENERS FOR BUTTONS

    private class DigitListener implements View.OnClickListener{


        @Override
        public void onClick(View v)
        {

            if(resultIn)
                displayTxtView.setText("");

            String current = displayTxtView.getText().toString();
            String toAdd = ((AppCompatButton) v).getText().toString();
            if(current.length() + toAdd.length() <= 14)
                current += toAdd;
            displayTxtView.setText(current);

            resultIn = false;
        }
    }



    private class SymbolListener implements View.OnClickListener{


        @Override
        public void onClick(View v)
        {
            String current = displayTxtView.getText().toString();

            args[0] = (current.equalsIgnoreCase("") ? "0" : current);

            args[1] = ((AppCompatButton) v).getText().toString();

            //set it to stand by for the second input
            displayTxtView.setText("");
        }
    }





    //CUSTOM ADAPTER FOR THE SPINNERS

    private class ConvertOptionsAdapter extends ArrayAdapter<CurrencyInfo>{

        private List<CurrencyInfo> rows;

        public ConvertOptionsAdapter(@NonNull Context context, int resource, @NonNull List<CurrencyInfo> objects) {
            super(context, resource, objects);

            this.rows = objects;


        }


        @Override
        public int getCount() {
            return rows.size();
        }


        @Nullable
        @Override
        public CurrencyInfo getItem(int position) {
            return rows.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }


        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

            View result = convertView;

            if(result == null)
                result = getLayoutInflater().inflate(R.layout.spinner_row, parent, false);

            CurrencyInfo info = rows.get(position);

            AppCompatImageView imageView = result.findViewById(R.id.the_flag);
            Bitmap flag =  mPresenter.fetchFlag(getApplicationContext(), info.getFlagPath());
            imageView.setImageBitmap(flag);

            AppCompatTextView codeTxt = result.findViewById(R.id.coin_name);
            codeTxt.setText(info.getCode());

            return result;

        }


        @Override
        public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

            View result = convertView;

            if(result == null)
                result = getLayoutInflater().inflate(R.layout.spinner_row, parent, false);

            CurrencyInfo info = rows.get(position);

            AppCompatImageView imageView = result.findViewById(R.id.the_flag);
            Bitmap flag =  mPresenter.fetchFlag(getApplicationContext(), info.getFlagPath());
            imageView.setImageBitmap(flag);

            AppCompatTextView codeTxt = result.findViewById(R.id.coin_name);
            codeTxt.setText(info.getCode());

            return result;
        }


    }




}

