package com.lull.tempconverter;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import java.text.NumberFormat;

public class TempConverterActivity extends AppCompatActivity
implements OnEditorActionListener{

    //define variables
    private String fahrenheitTempString;
    private String celsiusTempString;
    private float fahrenheitTemp;
    private float celsiusTemp;

    //define variables for widgets
    private TextView celsiusTempTextView;
    private EditText fahrenheitEditText;

    //define shared preferences object
    private SharedPreferences savedValues;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_temp_converter);

        //attach widgets to code using R.id

        celsiusTempTextView = (TextView) findViewById(R.id.celsiusTempTextView);
        fahrenheitEditText = (EditText) findViewById(R.id.fahrenheitEditText);

        // set the listener
        fahrenheitEditText.setOnEditorActionListener(this);

        savedValues = getSharedPreferences("SavedValues",MODE_PRIVATE );

    }

    @Override
    protected void onPause() {
        Editor editor = savedValues.edit();

        editor.putString("fahrenheitTempString", fahrenheitTempString);
        editor.putFloat("celsiusTemp", celsiusTemp);
        editor.commit();
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();

        NumberFormat tempNum = NumberFormat.getNumberInstance();
        fahrenheitTempString = savedValues.getString("fahrenheitTempString", "");
        celsiusTemp = savedValues.getFloat("celsiusTemp", 0.0f);

        fahrenheitEditText.setText(fahrenheitTempString);
        celsiusTempTextView.setText(tempNum.format(celsiusTemp));

        calcTempAndDisplay();
    }

    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event)
    {

        if(actionId == EditorInfo.IME_ACTION_DONE || actionId == EditorInfo.IME_ACTION_UNSPECIFIED)
        {
            calcTempAndDisplay();
        }
        return false;
    }

    public void calcTempAndDisplay()
    { /// new stuff
        fahrenheitTempString = fahrenheitEditText.getText().toString();

        if(fahrenheitTempString.equals(""))
        {
            fahrenheitTemp = 0.0f;
        }
        else
        {
            fahrenheitTemp = Float.parseFloat(fahrenheitTempString);
        }

        celsiusTemp = (fahrenheitTemp - 32) * 5/9;

        NumberFormat temperature = NumberFormat.getNumberInstance();
        celsiusTempTextView.setText(temperature.format(celsiusTemp));
    }
}