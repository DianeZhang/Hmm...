package thinkers.hmm.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import thinkers.hmm.R;


public class Login extends Activity {

    private Button signUp = null;
    private Button login  = null;
    private Button adminlogin = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //Get element
        login = (Button) findViewById(R.id.button);
        signUp = (Button) findViewById(R.id.button2);
        adminlogin = (Button) findViewById(R.id.adminloginButton);


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
