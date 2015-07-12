package thinkers.hmm.ui;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;

import thinkers.hmm.R;

public class UserMain extends Activity {

    private TextView welcomeText;
    private Button courseButton;
    private Button facultiesButton;
    private Button draftsButton;
    private Button reviewsButton;
    private Button settingsButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_main);

        welcomeText = (TextView) findViewById(R.id.welcomeText);
        courseButton = (Button) findViewById(R.id.coursesButton);
        facultiesButton = (Button) findViewById(R.id.facultiesButton);
        draftsButton = (Button) findViewById(R.id.draftsButton);
        reviewsButton = (Button) findViewById(R.id.reviewsButton);
        settingsButton = (Button) findViewById(R.id.settingsButton);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_user_main, menu);
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
}
