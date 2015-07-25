package thinkers.hmm.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
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

    //Widgets
    private TextView titleListUsers;
    private EditText searchUserEditText;
    private ImageButton addNewUserButton;
    private ListView listUsersListView;

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

    // event listener that responds to the user touching a user's name
    // in the ListView
    private AdapterView.OnItemClickListener viewUserReviewsListener = new AdapterView.OnItemClickListener()
    {
        @Override
        public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3)
        {
            // create an Intent to launch the ListCourseReviews Activity
            Intent viewFacultyReviews = new Intent(ListUsers.this, UserInfo.class);
            startActivity(viewFacultyReviews); // start the viewCourseReviews Activity
        } // end method onItemClick
    }; // end viewContactListener

    private class ListUserHelper extends AsyncTask<Object, Void, Void> {

        private String option = "";
        private ArrayList<User> userList;
        private ArrayList<String> userNameList;

        @Override
        protected Void doInBackground(Object... params ) {
            option = (String)params[0];
            if(option.equals(LIST_OPERATION)) {
                UserUtil userUtil = new UserUtil();
                userList = userUtil.selectUser();
                userNameList = new ArrayList<String>();
                for (User user : userList) {
                    userNameList.add(user.getUsername());
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void object) {
            if(option.equals(LIST_OPERATION)) {
                ArrayAdapter arrayAdapter = new ArrayAdapter(ListUsers.this, android.R.layout.simple_list_item_1, userNameList);
                listUsersListView.setAdapter(arrayAdapter);
            }
            return;
        }
    }
}
