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
import android.widget.EditText;
import android.widget.Toast;

import thinkers.hmm.R;
import thinkers.hmm.model.Admin;
import thinkers.hmm.model.User;
import thinkers.hmm.util.AdminUtil;
import thinkers.hmm.util.UserUtil;

public class Settings extends Activity {
    //Operation Strings
    private final String GET_USER = "Get_User";
    private final String GET_ADMIN = "Get_Admin";
    private final String SAVE_SETTINGS = "Save_Settings";

    //MSG
    private final String ERROR_MSG = "Your Request Failed";
    private final String Success_Setting_Change_MSG = "Your changes has been saved";

    //Widgets
    private Button saveButton;
    private Button cancelButton;
    private EditText editUserName;
    private EditText editPassword;
    private EditText editEmail;

    //Current user
    private int uid = -1;
    private String role = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ui_settings);

        saveButton = (Button) findViewById(R.id.saveButton);
        cancelButton = (Button) findViewById(R.id.cancelButton);
        editUserName = (EditText) findViewById(R.id.editUserName);
        editPassword = (EditText) findViewById(R.id.editPassword);
        editEmail = (EditText) findViewById(R.id.editEmail);

        saveButton.setOnClickListener(saveListener);
        cancelButton.setOnClickListener(cancelListener);

        // get role
        SharedPreferences sharedpreferences = getSharedPreferences(Login.USER_INFO, Context.MODE_PRIVATE);
        role = sharedpreferences.getString("role", null);
        uid = sharedpreferences.getInt("uid", -1);

        //Set edit text field
        if(role.equals("user")) {
            //Get user
            SettingHelper searchHelper = new SettingHelper();
            String[] params= new String[1];
            params[0] = GET_USER;
            searchHelper.execute(params);
        } else {
            SettingHelper searchHelper = new SettingHelper();
            String[] params= new String[1];
            params[0] = GET_ADMIN;
            searchHelper.execute(params);
        }
    }

    private View.OnClickListener saveListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            SettingHelper settingHelper = new SettingHelper();
            String[] params= new String[1];
            params[0] = SAVE_SETTINGS;
            settingHelper.execute(params);
        }
    };

    private View.OnClickListener cancelListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            finish();
        }
    };

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_settings, menu);
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
            Intent intent = new Intent(Settings.this, Login.class);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private class SettingHelper extends AsyncTask<Object, Void, Void> {

        private String option = "";
        private User user = null;
        private Admin admin = null;
        private boolean operationResult = false;

        @Override
        protected Void doInBackground(Object... params) {
            option = (String) params[0];
            if (option.equals(GET_USER)) {
                UserUtil userUtil = new UserUtil();
                user = userUtil.selectUser(uid);
            } else if (option.equals(GET_ADMIN)) {
                AdminUtil adminUtil = new AdminUtil();
                admin = adminUtil.selectAdmin(uid);
            } else if (option.equals(SAVE_SETTINGS)) {
                String username = editUserName.getText().toString();
                String password = editPassword.getText().toString();
                String email = editEmail.getText().toString();

                if (role.equals("user")) {
                    UserUtil userUtil = new UserUtil();
                    operationResult = userUtil.updateSetting(username, password, email, uid);
                } else {
                    AdminUtil adminUtil = new AdminUtil();
                    operationResult = adminUtil.updateSetting(username, password, email, uid);
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void object) {
            if (option.equals(GET_USER)) {
                editUserName.setText(user.getUsername());
                editPassword.setText(user.getPassword());
                editEmail.setText(user.getEmail());
            } else if (option.equals(GET_ADMIN)) {
                editUserName.setText(admin.getUsername());
                editPassword.setText(admin.getPassword());
                editEmail.setText(admin.getEmail());
            } else if (option.equals(SAVE_SETTINGS)) {
                if (operationResult == true) {
                    Toast.makeText(Settings.this, Success_Setting_Change_MSG, Toast.LENGTH_LONG)
                            .show();
                    finish();
                } else {
                    Toast.makeText(Settings.this, ERROR_MSG, Toast.LENGTH_LONG).show();
                }
            }
        }
    }
}
