package thinkers.hmm.ui;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import thinkers.hmm.R;
import android.content.Intent;
import android.widget.EditText;
import android.widget.Toast;

import thinkers.hmm.model.*;
import thinkers.hmm.model.CourseReview;
import thinkers.hmm.util.CourseReviewDraftUtil;
import thinkers.hmm.util.CourseReviewUtil;
import thinkers.hmm.model.FacultyReview;
import thinkers.hmm.util.FacultyReviewDraftUtil;
import thinkers.hmm.util.FacultyReviewUtil;

public class ConstructReview extends Activity {

    private final String TAG = "ConstrcutRevew";
    private final String SUBMIT_OPERATION = "submit";
    private final String SAVE_OPEARTION = "save";
    private final String COURSE_TYPE = "course";
    private final String FACULTY_TYPE = "faculty";
    private final String SUBMIT_SUCCEED_MSG = "Review Submitted!";
    private final String SUBMIT_FAILED_MSG = "Failed to Submit!";
    private final String SAVE_SUCCEED_MSG = "Review Saved!";
    private final String SAVE_FAILED_MSG = "Falied to Save!";

    //Widgets
    private Button cancelButton = null;
    private Button submitbutton = null;
    private Button saveButton = null;
    private EditText titleText = null;
    private EditText contentText = null;

    //Review Variables
    private String type = null;
    private int id = -1;
    private int uid = -1;

    //Async Helper
    ConstructReviewHelper constructReviewHelper = new ConstructReviewHelper();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ui_construct_review);

        //Get UID
        SharedPreferences sharedpreferences = getSharedPreferences(Login.USER_INFO, Context.MODE_PRIVATE);
        uid = sharedpreferences.getInt("uid", -1);
        if(uid == -1) {
            Log.d(TAG, "Invalid UID:" + uid);
            onBackPressed();
            return;
        }

        //Get review type
        Intent intent = getIntent();
        type = intent.getStringExtra("type");
        id = intent.getIntExtra("id", -1);
        if(type == null || id == -1) {
            Log.d(TAG, "Type:" + type + ",id"+id);
            onBackPressed();
            return;
        }

        //Get elements
        submitbutton = (Button)findViewById(R.id.submitButton);
        saveButton = (Button)findViewById(R.id.saveButton);
        cancelButton = (Button)findViewById(R.id.cancelButton);
        titleText = (EditText)findViewById(R.id.titleText);
        contentText = (EditText)findViewById(R.id.contentText);

        //Set Button Dispatcher
        cancelButton.setOnClickListener(cancelAction);
        submitbutton.setOnClickListener(submitAction);
        saveButton.setOnClickListener(saveAction);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_construct_review, menu);
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

    /** Button Click Dispatcher */
    private View.OnClickListener cancelAction = new View.OnClickListener(){
        @Override
        public void onClick(View view) {
            Intent intent = new Intent(ConstructReview.this, ListMyDrafts.class);
            startActivity(intent);
        }
    };

    private View.OnClickListener submitAction = new View.OnClickListener(){
        public void onClick(View v) {
            String title = titleText.getText().toString();
            String content = contentText.getText().toString();

            String[] params = new String[4];
            params[0] = SUBMIT_OPERATION;
            params[1] = title;
            params[2] = content;
            params[3] = type;
            constructReviewHelper.execute(params);
        }
    };

    private View.OnClickListener saveAction = new View.OnClickListener() {
        public void onClick(View v) {
            String title = titleText.getText().toString();
            String content = contentText.getText().toString();

            String[] params = new String[4];
            params[0] = SAVE_OPEARTION;
            params[1] = title;
            params[2] = content;
            params[3] = type;
            constructReviewHelper.execute(params);
        }
    };

    private class ConstructReviewHelper extends AsyncTask<Object, Void, Void> {

        private String option = "";
        private String type = "";
        private String title = "";
        private String content = "";
        private boolean result = false;

        //TODO ADD LOCATION INFO

        @Override
        protected Void doInBackground(Object... params) {
            //Get parameters
            option = (String) params[0];
            title = (String) params[1];
            content = (String) params[2];
            type = (String) params[3];

            if(type.equals(COURSE_TYPE)) {

                if(option.equals(SUBMIT_OPERATION)) {
                    //Construct Course Review
                    CourseReview review = new CourseReview(
                            id, uid, title,content,"location", null);
                    //Insert into DB
                    CourseReviewUtil courseReviewUtil =
                            new CourseReviewUtil();
                    result = courseReviewUtil.insertCourseReview(review);
                } else {
                    //Construct Course Review Draft
                    CourseReviewDraft review = new CourseReviewDraft(
                            id, uid, title,content);
                    //Insert into DB
                    CourseReviewDraftUtil courseReviewDraftUtil =
                            new CourseReviewDraftUtil();
                    result = courseReviewDraftUtil.insertCourseReviewDraft(review);
                }

            } else {

                if(option.equals(SUBMIT_OPERATION)) {
                    //Construct Faculty Review
                    FacultyReview review = new FacultyReview(
                            uid, id, title, content, "location", null);
                    //Insert into DB
                    FacultyReviewUtil facultyReviewUtil =
                            new FacultyReviewUtil();
                    result = facultyReviewUtil.insertFacultyReview(review);
                } else {
                    //Construct Faculty Review Draft
                    FacultyReviewDraft facultyReviewDraft = new FacultyReviewDraft(
                            id, uid, title, content);
                    //Insert into DB
                    FacultyReviewDraftUtil facultyReviewDraftUtil =
                            new FacultyReviewDraftUtil();
                    result = facultyReviewDraftUtil.insertFacultyReviewDraft(facultyReviewDraft);

                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void object) {
            if(option.equals(SUBMIT_OPERATION)) {
                if(result == true) {
                    Toast.makeText(ConstructReview.this, SUBMIT_SUCCEED_MSG,Toast.LENGTH_SHORT).show();
                    onBackPressed();
                } else {
                    Toast.makeText(ConstructReview.this, SUBMIT_FAILED_MSG,Toast.LENGTH_SHORT).show();
                }
            } else {
                if(result == true) {
                    Toast.makeText(ConstructReview.this, SAVE_SUCCEED_MSG,Toast.LENGTH_SHORT).show();
                    onBackPressed();
                } else {
                    Toast.makeText(ConstructReview.this, SAVE_FAILED_MSG,Toast.LENGTH_SHORT).show();
                }
            }
        }
    }
}
