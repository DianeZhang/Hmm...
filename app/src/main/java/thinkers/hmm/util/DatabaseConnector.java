package thinkers.hmm.util;

import android.util.Log;

import java.io.BufferedReader;
import java.io.FileReader;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Created by Yao on 7/18/15.
 */
public class DatabaseConnector extends DatabaseConnectorBase implements  DatabaseConstants {
    //Debug TAG
    private final String TAG = "Database Message";

    /**
     * @brief open database connection
     */
    @Override
    public void open() {
        if (connection == null) {
            //If first time run, create connection first
            try {
                Class.forName(JDBC_DRIVER);
                connection = DriverManager.getConnection("jdbc:mysql://"+DB_URL, USERNAME, PASSWORD);
            } catch (ClassNotFoundException ex) {
                Log.d(TAG, ex.getClass().getSimpleName());
            } catch (SQLException ex) {
                Log.d(TAG, ex.getClass().getSimpleName());
            }
        }
    }

    /**
     * @brief Close database connection
     */
    @Override
    public void close() {
        try {
            if(connection != null) {
                connection.close();
            }
        }catch(SQLException ex) {
            Log.d(TAG, ex.getClass().getSimpleName());
        }
    }
}
