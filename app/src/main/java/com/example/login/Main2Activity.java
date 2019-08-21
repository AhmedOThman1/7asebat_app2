package com.example.login;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.IOException;

import br.com.simplepass.loading_button_lib.customViews.CircularProgressButton;
import de.hdodenhof.circleimageview.CircleImageView;

import static com.example.login.AccountFragment.encodeImage;
import static com.example.login.launchActivity.CHOOSE_IMG;
import static com.example.login.launchActivity.ID;
import static com.example.login.launchActivity.SHARED_PREF;
import static com.example.login.launchActivity.SP_F_NAME;
import static com.example.login.launchActivity.SP_LEVEL_POS;
import static com.example.login.launchActivity.SP_L_NAME;
import static com.example.login.launchActivity.SP_PROFILE_IMAGE;
import static com.example.login.launchActivity.SP_TERM_POS;
import static com.example.login.launchActivity.SP_UNIVERSITY_POS;
import static com.example.login.launchActivity.TERM;
import static com.example.login.launchActivity.TERM_pos;
import static com.example.login.launchActivity.fir_name;
import static com.example.login.launchActivity.lev;
import static com.example.login.launchActivity.lev_pos;
import static com.example.login.launchActivity.prof_img;
import static com.example.login.launchActivity.sec_name;
import static com.example.login.launchActivity.univ;
import static com.example.login.launchActivity.univ_pos;


public class Main2Activity extends AppCompatActivity {
    String[] university_strings = {"University", "Benha", "Cairo", "Ain-Shams", "Assiut", "Mansoura", "Zagazig", "Helwan", "Minia", "Menoufia", "Suez", "Faiyum", "Bani_Swaif"};
    String[] level_strings = {"level","1st year", "2nd year", "3th year", "4th year", "Other"};
    String[] term_arr = {"term","first term", "second term"};

    DatabaseReference databaseReference;

    String university, year, term_val;
    EditText user;
    EditText second;
    Spinner university_sp, level_sp, term_sp;
    CircleImageView upload_profile_img;
    Uri uriProfileImage;
    Bitmap bitmap, Img_bitmap;
    CircularProgressButton submit_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        databaseReference = FirebaseDatabase.getInstance().getReference("data");

        user = (EditText) findViewById(R.id.name1);

        user.setText(SP_F_NAME);

        second = (EditText) findViewById(R.id.name2);

        second.setText(SP_L_NAME);

        Toast.makeText(this, SP_F_NAME+" "+SP_L_NAME, Toast.LENGTH_SHORT).show();

        submit_btn = findViewById(R.id.submit);

        upload_profile_img = findViewById(R.id.upload_profile_img);

        upload_profile_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select a photo !"), CHOOSE_IMG);
            }
        });


        university_sp = (Spinner) findViewById(R.id.uni);

        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, university_strings) {
            @Override
            public boolean isEnabled(int i) {
                if (i == 0) {
                    return false;
                } else {

                    return true;
                }
            }
        };
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        university_sp.setAdapter(adapter);


        level_sp = (Spinner) findViewById(R.id.level);


        ArrayAdapter adapter2 = new ArrayAdapter(this, android.R.layout.simple_list_item_1, level_strings) {

            @Override
            public boolean isEnabled(int i) {
                if (i == 0) {
                    return false;
                } else {

                    return true;
                }
            }
        };
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        level_sp.setAdapter(adapter2);


        term_sp = (Spinner) findViewById(R.id.term);


        ArrayAdapter adapter4 = new ArrayAdapter(this, android.R.layout.simple_list_item_1, term_arr) {
            @Override
            public boolean isEnabled(int i) {
                if (i == 0) {
                    return false;
                } else {

                    return true;
                }
            }
        };
        adapter4.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        term_sp.setAdapter(adapter4);


        submit_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (user.getText().toString().equals(""))
                {
                    user.setError("First name is required");
                    user.requestFocus();
                }
                else if (second.getText().toString().equals(""))
                {
                    second.setError("second name is required");
                    second.requestFocus();
                }
                else if (university_sp.getSelectedItemPosition() == 0)
                {
                    Toast.makeText(Main2Activity.this, " university is required", Toast.LENGTH_SHORT).show();
                    university_sp.requestFocus();
                }
                else if (level_sp.getSelectedItemPosition() == 0)
                {
                    Toast.makeText(Main2Activity.this, " level is required", Toast.LENGTH_SHORT).show();
                    level_sp.requestFocus();
                }
                else if (term_sp.getSelectedItemPosition() == 0)
                {
                    Toast.makeText(Main2Activity.this, " term is required", Toast.LENGTH_SHORT).show();
                    term_sp.requestFocus();
                }
                else
                {
                    Done();
                }

            }
        });
    }


    void Done() {
        AsyncTask<String, String, String> demoDown = new AsyncTask<String, String, String>() {

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
                if (s.equals("done")) {
                    Toast.makeText(Main2Activity.this, "Done", Toast.LENGTH_SHORT).show();
                    submit_btn.doneLoadingAnimation(Color.parseColor("#333639"), BitmapFactory.decodeResource(getResources(), R.drawable.ic_done_white_48dp));
                }

            }
        };

        submit_btn.startAnimation();
        demoDown.execute();

        save_data();
        upload();
        Intent home_intent = new Intent(Main2Activity.this, Home.class);
        startActivity(home_intent);
        finish();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == CHOOSE_IMG && resultCode == RESULT_OK && data != null && data.getData() != null) {
            uriProfileImage = data.getData();
            try {
                bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), uriProfileImage);
                upload_profile_img.setImageBitmap(bitmap);

            } catch (IOException e) {
                Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }
    }


    public void save_data() {

        BitmapDrawable bitmapDrawable = ((BitmapDrawable) upload_profile_img.getDrawable());
        Img_bitmap = bitmapDrawable.getBitmap();


        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREF, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(fir_name, user.getText().toString());
        editor.putString(sec_name, second.getText().toString());
        editor.putString(univ, university_sp.getSelectedItem().toString());
        editor.putInt(univ_pos, university_sp.getSelectedItemPosition());
        editor.putString(lev, level_sp.getSelectedItem().toString());
        editor.putInt(lev_pos, level_sp.getSelectedItemPosition());
        editor.putString(TERM, term_sp.getSelectedItem().toString());
        editor.putInt(TERM_pos, term_sp.getSelectedItemPosition());
        editor.putString(prof_img, encodeImage(Img_bitmap));
        editor.putInt(ID, 3);
        editor.apply();


        SP_UNIVERSITY_POS = sharedPreferences.getInt(univ_pos, 0);
        SP_LEVEL_POS = sharedPreferences.getInt(lev_pos, 0);
        SP_TERM_POS = sharedPreferences.getInt(TERM_pos, 0);
        SP_F_NAME = sharedPreferences.getString(fir_name, "");
        SP_L_NAME = sharedPreferences.getString(sec_name, "");
        SP_PROFILE_IMAGE = sharedPreferences.getString(prof_img, "");

    }

    private void upload() {
        String user_name = user.getText().toString() + second.getText().toString();
        Intent i = getIntent();
        String email = i.getStringExtra("email");

        if (!TextUtils.isEmpty(user_name)) {
            String id = databaseReference.push().getKey();
            UsersData usersData = new UsersData(id, user_name, university, term_val, email, year);
            databaseReference.child(id).setValue(usersData);
            Toast.makeText(this, "Added", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Failed to upload", Toast.LENGTH_SHORT).show();
        }


    }
}
