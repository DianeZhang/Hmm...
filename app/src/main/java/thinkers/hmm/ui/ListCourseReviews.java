package thinkers.hmm.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import thinkers.hmm.R;
import thinkers.hmm.model.CourseReview;
import thinkers.hmm.util.CourseReviewUtil;

public class ListCourseReviews extends Activity {
    //Operation String
    private final String LIST_OPERATION = "List_Course_Reviews";
    private int courseID;

    private TextView titleListCourseReviews;
    private Button professor1Button;
    private Button professor2Button;
    private Button professor3Button;
    private Button professor4Button;
    private Button addNewReviewButton;

    private ListView listCourseReviewsListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ui_list_course_reviews);

        // Get courseID from bundle
        courseID =  getIntent().getExtras().getInt("cid");
        Toast.makeText(ListCourseReviews.this, "Course ID:" + courseID, Toast.LENGTH_SHORT).show();

        titleListCourseReviews = (TextView) findViewById(R.id.titleTextView);

        //TODO: find a horizontal list view for professors
        professor1Button = (Button) findViewById(R.id.professor1Button);
        professor2Button = (Button) findViewById(R.id.professor2Button);
        professor3Button = (Button) findViewById(R.id.professor3Button);
        professor4Button = (Button) findViewById(R.id.professor4Button);
        professor1Button.setOnClickListener(viewFacultyReviewListener);

        //Clicking on the button to add new review
        addNewReviewButton = (Button) findViewById(R.id.addNewReviewButton);
        addNewReviewButton.setOnClickListener(addNewReviewListener);

        //Clicking on an item goes to CourseReview page
        listCourseReviewsListView = (ListView) findViewById(R.id.listCourseReviewsListView);
        listCourseReviewsListView.setOnItemClickListener(viewCourseReviewListener);

        //Populate list
        ListCourseReviewHelper listHelper = new ListCourseReviewHelper();
        String[] params= new String[1];
        params[0] = LIST_OPERATION;
        listHelper.execute(params);
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

    // event listener that responds to the user touching a faculty's name
    // in the ListView
    View.OnClickListener viewFacultyReviewListener = new View.OnClickListener()
    {
        @Override
        public void onClick(View v) {
            // create an Intent to launch the ListCourseReviews Activity
            Intent viewFacultyReview = new Intent(ListCourseReviews.this, FacultyReview.class);
            startActivity(viewFacultyReview); // start the viewCourseReviews Activity
        } // end method onClick
    }; // end viewContactListener

    // event-handling object that responds to addNewReview's events
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
            Intent viewCourseReview = new Intent(ListCourseReviews.this, CourseReview.class);
            viewCourseReview.putExtra("rid", review.getId());
            // Start activity
            startActivity(viewCourseReview); // start the viewCourseReviews Activity
        } // end method onItemClick
    }; // end viewContactListener

    private class ListCourseReviewHelper extends AsyncTask<Object, Void, Void> {

        private String option = "";
        private ArrayList<CourseReview> courseReviewList;

        @Override
        protected Void doInBackground(Object... params ) {
            option = (String)params[0];
            if(option.equals(LIST_OPERATION)) {
                CourseReviewUtil courseReviewUtil = new CourseReviewUtil();
                courseReviewList = courseReviewUtil.selectCourseReview(courseID);
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void object) {
            if(option.equals(LIST_OPERATION)) {
                CourseReviewAdapter courseReviewAdapter = new CourseReviewAdapter(courseReviewList);
                listCourseReviewsListView.setAdapter(courseReviewAdapter);
            }
            return;
        }
    }

    private class CourseReviewAdapter extends ArrayAdapter<CourseReview> {
        public CourseReviewAdapter(ArrayList<CourseReview> reviews) {
            super(ListCourseReviews.this, android.R.layout.simple_list_item_1, reviews);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            // if we weren't given a view, inflate one
            if (convertView == null) {
                convertView = ListCourseReviews.this.getLayoutInflater()
                        .inflate(android.R.layout.simple_list_item_1, null);
            }

            // configure the view for this Song
            final CourseReview review = getItem(position);

            TextView courseTitle = (TextView) convertView.findViewById(android.R.id.text1);
            courseTitle.setText(review.getTitle());

            return convertView;
        }
    }
}
