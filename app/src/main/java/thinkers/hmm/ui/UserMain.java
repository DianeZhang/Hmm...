package thinkers.hmm.ui;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import thinkers.hmm.R;

public class UserMain extends Activity {

    private TextView welcomeText;
    private Button courseButton;
    private Button facultiesButton;
    private Button draftsButton;
    private Button reviewsButton;
    private Button settingsButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ui_user_main);

        welcomeText = (TextView) findViewById(R.id.welcomeText);
        courseButton = (Button) findViewById(R.id.coursesButton);
        facultiesButton = (Button) findViewById(R.id.facultiesButton);
        draftsButton = (Button) findViewById(R.id.draftsButton);
        reviewsButton = (Button) findViewById(R.id.reviewsButton);
        settingsButton = (Button) findViewById(R.id.settingsButton);

        settingsButton.setOnClickListener(settingsListener);
        courseButton.setOnClickListener(coursesListener);
        facultiesButton.setOnClickListener(facultiesListener);
        reviewsButton.setOnClickListener(reviewsListener);
        draftsButton.setOnClickListener(draftsListener);

        // test to see what's in shared preference
        SharedPreferences sharedpreferences = getSharedPreferences(Login.USER_INFO, Context.MODE_PRIVATE);
        String role = sharedpreferences.getString("role", null);
        Toast.makeText(UserMain.this, "ROLE: " + role, Toast.LENGTH_SHORT).show();
    }

    private View.OnClickListener settingsListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent viewSettings = new Intent(UserMain.this, Settings.class);
            startActivity(viewSettings);
        }
    };

    private View.OnClickListener coursesListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent viewCourses = new Intent(UserMain.this, ListCourses.class);
            startActivity(viewCourses);
        }
    };

    private View.OnClickListener facultiesListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent viewFaculties = new Intent(UserMain.this, ListFaculties.class);
            startActivity(viewFaculties);
        }
    };

    private View.OnClickListener reviewsListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent viewReviews = new Intent(UserMain.this, ListMyReviews.class);
            startActivity(viewReviews);
        }
    };

    private View.OnClickListener draftsListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent viewDrafts = new Intent(UserMain.this, ListMyDrafts.class);
            startActivity(viewDrafts);
        }
    };

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_user_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        if (id == R.id.action_logout) {
            Intent intent = new Intent(UserMain.this, Login.class);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
