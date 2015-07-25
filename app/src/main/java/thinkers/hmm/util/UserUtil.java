package thinkers.hmm.util;

import android.util.Log;

import java.sql.SQLException;
import java.util.Date;

import thinkers.hmm.model.Admin;
import thinkers.hmm.model.User;

/**
 * Created by chaoli on 7/18/15.
 */
public class UserUtil extends DatabaseConnector{
    //All SQL Statement
    private final String TAG = "UserUtil";
    private final String selectUsersByIDSQL = "SELECT * FROM USERS WHERE ID=?;";
    private final String selectUserByUsernameSQL = "SELECT * FROM Users WHERE username=?;";
    private final String updateUserByIDSQL = "UPDATE USERS SET USERNAME=?,EMAIL=?," +
            "PASSWORD=? WHERE UID=?;";
    private final String updateUserByUsernameSQL = "UPDATE USERS SET USERNAME=?,EMAIL=?," +
            "PASSWORD=? WHERE USERNAME=?;";
    private final String deleteUserByIDSQL = "DELETE FROM USERS WHERE ID=?;";
    private final String deleteUserByUsernameSQL = "DELETE FROM USERS WHERE USERNAME=?;";
    private final String insertUserSQL = "INSERT INTO USERS(USERNAME, EMAIL, PASSWORD) VALUES" +
            "(?,?,?);";

    /**
     * @brief select user by id
     * @param uid
     * @return
     */
    public User selectUser(int uid){
        try {
            open();
            Log.d(TAG, "Conn opened");
            //Execute statement
            preparedStatement = connection.prepareStatement(selectUsersByIDSQL);
            preparedStatement.setInt(1, uid);
            resultSet = preparedStatement.executeQuery();

            //Check if admin exist
            if(resultSet.next()) {
                String username = resultSet.getString("username");
                String email = resultSet.getString("email");
                String password = resultSet.getString("password");
                Date lastLogin = resultSet.getTimestamp("lastlogin");
                Log.d(TAG, "username:"+username);
                User user = new User(uid, username, email, password, lastLogin);
                return user;
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
     * @brief select user by username
     * @param username
     * @return
     */
    public User selectUser(String username){
        User user = null;
        try {
            open();
            //Execute statement
            preparedStatement = connection.prepareStatement(selectUserByUsernameSQL);
            preparedStatement.setString(1, username);
            resultSet = preparedStatement.executeQuery();

            //Check if admin exist
            if(resultSet.next()) {
                int uid = resultSet.getInt("id");
                String email = resultSet.getString("email");
                String password = resultSet.getString("password");
                Date lastLogin = resultSet.getTimestamp("lastlogin");
                user = new User(uid, username, email, password, lastLogin);
                Log.d(TAG, "password:"+user.getPassword());
            }
        } catch(SQLException ex) {
            Log.d(TAG, Integer.toString(ex.getErrorCode()));
            Log.d(TAG, preparedStatement.toString());
        } finally {
            close();
            return user;
        }
    }

    /**
     * @brief update user its id
     * @param uid
     * @param user
     * @return
     */
    public boolean updateUser(int uid, User user){
        try {
            open();
            preparedStatement = connection.prepareStatement(updateUserByIDSQL);
            preparedStatement.setString(1, user.getUsername());
            preparedStatement.setString(2, user.getEmail());
            preparedStatement.setString(3, user.getPassword());
            preparedStatement.setInt(4, user.getId());

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
     * @brief update user by its name
     * @param username
     * @param user
     * @return
     */
    public boolean updateUser(String username, User user){
        try {
            open();
            preparedStatement = connection.prepareStatement(updateUserByUsernameSQL);
            preparedStatement.setString(1, user.getUsername());
            preparedStatement.setString(2, user.getEmail());
            preparedStatement.setString(3, user.getPassword());
            preparedStatement.setString(4, user.getUsername());

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
     * @brief delete one user by its id from database
     * @param uid
     * @return
     */
    public boolean deleteUser(int uid){
        try {
            open();
            preparedStatement = connection.prepareStatement(deleteUserByIDSQL);
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
     * @brief delete one user by its name from database
     * @param username
     * @return
     */
    public boolean deleteUser(String username){
        try {
            open();
            preparedStatement = connection.prepareStatement(deleteUserByUsernameSQL);
            preparedStatement.setString(1, username);

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
     * @brief insert one user into database
     * @param user
     * @return
     */
    public boolean insertUser(User user){
        try {
            open();
            preparedStatement = connection.prepareStatement(insertUserSQL);
            preparedStatement.setString(1, user.getUsername());
            preparedStatement.setString(2, user.getEmail());
            preparedStatement.setString(3, user.getPassword());

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
