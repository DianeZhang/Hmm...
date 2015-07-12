package thinkers.hmm.ui;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import thinkers.hmm.R;

public class AddNewFaculty extends Activity {

    private TextView titleAddFaculties;
    private EditText newCourseEditText;
    private Button submitNewCourseButton;
    private Button cancelNewCourseButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_faculty);

        titleAddFaculties = (TextView) findViewById(R.id.textView);

        //TODO: search bar refreshes the page
        newCourseEditText = (EditText) findViewById(R.id.editText);

        //Clicking on the button to add new courses
        submitNewCourseButton = (Button) findViewById(R.id.button);
        submitNewCourseButton.setOnClickListener(submitNewCourseListener);

        //Clicking on an item goes to ListCourseReviews page
        cancelNewCourseButton = (Button) findViewById(R.id.button2);
        cancelNewCourseButton.setOnClickListener(cancelNewCourseListener);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_add_new_faculty, menu);
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

    private View.OnClickListener submitNewCourseListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent submitNewCourse = new Intent(AddNewFaculty.this, ListFaculty.class);
        }
    };

    private View.OnClickListener cancelNewCourseListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent cancelNewCourse = new Intent(AddNewFaculty.this, ListFaculty.class);
        }
    };
}
