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
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.RelativeLayout.LayoutParams;

import java.io.IOException;
import java.util.ArrayList;

import thinkers.hmm.R;

import thinkers.hmm.util.*;
import thinkers.hmm.model.*;


public class ListCourses extends Activity {
    //Operation Strings
    private final String LIST_OPERATION = "List_Courses";
    private final String SEARCH_OPERATION = "Search_Course";

    public static final String USER = "user";
    public static final String ADMIN = "admin";

    private TextView titleListCourses;
    private EditText searchCourseEditText;
    private Button searchCourseButton;
    private ImageButton addNewCourseButton;
    private ListView listCoursesListView;
    private ImageButton homeButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ui_list_courses);

        titleListCourses = (TextView) findViewById(R.id.titleTextView);

        searchCourseEditText = (EditText) findViewById(R.id.searchCourseEditText);
        searchCourseButton = (Button) findViewById(R.id.searchCourseButton);
        searchCourseButton.setOnClickListener(searchCourseListener);

        //Clicking on the button to add new courses
        //addNewCourseButton = (ImageButton) findViewById(R.id.addNewCourseButton);
        //addNewCourseButton.setOnClickListener(addNewCourseListener);

        //Clicking on an item goes to ListCourseReviews page
        listCoursesListView = (ListView) findViewById(R.id.listCoursesListView);
        listCoursesListView.setOnItemClickListener(viewCourseReviewsListener);

        homeButton = (ImageButton) findViewById(R.id.homeButton);
        homeButton.setOnClickListener(homeListener);

        ListCourseHelper listHelper = new ListCourseHelper();
        String[] params= new String[1];
        params[0] = LIST_OPERATION;
        listHelper.execute(params);

        //Show add new course button to admins
        SharedPreferences sharedpreferences = getSharedPreferences(Login.USER_INFO, Context.MODE_PRIVATE);
        String role = sharedpreferences.getString("role", null);

        if (role.equals(ADMIN)) {
            Button addCourseButton = new Button(this);
            addCourseButton.setText("Add Course");
            RelativeLayout rl = (RelativeLayout)findViewById(R.id.relativeLayout);
            RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(
                    LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
            addCourseButton.setOnClickListener(addNewCourseListener);
            //addCourseButton.setLa
            rl.addView(addCourseButton, lp);
        }
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
        if (id == R.id.action_logout) {
            Intent intent = new Intent(ListCourses.this, Login.class);
            startActivity(intent);
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

    // event-handling object that responds to searchCourse events
    private View.OnClickListener searchCourseListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            ListCourseHelper searchHelper = new ListCourseHelper();
            String[] params= new String[2];
            params[0] = SEARCH_OPERATION;
            params[1] = searchCourseEditText.getText().toString().toLowerCase();
            searchHelper.execute(params);
        }
    };

    private View.OnClickListener homeListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            SharedPreferences sharedpreferences = getSharedPreferences(Login.USER_INFO, Context.MODE_PRIVATE);
            String role = sharedpreferences.getString("role", null);
            if (role.equals(USER)) {
                Intent home = new Intent(ListCourses.this, UserMain.class);
                startActivity(home);
            }
            else {
                Intent home = new Intent(ListCourses.this, AdminMain.class);
                startActivity(home);
            }
        }
    };

    private AdapterView.OnItemClickListener viewCourseReviewsListener = new AdapterView.OnItemClickListener()
    {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id)
        {
            // Get selected course from adapter
            Course course = (Course) parent.getAdapter().getItem(position);
            // Toast.makeText(ListCourses.this, "Course ID:" + course.getId(), Toast.LENGTH_SHORT).show();

            // Put in extras
            Intent viewCourseReviews = new Intent(ListCourses.this, ListCourseReviews.class);
            viewCourseReviews.putExtra("cid", course.getId());
            // Start activity
            startActivity(viewCourseReviews); // start the viewCourseReviews Activity
        } // end method onItemClick
    }; // end viewContactListener

    private class ListCourseHelper extends AsyncTask<Object, Void, Void> {

        private String option = "";
        private ArrayList<Course> courseList = null;
        private ArrayList<Course> searchList = new ArrayList<>();

        @Override
        protected Void doInBackground(Object... params ) {
            option = (String)params[0];
            if(option.equals(LIST_OPERATION)) {
                CourseUtil courseUtil = new CourseUtil();
                courseList = courseUtil.getAllCourses();
            } else if (option.equals(SEARCH_OPERATION)) {
                String keyword = (String) params[1];

                CourseUtil courseUtil = new CourseUtil();
                courseList = courseUtil.getAllCourses();
                for (Course course: courseList) {
                    if (course.getName().toLowerCase().contains(keyword)) {
                        searchList.add(course);
                    }
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void object) {
            if(option.equals(LIST_OPERATION)) {
                CourseAdapter courseAdapter = new CourseAdapter(courseList);
                listCoursesListView.setAdapter(courseAdapter);
            } else if (option.equals(SEARCH_OPERATION)) {
                CourseAdapter courseAdapter = new CourseAdapter(searchList);
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
                        .inflate(android.R.layout.simple_list_item_1, null);
            }

            // configure the view for this Song
            final Course course = getItem(position);

            TextView courseTitle = (TextView) convertView.findViewById(android.R.id.text1);
            courseTitle.setText(course.getName());

            return convertView;
        }
    }
}
