package thinkers.hmm.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import thinkers.hmm.R;

public class ListFaculties extends Activity {
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
        addNewFacultyButton = (ImageButton) findViewById(R.id.addNewFacultyButton);
        addNewFacultyButton.setOnClickListener(addNewFacultyListener);

        //Clicking on an item goes to ListCourseReviews page
        listFacultiesListView = (ListView) findViewById(R.id.listFacultiesListView);
        listFacultiesListView.setOnItemClickListener(viewFacultyReviewsListener);

        ArrayList<String> testList = new ArrayList<String>();
        testList.add("1");
        testList.add("2");
        testList.add("3");
        ArrayAdapter arrayAdapter = new ArrayAdapter(ListFaculties.this, android.R.layout.simple_list_item_1, testList);
        listFacultiesListView.setAdapter(arrayAdapter);
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
}
