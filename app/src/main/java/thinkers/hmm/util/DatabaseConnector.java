package thinkers.hmm.util;

import android.os.StrictMode;
import android.util.Log;

import java.io.BufferedReader;
import java.io.FileReader;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnector extends DatabaseConnectorBase implements  DatabaseConstants {
    //Debug TAG
    private final String TAG = "Database Message";

    /**
     * @brief open database connection
     */
    @Override
    public void open() {
        //StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();

        //StrictMode.setThreadPolicy(policy);
        try {
            Class.forName(JDBC_DRIVER);
            Log.d(TAG, "try to connect");
            connection = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
            Log.d(TAG, "connected");
        } catch (ClassNotFoundException ex) {
            Log.d(TAG, ex.getException().toString());
        } catch (SQLException ex) {
            Log.d(TAG, ex.getNextException().toString());
        } catch (Exception ex) {
            Log.d(TAG, ex.getMessage().toString());
        }
    }

    /**
     * @brief Close database connection
     */
    @Override
    public void close() {
        try {
            //Close resultset
            if(resultSet != null) {
                resultSet.close();
            }
            //Close statement
            if(preparedStatement != null) {
                preparedStatement.close();
            }
            //Close connection
            if(connection != null) {
                connection.close();
            }
        }catch(SQLException ex) {
            Log.d(TAG, ex.getClass().getSimpleName());
        }
    }
}
