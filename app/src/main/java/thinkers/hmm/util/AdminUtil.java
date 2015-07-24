package thinkers.hmm.util;

import android.util.Log;
import java.sql.SQLException;
import thinkers.hmm.model.Admin;

/**
 * Created by chaoli on 7/18/15.
 */
public class AdminUtil extends DatabaseConnector{
    //All SQL Statement
    private final String TAG = "AdminUtil";
    private final String selectAdminByIDSQL = "SELECT * FROM ADMINS WHERE ID=?;";
    private final String selectAdminByUsernameSQL = "SELECT * FROM ADMINS WHERE USERNAME=?;";
    private final String updateAdminByIDSQL = "UPDATE ADMINS SET USERNAME=?,EMAIL=?," +
            "PASSWORD=? WHERE UID=?;";
    private final String updateAdminByUsernameSQL = "UPDATE ADMINS SET USERNAME=?,EMAIL=?," +
            "PASSWORD=? WHERE USERNAME=?;";
    private final String deleteAdminByIDSQL = "DELETE FROM ADMINS WHERE ID=?;";
    private final String deleteAdminByUsernameSQL = "DELETE FROM ADMINS WHERE USERNAME=?;";
    private final String insertAdminSQL = "INSERT INTO ADMINS(USERNAME, EMAIL, PASSWORD) VALUES" +
            "(?,?,?);";

    /**
     * @brief Select Admin by UID
     * @param uid
     * @return admin object if admin found, null if not found
     */
    public Admin selectAdmin(int uid){
        try {
            open();
            //Execute statement
            preparedStatement = connection.prepareStatement(selectAdminByIDSQL);
            preparedStatement.setInt(1, uid);
            resultSet = preparedStatement.executeQuery();

            //Check if admin exist
            if(resultSet.next()) {
                String username = resultSet.getString("username");
                String email = resultSet.getString("email");
                String password = resultSet.getString("password");
                Admin admin = new Admin(uid, username, email, password);
                return admin;
            } else {
                return null;
            }
        } catch(SQLException ex) {
            Log.d(TAG, ex.getClass().getSimpleName());
            return null;
        } finally {
            close();
        }
    }

    /**
     * @brief Select Admin by Username
     * @param adminname
     * @return admin object if admin found, null if not found
     */
    public Admin selectAdmin(String adminname){
        try {
            open();
            //Execute statement
            preparedStatement = connection.prepareStatement(selectAdminByUsernameSQL);
            preparedStatement.setString(1, adminname);
            resultSet = preparedStatement.executeQuery();

            //Check if admin exist
            if(resultSet.next()) {
                int uid = resultSet.getInt("id");
                String email = resultSet.getString("email");
                String password = resultSet.getString("password");
                Admin admin = new Admin(uid, adminname, email, password);
                return admin;
            } else {
                return null;
            }
        } catch(SQLException ex) {
            Log.d(TAG, ex.getClass().getSimpleName());
            return null;
        } finally {
            close();
        }
    }

    /**
     * @brief update an admin by id
     * @param uid
     * @param admin
     * @return
     */
    public boolean updateAdmin(int uid, Admin admin){
        try {
            open();
            preparedStatement = connection.prepareStatement(updateAdminByIDSQL);
            preparedStatement.setString(1, admin.getUsername());
            preparedStatement.setString(2, admin.getEmail());
            preparedStatement.setString(3, admin.getPassword());
            preparedStatement.setInt(4, admin.getId());

            // execute the java preparedStatement
            preparedStatement.executeUpdate();
            close();
            return true;
        } catch(SQLException ex) {
            Log.d(TAG, ex.getClass().getSimpleName());
        } finally {
            close();
        }
        return false;
    }

    /**
     * @brief update Admin by its name
     * @param adminname
     * @param admin
     * @return
     */
    public boolean updateAdmin(String adminname, Admin admin){
        try {
            open();
            preparedStatement = connection.prepareStatement(updateAdminByUsernameSQL);
            preparedStatement.setString(1, admin.getUsername());
            preparedStatement.setString(2, admin.getEmail());
            preparedStatement.setString(3, admin.getPassword());
            preparedStatement.setString(4, adminname);

            // execute the java preparedStatement
            preparedStatement.executeUpdate();
            close();
            return true;
        } catch(SQLException ex) {
            Log.d(TAG, ex.getClass().getSimpleName());
        } finally {
            close();
        }
        return false;
    }

    /**
     * @brief delete one admin by its id from database
     * @param uid
     * @return
     */
    public boolean deleteAdmin(int uid){
        try {
            open();
            preparedStatement = connection.prepareStatement(deleteAdminByIDSQL);
            preparedStatement.setInt(1, uid);

            // execute the java preparedStatement
            preparedStatement.executeUpdate();
            close();
            return true;
        } catch(SQLException ex) {
            Log.d(TAG, ex.getClass().getSimpleName());
        } finally {
            close();
        }
        return false;
    }

    /**
     * @brief delete one admin by its name from database
     * @param Adminname
     * @return
     */
    public boolean deleteAdmin(String Adminname){
        try {
            open();
            preparedStatement = connection.prepareStatement(deleteAdminByUsernameSQL);
            preparedStatement.setString(1, Adminname);

            // execute the java preparedStatement
            preparedStatement.executeUpdate();
            close();
            return true;
        } catch(SQLException ex) {
            Log.d(TAG, ex.getClass().getSimpleName());
        } finally {
            close();
        }
        return false;
    }

    /**
     * @brief insert one admin into database
     * @param admin
     * @return
     */
    public boolean insertAdmin(Admin admin){
        try {
            open();
            preparedStatement = connection.prepareStatement(insertAdminSQL);
            preparedStatement.setString(1, admin.getUsername());
            preparedStatement.setString(2, admin.getEmail());
            preparedStatement.setString(3, admin.getPassword());

            // execute the java preparedStatement
            preparedStatement.executeUpdate();
            close();
            return true;
        } catch(SQLException ex) {
            Log.d(TAG, ex.getClass().getSimpleName());
        } finally {
            close();
        }
        return false;
    }
}
