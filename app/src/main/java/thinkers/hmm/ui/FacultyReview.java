package thinkers.hmm.ui;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;

import thinkers.hmm.R;
import thinkers.hmm.model.User;
import thinkers.hmm.util.FacultyReviewUtil;
import thinkers.hmm.util.UserUtil;

public class FacultyReview extends Activity {
    //Operation Strings
    private static final String GET_AUTHOR = "Get_Author";
    private static final String LIKE = "Like";
    private static final String DISLIKE = "Dislike";
    private static final String DELETE = "Delete";
    private thinkers.hmm.model.FacultyReview facultyReview;
    private int userID;

    public static final String USER = "user";
    public static final String ADMIN = "admin";

    private TextView reviewTitle;
    private TextView reviewContent;

    private TextView reviewAuthor;
    private Button likeButton;
    private Button dislikeButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ui_faculty_review);

        SharedPreferences sharedpreferences = getSharedPreferences(Login.USER_INFO, Context.MODE_PRIVATE);
        userID = sharedpreferences.getInt("uid", -1);

        likeButton = (Button) findViewById(R.id.likeButton);
        dislikeButton = (Button) findViewById(R.id.dislikeButton);

        facultyReview = (thinkers.hmm.model.FacultyReview) getIntent().getBundleExtra("ReviewBundle").getSerializable("Review");
        Toast.makeText(FacultyReview.this, "Review ID:" + facultyReview.getId(), Toast.LENGTH_SHORT).show();

        reviewTitle = (TextView) findViewById(R.id.reviewTitle);
        reviewTitle.setText(facultyReview.getTitle());

        reviewContent = (TextView) findViewById(R.id.reviewContent);
        reviewContent.setText(facultyReview.getContent());

        reviewAuthor = (TextView) findViewById(R.id.reviewAuthor);
        FacultyReviewHelper getAuthorHelper = new FacultyReviewHelper();
        String[] getAuthorParams = new String[1];
        getAuthorParams[0] = GET_AUTHOR;
        getAuthorHelper.execute(getAuthorParams);

        likeButton = (Button) findViewById(R.id.likeButton);
        likeButton.setText(String.valueOf(facultyReview.getLike()));
        likeButton.setOnClickListener(likeListener);

        dislikeButton = (Button) findViewById(R.id.dislikeButton);
        dislikeButton.setText(String.valueOf(facultyReview.getDislike()));
        dislikeButton.setOnClickListener(dislikeListener);

        String role = sharedpreferences.getString("role", null);

        if (role.equals(ADMIN)) {
            Button addCourseReviewButton = new Button(FacultyReview.this);
            addCourseReviewButton.setText("Delete Review");
            RelativeLayout rl = (RelativeLayout) findViewById(R.id.relativeLayout);
            RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(
                    RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
            addCourseReviewButton.setOnClickListener(deleteReviewListener);
            rl.addView(addCourseReviewButton, lp);
        }
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
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private View.OnClickListener likeListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            FacultyReviewHelper likeHelper = new FacultyReviewHelper();
            String[] likeParams = new String[1];
            likeParams[0] = LIKE;
            likeHelper.execute(likeParams);
        }
    };

    private View.OnClickListener dislikeListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            FacultyReviewHelper dislikeHelper = new FacultyReviewHelper();
            String[] dislikeParams = new String[1];
            dislikeParams[0] = DISLIKE;
            dislikeHelper.execute(dislikeParams);
        }
    };

    private View.OnClickListener deleteReviewListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            FacultyReviewHelper deleteHelper = new FacultyReviewHelper();
            String[] deleteParams = new String[1];
            deleteParams[0] = DELETE;
            deleteHelper.execute(deleteParams);
        }
    };

    private class FacultyReviewHelper extends AsyncTask<Object, Void, Void> {
        private String option = "";
        private User author;
        private int result = 0;

        @Override
        protected Void doInBackground(Object... params ) {
            option = (String)params[0];
            if(option.equals(GET_AUTHOR)) {
                UserUtil userUtil = new UserUtil();
                author = userUtil.selectUser(facultyReview.getUid());
            } else if (option.equals(LIKE)) {
                if (facultyReview.getUid() != userID) {
                    facultyReview.setLike(facultyReview.getLike() + 1);
                    FacultyReviewUtil facultyReviewUtil = new FacultyReviewUtil();
                    facultyReviewUtil.updateFacultyReview(facultyReview.getId(), facultyReview);
                    result = 0;
                } else {
                    result = 1;
                }
            } else if (option.equals(DISLIKE)) {
                if (facultyReview.getUid() != userID) {
                    facultyReview.setDislike(facultyReview.getDislike() + 1);
                    FacultyReviewUtil facultyReviewUtil = new FacultyReviewUtil();
                    facultyReviewUtil.updateFacultyReview(facultyReview.getId(), facultyReview);
                    result = 0;
                } else {
                    result = 1;
                }
            } else if (option.equals(DELETE)) {
                FacultyReviewUtil facultyReviewUtil = new FacultyReviewUtil();
                facultyReviewUtil.deleteFacultyReview(facultyReview.getId());

            }
            return null;
        }

        @Override
        protected void onPostExecute(Void object) {
            if(option.equals(GET_AUTHOR)) {
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                String text = "BY: " + author.getUsername();
                text += " @" + format.format(facultyReview.getCreatedtime());
                if (facultyReview.getLocation() != null) {
                    text += " @" + facultyReview.getLocation();
                }
                reviewAuthor.setText(text);
            } else if (option.equals(LIKE)) {
                if (result != 0) {
                    Toast.makeText(FacultyReview.this, "ERROR: One can not like one's own post",Toast.LENGTH_LONG ).show();
                } else {
                    likeButton.setText(String.valueOf(facultyReview.getLike()));
                }
            } else if (option.equals(DISLIKE)) {
                if (result != 0) {
                    Toast.makeText(FacultyReview.this, "ERROR: One can not dislike one's own post",Toast.LENGTH_LONG ).show();
                } else {
                    dislikeButton.setText(String.valueOf(facultyReview.getDislike()));
                }
            } else if (option.equals(DELETE)) {
                onBackPressed();
            }
            return;
        }
    }
}
