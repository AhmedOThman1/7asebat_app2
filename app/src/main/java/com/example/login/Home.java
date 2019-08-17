package com.example.login;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;

import uk.co.samuelwall.materialtaptargetprompt.MaterialTapTargetPrompt;

public class Home extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {


    public static NavigationView navigationView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home);



        /**
         * set the toolbar **/
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        /** initialize the floating button & what happen after clicking it**/
        FloatingActionButton fab = findViewById(R.id.fab);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Create_Post.class);
                startActivity(intent);
            }
        });

        /**  set the drawer navigation**/
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);

        /** if this the first time he enter to the home **/
        if (savedInstanceState == null) {
            //show the home fragment
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new HomeFragment()).commit();
            //select the home icon in the drawer
            navigationView.setCheckedItem(R.id.nav_home);
        }

        /** this is the help view that tell the user what this view do ? **/
        new MaterialTapTargetPrompt.Builder(Home.this)
                .setTarget(R.id.fab)
                .setPrimaryText("Create your first post !")
                .setSecondaryText("Tap the envelope to start creating your first post")
                .setPromptStateChangeListener(new MaterialTapTargetPrompt.PromptStateChangeListener()
                {
                    @Override
                    public void onPromptStateChanged(MaterialTapTargetPrompt prompt, int state)
                    {
                        if (state == MaterialTapTargetPrompt.STATE_FOCAL_PRESSED)
                        {
                            Toast.makeText(Home.this, "he click here !", Toast.LENGTH_SHORT).show();
                            /** Here we must add that this help view should appear the first time only if he click here this help view won't appear again **/

                        }
                    }
                })
                .show();
        /*
        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("matrial");
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                matrial post_subject = dataSnapshot.getValue(matrial.class);
                //  Toast.makeText(Home.this, post_subject.title+" "+post_subject.description, Toast.LENGTH_SHORT).show();
                Log.v("ammm"," value is : " + post_subject.title);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(Home.this, "What !", Toast.LENGTH_SHORT).show();
            }
        });
/*
        username_header = findViewById(R.id.username_header);
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREF, MODE_PRIVATE);
        String username = sharedPreferences.getString(fir_name, "") + " " + sharedPreferences.getString(sec_name, "");

        username_header.setText(username);
*/
/*
        SharedPreferences shared = getSharedPreferences(SHARED_PREF, MODE_PRIVATE);

        String f_name = shared.getString(fir_name, "");
        String l_name = shared.getString(sec_name, "");
        String user_name = f_name + " " + l_name;
        String email = shared.getString(ACCOUNT,"");


        TextView username_header = findViewById(R.id.username_header);
        username_header.setText(user_name);

        TextView email_header = findViewById(R.id.email_header);
        email_header.setText(email);
*/
    }
    /**
     * this function to show the search bar in the action bar **/
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.search,menu);
        return true;
    }

    boolean doubleBackToExitPressedOnce = false ;
    /** if user click back button **/
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
            return;
        }

        int id = navigationView.getCheckedItem().getItemId();
        /** if the current page isn't home page then go to home page **/
        if(id !=R.id.nav_home)
        {
            //show the home fragment
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new HomeFragment()).commit();
            //select the home icon in the drawer
            navigationView.setCheckedItem(R.id.nav_home);
            return;
        }

        /** exit only if the user click the back button twice **/
        if(doubleBackToExitPressedOnce)
        {
            super.onBackPressed();
            return;
        }
        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                doubleBackToExitPressedOnce = false ;
            }
        } , 2000);


    }

    /** if user click at icon in the drawer **/
    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();


        if (id == R.id.nav_home) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new HomeFragment()).commit();
        } else if (id == R.id.nav_account) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new AccountFragment()).commit();

        } else if (id == R.id.nav_classroom) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new ClassroomFragment()).commit();

        } else if (id == R.id.nav_favorite) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new FavoriteFragment()).commit();

        } else if (id == R.id.nav_tools) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new ToolsFragment()).commit();

        } else if (id == R.id.nav_share) {
            Toast.makeText(this, "Share", Toast.LENGTH_SHORT).show();
        } else if (id == R.id.nav_send) {
            Toast.makeText(this, "Send", Toast.LENGTH_SHORT).show();
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);    // close the drawer
        return true;

    }
}
