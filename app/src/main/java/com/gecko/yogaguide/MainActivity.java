package com.gecko.yogaguide;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.WindowDecorActionBar;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.graphics.Typeface;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

public class MainActivity extends AppCompatActivity {

    public static final String TAG = "myLog";

    private ActionBarDrawerToggle mToggle;
    FloatingActionButton mActionButton;
    Button mButtonToUp, mButtonToDown;
    TextView mText;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mToggle = new ActionBarDrawerToggle(
                this,
                drawerLayout,
                R.string.navigation_drawer_open,
                R.string.navigation_drawer_close);
        mToggle.setDrawerIndicatorEnabled(true);
        drawerLayout.setDrawerListener(mToggle);

        FragmentManager fragmentManager = getSupportFragmentManager();
        Fragment fragmentUp = fragmentManager.findFragmentById(R.id.fragment_container_up);
        Fragment fragmentDown = fragmentManager.findFragmentById(R.id.fragment_container_down);
        if (fragmentUp == null){
            addFragment(R.id.fragment_container_up, new UpFragment());
        }
        if (fragmentDown == null){
            addFragment(R.id.fragment_container_down, new DownFragment());
        }

        mButtonToUp = findViewById(R.id.button_activity_to_up);
        mButtonToDown = findViewById(R.id.button_activity_to_down);
        mText = findViewById(R.id.text_view_activity);
        mText.setTypeface(Typeface.createFromAsset(getAssets(), "NeoSansPro-Regular.otf"));
        mText.setTextSize(25);
        mActionButton = findViewById(R.id.floating_action_button);

        mButtonToUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LinearLayout container = findViewById(R.id.activity_root_layout);

                TextView textViewUp = findViewById(R.id.text_view_up);
                if(textViewUp == null){
                    Snackbar.make(container, "up TextView NOT found", Snackbar.LENGTH_LONG).show();
                } else {
                    Snackbar.make(container, "up TextView found", Snackbar.LENGTH_LONG).show();
                    textViewUp.setText("got text from actitvity");
                }
            }
        });

        mButtonToDown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LinearLayout container = findViewById(R.id.activity_root_layout);
                TextView textViewDown = findViewById(R.id.text_view_down);
                if(textViewDown == null){
                    Snackbar.make(container, "down TextView NOT found", Snackbar.LENGTH_LONG).show();
                } else {
                    Snackbar.make(container, "down TextView found", Snackbar.LENGTH_LONG).show();
                    textViewDown.setText("got text from actitvity");
                }

            }
        });

        mActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LinearLayout container = findViewById(R.id.activity_root_layout);
                Snackbar.make(container, "have action button pressed", Snackbar.LENGTH_LONG).show();
            }
        });

    }

    private void addFragment(int container, Fragment fragment){
        FragmentManager fm = getSupportFragmentManager();
        fm.beginTransaction().add(container, fragment).commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.navigation_view_menu, menu);
        return true;
    }

}
