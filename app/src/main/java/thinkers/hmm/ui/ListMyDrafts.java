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
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import thinkers.hmm.R;
import thinkers.hmm.model.*;
import thinkers.hmm.model.FacultyReview;
import thinkers.hmm.util.CourseReviewDraftUtil;
import thinkers.hmm.util.CourseUtil;

public class ListMyDrafts extends Activity {

    //Operation String
    private final String LIST_MYDRAFT = "List_MyDrafts";
    private final String Draft_ID = "review_id";

    //Widgets
    private ListView myCourseReviewDraftListView;
    private ListView myFacultyReviewDraftListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ui_list_my_drafts);

        //Get elements
        myCourseReviewDraftListView = (ListView)findViewById(R.id.DraftListView);
        myCourseReviewDraftListView.setOnItemClickListener(selectMyCourseReviewDraft);

        //Test
        ArrayList<String> test = new ArrayList<String>();
        test.add("1");
        test.add("2");
        test.add("3");

        ArrayAdapter<String> testAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, test);
     //   listView.setAdapter(testAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_list_my_draft, menu);
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
            Intent intent = new Intent(ListMyDrafts.this, Login.class);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /** Item Select Action */
    private AdapterView.OnItemClickListener selectMyCourseReviewDraft = new AdapterView.OnItemClickListener(){
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position,long arg3) {
            Intent intent = new Intent(ListMyDrafts.this, ConstructReview.class);
            startActivity(intent);
        }
    };

    private class ListDraftHelper extends AsyncTask<Object, Void, Void> {

        private String option = "";
        private ArrayList<Course> courseReviewDraftList;

        @Override
        protected Void doInBackground(Object... params ) {
            option = (String)params[0];
            if(option.equals(LIST_MYDRAFT)) {
                CourseReviewDraftUtil courseReviewDraftUtil = new CourseReviewDraftUtil();
            //    courseReviewDraftList = courseReviewDraftUtil.;
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void object) {
            if(option.equals(LIST_MYDRAFT)) {
       //         CourseReviewDraftAdapter courseReviewDraftAdapter = new CourseReviewDraftAdapter(courseList);
       //         myCourseReviewDraftListView.setAdapter(courseReviewDraftAdapter);
            }
            return;
        }
    }

    private class CourseReviewDraftAdapter extends ArrayAdapter<thinkers.hmm.model.CourseReviewDraft> {
        public CourseReviewDraftAdapter(ArrayList<thinkers.hmm.model.CourseReviewDraft> courseReviewDrafts) {
            super(ListMyDrafts.this, android.R.layout.simple_list_item_1, courseReviewDrafts);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            // if we weren't given a view, inflate one
            if (convertView == null) {
                convertView = ListMyDrafts.this.getLayoutInflater()
                        .inflate(android.R.layout.simple_list_item_1, null);
            }

            // configure the view for this Song
            final thinkers.hmm.model.CourseReviewDraft courseReviewDraft = getItem(position);

            TextView courseReviewDraftTitle = (TextView) convertView.findViewById(android.R.id.text1);
            courseReviewDraftTitle.setText(courseReviewDraft.getTitle());

            return convertView;
        }
    }

    private class FacultyReviewDraftAdapter extends ArrayAdapter<thinkers.hmm.model.FacultyReview> {
        public FacultyReviewDraftAdapter(ArrayList<thinkers.hmm.model.FacultyReview> facultyReviews) {
            super(ListMyDrafts.this, android.R.layout.simple_list_item_1, facultyReviews);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            // if we weren't given a view, inflate one
            if (convertView == null) {
                convertView = ListMyDrafts.this.getLayoutInflater()
                        .inflate(android.R.layout.simple_list_item_1, null);
            }

            // configure the view for this Song
            final FacultyReview facultyReview = getItem(position);

            TextView facultyReviewTitle = (TextView) convertView.findViewById(android.R.id.text1);
            facultyReviewTitle.setText(facultyReview.getTitle());

            return convertView;
        }
    }
}
