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
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import thinkers.hmm.R;
import thinkers.hmm.model.Course;
import thinkers.hmm.model.CourseReview;
import thinkers.hmm.model.Faculty;
import thinkers.hmm.model.FacultyReview;
import thinkers.hmm.model.Review;
import thinkers.hmm.model.User;
import thinkers.hmm.util.CourseReviewUtil;
import thinkers.hmm.util.FacultyReviewUtil;
import thinkers.hmm.util.UserUtil;

public class ListMyReviews extends Activity {

    //Operation String
    private final String LIST_MYREVIEW = "List_MyReviews";
    private final String REVIEW_ID = "review_id";

    //Widgets
    private TextView titleMyReview;
    private ListView myCourseReviewsListView;
    private ListView myFacultyReviewsListView;

    private ListView myReviews;
    private ImageButton homeButton;

    public static final String USER = "user";
    public static final String ADMIN = "admin";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ui_list_my_reviews);

        titleMyReview = (TextView) findViewById(R.id.textView);
        myCourseReviewsListView = (ListView) findViewById(R.id.listView);
        myFacultyReviewsListView = (ListView) findViewById(R.id.listView2);

        ListMyReviewHelper listMyReviewHelper = new ListMyReviewHelper();
        String[] params= new String[1];
        params[0] = LIST_MYREVIEW;
        listMyReviewHelper.execute(params);

        //Get elements
        myCourseReviewsListView.setOnItemClickListener(viewCourseReviewListener);
        myFacultyReviewsListView.setOnItemClickListener(viewFacultyReviewListener);


        homeButton = (ImageButton) findViewById(R.id.homeButton);
        homeButton.setOnClickListener(homeListener);



    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_list_my_reviews, menu);
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

    private AdapterView.OnItemClickListener viewCourseReviewListener = new AdapterView.OnItemClickListener()
    {
        @Override
        public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3)
        {
            CourseReview courseReview = (CourseReview) arg0.getAdapter().getItem(arg2);
            Log.d("OnItemClick", Integer.toString(courseReview.getUid()));
            // create an Intent to launch the CourseReview Activity
            Intent viewCourseReview = new Intent(ListMyReviews.this, thinkers.hmm.ui.CourseReview.class);
            viewCourseReview.putExtra(REVIEW_ID, courseReview.getId());
            startActivity(viewCourseReview); // start the viewCourseReview Activity
        } // end method onItemClick
    }; // end viewListener


    private AdapterView.OnItemClickListener viewFacultyReviewListener = new AdapterView.OnItemClickListener()
    {
        @Override
        public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3)
        {
            FacultyReview facultyReview = (FacultyReview) arg0.getAdapter().getItem(arg2);
            Log.d("OnItemClick", Integer.toString(facultyReview.getUid()));
            // create an Intent to launch the CourseReview Activity
            Intent viewFacultyReview = new Intent(ListMyReviews.this, thinkers.hmm.ui.FacultyReview.class);
            viewFacultyReview.putExtra(REVIEW_ID, facultyReview.getId());
            startActivity(viewFacultyReview); // start the viewCourseReview Activity
        } // end method onItemClick
    }; // end viewListener


    private View.OnClickListener homeListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            SharedPreferences sharedpreferences = getSharedPreferences(Login.USER_INFO, Context.MODE_PRIVATE);
            String role = sharedpreferences.getString("role", null);
            if (role.equals(USER)) {
                Intent home = new Intent(ListMyReviews.this, UserMain.class);
                startActivity(home);
            }
            else {
                Intent home = new Intent(ListMyReviews.this, AdminMain.class);
                startActivity(home);
            }
        }
    };


    private class ListMyReviewHelper extends AsyncTask<Object, Void, Void> {

        private String option = "";
        private ArrayList<CourseReview> myCourseReviews;
        private ArrayList<FacultyReview> myFacultyReviews;

        @Override
        protected Void doInBackground(Object... params ) {
            option = (String)params[0];
            SharedPreferences sharedpreferences = getSharedPreferences(Login.USER_INFO, Context.MODE_PRIVATE);
            int uid = sharedpreferences.getInt("uid",0);

            if(option.equals(LIST_MYREVIEW)) {
                CourseReviewUtil myCourseReviewUtil = new CourseReviewUtil();
                myCourseReviews = myCourseReviewUtil.selectCourseReviewByUserId(uid);

                FacultyReviewUtil myFacultyReviewUtil = new FacultyReviewUtil();
                myFacultyReviews = myFacultyReviewUtil.selectFacultyReviewByUserId(uid);
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void object) {
            if(option.equals(LIST_MYREVIEW)) {
                CourseReviewAdapter courseReviewAdapter = new CourseReviewAdapter(myCourseReviews);
                myCourseReviewsListView.setAdapter(courseReviewAdapter);

                FacultyReviewAdapter facultyReviewAdapter = new FacultyReviewAdapter(myFacultyReviews);
                myFacultyReviewsListView.setAdapter(facultyReviewAdapter);
            }
            return;
        }

    }

    private class CourseReviewAdapter extends ArrayAdapter<CourseReview> {
        public CourseReviewAdapter(ArrayList<CourseReview> courseReviews) {
            super(ListMyReviews.this, android.R.layout.simple_list_item_1, courseReviews);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            // if we weren't given a view, inflate one
            if (convertView == null) {
                convertView = ListMyReviews.this.getLayoutInflater()
                        .inflate(android.R.layout.simple_list_item_1, null);
            }

            // configure the view for this review
            final CourseReview courseReview = getItem(position);

            TextView courseReviewTitle = (TextView) convertView.findViewById(android.R.id.text1);
            courseReviewTitle.setText(courseReview.getTitle());

            return convertView;
        }
    }

    private class FacultyReviewAdapter extends ArrayAdapter<FacultyReview> {
        public FacultyReviewAdapter(ArrayList<FacultyReview> facultyReviews) {
            super(ListMyReviews.this, android.R.layout.simple_list_item_1, facultyReviews);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            // if we weren't given a view, inflate one
            if (convertView == null) {
                convertView = ListMyReviews.this.getLayoutInflater()
                        .inflate(android.R.layout.simple_list_item_1, null);
            }

            // configure the view for this review
            final FacultyReview facultyReview = getItem(position);

            TextView facultyReviewTitle = (TextView) convertView.findViewById(android.R.id.text1);
            facultyReviewTitle.setText(facultyReview.getTitle());

            return convertView;
        }
    }
}
