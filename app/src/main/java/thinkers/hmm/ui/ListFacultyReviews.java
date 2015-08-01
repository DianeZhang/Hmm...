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
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import thinkers.hmm.R;
import thinkers.hmm.util.FacultyReviewUtil;
import thinkers.hmm.model.*;

public class ListFacultyReviews extends Activity {

    private final String LIST_OPERATION = "List_Faculty_Reviews";
    private int facultyID;

    private TextView titleListFacultyReviews;
    private Button course1Button;
    private Button course2Button;
    private Button course3Button;
    private Button course4Button;
    private Button addNewReviewButton;
    private ImageButton homeButton;

    public static final String USER = "user";
    public static final String ADMIN = "admin";

    private ListView listFacultyReviewsListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ui_list_faculty_reviews);

        // Get facultyID from bundle
        facultyID =  getIntent().getExtras().getInt("fid");
        Toast.makeText(ListFacultyReviews.this, "faculty ID:" + facultyID, Toast.LENGTH_SHORT).show();

        titleListFacultyReviews = (TextView) findViewById(R.id.titleTextView);

        //TODO: find a horizontal list view for courses
        course1Button = (Button) findViewById(R.id.course1Button);
        course2Button = (Button) findViewById(R.id.course2Button);
        course3Button = (Button) findViewById(R.id.course3Button);
        course4Button = (Button) findViewById(R.id.course4Button);
        course1Button.setOnClickListener(viewCourseReviewListener);

        //Clicking on the button to add new review
        addNewReviewButton = (Button) findViewById(R.id.addNewReviewButton);
        addNewReviewButton.setOnClickListener(addNewReviewListener);

        //Clicking on an item goes to Faculty page
        listFacultyReviewsListView = (ListView) findViewById(R.id.listFacultyReviewsListView);
        listFacultyReviewsListView.setOnItemClickListener(viewFacultyReviewListener);

        homeButton = (ImageButton) findViewById(R.id.homeButton);
        homeButton.setOnClickListener(homeListener);

        //Populate list
        ListFacultyReviewHelper listHelper = new ListFacultyReviewHelper();
        String[] params= new String[1];
        params[0] = LIST_OPERATION;
        listHelper.execute(params);
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
        if (id == R.id.action_logout) {
            Intent intent = new Intent(ListFacultyReviews.this, Login.class);
            startActivity(intent);
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
            Intent addNewReview = new Intent(ListFacultyReviews.this, ConstructReview.class);
            addNewReview.putExtra("type", "faculty");
            addNewReview.putExtra("id", facultyID);
            startActivity(addNewReview); // start the addNewReview Activity
        }
    };

    private View.OnClickListener homeListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            SharedPreferences sharedpreferences = getSharedPreferences(Login.USER_INFO, Context.MODE_PRIVATE);
            String role = sharedpreferences.getString("role", null);
            if (role.equals(USER)) {
                Intent home = new Intent(ListFacultyReviews.this, UserMain.class);
                startActivity(home);
            }
            else {
                Intent home = new Intent(ListFacultyReviews.this, AdminMain.class);
                startActivity(home);
            }
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

    private class ListFacultyReviewHelper extends AsyncTask<Object, Void, Void> {

        private String option = "";
        private ArrayList<thinkers.hmm.model.FacultyReview> facultyReviewList;

        @Override
        protected Void doInBackground(Object... params ) {
            option = (String)params[0];
            if(option.equals(LIST_OPERATION)) {
                FacultyReviewUtil facultyReviewUtil = new FacultyReviewUtil();
                facultyReviewList = facultyReviewUtil.selectFacultyReview(facultyID);
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void object) {
            if(option.equals(LIST_OPERATION)) {
                FacultyReviewAdapter facultyReviewAdapter = new FacultyReviewAdapter(facultyReviewList);
                listFacultyReviewsListView.setAdapter(facultyReviewAdapter);
            }
            return;
        }
    }

    private class FacultyReviewAdapter extends ArrayAdapter<thinkers.hmm.model.CourseReview> {
        public CourseReviewAdapter(ArrayList<thinkers.hmm.model.CourseReview> reviews) {
            super(ListCourseReviews.this, R.layout.ui_list_item_review, reviews);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            // if we weren't given a view, inflate one
            if (convertView == null) {
                convertView = ListCourseReviews.this.getLayoutInflater()
                        .inflate(R.layout.ui_list_item_review, null);
            }

            // configure the view for this Song
            final thinkers.hmm.model.CourseReview review = getItem(position);

            TextView courseTitle = (TextView) convertView.findViewById(R.id.reviewTitle);
            courseTitle.setText(review.getTitle());

            Button likeButton = (Button) convertView.findViewById(R.id.likeButton);
            likeButton.setText(Integer.toString(review.getLike()));

            Button dislikeButton = (Button) convertView.findViewById(R.id.dislikeButton);
            dislikeButton.setText(Integer.toString(review.getDislike()));

            return convertView;
        }

    }
}
