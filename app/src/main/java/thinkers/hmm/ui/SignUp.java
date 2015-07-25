package thinkers.hmm.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import thinkers.hmm.R;
import thinkers.hmm.model.Admin;
import thinkers.hmm.model.User;
import thinkers.hmm.util.AdminUtil;
import thinkers.hmm.util.UserUtil;


public class SignUp extends Activity {

    //Operation String
    private final String USER_SIGNUP_OPERATION = "SignUp_User";

    //DEBUG INFO
    private final String TAG = "SignUP";

    //Widgets
    private Button submitButton = null;
    private Button cancelButton = null;
    private EditText usernameText = null;
    private EditText passwordText = null;
    private EditText emailText = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ui_sign_up);

        //Get element
        submitButton = (Button)findViewById(R.id.submitButton);
        cancelButton = (Button)findViewById(R.id.cancelButton);
        usernameText = (EditText) findViewById(R.id.usernameText);
        passwordText = (EditText) findViewById(R.id.passwordText);
        emailText = (EditText) findViewById(R.id.emailText);

        //Set up Button action listerner
        submitButton.setOnClickListener(submitAction);
        cancelButton.setOnClickListener(cancelAction);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_sign_up, menu);
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
            Intent intent = new Intent(SignUp.this, Login.class);
            startActivity(intent);
        }
    };

    private View.OnClickListener submitAction = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            LoginHelper loginHelper = new LoginHelper();
            String[] params= new String[4];
            params[0] = USER_SIGNUP_OPERATION;
            params[1] = usernameText.getText().toString();
            params[2] = passwordText.getText().toString();
            params[3] = emailText.getText().toString();
            loginHelper.execute(params);
        }
    };

    private class LoginHelper extends AsyncTask<Object, Void, Void> {

        private String option = "";
        private User user = null;
        private Admin admin = null;
        private boolean signUpSucceed = false;

        @Override
        protected Void doInBackground(Object... params ) {
            option = (String)params[0];
            if(option.equals(USER_SIGNUP_OPERATION)) {
                UserUtil userUtil = new UserUtil();
                String username = (String)params[1];
                String password = (String)params[2];
                String email = (String)params[3];
                User user = new User(username, email, password, null);
                signUpSucceed = userUtil.insertUser(user);
            } else {
                Log.d(TAG, "Invalid Operation");
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void object) {
            if(option.equals(USER_SIGNUP_OPERATION)) {
                if(signUpSucceed == false) {
                    Toast.makeText(SignUp.this, "Failed to sign up", Toast.LENGTH_SHORT).show();
                    return;
                }
                Intent intent = new Intent(SignUp.this, UserMain.class);
                startActivity(intent);
            } else {
                Log.d(TAG, "Invalid Operation");
            }
            return;
        }

    }
}
