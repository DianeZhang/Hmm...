package thinkers.hmm.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import thinkers.hmm.R;
import thinkers.hmm.model.Course;
import thinkers.hmm.util.CourseReviewUtil;
import thinkers.hmm.util.CourseUtil;

public class CourseReview extends Activity {
    private thinkers.hmm.model.CourseReview courseReview;

    private TextView titleCourseReview;
    private TextView courseReviewContent;
    private RatingBar courseReviewRating;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ui_course_review);

        // Get course review from bundle
        courseReview = (thinkers.hmm.model.CourseReview) getIntent().getBundleExtra("ReviewBundle").getSerializable("Review");
        Toast.makeText(CourseReview.this, "Review ID:" + courseReview.getId(), Toast.LENGTH_SHORT).show();

        titleCourseReview = (TextView) findViewById(R.id.titleCourseReview);
        titleCourseReview.setText(courseReview.getTitle());

        courseReviewContent = (TextView) findViewById(R.id.courseReviewContent);
        courseReviewContent.setText(courseReview.getContent());

        courseReviewRating = (RatingBar) findViewById(R.id.ratingBar);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //getMenuInflater().inflate(R.menu.menu_course_review, menu);
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
            Intent intent = new Intent(CourseReview.this, Login.class);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
