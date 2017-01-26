package com.novatore.phonepicker;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Patterns;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.List;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity  {


    TextView mobileInfo;
    EditText countryPhone;
    TextView hintCode;

    // Fields
    private Country mSelectedCountry;
    private CountriesFetcher.CountryList mCountries;
    private final String DEFAULT_COUNTRY = Locale.getDefault().getCountry();
    ImageView countryImage;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        try{
            getSupportActionBar().hide();
        }catch (Exception e){

        }


        setScreenViews();

    }

    private void setScreenViews() {

        countryImage = (ImageView) findViewById(R.id.countyImageInAccount);
        hintCode=(TextView)findViewById(R.id.hint_code);
        mCountries = CountriesFetcher.getCountries(this);
        //setDefault();
        countryPhone=(EditText)findViewById(R.id.countryPhone);

        mobileInfo=(TextView)findViewById(R.id.textViewMobileInfo);
        Typeface regular = Typeface.createFromAsset(getAssets(), "fonts/proximanova-regular.ttf");
        mobileInfo=(TextView)findViewById(R.id.textViewMobileInfo);
        LinearLayout layoutImage=(LinearLayout)findViewById(R.id.layoutImage);


        layoutImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                hideKeyboard(MainActivity.this);

                new DialogePicker(MainActivity.this, "Select Mobile Number", CountriesFetcher.getCountries(MainActivity.this), new DialogePicker.DialogeInterfaceCountry() {
                    @Override
                    public void onCountryItemSelectedSelected(Country countryObject) {

                        countryImage.setImageResource(getFlagResource(countryObject));
                        hintCode.setText("+" + countryObject.getDialCode() + "");
                        countryPhone.requestFocus();


                    }

                }).show();

            }
        });

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                openKeyboard(MainActivity.this);
            }
        }, 700);








    }
    private int getFlagResource(Country country) {
        return this.getResources().getIdentifier("country_" + country.getIso().toLowerCase(), "drawable", this.getPackageName());
    }
    public void setEmptyDefault(String iso) {
        if (iso == null || iso.isEmpty()) {
            iso = DEFAULT_COUNTRY;
        }
        int defaultIdx = mCountries.indexOfIso(iso);
        mSelectedCountry = mCountries.get(defaultIdx);
        //hintCode.setText("+" + mSelectedCountry.getDialCode() + "");
        //countryImage.setImageResource(getFlagResource(mSelectedCountry));
    }
    public void setNumber(String number) {
        String iso = null;
        if (mSelectedCountry != null) {
            iso = mSelectedCountry.getIso();
        }
        countryImage.setImageResource(getFlagResourceTwo(iso));
    }
    private int getFlagResourceTwo(String iso) {
        return this.getResources().getIdentifier("country_" + iso.toLowerCase(), "drawable", this.getPackageName());
    }


    /**
     * open keyboard
     *
     * @param activity
     */
    public static void openKeyboard(Activity activity) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
    }

    /**
     * close keyboard
     *
     * @param activity
     */
    public static void hideKeyboard(Activity activity) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), 0);
    }

    public boolean isValidPhone(String phone) {



        phone=phone.replaceAll("\\s+", "");

        String expression = "^([0-9\\+]|\\(\\d{1,3}\\))[0-9\\-\\. ]{3,15}$";
        CharSequence inputString = phone;
        Pattern pattern = Pattern.compile(expression);
        Matcher matcher = pattern.matcher(inputString);
        if (matcher.matches() && phone.length()>=8 && phone.length()<=15 ) {
            return true;
        }
        else{
            return false;
        }




    }

    public void checkPhone(View view){

        if (isValidPhone(hintCode.getText().toString().trim()+countryPhone.getText().toString().trim())){
            Toast.makeText(MainActivity.this,"Valid Mobile Number",Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(MainActivity.this,"InValid Mobile Number",Toast.LENGTH_SHORT).show();
        }

    }






}
