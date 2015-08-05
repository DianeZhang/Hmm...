package thinkers.hmm.ui;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Looper;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;

import thinkers.hmm.R;
import thinkers.hmm.model.Course;
import thinkers.hmm.model.User;
import thinkers.hmm.util.CourseReviewUtil;
import thinkers.hmm.util.UserUtil;

public class CourseReview extends Activity {
    //Operation Strings
    private static final String GET_AUTHOR = "Get_Author";
    private static final String LIKE = "Like";
    private static final String DISLIKE = "Dislike";
    private thinkers.hmm.model.CourseReview courseReview;
    private int userID;

    private TextView reviewTitle;
    private TextView reviewContent;

    private TextView reviewAuthor;
    private Button likeButton;
    private Button dislikeButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ui_course_review);

        SharedPreferences sharedpreferences = getSharedPreferences(Login.USER_INFO, Context.MODE_PRIVATE);
        userID = sharedpreferences.getInt("uid", -1);

        likeButton = (Button) findViewById(R.id.likeButton);
        dislikeButton = (Button) findViewById(R.id.dislikeButton);

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
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
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
        private int result = 0;

        @Override
        protected Void doInBackground(Object... params ) {
            option = (String)params[0];
            if(option.equals(GET_AUTHOR)) {
                UserUtil userUtil = new UserUtil();
                author = userUtil.selectUser(courseReview.getUid());
            } else if (option.equals(LIKE)) {
                if (courseReview.getUid() != userID) {
                    courseReview.setLike(courseReview.getLike() + 1);
                    CourseReviewUtil courseReviewUtil = new CourseReviewUtil();
                    courseReviewUtil.updateCourseReview(courseReview.getId(), courseReview);
                    result = 0;
                } else {
                    result = 1;
                }
            } else if (option.equals(DISLIKE)) {
                if (courseReview.getUid() != userID) {
                    courseReview.setDislike(courseReview.getDislike() + 1);
                    CourseReviewUtil courseReviewUtil = new CourseReviewUtil();
                    courseReviewUtil.updateCourseReview(courseReview.getId(), courseReview);
                    result = 0;
                } else {
                    result = 1;
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void object) {
            if(option.equals(GET_AUTHOR)) {
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                String text = "BY: " + author.getUsername();
                text += " @" + format.format(courseReview.getCreatedtime());
                if (courseReview.getLocation() != null) {
                    text += "\n@" + courseReview.getLocation();
                }
                reviewAuthor.setText(text);
            } else if (option.equals(LIKE)) {
                if (result != 0) {
                    Toast.makeText(CourseReview.this, "ERROR: One can not like one's own post",Toast.LENGTH_LONG ).show();
                } else {
                    likeButton.setText(String.valueOf(courseReview.getLike()));
                }
            } else if (option.equals(DISLIKE)) {
                if (result != 0) {
                    Toast.makeText(CourseReview.this, "ERROR: One can not dislike one's own post",Toast.LENGTH_LONG ).show();
                } else {
                    dislikeButton.setText(String.valueOf(courseReview.getDislike()));
                }
            }
            return;
        }
    }
}
