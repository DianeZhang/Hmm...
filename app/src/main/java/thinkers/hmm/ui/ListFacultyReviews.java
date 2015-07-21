package thinkers.hmm.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import thinkers.hmm.R;

public class ListFacultyReviews extends Activity {
    private TextView titleListFacultyReviews;
    private Button course1Button;
    private Button course2Button;
    private Button course3Button;
    private Button course4Button;
    private ImageButton addNewReviewButton;

    private ListView listFacultyReviewsListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ui_list_faculty_reviews);

        titleListFacultyReviews = (TextView) findViewById(R.id.titleTextView);

        //TODO: find a horizontal list view for courses
        course1Button = (Button) findViewById(R.id.course1Button);
        course2Button = (Button) findViewById(R.id.course2Button);
        course3Button = (Button) findViewById(R.id.course3Button);
        course4Button = (Button) findViewById(R.id.course4Button);
        course1Button.setOnClickListener(viewCourseReviewListener);

        //Clicking on the button to add new review
        addNewReviewButton = (ImageButton) findViewById(R.id.addNewReviewButton);
        addNewReviewButton.setOnClickListener(addNewReviewListener);

        //Clicking on an item goes to Faculty page
        listFacultyReviewsListView = (ListView) findViewById(R.id.listFacultyReviewsListView);
        listFacultyReviewsListView.setOnItemClickListener(viewFacultyReviewListener);

        ArrayList<String> testList = new ArrayList<String>();
        testList.add("1");
        testList.add("2");
        testList.add("3");
        ArrayAdapter arrayAdapter = new ArrayAdapter(ListFacultyReviews.this, android.R.layout.simple_list_item_1, testList);
        listFacultyReviewsListView.setAdapter(arrayAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_list_faculty_reviews, menu);
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

    // event listener that responds to the user touching a course's name
    // in the ListView
    View.OnClickListener viewCourseReviewListener = new View.OnClickListener()
    {
        @Override
        public void onClick(View v) {
            // create an Intent to launch the ListCourseReviews Activity
            Intent viewCourseReview = new Intent(ListFacultyReviews.this, CourseReview.class);

            startActivity(viewCourseReview); // start the viewCourseReviews Activity
        } // end method onClick
    }; // end viewContactListener

    // event-handling object that responds to addNewReview's events
    private View.OnClickListener addNewReviewListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent addNewReview = new Intent(ListFacultyReviews.this, FacultyReview.class);

            startActivity(addNewReview); // start the addNewReview Activity
        }
    };

    // event listener that responds to the user touching a review's name
    // in the ListView
    AdapterView.OnItemClickListener viewFacultyReviewListener = new AdapterView.OnItemClickListener()
    {
        @Override
        public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3)
        {
            // create an Intent to launch the ListCourseReviews Activity
            Intent viewFacultyReview = new Intent(ListFacultyReviews.this, FacultyReview.class);

            startActivity(viewFacultyReview); // start the viewCourseReviews Activity
        } // end method onItemClick
    }; // end viewContactListener
}
