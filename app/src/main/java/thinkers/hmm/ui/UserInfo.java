package thinkers.hmm.ui;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import thinkers.hmm.R;

public class UserInfo extends Activity {

    private TextView titleUserInfo;
    private TextView username;
    private TextView userEmail;
    private TextView userPhone;
    private Button seeUserReviewsButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_info);

        titleUserInfo = (TextView) findViewById(R.id.textView);
        username = (TextView) findViewById(R.id.textView2);
        titleUserInfo = (TextView) findViewById(R.id.textView3);
        titleUserInfo = (TextView) findViewById(R.id.textView4);


        //Clicking on the button to see user's reviews
        seeUserReviewsButton = (Button) findViewById(R.id.button);
        seeUserReviewsButton.setOnClickListener(seeUserReviewListener);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_user_info, menu);
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

    private View.OnClickListener seeUserReviewListener = new View.OnClickListener() {
        @Override
        public void onClick(View v){
            Intent seeUserReview = new Intent(UserInfo.this, ListMyReviews.class);
            startActivity(seeUserReview);
        }
    };
}
