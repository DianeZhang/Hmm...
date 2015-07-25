package thinkers.hmm.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import thinkers.hmm.R;
import thinkers.hmm.model.User;
import thinkers.hmm.util.UserUtil;


public class Login extends Activity {

    private EditText usernameText = null;
    private EditText passwordText = null;
    private Button signUp = null;
    private Button login  = null;
    private Button adminlogin = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ui_login);

        //Get element
        login = (Button) findViewById(R.id.button);
        signUp = (Button) findViewById(R.id.button2);
        adminlogin = (Button) findViewById(R.id.adminloginButton);
        usernameText = (EditText) findViewById(R.id.usernameText);
        passwordText = (EditText) findViewById(R.id.passwordText);

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
            UserUtil userUtil = new UserUtil();
            Log.d("login", "login Action start");
            User user = userUtil.selectUser(usernameText.getText().toString());
            if(user == null) {
                Toast.makeText(Login.this, "Wrong Username!", Toast.LENGTH_SHORT).show();
                return;
            }
            if(user.getPassword().equals(passwordText.getText().toString())
                == false) {
                Toast.makeText(Login.this, "Wrong Password!", Toast.LENGTH_SHORT).show();
                return;
            }
            Intent intent = new Intent(Login.this, UserMain.class);
            startActivity(intent);
        }
    };

    private View.OnClickListener adminloginAction = new View.OnClickListener(){
        @Override
        public void onClick(View view) {
            Intent intent = new Intent(Login.this, AdminMain.class);
            startActivity(intent);
        }
    };
}
