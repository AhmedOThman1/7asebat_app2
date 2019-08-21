package com.example.login;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import static com.example.login.AccountFragment.encodeImage;

public class launchActivity extends AppCompatActivity {

    private static int time_out=0000;

    public static final String SHARED_PREF="sharedPrefs";
    public static final String fir_name = "fname";
    public static final String sec_name = "sname";
    public static final String univ = "univer";
    public static final String univ_pos = "univer_pos";
    public static final String lev = "level_sp";
    public static final String lev_pos = "level_pos";
    public static final String TERM = "term_strings";
    public static final String TERM_pos = "term_pos";
    public static final String prof_img = "Profile_image";
    public static final int CHOOSE_IMG = 101;
    public static final int RC_SIGN_IN=101;
    public static final String ACCOUNT="account";

    public static String ID;
    static public String SP_F_NAME ="";
    static public String SP_L_NAME ="";
    static public String SP_UNIVERSITY="";
    static public int SP_UNIVERSITY_POS=0;
    static public String SP_LEVEL ="";
    static public int SP_LEVEL_POS =0;
    static public String SP_TERM ="";
    static public int SP_TERM_POS =0;
    static public String SP_ACCOUNT ="";
    static public String SP_PROFILE_IMAGE ="";


    int FirstTime;
    Bitmap icon ;
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launch);

        icon = BitmapFactory.decodeResource(getResources(),
                R.drawable.profile_img);

        sharedPreferences=getSharedPreferences(SHARED_PREF,MODE_PRIVATE);

        FirstTime= sharedPreferences.getInt(ID,0);

        if(FirstTime != 0)
        {



            SharedPreferences sharedPreferences= getSharedPreferences(SHARED_PREF,MODE_PRIVATE);
            SP_F_NAME = sharedPreferences.getString(fir_name ,"") ;
            SP_L_NAME = sharedPreferences.getString(sec_name ,"") ;
            SP_UNIVERSITY = sharedPreferences.getString(univ ,"") ;
            SP_LEVEL = sharedPreferences.getString(lev ,"") ;
            SP_TERM = sharedPreferences.getString(TERM ,"") ;
            SP_ACCOUNT = sharedPreferences.getString(ACCOUNT ,"") ;
            SP_PROFILE_IMAGE = sharedPreferences.getString(prof_img, encodeImage(icon));
            SP_UNIVERSITY_POS = sharedPreferences.getInt(univ_pos,0);
            SP_LEVEL_POS = sharedPreferences.getInt(lev_pos,0);
            SP_TERM_POS =sharedPreferences.getInt(TERM_pos,0);


            Toast.makeText(launchActivity.this, "Welcome " + SP_F_NAME + " " +  SP_L_NAME, Toast.LENGTH_LONG).show();

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {

                    Intent homeIntent = new Intent(launchActivity.this, Home.class);
                    startActivity(homeIntent);
                    finish();

                }
            },time_out);
        }

        else
        {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {


                    Intent mainIntent = new Intent(launchActivity.this, MainActivity.class);
                    startActivity(mainIntent);
                    finish();

                }
            },time_out);
            }
        }
    }

