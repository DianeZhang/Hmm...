package thinkers.hmm.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import thinkers.hmm.R;
import thinkers.hmm.model.Course;
import thinkers.hmm.model.User;
import thinkers.hmm.util.CourseReviewUtil;
import thinkers.hmm.util.CourseUtil;
import thinkers.hmm.util.UserUtil;

public class CourseReview extends Activity {
    //Operation Strings
    private static final String GET_AUTHOR = "Get_Author";
    private static final String LIKE = "Like";
    private static final String DISLIKE = "Dislike";
    private thinkers.hmm.model.CourseReview courseReview;

    private TextView reviewTitle;
    private TextView reviewContent;

    private TextView reviewAuthor;
    private Button likeButton;
    private Button dislikeButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ui_course_review);

        // Get course review from bundle
        courseReview = (thinkers.hmm.model.CourseReview) getIntent().getBundleExtra("ReviewBundle").getSerializable("Review");
        Toast.makeText(CourseReview.this, "Review ID:" + courseReview.getId(), Toast.LENGTH_SHORT).show();

        reviewTitle = (TextView) findViewById(R.id.reviewTitle);
        reviewTitle.setText(courseReview.getTitle());

        reviewContent = (TextView) findViewById(R.id.reviewContent);
        reviewContent.setText(courseReview.getContent());

        reviewAuthor = (TextView) findViewById(R.id.reviewAuthor);
        CourseReviewHelper getAuthorHelper = new CourseReviewHelper();
        String[] getAuthorParams = new String[1];
        getAuthorParams[0] = GET_AUTHOR;
        getAuthorHelper.execute(getAuthorParams);

        likeButton = (Button) findViewById(R.id.likeButton);
        likeButton.setText(String.valueOf(courseReview.getLike()));
        likeButton.setOnClickListener(likeListener);

        dislikeButton = (Button) findViewById(R.id.dislikeButton);
        dislikeButton.setText(String.valueOf(courseReview.getDislike()));
        dislikeButton.setOnClickListener(dislikeListener);

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

    private View.OnClickListener likeListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            CourseReviewHelper likeHelper = new CourseReviewHelper();
            String[] likeParams = new String[1];
            likeParams[0] = LIKE;
            likeHelper.execute(likeParams);
        }
    };

    private View.OnClickListener dislikeListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            CourseReviewHelper dislikeHelper = new CourseReviewHelper();
            String[] dislikeParams = new String[1];
            dislikeParams[0] = DISLIKE;
            dislikeHelper.execute(dislikeParams);
        }
    };

    private class CourseReviewHelper extends AsyncTask<Object, Void, Void> {
        private String option = "";
        private User author;

        @Override
        protected Void doInBackground(Object... params ) {
            option = (String)params[0];
            if(option.equals(GET_AUTHOR)) {
                UserUtil userUtil = new UserUtil();
                author = userUtil.selectUser(courseReview.getUid());
            } else if (option.equals(LIKE)) {
                courseReview.setLike(courseReview.getLike() + 1);
                CourseReviewUtil courseReviewUtil = new CourseReviewUtil();
                courseReviewUtil.updateCourseReview(courseReview.getId(), courseReview);
            } else if (option.equals(DISLIKE)) {
                courseReview.setDislike(courseReview.getDislike() + 1);
                CourseReviewUtil courseReviewUtil = new CourseReviewUtil();
                courseReviewUtil.updateCourseReview(courseReview.getId(), courseReview);
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void object) {
            if(option.equals(GET_AUTHOR)) {
                reviewAuthor.setText("BY: " + author.getUsername());
            } else if (option.equals(LIKE)) {
                likeButton.setText(String.valueOf(courseReview.getLike()));
            } else if (option.equals(DISLIKE)) {
                likeButton.setText(String.valueOf(courseReview.getDislike()));
            }
            return;
        }
    }
}
