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
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import thinkers.hmm.R;
import thinkers.hmm.model.*;
import thinkers.hmm.model.FacultyReview;
import thinkers.hmm.util.CourseReviewDraftUtil;
import thinkers.hmm.util.CourseUtil;
import thinkers.hmm.util.FacultyReviewDraftUtil;

public class ListMyDrafts extends Activity {

    //Operation String
    private final String LIST_MYDRAFT = "List_MyDrafts";
    private final String Draft_CONTENT = "draft_content";
    private final String COURSE_TYPE = "course";
    private final String FACULTY_TYPE = "faculty";

    //Widgets
    private ListView myCourseReviewDraftListView;
    private ListView myFacultyReviewDraftListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ui_list_my_drafts);

        //Get elements
        myCourseReviewDraftListView = (ListView)findViewById(R.id.DraftListViewCourse);
        myFacultyReviewDraftListView = (ListView)findViewById(R.id.DraftListViewFaculty);

        ListDraftHelper listMyDraftHelper = new ListDraftHelper();
        String[] params= new String[1];
        params[0] = LIST_MYDRAFT;
        listMyDraftHelper.execute(params);

        myCourseReviewDraftListView.setOnItemClickListener(viewCourseReviewDraftListener);
        myFacultyReviewDraftListView.setOnItemClickListener(viewFacultyReviewDraftListener);



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

        return super.onOptionsItemSelected(item);
    }

    private AdapterView.OnItemClickListener viewCourseReviewDraftListener = new AdapterView.OnItemClickListener()
    {
        @Override
        public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3)
        {
            thinkers.hmm.model.CourseReviewDraft courseReviewDraft = (thinkers.hmm.model.CourseReviewDraft) arg0.getAdapter().getItem(arg2);
            Log.d("OnItemClick", Integer.toString(courseReviewDraft.getId()));
            // create an Intent to launch the CourseReview Activity
            Intent viewCourseReviewDraft = new Intent(ListMyDrafts.this, thinkers.hmm.ui.ConstructReview.class);
            Bundle bundle = new Bundle();
            bundle.putSerializable(Draft_CONTENT, courseReviewDraft);
            viewCourseReviewDraft.putExtras(bundle);
            viewCourseReviewDraft.putExtra("type", COURSE_TYPE);
            startActivity(viewCourseReviewDraft); // start the ConstructReview Activity
        } // end method onItemClick
    }; // end viewListener


    private AdapterView.OnItemClickListener viewFacultyReviewDraftListener = new AdapterView.OnItemClickListener()
    {
        @Override
        public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3)
        {
            FacultyReviewDraft facultyReviewDraft = (FacultyReviewDraft) arg0.getAdapter().getItem(arg2);
            Log.d("OnItemClick", Integer.toString(facultyReviewDraft.getId()));
            // create an Intent to launch the CourseReview Activity
            Intent viewFacultyReviewDraft= new Intent(ListMyDrafts.this, thinkers.hmm.ui.ConstructReview.class);
            Bundle bundle = new Bundle();
            bundle.putSerializable(Draft_CONTENT, facultyReviewDraft);
            viewFacultyReviewDraft.putExtras(bundle);
            viewFacultyReviewDraft.putExtra("type", FACULTY_TYPE);
            startActivity(viewFacultyReviewDraft); // start the ConstructReview Activity
        } // end method onItemClick
    }; // end viewListener

    private class ListDraftHelper extends AsyncTask<Object, Void, Void> {

        private String option = "";
        private ArrayList<CourseReviewDraft> courseReviewDraftList;
        private ArrayList<FacultyReviewDraft> facultyReviewDraftList;

        @Override
        protected Void doInBackground(Object... params ) {
            option = (String)params[0];
            SharedPreferences sharedpreferences = getSharedPreferences(Login.USER_INFO, Context.MODE_PRIVATE);
            int uid = sharedpreferences.getInt("uid",0);

            if(option.equals(LIST_MYDRAFT)) {
                CourseReviewDraftUtil courseReviewDraftUtil = new CourseReviewDraftUtil();
                courseReviewDraftList = courseReviewDraftUtil.selectDraftByUid(uid);

                FacultyReviewDraftUtil facultyReviewDraftUtil = new FacultyReviewDraftUtil();
                facultyReviewDraftList = facultyReviewDraftUtil.selectDraftByUid(uid);
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void object) {
            if(option.equals(LIST_MYDRAFT)) {
                CourseReviewDraftAdapter courseReviewDraftAdapter = new CourseReviewDraftAdapter(courseReviewDraftList);
                myCourseReviewDraftListView.setAdapter(courseReviewDraftAdapter);

                FacultyReviewDraftAdapter facultyReviewDraftAdapter = new FacultyReviewDraftAdapter(facultyReviewDraftList);
                myFacultyReviewDraftListView.setAdapter(facultyReviewDraftAdapter);
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

            // configure the view for this draft
            final thinkers.hmm.model.CourseReviewDraft courseReviewDraft = getItem(position);

            TextView courseReviewDraftTitle = (TextView) convertView.findViewById(android.R.id.text1);
            courseReviewDraftTitle.setText(courseReviewDraft.getTitle());

            return convertView;
        }
    }

    private class FacultyReviewDraftAdapter extends ArrayAdapter<thinkers.hmm.model.FacultyReviewDraft> {
        public FacultyReviewDraftAdapter(ArrayList<thinkers.hmm.model.FacultyReviewDraft> facultyReviewsDrafts) {
            super(ListMyDrafts.this, android.R.layout.simple_list_item_1, facultyReviewsDrafts);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            // if we weren't given a view, inflate one
            if (convertView == null) {
                convertView = ListMyDrafts.this.getLayoutInflater()
                        .inflate(android.R.layout.simple_list_item_1, null);
            }

            // configure the view for this draft
            final FacultyReviewDraft facultyReviewDraft = getItem(position);

            TextView facultyReviewTitle = (TextView) convertView.findViewById(android.R.id.text1);
            facultyReviewTitle.setText(facultyReviewDraft.getTitle());

            return convertView;
        }
    }
}
