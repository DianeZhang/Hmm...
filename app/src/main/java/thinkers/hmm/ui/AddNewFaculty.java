package thinkers.hmm.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import thinkers.hmm.R;

import thinkers.hmm.model.Faculty;
import thinkers.hmm.util.FacultyUtil;

public class AddNewFaculty extends Activity {
    //Operation String
    private final String ADD_FACULTY_OPERATION = "Add_Faculty";

    private EditText editName;
    private Button submitNewFacultyButton;
    private Button cancelNewFacultyButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ui_add_new_faculty);

        editName = (EditText) findViewById(R.id.editName);

        //Clicking on the button to add new Facultys
        submitNewFacultyButton = (Button) findViewById(R.id.button8);
        submitNewFacultyButton.setOnClickListener(submitNewFacultyListener);

        //Clicking on an item goes to ListFacultyReviews page
        cancelNewFacultyButton = (Button) findViewById(R.id.button9);
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
            AddFacultyHelper addHelper = new AddFacultyHelper();
            String[] params= new String[1];
            params[0] = ADD_FACULTY_OPERATION;
            addHelper.execute(params);
        }
    };

    private View.OnClickListener cancelNewFacultyListener = new View.OnClickListener() {
        @Override
        public void onClick(View v){
            Intent cancelNewFaculty = new Intent(AddNewFaculty.this, ListFaculties.class);
            startActivity(cancelNewFaculty);
        }
    };

    private class AddFacultyHelper extends AsyncTask<Object, Void, Void> {

        private String option = "";

        @Override
        protected Void doInBackground(Object... params ) {
            option = (String)params[0];
            if(option.equals(ADD_FACULTY_OPERATION)) {
                FacultyUtil facultyUtil = new FacultyUtil();
                Faculty newFaculty = new Faculty(editName.getText().toString());
                facultyUtil.insertFaculty(newFaculty);
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void object) {
            if(option.equals(ADD_FACULTY_OPERATION)) {
                Intent submitNewFaculty = new Intent(AddNewFaculty.this, ListFaculties.class);
                startActivity(submitNewFaculty);
            }
            return;
        }
    }
}
