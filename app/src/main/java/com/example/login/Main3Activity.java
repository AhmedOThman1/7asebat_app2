package com.example.login;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;

import br.com.simplepass.loading_button_lib.customViews.CircularProgressButton;

import static com.example.login.launchActivity.ACCOUNT;
import static com.example.login.launchActivity.SHARED_PREF;
public class Main3Activity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    CircularProgressButton circularProgressButton;

    String email , password ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);

        mAuth = FirebaseAuth.getInstance();

        circularProgressButton=(CircularProgressButton) findViewById(R.id.btn_signup);

        circularProgressButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                signup();
            }
        });

}


    public void log(View view) {
        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);
        finish();
    }

    void Done()
    {
        AsyncTask<String,String,String> demoDown= new AsyncTask<String, String, String>() {

            @Override
            protected String doInBackground(String... Params) {

                    try {
                        Thread.sleep(3000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    return "done";


            }


            @Override
            protected void onPostExecute(String s) {
                if(s.equals("done"))
                {
                    Toast.makeText(Main3Activity.this, "Done", Toast.LENGTH_SHORT).show();
                    circularProgressButton.doneLoadingAnimation(Color.parseColor("#333639"), BitmapFactory.decodeResource(getResources(), R.drawable.ic_done_white_48dp));
                }

            }
        };

            circularProgressButton.startAnimation();
            demoDown.execute();
            Intent myintent = new Intent(Main3Activity.this, Main2Activity.class);
            startActivity(myintent);
    }
    public void signup() {
        EditText Email = (EditText) findViewById(R.id.input_email);
        EditText userPass = (EditText) findViewById(R.id.input_password);

        email = Email.getText().toString();
        password = userPass.getText().toString();
        /*
        Intent i=new Intent(Main3Activity.this, Main2Activity.class);

        i.putExtra("email",email);
        startActivity(i);
*/
        if (email.isEmpty()) {
            Email.setError("Email is required");
            Email.requestFocus();
            return;
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            Email.setError("Unvallid Email");
            Email.requestFocus();
            return;
        }
        if (password.length() < 6) {
            userPass.setError("Password most be 6 char or more !");
            userPass.requestFocus();
            return;
        }
        if (password.isEmpty()) {
            userPass.setError("Password is required");
            userPass.requestFocus();
            return;
        }
        // progressBar.setVisibility(View.VISIBLE);
        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                // progressBar.setVisibility(View.GONE);
                if (task.isSuccessful()) {
                    Toast.makeText(getApplicationContext(), "Done sign-up", Toast.LENGTH_LONG).show();
                    SharedPreferences sharedPreferences=getSharedPreferences(SHARED_PREF,MODE_PRIVATE);
                    SharedPreferences.Editor editor=sharedPreferences.edit();
                    editor.putString(ACCOUNT,email);
                    editor.apply();

                    launchActivity.SP_ACCOUNT = sharedPreferences.getString(ACCOUNT ,"") ;

                    Done();
                } else {
                    if (task.getException() instanceof FirebaseAuthUserCollisionException)
                        Toast.makeText(getApplicationContext(), "Already Signed", Toast.LENGTH_LONG).show();
                    else {
                        Toast.makeText(getApplicationContext(), task.getException().getMessage(), Toast.LENGTH_LONG).show();
                    }
                }
            }
        });
    }

}
