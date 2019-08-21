package com.example.login;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import de.hdodenhof.circleimageview.CircleImageView;

import static com.example.login.AccountFragment.StringToBitMap;
import static com.example.login.launchActivity.CHOOSE_IMG;
import static com.example.login.launchActivity.SP_ACCOUNT;
import static com.example.login.launchActivity.SP_F_NAME;
import static com.example.login.launchActivity.SP_LEVEL_POS;
import static com.example.login.launchActivity.SP_L_NAME;
import static com.example.login.launchActivity.SP_PROFILE_IMAGE;
import static com.example.login.launchActivity.SP_TERM_POS;
import static com.example.login.launchActivity.SP_UNIVERSITY_POS;

public class Create_Post extends AppCompatActivity {
    String[] university_strings = {"University", "Benha", "Cairo", "Ain-Shams", "Assiut", "Mansoura", "Zagazig", "Helwan", "Minia", "Menoufia", "Suez", "Faiyum", "Bani_Swaif"};
    String[] year_strings = {"level", "1st year", "2nd year", "3th year", "4th year", "Others"};
    String[] term_strings = {"term", "first term", "second term"};
    String[] subjects_string = {"subject", "مقدمه حاسبات", "OB", "math1", "physics","English","لغويات حاسوبية"};
    String num = "0" ;

