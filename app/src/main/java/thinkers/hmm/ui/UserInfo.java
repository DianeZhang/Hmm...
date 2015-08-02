package thinkers.hmm.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;

import thinkers.hmm.R;

public class UserInfo extends Activity {

    private TextView userID;
    private TextView userName;
    private TextView userEmail;
    private TextView userPassword;
    private TextView userLastLogin;

    private thinkers.hmm.model.User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ui_user_info);

        Bundle extras = getIntent().getExtras();
        userID = (TextView) findViewById(R.id.userID);

        userName = (TextView) findViewById(R.id.userName);
        userLastLogin = (TextView) findViewById(R.id.userLastLogin);
        userEmail = (TextView) findViewById(R.id.userEmail);
        userPassword = (TextView) findViewById(R.id.userPass);

        user = (thinkers.hmm.model.User) getIntent().getBundleExtra("UserBundle").getSerializable("User");

        userName.setText(user.getUsername());
        userEmail.setText(user.getEmail());
        userPassword.setText(user.getPassword());
        userLastLogin.setText(user.getLastlogin().toString());
        userID.setText(Integer.toString(user.getId()));
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
        if (id == R.id.action_logout) {
            Intent intent = new Intent(UserInfo.this, Login.class);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

//    private View.OnClickListener seeUserReviewListener = new View.OnClickListener() {
//        @Override
//        public void onClick(View v){
//            Intent seeUserReview = new Intent(UserInfo.this, ListMyReviews.class);
//            startActivity(seeUserReview);
//        }
//    };
}
