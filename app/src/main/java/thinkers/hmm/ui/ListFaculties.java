package thinkers.hmm.ui;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import thinkers.hmm.R;

import thinkers.hmm.util.*;
import thinkers.hmm.model.*;

public class ListFaculties extends Activity {
    //Operation String
    private final String LIST_OPERATION = "List_Faculties";

    public static final String USER = "user";
    public static final String ADMIN = "admin";

    private TextView titleListFaculties;
    private EditText searchFaultyEditText;
    private ImageButton addNewFacultyButton;
    private ListView listFacultiesListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ui_list_faculties);

        titleListFaculties = (TextView) findViewById(R.id.titleTextView);

        //TODO: search bar refreshes the page
        searchFaultyEditText = (EditText) findViewById(R.id.searchFacultyEditText);

        //Clicking on the button to add new courses
        //addNewFacultyButton = (ImageButton) findViewById(R.id.addNewFacultyButton);
        //addNewFacultyButton.setOnClickListener(addNewFacultyListener);

        //Clicking on an item goes to ListCourseReviews page
        listFacultiesListView = (ListView) findViewById(R.id.listFacultiesListView);
        listFacultiesListView.setOnItemClickListener(viewFacultyReviewsListener);

        ListFacultyHelper listHelper = new ListFacultyHelper();
        String[] params= new String[1];
        params[0] = LIST_OPERATION;
        listHelper.execute(params);

        SharedPreferences sharedpreferences = getSharedPreferences(Login.USER_INFO, Context.MODE_PRIVATE);
        String role = sharedpreferences.getString("role", null);
        Toast.makeText(ListFaculties.this, "ROLE: " + role, Toast.LENGTH_SHORT).show();

        if (role.equals(ADMIN)) {
            Button addFacultyButton = new Button(this);
            addFacultyButton.setText("Add Faculty");
            RelativeLayout rl = (RelativeLayout)findViewById(R.id.relativeLayout);
            RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(
                    RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
            addFacultyButton.setOnClickListener(addNewFacultyListener);
            //addCourseButton.setLa
            rl.addView(addFacultyButton, lp);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_list_faculties, menu);
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

    // event-handling object that responds to addNewFaculty's events
    private View.OnClickListener addNewFacultyListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent addNewFaculty = new Intent(ListFaculties.this, AddNewFaculty.class);
            startActivity(addNewFaculty); // start the addNewFaculty Activity
        }
    };

    // event listener that responds to the user touching a course's name
    // in the ListView
    private AdapterView.OnItemClickListener viewFacultyReviewsListener = new AdapterView.OnItemClickListener()
    {
        @Override
        public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3)
        {
            // create an Intent to launch the ListCourseReviews Activity
            Intent viewFacultyReviews = new Intent(ListFaculties.this, ListFacultyReviews.class);
            startActivity(viewFacultyReviews); // start the viewCourseReviews Activity
        } // end method onItemClick
    }; // end viewContactListener

    private class ListFacultyHelper extends AsyncTask<Object, Void, Void> {

        private String option = "";
        private ArrayList<Faculty> facultyList;
        private ArrayList<String> facultyNameList;

        @Override
        protected Void doInBackground(Object... params ) {
            option = (String)params[0];
            if(option.equals(LIST_OPERATION)) {
                FacultyUtil facultyUtil = new FacultyUtil();
                facultyList = facultyUtil.getAllFaculties();
                facultyNameList = new ArrayList<String>();
                for (Faculty faculty : facultyList) {
                    facultyNameList.add(faculty.getName());
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void object) {
            if(option.equals(LIST_OPERATION)) {
                ArrayAdapter arrayAdapter = new ArrayAdapter(ListFaculties.this, android.R.layout.simple_list_item_1, facultyNameList);
                listFacultiesListView.setAdapter(arrayAdapter);
            }
            return;
        }
    }
}
