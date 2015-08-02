package thinkers.hmm.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
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
    private thinkers.hmm.model.FacultyReview facultyReview;

    private TextView reviewTitle;
    private TextView reviewContent;

    private TextView reviewAuthor;
    private Button likeButton;
    private Button dislikeButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ui_faculty_review);

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

    private class FacultyReviewHelper extends AsyncTask<Object, Void, Void> {
        private String option = "";
        private User author;

        @Override
        protected Void doInBackground(Object... params ) {
            option = (String)params[0];
            if(option.equals(GET_AUTHOR)) {
                UserUtil userUtil = new UserUtil();
                author = userUtil.selectUser(facultyReview.getUid());
            } else if (option.equals(LIKE)) {
                facultyReview.setLike(facultyReview.getLike() + 1);
                FacultyReviewUtil facultyReviewUtil = new FacultyReviewUtil();
                facultyReviewUtil.updateFacultyReview(facultyReview.getId(), facultyReview);
            } else if (option.equals(DISLIKE)) {
                facultyReview.setDislike(facultyReview.getDislike() + 1);
                FacultyReviewUtil facultyReviewUtil = new FacultyReviewUtil();
                facultyReviewUtil.updateFacultyReview(facultyReview.getId(), facultyReview);
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
                likeButton.setText(String.valueOf(facultyReview.getLike()));
            } else if (option.equals(DISLIKE)) {
                dislikeButton.setText(String.valueOf(facultyReview.getDislike()));
            }
            return;
        }
    }
}
