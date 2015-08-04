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
import android.view.ViewGroup;
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
    private final String SEARCH_OPERATION = "Search_Faculty";

    public static final String USER = "user";
    public static final String ADMIN = "admin";

    private TextView titleListFaculties;
    private EditText searchFacultyEditText;
    private Button searchFacultyButton;
    private ImageButton addNewFacultyButton;
    private ListView listFacultiesListView;
    private ImageButton homeButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ui_list_faculties);

        titleListFaculties = (TextView) findViewById(R.id.titleTextView);

        searchFacultyEditText = (EditText) findViewById(R.id.searchFacultyEditText);
        searchFacultyButton = (Button) findViewById(R.id.searchFacultyButton);
        searchFacultyButton.setOnClickListener(searchFacultyListener);

        //Clicking on the button to add new courses
        //addNewFacultyButton = (ImageButton) findViewById(R.id.addNewFacultyButton);
        //addNewFacultyButton.setOnClickListener(addNewFacultyListener);

        //Clicking on an item goes to ListCourseReviews page
        listFacultiesListView = (ListView) findViewById(R.id.listFacultiesListView);
        listFacultiesListView.setOnItemClickListener(viewFacultyReviewsListener);

        homeButton = (ImageButton) findViewById(R.id.homeButton);
        homeButton.setOnClickListener(homeListener);

        //Show faculties
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
    public void onResume() {
        super.onResume();

        //Show faculties
        ListFacultyHelper listHelper = new ListFacultyHelper();
        String[] params= new String[1];
        params[0] = LIST_OPERATION;
        listHelper.execute(params);
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
        if (id == R.id.action_logout) {
            Intent intent = new Intent(ListFaculties.this, Login.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
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

    private View.OnClickListener searchFacultyListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            ListFacultyHelper searchHelper = new ListFacultyHelper();
            String[] params= new String[2];
            params[0] = SEARCH_OPERATION;
            params[1] = searchFacultyEditText.getText().toString().toLowerCase();
            searchHelper.execute(params);
        }
    };

    private View.OnClickListener homeListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            SharedPreferences sharedpreferences = getSharedPreferences(Login.USER_INFO, Context.MODE_PRIVATE);
            String role = sharedpreferences.getString("role", null);
            if (role.equals(USER)) {
                Intent home = new Intent(ListFaculties.this, UserMain.class);
                startActivity(home);
            }
            else {
                Intent home = new Intent(ListFaculties.this, AdminMain.class);
                startActivity(home);
            }
        }
    };

    // event listener that responds to the user touching a course's name
    // in the ListView
    private AdapterView.OnItemClickListener viewFacultyReviewsListener = new AdapterView.OnItemClickListener()
    {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id)
        {
            // Get selected course from adapter
            Faculty faculty = (Faculty) parent.getAdapter().getItem(position);
            // Toast.makeText(ListCourses.this, "Course ID:" + course.getId(), Toast.LENGTH_SHORT).show();

            // Put in extras
            Intent viewFacultyReviews = new Intent(ListFaculties.this, ListFacultyReviews.class);
            Bundle bundle =  new Bundle();
            bundle.putSerializable("Faculty", faculty);
            viewFacultyReviews.putExtra("FacultyBundle", bundle);
            // Start activity
            startActivity(viewFacultyReviews); // start the viewCourseReviews Activity
        } // end method onItemClick
    }; // end viewContactListener

    private class ListFacultyHelper extends AsyncTask<Object, Void, Void> {

        private String option = "";
        private ArrayList<Faculty> facultyList = null;
        private ArrayList<Faculty> searchList = new ArrayList<>();

        @Override
        protected Void doInBackground(Object... params ) {
            option = (String)params[0];
            if(option.equals(LIST_OPERATION)) {
                FacultyUtil facultyUtil = new FacultyUtil();
                facultyList = facultyUtil.getAllFaculties();
            } else if (option.equals(SEARCH_OPERATION)) {
                String keyword = (String) params[1];

                FacultyUtil facultyUtil = new FacultyUtil();
                facultyList = facultyUtil.getAllFaculties();
                for (Faculty faculty: facultyList) {
                    if (faculty.getName().toLowerCase().contains(keyword)) {
                        searchList.add(faculty);
                    }
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void object) {
            if(option.equals(LIST_OPERATION)) {
                FacultyAdapter facultyAdapter = new FacultyAdapter(facultyList);
                listFacultiesListView.setAdapter(facultyAdapter);
            } else if (option.equals(SEARCH_OPERATION)) {
                FacultyAdapter facultyAdapter = new FacultyAdapter(searchList);
                listFacultiesListView.setAdapter(facultyAdapter);
            }
            return;
        }
    }

    private class FacultyAdapter extends ArrayAdapter<Faculty> {
        public FacultyAdapter(ArrayList<Faculty> faculties) {
            super(ListFaculties.this, android.R.layout.simple_list_item_1, faculties);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            // if we weren't given a view, inflate one
            if (convertView == null) {
                convertView = ListFaculties.this.getLayoutInflater()
                        .inflate(android.R.layout.simple_list_item_1, null);
            }

            // configure the view for this Song
            final Faculty faculty = getItem(position);

            TextView facultyTitle = (TextView) convertView.findViewById(android.R.id.text1);
            facultyTitle.setText(faculty.getName());

            return convertView;
        }
    }
}
