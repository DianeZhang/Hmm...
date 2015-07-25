package thinkers.hmm.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import thinkers.hmm.R;

public class AddNewFaculty extends Activity {

    private TextView titleAddFaculties;
    private EditText newFacultyEditText;
    private Button submitNewFacultyButton;
    private Button cancelNewFacultyButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ui_add_new_faculty);

        titleAddFaculties = (TextView) findViewById(R.id.textView);

        newFacultyEditText = (EditText) findViewById(R.id.usernameText);

        //Clicking on the button to add new Facultys
        submitNewFacultyButton = (Button) findViewById(R.id.button);
        submitNewFacultyButton.setOnClickListener(submitNewFacultyListener);

        //Clicking on an item goes to ListFacultyReviews page
        cancelNewFacultyButton = (Button) findViewById(R.id.button2);
        cancelNewFacultyButton.setOnClickListener(cancelNewFacultyListener);

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

    private View.OnClickListener submitNewFacultyListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent submitNewFaculty = new Intent(AddNewFaculty.this, ListFaculties.class);
            startActivity(submitNewFaculty);
        }
    };

    private View.OnClickListener cancelNewFacultyListener = new View.OnClickListener() {
        @Override
        public void onClick(View v){
            Intent cancelNewFaculty = new Intent(AddNewFaculty.this, ListFaculties.class);
            startActivity(cancelNewFaculty);
        }
    };
}
