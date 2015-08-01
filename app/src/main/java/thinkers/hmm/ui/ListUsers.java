package thinkers.hmm.ui;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import thinkers.hmm.R;
import thinkers.hmm.model.Faculty;
import thinkers.hmm.model.User;
import thinkers.hmm.util.FacultyUtil;
import thinkers.hmm.util.UserUtil;

public class ListUsers extends Activity {
    //Operation String
    private final String LIST_OPERATION = "List_Faculties";
    public static final String USER_ID = "user_id";

    //Widgets
    private TextView titleListUsers;
    private EditText searchUserEditText;
    private ImageButton addNewUserButton;
    private ListView listUsersListView;
    private ImageButton homeButton;

    public static final String USER = "user";
    public static final String ADMIN = "admin";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ui_list_users);

        titleListUsers = (TextView) findViewById(R.id.titleTextView);

        //TODO: search bar refreshes the page
        searchUserEditText = (EditText) findViewById(R.id.searchUserEditText);

        //Clicking on an item goes to ListCourseReviews page
        listUsersListView = (ListView) findViewById(R.id.listUsersListView);
        listUsersListView.setOnItemClickListener(viewUserReviewsListener);

        homeButton = (ImageButton) findViewById(R.id.homeButton);
        homeButton.setOnClickListener(homeListener);

        ListUserHelper listHelper = new ListUserHelper();
        String[] params= new String[1];
        params[0] = LIST_OPERATION;
        listHelper.execute(params);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_list_users, menu);
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

    private View.OnClickListener homeListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            SharedPreferences sharedpreferences = getSharedPreferences(Login.USER_INFO, Context.MODE_PRIVATE);
            String role = sharedpreferences.getString("role", null);
            if (role.equals(USER)) {
                Intent home = new Intent(ListUsers.this, UserMain.class);
                startActivity(home);
            }
            else {
                Intent home = new Intent(ListUsers.this, AdminMain.class);
                startActivity(home);
            }
        }
    };

    // event listener that responds to the user touching a user's name
    // in the ListView
    private AdapterView.OnItemClickListener viewUserReviewsListener = new AdapterView.OnItemClickListener()
    {
        @Override
        public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3)
        {
            User user = (User) arg0.getAdapter().getItem(arg2);
            Log.d("OnItemClick",user.getUsername());
            // create an Intent to launch the ListCourseReviews Activity
            Intent viewUserInfo = new Intent(ListUsers.this, UserInfo.class);
            viewUserInfo.putExtra(USER_ID, user.getId());
            startActivity(viewUserInfo); // start the viewCourseReviews Activity
        } // end method onItemClick
    }; // end viewContactListener

    private class ListUserHelper extends AsyncTask<Object, Void, Void> {

        private String option = "";
        private ArrayList<User> userList;

        @Override
        protected Void doInBackground(Object... params ) {
            option = (String)params[0];
            if(option.equals(LIST_OPERATION)) {
                UserUtil userUtil = new UserUtil();
                userList = userUtil.selectUser();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void object) {
            if(option.equals(LIST_OPERATION)) {
                UserAdapter userAdapter = new UserAdapter(userList);
                listUsersListView.setAdapter(userAdapter);
            }
            return;
        }
    }

    private class UserAdapter extends ArrayAdapter<User> {
        public UserAdapter(ArrayList<User> users) {
            super(ListUsers.this, android.R.layout.simple_list_item_1, users);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            // if we weren't given a view, inflate one
            if (convertView == null) {
                convertView = ListUsers.this.getLayoutInflater()
                        .inflate(android.R.layout.simple_list_item_1, null);
            }

            // configure the view for this Song
            final User user = getItem(position);

            TextView userName = (TextView) convertView.findViewById(android.R.id.text1);
            userName.setText(user.getUsername());

            return convertView;
        }
    }
}
