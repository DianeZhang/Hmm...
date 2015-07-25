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
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import thinkers.hmm.R;
import thinkers.hmm.model.Review;
import thinkers.hmm.model.User;
import thinkers.hmm.util.CourseReviewUtil;
import thinkers.hmm.util.UserUtil;

public class ListMyReviews extends Activity {

    //Operation String
    private final String LIST_MYREVIEW = "List_MyReviews";

    //Widgets
    private TextView titleMyReview;
    private ListView myReviews;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ui_list_my_reviews);

        titleMyReview = (TextView) findViewById(R.id.textView);
        myReviews = (ListView) findViewById(R.id.listView);

        ListMyReviewHelper listMyReviewHelper = new ListMyReviewHelper();
        String[] params= new String[1];
        params[0] = LIST_MYREVIEW;
        listMyReviewHelper.execute(params);

        //Get elements
        myReviews.setOnItemClickListener(viewCourseReviewListener);

        //Test
        ArrayList<String> test = new ArrayList<String>();
        test.add("1");
        test.add("2");
        test.add("3");

        ArrayAdapter<String> testAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, test);
        myReviews.setAdapter(testAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_list_my_reviews, menu);
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

    private AdapterView.OnItemClickListener viewCourseReviewListener = new AdapterView.OnItemClickListener()
    {
        @Override
        public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3)
        {
            // create an Intent to launch the CourseReview Activity
            Intent viewCourseReview = new Intent(ListMyReviews.this, CourseReview.class);
            startActivity(viewCourseReview); // start the viewCourseReview Activity
        } // end method onItemClick
    }; // end viewContactListener


    private class ListMyReviewHelper extends AsyncTask<Object, Void, Void> {

        private String option = "";
        private User user = null;

        @Override
        protected Void doInBackground(Object... params ) {
            option = (String)params[0];
            if(option.equals(LIST_MYREVIEW)) {
                ArrayList<Review> myReview = new ArrayList<Review>();
                CourseReviewUtil myCourseReviews = new CourseReviewUtil();
   //             myReview = myCourseReviews.selectCourseReview()


            }
            return null;
        }

        @Override
        protected void onPostExecute(Void object) {
            if(option.equals(LIST_MYREVIEW)) {
                if(user == null) {
                    Toast.makeText(ListMyReviews.this, "Wrong Username!", Toast.LENGTH_SHORT).show();
                    return;
                }
//                if(user.getPassword().equals(passwordText.getText().toString())
//                        == false) {
//                    Toast.makeText(ListMyReviews.this, "Wrong Password!", Toast.LENGTH_SHORT).show();
//                    return;
//                }
                Intent intent = new Intent(ListMyReviews.this, UserMain.class);
                startActivity(intent);
            }
            return;
        }

    }
}
