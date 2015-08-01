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
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import thinkers.hmm.R;
import thinkers.hmm.model.Course;
import thinkers.hmm.model.Faculty;
import thinkers.hmm.model.FacultyReview;
import thinkers.hmm.util.CourseFacultyRelationshipUtil;
import thinkers.hmm.util.CourseUtil;
import thinkers.hmm.util.FacultyReviewUtil;
import thinkers.hmm.util.FacultyUtil;

public class ListFacultyReviews extends Activity {

    private final String LIST_FACULTY_REVIEWS = "List_Faculty_Reviews";
    private final String LIST_COURSES = "List_Facultys";
    private int facultyID;

    private TextView titleListFacultyReviews;
    private Button faculty1Button;
    private Button faculty2Button;
    private Button faculty3Button;
    private Button faculty4Button;
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
        params[0] = LIST_FACULTY_REVIEWS;
        listHelper.execute(params);

        //Show relevant professors
        ListFacultyReviewHelper listFacultysHelper = new ListFacultyReviewHelper();
        String[] facultyParams= new String[1];
        facultyParams[0] = LIST_COURSES;
        listFacultysHelper.execute(facultyParams);

        //Show add new review button to users
        SharedPreferences sharedpreferences = getSharedPreferences(Login.USER_INFO, Context.MODE_PRIVATE);
        String role = sharedpreferences.getString("role", null);

        if (role.equals(USER)) {
            Button addFacultyReviewButton = new Button(ListFacultyReviews.this);
            addFacultyReviewButton.setText("New Review");
            RelativeLayout rl = (RelativeLayout) findViewById(R.id.relativeLayout);
            RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(
                    RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
            addFacultyReviewButton.setOnClickListener(addNewReviewListener);
            rl.addView(addFacultyReviewButton, lp);
        }
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
        public void onItemClick(AdapterView<?> parent, View view, int position, long id)
        {
            // Get selected faculty review from adapter
            FacultyReview review = (FacultyReview) parent.getAdapter().getItem(position);
            Toast.makeText(ListFacultyReviews.this, "Review ID:" + review.getId(), Toast.LENGTH_SHORT).show();

            // Put in extras
            Intent viewFacultyReview = new Intent(ListFacultyReviews.this, thinkers.hmm.ui.FacultyReview.class);
            Bundle bundle = new Bundle();
            bundle.putSerializable("Review", review);
            viewFacultyReview.putExtra("ReviewBundle", bundle);
            // Start activity
            startActivity(viewFacultyReview); // start the viewFacultyReviews Activity
        } // end method onItemClick
    }; // end viewContactListener

    private class ListFacultyReviewHelper extends AsyncTask<Object, Void, Void> {

        private String option = "";
        private ArrayList<FacultyReview> facultyReviewList = null;
        private ArrayList<Course> courses = new ArrayList<>();

        @Override
        protected Void doInBackground(Object... params ) {
            option = (String)params[0];
            if(option.equals(LIST_FACULTY_REVIEWS)) {
                FacultyReviewUtil facultyReviewUtil = new FacultyReviewUtil();
                facultyReviewList = facultyReviewUtil.selectFacultyReview(facultyID);
            } else if (option.equals(LIST_COURSES)) {
                CourseFacultyRelationshipUtil courseFacultyRelationshipUtil = new CourseFacultyRelationshipUtil();
                ArrayList<Integer> courseIDs = courseFacultyRelationshipUtil.selectCourses(facultyID);

                CourseUtil courseUtil = new CourseUtil();
                for (int cid : courseIDs) {
                    Course course = courseUtil.selectCourse(cid);
                    courses.add(course);
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void object) {
            if(option.equals(LIST_FACULTY_REVIEWS)) {
                FacultyReviewAdapter facultyReviewAdapter = new FacultyReviewAdapter(facultyReviewList);
                listFacultyReviewsListView.setAdapter(facultyReviewAdapter);
            } else if (option.equals(LIST_COURSES)) {
                LinearLayout rl = (LinearLayout) findViewById(R.id.classesLayout);
                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                        220, LinearLayout.LayoutParams.WRAP_CONTENT);
                for (Course course : courses) {
                    Button courseButton = new Button(ListFacultyReviews.this);
                    courseButton.setText(course.getName());
                    final Course c = course;
                    courseButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent listCourseReviews = new Intent(getApplication(), ListCourseReviews.class);
                            listCourseReviews.putExtra("cid", c.getId());
                            startActivity(listCourseReviews);
                        }
                    });
                    rl.addView(courseButton, lp);
                }
            }
            return;
        }
    }

    private class FacultyReviewAdapter extends ArrayAdapter<FacultyReview> {
        public FacultyReviewAdapter(ArrayList<FacultyReview> reviews) {
            super(ListFacultyReviews.this, R.layout.ui_list_item_review, reviews);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            // if we weren't given a view, inflate one
            if (convertView == null) {
                convertView = ListFacultyReviews.this.getLayoutInflater()
                        .inflate(R.layout.ui_list_item_review, null);
            }

            // configure the view for this Song
            final FacultyReview review = getItem(position);

            TextView facultyTitle = (TextView) convertView.findViewById(R.id.reviewTitle);
            facultyTitle.setText(review.getTitle());

            Button likeButton = (Button) convertView.findViewById(R.id.likeButton);
            likeButton.setText(Integer.toString(review.getLike()));

            Button dislikeButton = (Button) convertView.findViewById(R.id.dislikeButton);
            dislikeButton.setText(Integer.toString(review.getDislike()));

            return convertView;
        }

    }
}
