package thinkers.hmm.util;

import android.util.Log;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import thinkers.hmm.model.Admin;

/**
 * Created by chaoli on 7/18/15.
 */
public class AdminUtil extends DatabaseConnector{
    //TODO
    private final String TAG = "AdminUtil";
    private final String selectAdminSQL = "SELECT * FROM ADMIN;"
    //
    public Admin selectAdmin(int uid){
        try {
            preparedStatement = connection.prepareStatement(selectAdminSQL);
            resultSet = preparedStatement.executeQuery();

        } catch(SQLException ex) {
            Log.d(TAG, ex.getClass().getSimpleName());
        }
        return null;
    }

    public Admin selectAdmin(String adminname){
        return null;
    }

    public boolean updateAdmin(int uid, Admin admin){

        return false;
    }

    public boolean updateAdmin(String adminname, Admin admin){
        return false;
    }

    public boolean deleteAdmin(int uid){
        return false;
    }

    public boolean deleteAdmin(String Adminname){
        return false;
    }

    public boolean insertAdmin(Admin admin){
        return false;
    }
}
