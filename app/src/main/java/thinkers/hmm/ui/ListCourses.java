package thinkers.hmm.ui;

import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
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
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;

import thinkers.hmm.R;

import thinkers.hmm.util.*;
import thinkers.hmm.model.*;


public class ListCourses extends Activity {
    //Operation String
    private final String LIST_OPERATION = "List_Courses";

    private TextView titleListCourses;
    private EditText searchCourseEditText;
    private ImageButton addNewCourseButton;
    private ListView listCoursesListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ui_list_courses);

        titleListCourses = (TextView) findViewById(R.id.titleTextView);

        //TODO: search bar refreshes the page
        searchCourseEditText = (EditText) findViewById(R.id.searchFacultyEditText);

        //Clicking on the button to add new courses
        addNewCourseButton = (ImageButton) findViewById(R.id.addNewCourseButton);
        addNewCourseButton.setOnClickListener(addNewCourseListener);

        //Clicking on an item goes to ListCourseReviews page
        listCoursesListView = (ListView) findViewById(R.id.listCoursesListView);
        listCoursesListView.setOnItemClickListener(viewCourseReviewsListener);

        ListCourseHelper listHelper = new ListCourseHelper();
        String[] params= new String[1];
        params[0] = LIST_OPERATION;
        listHelper.execute(params);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_list_courses, menu);
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

    // event-handling object that responds to addNewCourse's events
    private View.OnClickListener addNewCourseListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent addNewCourse = new Intent(ListCourses.this, AddNewCourse.class);

            startActivity(addNewCourse); // start the addNewCourse Activity
        }
    };

    // event listener that responds to the user touching a course's name
    // in the ListView
    private AdapterView.OnItemClickListener viewCourseReviewsListener = new AdapterView.OnItemClickListener()
    {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id)
        {
            // create an Intent to launch the ListCourseReviews Activity
            Course course = (Course) parent.getAdapter().getItem(position);
            Toast.makeText(ListCourses.this, "Course ID:" + course.getId(), Toast.LENGTH_SHORT).show();
            Intent viewCourseReviews = new Intent(ListCourses.this, ListCourseReviews.class);
            startActivity(viewCourseReviews); // start the viewCourseReviews Activity
        } // end method onItemClick
    }; // end viewContactListener

    private class ListCourseHelper extends AsyncTask<Object, Void, Void> {

        private String option = "";
        private ArrayList<Course> courseList;

        @Override
        protected Void doInBackground(Object... params ) {
            option = (String)params[0];
            if(option.equals(LIST_OPERATION)) {
                CourseUtil courseUtil = new CourseUtil();
                courseList = courseUtil.getAllCourses();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void object) {
            if(option.equals(LIST_OPERATION)) {
                CourseAdapter courseAdapter = new CourseAdapter(courseList);
                listCoursesListView.setAdapter(courseAdapter);
            }
            return;
        }
    }

    private class CourseAdapter extends ArrayAdapter<Course> {
        public CourseAdapter(ArrayList<Course> courses) {
            super(ListCourses.this, android.R.layout.simple_list_item_1, courses);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            // if we weren't given a view, inflate one
            if (convertView == null) {
                convertView = ListCourses.this.getLayoutInflater()
                        .inflate(R.layout.ui_list_item_course, null);
            }

            // configure the view for this Song
            final Course course = getItem(position);

            TextView courseTitle = (TextView) convertView.findViewById(R.id.courseTitle);
            courseTitle.setText(course.getName());

            return convertView;
        }
    }
}
