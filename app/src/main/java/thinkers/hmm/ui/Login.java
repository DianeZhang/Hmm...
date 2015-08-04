package thinkers.hmm.ui;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Date;

import thinkers.hmm.R;
import thinkers.hmm.model.Admin;
import thinkers.hmm.model.User;
import thinkers.hmm.util.AdminUtil;
import thinkers.hmm.util.UserUtil;


public class Login extends Activity {

    //Operation String
    private final String USER_LOGIN_OPERATION = "Login_User";
    private final String ADMIN_LOGIN_OPERATION = "Login_Admin";

    //Widgets
    private EditText usernameText = null;
    private EditText passwordText = null;
    private Button signUp = null;
    private Button login  = null;
    private Button adminlogin = null;

    // Session Management
    public static final String USER_INFO = "User_Info";
    public static final String USER = "user";
    public static final String ADMIN = "admin";
    SharedPreferences sharedpreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ui_login);

        //Get element
        login = (Button) findViewById(R.id.button);
        signUp = (Button) findViewById(R.id.button2);
        adminlogin = (Button) findViewById(R.id.adminloginButton);
        usernameText = (EditText) findViewById(R.id.usernameText);
        usernameText.setTypeface(Typeface.SERIF);

        passwordText = (EditText) findViewById(R.id.passwordText);
        passwordText.setTypeface(Typeface.SERIF);

        //Set up button action listener
        signUp.setOnClickListener(signUpAction);
        login.setOnClickListener(loginAction);
        adminlogin.setOnClickListener(adminloginAction);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_login, menu);
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
    private View.OnClickListener signUpAction = new View.OnClickListener(){
        @Override
        public void onClick(View view) {
            Intent intent = new Intent(Login.this, SignUp.class);
            startActivity(intent);
      }
    };

    private View.OnClickListener loginAction = new View.OnClickListener(){
        @Override
        public void onClick(View view) {
            LoginHelper loginHelper = new LoginHelper();
            String[] params= new String[2];
            params[0] = USER_LOGIN_OPERATION;
            params[1] = usernameText.getText().toString();
            loginHelper.execute(params);
        }
    };

    private View.OnClickListener adminloginAction = new View.OnClickListener(){
        @Override
        public void onClick(View view) {
            LoginHelper loginHelper = new LoginHelper();
            String[] params= new String[2];
            params[0] = ADMIN_LOGIN_OPERATION;
            params[1] = usernameText.getText().toString();
            loginHelper.execute(params);
        }
    };

    private class LoginHelper extends AsyncTask<Object, Void, Void> {

        private String option = "";
        private User user = null;
        private Admin admin = null;
        private String newRecordsCount = "";

        @Override
        protected Void doInBackground(Object... params ) {
            option = (String)params[0];
            if(option.equals(USER_LOGIN_OPERATION)) {
                UserUtil userUtil = new UserUtil();
                String username = (String)params[1];
                user = userUtil.selectUser(username);
                if(user!=null) {
                    newRecordsCount = userUtil.getNewReviewsCount(user.getId());
                    Log.d("Old Date", user.getLastlogin().toString());
                    user.setLastlogin(new Date());
                    Log.d("New Date", user.getLastlogin().toString());
                    userUtil.updateUser(user.getId(), user);
                }

            } else if (option.equals(ADMIN_LOGIN_OPERATION)) {
                AdminUtil adminUtil = new AdminUtil();
                String username = (String)params[1];
                admin = adminUtil.selectAdmin(username);
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void object) {
            if(option.equals(USER_LOGIN_OPERATION)) {
                if(user == null) {
                    Toast.makeText(Login.this, "Wrong Username!", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(user.getPassword().equals(passwordText.getText().toString())
                        == false) {
                    Toast.makeText(Login.this, "Wrong Password!", Toast.LENGTH_SHORT).show();
                    return;
                }
                // update session
                sharedpreferences = getSharedPreferences(USER_INFO, Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedpreferences.edit();
                editor.putString("role", USER);
                editor.putInt("uid", user.getId());
                editor.commit();
                // start main page
                Intent intent = new Intent(Login.this, UserMain.class);
                intent.putExtra("NewCourseReviews", Integer.parseInt(newRecordsCount.split("\t")[0]));
                intent.putExtra("NewFacultyReviews", Integer.parseInt(newRecordsCount.split("\t")[1]));

                startActivity(intent);
            } else if (option.equals(ADMIN_LOGIN_OPERATION)) {
                if(admin == null) {
                    Toast.makeText(Login.this, "Wrong Username!", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(admin.getPassword().equals(passwordText.getText().toString())
                        == false) {
                    Toast.makeText(Login.this, "Wrong Password!", Toast.LENGTH_SHORT).show();
                    return;
                }
                // update session
                sharedpreferences = getSharedPreferences(USER_INFO, Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedpreferences.edit();
                editor.putString("role", ADMIN);
                editor.putInt("uid", admin.getId());
                editor.commit();
                // start main page
                Intent intent = new Intent(Login.this, AdminMain.class);
                startActivity(intent);
            }
            return;
        }

    }
}
