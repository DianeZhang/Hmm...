package thinkers.hmm.ui;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
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
import thinkers.hmm.model.CourseReview;
import thinkers.hmm.model.Faculty;
import thinkers.hmm.util.CourseFacultyRelationshipUtil;
import thinkers.hmm.util.CourseReviewUtil;
import thinkers.hmm.util.FacultyUtil;

public class ListCourseReviews extends Activity {
    //Operation String
    private final String LIST_COURSE_REVIEWS = "List_Course_Reviews";
    private final String LIST_FACULTIES = "List_Faculties";
    private int courseID;

    private TextView titleListCourseReviews;
    private ImageButton homeButton;

    public static final String USER = "user";
    public static final String ADMIN = "admin";

    private ListView listCourseReviewsListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ui_list_course_reviews);

        // Get courseID from bundle
        courseID =  getIntent().getExtras().getInt("cid");
        Toast.makeText(ListCourseReviews.this, "Course ID:" + courseID, Toast.LENGTH_SHORT).show();

        titleListCourseReviews = (TextView) findViewById(R.id.titleTextView);

        //Clicking on an item goes to CourseReview page
        listCourseReviewsListView = (ListView) findViewById(R.id.listCourseReviewsListView);
        listCourseReviewsListView.setOnItemClickListener(viewCourseReviewListener);

        homeButton = (ImageButton) findViewById(R.id.homeButton);
        homeButton.setOnClickListener(homeListener);

        //Show reviews
        ListCourseReviewHelper listReviewsHelper = new ListCourseReviewHelper();
        String[] reviewParams= new String[1];
        reviewParams[0] = LIST_COURSE_REVIEWS;
        listReviewsHelper.execute(reviewParams);

        //Show relevant professors
        ListCourseReviewHelper listFacultiesHelper = new ListCourseReviewHelper();
        String[] facultyParams= new String[1];
        facultyParams[0] = LIST_FACULTIES;
        listFacultiesHelper.execute(facultyParams);

        //Show add new review button to users
        SharedPreferences sharedpreferences = getSharedPreferences(Login.USER_INFO, Context.MODE_PRIVATE);
        String role = sharedpreferences.getString("role", null);

        if (role.equals(USER)) {
            Button addCourseReviewButton = new Button(ListCourseReviews.this);
            addCourseReviewButton.setText("New Review");
            RelativeLayout rl = (RelativeLayout) findViewById(R.id.relativeLayout);
            RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(
                    RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
            addCourseReviewButton.setOnClickListener(addNewReviewListener);
            rl.addView(addCourseReviewButton, lp);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_list_course_reviews, menu);
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

    private View.OnClickListener homeListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            SharedPreferences sharedpreferences = getSharedPreferences(Login.USER_INFO, Context.MODE_PRIVATE);
            String role = sharedpreferences.getString("role", null);
            if (role.equals(USER)) {
                Intent home = new Intent(ListCourseReviews.this, UserMain.class);
                startActivity(home);
            }
            else {
                Intent home = new Intent(ListCourseReviews.this, AdminMain.class);
                startActivity(home);
            }
        }
    };

    private View.OnClickListener addNewReviewListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent addNewReview = new Intent(ListCourseReviews.this, ConstructReview.class);
            addNewReview.putExtra("type", "course");
            addNewReview.putExtra("id", courseID);
            startActivity(addNewReview); // start the addNewReview Activity
        }
    };



    // event listener that responds to the user touching a review's name
    // in the ListView
    AdapterView.OnItemClickListener viewCourseReviewListener = new AdapterView.OnItemClickListener()
    {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id)
        {
            // Get selected course review from adapter
            CourseReview review = (CourseReview) parent.getAdapter().getItem(position);
            Toast.makeText(ListCourseReviews.this, "Review ID:" + review.getId(), Toast.LENGTH_SHORT).show();

            // Put in extras
            Intent viewCourseReview = new Intent(ListCourseReviews.this, thinkers.hmm.ui.CourseReview.class);
            Bundle bundle = new Bundle();
            bundle.putSerializable("Review", review);
            viewCourseReview.putExtra("ReviewBundle", bundle);
            // Start activity
            startActivity(viewCourseReview); // start the viewCourseReviews Activity
        } // end method onItemClick
    }; // end viewContactListener

    private class ListCourseReviewHelper extends AsyncTask<Object, Void, Void> {

        private String option = "";
        private ArrayList<CourseReview> courseReviewList = null;
        private ArrayList<Faculty> faculties = new ArrayList<>();

        @Override
        protected Void doInBackground(Object... params ) {
            option = (String)params[0];
            if(option.equals(LIST_COURSE_REVIEWS)) {
                CourseReviewUtil courseReviewUtil = new CourseReviewUtil();
                courseReviewList = courseReviewUtil.selectCourseReviewsByCourseId(courseID);
            } else if (option.equals(LIST_FACULTIES)) {
                CourseFacultyRelationshipUtil courseFacultyRelationshipUtil = new CourseFacultyRelationshipUtil();
                ArrayList<Integer> facultyIDs = courseFacultyRelationshipUtil.selectFaculties(courseID);

                FacultyUtil facultyUtil = new FacultyUtil();
                for (int fid: facultyIDs) {
                    Faculty faculty = facultyUtil.selectFaculty(fid);
                    faculties.add(faculty);
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void object) {
            if(option.equals(LIST_COURSE_REVIEWS)) {
                CourseReviewAdapter courseReviewAdapter = new CourseReviewAdapter(courseReviewList);
                listCourseReviewsListView.setAdapter(courseReviewAdapter);
            } else if (option.equals(LIST_FACULTIES)) {
                LinearLayout rl = (LinearLayout) findViewById(R.id.professorsLayout);
                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                        220, LinearLayout.LayoutParams.WRAP_CONTENT);
                for (Faculty faculty: faculties) {
                    Button facultyButton = new Button(ListCourseReviews.this);
                    facultyButton.setText(faculty.getName());
                    final Faculty f = faculty;
                    facultyButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent listFacultyReviews = new Intent(getApplication(), ListFacultyReviews.class);
                            listFacultyReviews.putExtra("fid", f.getId());
                            startActivity(listFacultyReviews);
                        }
                    });
                    rl.addView(facultyButton, lp);
                }
            }
            return;
        }
    }

    private class CourseReviewAdapter extends ArrayAdapter<CourseReview> {
        public CourseReviewAdapter(ArrayList<CourseReview> reviews) {
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
            final CourseReview review = getItem(position);

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
