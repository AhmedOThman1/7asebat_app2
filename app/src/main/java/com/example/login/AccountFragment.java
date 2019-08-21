package com.example.login;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import de.hdodenhof.circleimageview.CircleImageView;

import static android.app.Activity.RESULT_OK;
import static android.content.Context.MODE_PRIVATE;
import static com.example.login.Home.navigationView;
import static com.example.login.launchActivity.CHOOSE_IMG;
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

public class AccountFragment extends Fragment {

    /**
     * initialization
     **/
    String[] university_strings = {"University", "Benha", "Cairo", "Ain-Shams", "Assiut", "Mansoura", "Zagazig", "Helwan", "Minia", "Menoufia", "Suez", "Faiyum", "Bani_Swaif"};
    String[] year_strings = {"level", "1st year", "2nd year", "3th year", "4th year", "Others"};
    String[] term_strings = {"term", "first term", "second term"};
    Spinner university_sp, level_sp, term_sp;
    ImageView edit_username_pen, edit_password_pen;

    EditText user_name, password;
    Button save;
    CircleImageView profile_image;
    Uri uriProfileImage;
    Bitmap bitmap, Img_bitmap;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_account, container, false);


        edit_username_pen = (ImageView) view.findViewById(R.id.edit_username_pen);
        edit_password_pen = (ImageView) view.findViewById(R.id.edit_password_pen);
        user_name = (EditText) view.findViewById(R.id.account_name);
        password = (EditText) view.findViewById(R.id.pass);
        save = (Button) view.findViewById(R.id.save);

        user_name.setText(SP_F_NAME + " " + SP_L_NAME);

        profile_image = view.findViewById(R.id.profile_image);
        profile_image.setImageBitmap(StringToBitMap(SP_PROFILE_IMAGE));

        /**
         *  when the user click on the image the gallery 'll open to choose an image
         **/
        profile_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select a photo !"), CHOOSE_IMG);
            }
        });


        /**
         *  fill the university spinner
         **/
        university_sp = (Spinner) view.findViewById(R.id.uni);

        ArrayAdapter adapter = new ArrayAdapter(getActivity(), android.R.layout.simple_list_item_1, university_strings) {
            /**
             * disable the first element in the spinner
             * **/
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
         *  fill the level_sp spinner
         **/

        level_sp = (Spinner) view.findViewById(R.id.level);

        ArrayAdapter adapter2 = new ArrayAdapter(getActivity(), android.R.layout.simple_list_item_1, year_strings) {

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
         *  fill the term_strings spinner
         **/

        term_sp = (Spinner) view.findViewById(R.id.term);

        ArrayAdapter adapter4 = new ArrayAdapter(getActivity(), android.R.layout.simple_list_item_1, term_strings) {
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


        /**
         *  if user click the pen icon it 'll تسمح to update the use name
         **/

        edit_username_pen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                user_name.setEnabled(true);
                user_name.setText("");
                user_name.requestFocus();
            }
        });

        /**
         * or update the password
         **/
        edit_password_pen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                password.setEnabled(true);
                password.setText("");
                password.requestFocus();
            }
        });
        /**
         * if the user hit the save button
         **/
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (user_name.getText().toString().equals(""))
                {
                    user_name.setError("Username is required");
                    user_name.requestFocus();
                }

                else if (password.getText().toString().equals(""))
                {
                    password.setError("password is required");
                    password.requestFocus();
                }

                else if (university_sp.getSelectedItemPosition() == 0)
                {
                    Toast.makeText(getContext(), " university_sp is required", Toast.LENGTH_SHORT).show();
                }

                else if (level_sp.getSelectedItemPosition() == 0)
                {
                    Toast.makeText(getContext(), " level_sp is required", Toast.LENGTH_SHORT).show();
                }

                else if (term_sp.getSelectedItemPosition() == 0)
                {
                    Toast.makeText(getContext(), " term_strings is required", Toast.LENGTH_SHORT).show();
                }

                else if (!user_name.getText().toString().contains(" ") || user_name.getText().toString().length() < 7)
                {
                    user_name.setError("Username must be two words , \"first name & second name \" ");
                    user_name.requestFocus();
                }
                else
                    {
                    user_name.setEnabled(false);
                    password.setEnabled(false);
                    /**
                     * هنا بنقطع الاسم عشان مكتوب كله مرة واحده بنشوف فين الاسبيس وناخد الاسم اللي قبلها والاسم اللي بعدها
                     **/
                    String user = "", second = "", All;
                    boolean f = false;
                    All = user_name.getText().toString();
                    for (int i = 0; i < All.length(); i++)
                        {
                            if (All.charAt(i) == ' ')
                                f = true;

                            else if (f)
                                second += All.charAt(i);
                            else
                                user += All.charAt(i);
                        }

                    /** get the image that user chosen as a Bitmap **/
                    BitmapDrawable bitmapDrawable = ((BitmapDrawable) profile_image.getDrawable());
                    Img_bitmap = bitmapDrawable.getBitmap();


                    Toast.makeText(getContext(), "OK , " + user + "_" + second +" !", Toast.LENGTH_LONG).show();

                    /** save the update in the sharedPreferences **/
                    SharedPreferences sharedPreferences = getActivity().getSharedPreferences(SHARED_PREF, MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString(fir_name, user);
                    editor.putString(sec_name, second);
                    editor.putString(univ, university_sp.getSelectedItem().toString());
                    editor.putInt(univ_pos, university_sp.getSelectedItemPosition());
                    editor.putString(lev, level_sp.getSelectedItem().toString());
                    editor.putInt(lev_pos, level_sp.getSelectedItemPosition());
                    editor.putString(TERM, term_sp.getSelectedItem().toString());
                    editor.putInt(TERM_pos, term_sp.getSelectedItemPosition());
                    editor.putString(prof_img, encodeImage(Img_bitmap));
                    editor.apply();


                    /** update the values of this global variables to the values in the sharedPreferences **/
                    SP_UNIVERSITY_POS = sharedPreferences.getInt(univ_pos, 0);
                    SP_LEVEL_POS = sharedPreferences.getInt(lev_pos, 0);
                    SP_TERM_POS = sharedPreferences.getInt(TERM_pos, 0);
                    SP_F_NAME = sharedPreferences.getString(fir_name, "");
                    SP_L_NAME = sharedPreferences.getString(sec_name, "");
                    SP_PROFILE_IMAGE = sharedPreferences.getString(prof_img, "");

                    /** open the home page **/
                    getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new HomeFragment()).commit();
                    navigationView.setCheckedItem(R.id.nav_home);
                }
            }
        });

        return view;
    }

    /**
     * after open gallery we wanna get the photo that the user chosen
     **/

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        /** if user choose an image **/
        if (requestCode == CHOOSE_IMG && resultCode == RESULT_OK && data != null && data.getData() != null) {
            uriProfileImage = data.getData();   // get the image
            try {
                bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), uriProfileImage);
                profile_image.setImageBitmap(bitmap);  // put the chosen image in the ImageView

            } catch (IOException e) {
                Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }
    }

    /**
     * convert Bitmap to String
     **/
    static public String encodeImage(Bitmap imagee) {

        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        imagee.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        byte[] b = bytes.toByteArray();
        String encodedImage = Base64.encodeToString(b, Base64.DEFAULT);
        //saving image to shared preferences
        return encodedImage;
    }

    /**
     * to convert string to Bitmap
     **/
    static public Bitmap StringToBitMap(String encodedString) {
        try {
            byte[] encodeByte = Base64.decode(encodedString, Base64.DEFAULT);
            Bitmap bitmap = BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.length);
            return bitmap;
        } catch (Exception e) {
            e.getMessage();
            return null;
        }
    }
}
