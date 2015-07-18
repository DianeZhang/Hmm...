package thinkers.hmm.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import thinkers.hmm.R;

public class AdminMain extends Activity {

    private TextView welcomeText;
    private Button courseButton;
    private Button facultiesButton;
    private Button usersButton;
    private Button settingsButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ui_admin_main);

        welcomeText = (TextView) findViewById(R.id.welcomeText);
        courseButton = (Button) findViewById(R.id.coursesButton);
        facultiesButton = (Button) findViewById(R.id.facultiesButton);
        usersButton = (Button) findViewById(R.id.usersButton);
        settingsButton = (Button) findViewById(R.id.settingsButton);

        courseButton.setOnClickListener(coursesListener);
        facultiesButton.setOnClickListener(facultiesListener);
        settingsButton.setOnClickListener(settingsListener);
        usersButton.setOnClickListener(usersListener);
    }

    private View.OnClickListener settingsListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent viewSettings = new Intent(AdminMain.this, Settings.class);
            startActivity(viewSettings);
        }
    };

    private View.OnClickListener coursesListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent viewCourses = new Intent(AdminMain.this, ListCourses.class);
            startActivity(viewCourses);
        }
    };

    private View.OnClickListener facultiesListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent viewFaculties = new Intent(AdminMain.this, ListFaculties.class);
            startActivity(viewFaculties);
        }
    };

    private View.OnClickListener usersListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent viewUsers = new Intent(AdminMain.this, ListUsers.class);
            startActivity(viewUsers);
        }
    };

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_admin_main, menu);
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

        return super.onOptionsItemSelected(item);
    }
}
