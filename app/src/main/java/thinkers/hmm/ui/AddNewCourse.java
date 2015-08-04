package thinkers.hmm.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import thinkers.hmm.R;
import thinkers.hmm.model.Course;
import thinkers.hmm.util.CourseUtil;

public class AddNewCourse extends Activity {
    //Operation String
    private final String ADD_COURSE_OPERATION = "Add_Course";

    private EditText editName;
    private EditText editCode;
    private EditText editSchool;
    private Button submitNewCourseButton;
    private Button cancelNewCourseButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ui_add_new_course);

        editName = (EditText) findViewById(R.id.editName);
        editCode = (EditText) findViewById(R.id.editCode);
        editSchool = (EditText) findViewById(R.id.editSchool);

        //Clicking on the button to add new Courses
        submitNewCourseButton = (Button) findViewById(R.id.button);
        submitNewCourseButton.setOnClickListener(submitNewCourseListener);

        //Clicking on an item goes to ListCourseReviews page
        cancelNewCourseButton = (Button) findViewById(R.id.button2);
        cancelNewCourseButton.setOnClickListener(cancelNewCourseListener);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_add_new_course, menu);
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
            Intent intent = new Intent(AddNewCourse.this, Login.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private View.OnClickListener submitNewCourseListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            AddCourseHelper addHelper = new AddCourseHelper();
            String[] params= new String[1];
            params[0] = ADD_COURSE_OPERATION;
            addHelper.execute(params);
        }
    };

    private View.OnClickListener cancelNewCourseListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent cancelNewCourse = new Intent(AddNewCourse.this, ListCourses.class);
            startActivity(cancelNewCourse);
        }
    };

    private class AddCourseHelper extends AsyncTask<Object, Void, Void> {

        private String option = "";

        @Override
        protected Void doInBackground(Object... params ) {
            option = (String)params[0];
            if(option.equals(ADD_COURSE_OPERATION)) {
                CourseUtil courseUtil = new CourseUtil();
                Course newCourse = new Course(editName.getText().toString(),
                        editCode.getText().toString(), editSchool.getText().toString());
                courseUtil.insertCourse(newCourse);
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void object) {
            if(option.equals(ADD_COURSE_OPERATION)) {
                Intent submitNewCourse = new Intent(AddNewCourse.this, ListCourses.class);
                startActivity(submitNewCourse);
            }
            return;
        }
    }
}