    RadioButton Exams;
    RadioButton Material;
    ImageView add;
    Spinner university_sp, level_sp, subject_sp, term_sp;
    EditText doctor, title, description;
    String post_subject, post_level, post_university, post_term, post_doctor, post_title, post_description;
    DatabaseReference databaseReference , subject_database;
    DatabaseReference database;
    StorageReference storageReference;
    CircleImageView profile_image;
    TextView account_name;
    FirebaseAuth mAuth;
    Uri ur1 , Data;
    boolean material_added = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create__post);


        /**
         * set the account name and image*
         **/
        profile_image = findViewById(R.id.profile_image);
        profile_image.setImageBitmap(StringToBitMap(SP_PROFILE_IMAGE));

        account_name = findViewById(R.id.account_name);
        account_name.setText(SP_F_NAME + " " + SP_L_NAME);

        /**
         * set the data base & i didn't understood this because it's written by Sam y **/
        databaseReference = FirebaseDatabase.getInstance().getReference("post");
        database = FirebaseDatabase.getInstance().getReference("material");
        storageReference = FirebaseStorage.getInstance().getReference();
        mAuth = FirebaseAuth.getInstance();

        title = (EditText) findViewById(R.id.title);

        description = (EditText) findViewById(R.id.description);

        /** set the Action bar title **/
        getSupportActionBar().setTitle("Create Post");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        /**
         *  fill the university spinner
         **/
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

        university_sp.setSelection(SP_UNIVERSITY_POS);

        /**
         *  fill the level spinner
         **/
        level_sp = (Spinner) findViewById(R.id.level);
        ArrayAdapter adapter2 = new ArrayAdapter(this, android.R.layout.simple_list_item_1, year_strings) {
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

        level_sp.setSelection(SP_LEVEL_POS);

        /**
         *  fill the subject spinner
         **/

        subject_sp = (Spinner) findViewById(R.id.sub);
        ArrayAdapter adapter3 = new ArrayAdapter(this, android.R.layout.simple_list_item_1, subjects_string) {
            @Override
            public boolean isEnabled(int i) {
                if (i == 0) {
                    return false;
                } else {

                    return true;
                }
            }
        };
        adapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        subject_sp.setAdapter(adapter3);


        /**
         *  fill the term spinner
         **/

        term_sp = (Spinner) findViewById(R.id.term);
        ArrayAdapter adapter4 = new ArrayAdapter(this, android.R.layout.simple_list_item_1, term_strings) {
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

        term_sp.setSelection(SP_TERM_POS);

        doctor = findViewById(R.id.Dr);


        Exams = (RadioButton) findViewById(R.id.Exams);
        Material = (RadioButton) findViewById(R.id.Material);
        Material.setChecked(true);
        Exams.setOnClickListener(new View.OnClickListener() {
            @Override

            public void onClick(View view) {
                doctor.setVisibility(View.VISIBLE);
            }
        });
        Material.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                doctor.setVisibility(View.GONE);
            }
        });


        /////////////////////////////////////////////////////////////   pdf ////////////
        add = (ImageView) findViewById(R.id.img_create_post);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(Create_Post.this, "Add material", Toast.LENGTH_SHORT).show();
               /**
                * Here we must add that the user can select an pdf from his device and upload it
                * the next 4 lines is a hbd **/
                Intent intent = new Intent();
                intent.setType("application/pdf");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select a pdf !"), CHOOSE_IMG);
            }
        });

    }

    /**
     * this function shows the Post button in the action bar**/
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.home_menu, menu);
        return true;
    }

    /** if the user click the post button **/
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        int id = item.getItemId();
        if (id == R.id.post)
        {

            if(subject_sp.getSelectedItemPosition()==0)
                {
                    Toast.makeText(this, "please select the subject", Toast.LENGTH_SHORT).show();
                }
            else if(title.getText().toString().equals(""))
            {
                title.setError("Title is required");
                title.requestFocus();
            }

            else if(description.getText().toString().equals(""))
            {
                description.setError("Title is required");
                description.requestFocus();
            }
            else if(!material_added)
            {
                Toast.makeText(this, "material is required ...  ", Toast.LENGTH_SHORT).show();

            }
            else
                {
                    Toast.makeText(this, "Posting ...  ", Toast.LENGTH_SHORT).show();
                    posting();
                }

        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * after open gallery we wanna get the pdf that the user chosen
     **/

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        /** if user choose an image **/
        if (requestCode == CHOOSE_IMG && resultCode == RESULT_OK && data != null && data.getData() != null) {
            material_added = true;
            Data = data.getData();
        }
        else
        {
            Toast.makeText(this, "Failed chosen pdf !", Toast.LENGTH_SHORT).show();
        }
    }
    /** another two function that written by Sam y i didn't understood them ! **/
    private void posting() {

        if (university_sp.getSelectedItemPosition() == 0 || level_sp.getSelectedItemPosition() == 0 || term_sp.getSelectedItemPosition() == 0 ) {

            Toast.makeText(this, "failed post", Toast.LENGTH_LONG).show();
            return;
        }

        final ProgressDialog progressDialog  =new ProgressDialog(this);
        progressDialog.setTitle("Uploading....");
        progressDialog.show();

        post_university = (university_sp.getSelectedItemPosition()-1)+"";
        post_level = (level_sp.getSelectedItemPosition()-1)+"";
        post_term = (term_sp.getSelectedItemPosition()-1)+"";
        post_subject = (subject_sp.getSelectedItemPosition()-1)+"";
        post_doctor = doctor.getText().toString();
        post_title = title.getText().toString();
        post_description = description.getText().toString();

        StorageReference reference = storageReference.child("materials/"+SP_ACCOUNT+System.currentTimeMillis()+".pdf");
        reference.putFile(Data).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                Task<Uri> uri = taskSnapshot.getStorage().getDownloadUrl();
                while(!uri.isComplete());
                ur1 = uri.getResult();

                subject_database = FirebaseDatabase.getInstance().getReference("universities").child(post_university).child("years").child(post_level).child("term").child(post_term).child("subjects").child(post_subject);

                    String id = subject_database.push().getKey();

                material mat = new material("id",post_title, post_description,SP_ACCOUNT+System.currentTimeMillis(),0,0,ur1.toString());

                subject_database.child(id).setValue(mat);

                Toast.makeText(Create_Post.this, ur1.toString()+"uploaded post", Toast.LENGTH_LONG).show();

                progressDialog.dismiss();

            }
        }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {

                double progress = (100.0*taskSnapshot.getBytesTransferred()/taskSnapshot.getTotalByteCount());
                progressDialog.setMessage("Uploaded: "+(int)progress+"%");
            }
        });



    }


}
