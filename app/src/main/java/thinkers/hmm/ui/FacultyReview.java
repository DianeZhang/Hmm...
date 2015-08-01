package thinkers.hmm.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;

import thinkers.hmm.R;

public class FacultyReview extends Activity {

    private TextView titleFacultyReview;
    private TextView facultyReviews;
    private Button likeButton;
    private Button dislikeButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ui_faculty_review);

        titleFacultyReview = (TextView) findViewById(R.id.reviewTitle);
        facultyReviews = (TextView) findViewById(R.id.reviewContent);


        likeButton = (Button) findViewById(R.id.likeButton);
        dislikeButton = (Button) findViewById(R.id.dislikeButton);
        
        titleFacultyReview = (TextView) findViewById(R.id.titleCourseReview);
        facultyReviews = (TextView) findViewById(R.id.courseReviewContent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_faculty_review, menu);
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
            Intent intent = new Intent(FacultyReview.this, Login.class);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
