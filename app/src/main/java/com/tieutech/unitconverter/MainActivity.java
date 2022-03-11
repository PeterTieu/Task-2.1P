package com.tieutech.unitconverter;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class MainActivity extends AppCompatActivity {

    //Declare variables for Views
    Spinner unitSpinner;
    EditText valueEditText;
    TextView firstConversionTextView;
    TextView secondConversionTextView;
    TextView thirdConversionTextView;
    TextView firstUnitTextView;
    TextView secondUnitTextView;
    TextView thirdUnitTextView;

    //Declare variable for the selection of the Spinner
    String spinnerSelection;

    //Declare variable for user into the valueEditText view
    String valueString = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Assign View variables to the associated View objects
        unitSpinner = (Spinner) findViewById(R.id.unitSpinner); //Spinner to select units
        valueEditText = (EditText) findViewById(R.id.valueEditText); //EditText for the inputted value

        firstConversionTextView = (TextView) findViewById(R.id.firstConversionTextView); //Display for First conversion value
        secondConversionTextView = (TextView) findViewById(R.id.secondConversionTextView); //Display for Second conversion value
        thirdConversionTextView = (TextView) findViewById(R.id.thirdConversionTextView); //Display for Third conversion value

        firstUnitTextView = (TextView) findViewById(R.id.firstUnitTextView); //Display for First unit
        secondUnitTextView = (TextView) findViewById(R.id.secondUnitTextView); //Display for Second unit
        thirdUnitTextView = (TextView) findViewById(R.id.thirdUnitTextView); //Display for Third unit

        //Make the Display for all the conversion values BOLD
        firstConversionTextView.setTypeface(null, Typeface.BOLD);
        secondConversionTextView.setTypeface(null, Typeface.BOLD);
        thirdConversionTextView.setTypeface(null, Typeface.BOLD);

        // Create an ArrayAdapter using the string array and a default Spinner layout
        ArrayAdapter<CharSequence> unitSpinnerAdapter = ArrayAdapter.createFromResource(this, R.array.units_array, android.R.layout.simple_spinner_item);

        // Specify the layout to use when the list of choices (i.e. Metre, Temperature, Weight) appears
        unitSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // Apply the adapter to the Spinner
        unitSpinner.setAdapter(unitSpinnerAdapter);

        //Set the Spinner to the list of choices (i.e. Metre, Temperature, Weight)
        unitSpinner.setOnItemSelectedListener(new SpinnerActivity());
    }

    //Define the Spinner class
    public class SpinnerActivity extends Activity implements AdapterView.OnItemSelectedListener {

        public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
            // An item was selected. You can retrieve the selected item using
            // parent.getItemAtPosition(pos)

            spinnerSelection = parent.getItemAtPosition(pos).toString();

            Log.i("Position", spinnerSelection);
        }

        public void onNothingSelected(AdapterView<?> parent) {
            // Another interface callback
        }
    }

    //Set the visibility each of the three conversion display sets
    //  Each display set comprises:
    //      1: Display of the conversion value
    //      2: Display of the conversion unit
    public void setTextVisible(boolean isFirstSetVisible, boolean isSecondSetVisible, boolean isThirdSetVisible) {

        //--- First display set ---
        if (isFirstSetVisible) {
            firstConversionTextView.setVisibility(View.VISIBLE);
            firstUnitTextView.setVisibility(View.VISIBLE);
        } else {
            firstConversionTextView.setVisibility(View.INVISIBLE);
            firstUnitTextView.setVisibility(View.INVISIBLE);
        }

        //--- Second displaySet ---
        if (isSecondSetVisible) {
            secondConversionTextView.setVisibility(View.VISIBLE);
            secondUnitTextView.setVisibility(View.VISIBLE);
        } else {
            secondConversionTextView.setVisibility(View.INVISIBLE);
            secondUnitTextView.setVisibility(View.INVISIBLE);
        }

        //--- Third display set ---
        if (isThirdSetVisible) {
            thirdConversionTextView.setVisibility(View.VISIBLE);
            thirdUnitTextView.setVisibility(View.VISIBLE);
        } else {
            thirdConversionTextView.setVisibility(View.INVISIBLE);
            thirdUnitTextView.setVisibility(View.INVISIBLE);
        }
    }

    //Make a Toast
    public void makeToast(String message) {
        Toast.makeText(MainActivity.this, message, Toast.LENGTH_SHORT).show();
    }

    //Decide if a String is a number or not
    public static boolean isNumeric(String strNum) {
        if (strNum == null) {
            return false;
        }
        try {
            double d = Double.parseDouble(strNum);
        } catch (NumberFormatException nfe) {
            return false;
        }
        return true;
    }

    //Round the double value to 2 decimal places
    public static double round(double value, int places) {

        //Throw an exception if the places is negative
        if (places < 0) throw new IllegalArgumentException();

        //Instantiate a BigDecimal
        BigDecimal bd = BigDecimal.valueOf(value);

        //Set the scale and rounding mode of the BigDecimal object
        bd = bd.setScale(places, RoundingMode.HALF_UP);

        return bd.doubleValue();
    }


    //==================== LENGTH Conversions ======================================================

    //Convert metre to centimetre
    public double metreToCentimetre(double meter) { return meter * 100; }

    //Convert metre to foot
    public double metreToFoot(double meter) { return meter * 3.28084; }

    //Convert metre to inch
    public double metreToInch(double meter) { return meter * 39.3701; }

    //Define Onclick Listener for Length image
    // Action: Convert the value in the EditText from metre to 3 different unit conversion values
    @SuppressLint("SetTextI18n")
    public void convertLength(View view) {

        //Obtain the input from the valueEditText view
        valueString = valueEditText.getText().toString();

        //If the Spinner selection is "Meter"
        if (spinnerSelection.equals("Metre")) {

            //If there there IS an inputted value in the valueEditText
            if (!valueString.isEmpty()) {

                //If the inputted value in teh valueEditText is NOT a number
                if (!isNumeric(valueString)) {
                    makeToast("Enter only numbers!"); //Prompt the user to enter a number
                }
                //If the inputted value in the valueEditText IS a number
                else {
                    //Obtain the double value from the inputted value in the valueEditText
                    double valueDouble = Double.parseDouble(valueString);

                    //Obtain the unit conversion values
                    double valueCentimetre = round(metreToCentimetre(valueDouble), 2);
                    double valueFoot = round(metreToFoot(valueDouble), 2);
                    double valueInch = round(metreToInch(valueDouble), 2);

                    //Set the text of the results
                    firstConversionTextView.setText(Double.toString(valueCentimetre));
                    secondConversionTextView.setText(Double.toString(valueFoot));
                    thirdConversionTextView.setText(Double.toString(valueInch));
                    firstUnitTextView.setText("Centimetre");
                    secondUnitTextView.setText("Foot");
                    thirdUnitTextView.setText("Inch");

                    //Make each of the three conversion display sets visible
                    setTextVisible(true, true, true);
                }
            }
            //If there there is NO inputted value in the valueEditText
            else {
                makeToast("Enter a value"); //Prompt the user to enter a value
            }
        }
        //If the Spinner selection is NOT "Meter" (i.e. it is "Temperature" or "Weight")
        else {
            makeToast("Please select the correct conversion icon"); //Prompt the user to select the correct conversion image
        }
    }


    //==================== TEMPERATURE Conversions =================================================

    //Convert Celsius to Fahrenheit
    public double celsiusToFahrenheit(double celsius) { return (celsius * (9.0 / 5.0)) + 32; }

    //Convert Celsius to Kelvin
    public double celsiusToKelvin(double celsius) { return celsius + 273.15; }

    //Define Onclick Listener for Temperature image
    // Action: Convert the value in the EditText from metre to 3 different unit conversion values
    @SuppressLint("SetTextI18n")
    public void convertTemperature(View view) {

        //Obtain the input from the valueEditText view
        valueString = valueEditText.getText().toString();

        //If the Spinner selection is "Celsius"
        if (spinnerSelection.equals("Celsius")) {

            //If there there IS an inputted value in the valueEditText
            if (!valueString.isEmpty()) {

                //If the inputted value in teh valueEditText is NOT a number
                if (!isNumeric(valueString)) {
                    makeToast("Enter only numbers!"); //Prompt the user to enter a number
                }
                //If the inputted value in the valueEditText IS a number
                else {
                    //Obtain the double value from the inputted value in the valueEditText
                    double valueDouble = Double.parseDouble(valueString);

                    //Obtain the unit conversion values
                    double valueFahrenheit = round(celsiusToFahrenheit(valueDouble), 2);
                    double valueKelvin = round(celsiusToKelvin(valueDouble), 2);

                    //Set the text of the results
                    firstConversionTextView.setText(Double.toString(valueFahrenheit));
                    secondConversionTextView.setText(Double.toString(valueKelvin));
                    firstUnitTextView.setText("Fahrenheit");
                    secondUnitTextView.setText("Kelvin");

                    //Make the first two conversion display sets visible
                    setTextVisible(true, true, false);
                }
            }
            //If there there is NO inputted value in the valueEditText
            else {
                makeToast("Enter a value"); //Prompt the user to enter a value
            }
        }
        //If the Spinner selection is NOT "Celsius" (i.e. it is "Length" or "Weight")
        else {
            makeToast("Please select the correct conversion icon"); //Prompt the user to select the correct conversion image
        }
    }


    // ==================== WEIGHT Conversions ===================================================

    //Convert kilogram to gram
    public double kilogramToGram(double kilogram) {
        return kilogram * 1000;
    }

    //Convert kilogram to ounce
    public double kilogramToOunce(double kilogram) {
        return kilogram * 35.274;
    }

    //Convert kilogram to pound
    public double kilogramToPound(double kilogram) {
        return kilogram * 2.20462;
    }

    //Define Onclick Listener for Weight image
    // Action: Convert the value in the EditText from metre to 2 different unit conversion values
    @SuppressLint("SetTextI18n")
    public void convertWeight(View view) {

        //Obtain the input from the valueEditText view
        valueString = valueEditText.getText().toString();

        //If the Spinner selection is "Kilogram"
        if (spinnerSelection.equals("Kilogram")) {

            //If there there IS an inputted value in the valueEditText
            if (!valueString.isEmpty()) {

                //If the inputted value in teh valueEditText is NOT a number
                if (!isNumeric(valueString)) {
                    makeToast("Enter only numbers!"); //Prompt the user to enter a number
                }
                //If the inputted value in the valueEditText IS a number
                else {
                    //Obtain the double value from the inputted value in the valueEditText
                    double valueDouble = Double.parseDouble(valueString);

                    //Obtain the unit conversion values
                    double valueGram = round(kilogramToGram(valueDouble), 2);
                    double valueOunce = round(kilogramToOunce(valueDouble), 2);
                    double valuePound = round(kilogramToPound(valueDouble), 2);

                    //Set the text of the results
                    firstConversionTextView.setText(Double.toString(valueGram));
                    secondConversionTextView.setText(Double.toString(valueOunce));
                    thirdConversionTextView.setText(Double.toString(valuePound));
                    firstUnitTextView.setText("Gram");
                    secondUnitTextView.setText("Ounce(Oz)");
                    thirdUnitTextView.setText("Pound(lb)");

                    //Make each of the three conversion display sets visible
                    setTextVisible(true, true, true);
                }
            }
            //If there there is NO inputted value in the valueEditText
            else {
                makeToast("Enter a value"); //Prompt the user to enter a value
            }
        }
        //If the Spinner selection is NOT "Meter" (i.e. it is "Length" or "Temperature")
        else {
            makeToast("Please select the correct conversion icon"); //Prompt the user to select the correct conversion image
        }
    }
}