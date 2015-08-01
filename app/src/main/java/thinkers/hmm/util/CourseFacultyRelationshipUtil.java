package thinkers.hmm.util;

import android.util.Log;

import java.sql.SQLException;
import java.util.ArrayList;

import thinkers.hmm.model.User;

public class CourseFacultyRelationshipUtil extends DatabaseConnector{

    //All SQL Statement
    private final String TAG = "CFRelationUtil";
    private final String selectCoursesByFIDSQL = "SELECT * FROM CourseFacultyRelationship " +
            "WHERE FID=?;";
    private final String selectFacultiesByCIDSQL = "SELECT * FROM CourseFacultyRelationship " +
            "WHERE CID=?;";
    private final String deleteByCIDSQL = "DELETE FROM CourseFacultyRelationship " +
            "WHERE CID=?;";
    private final String deleteByFIDSQL = "DELETE FROM CourseFacultyRelationship " +
            "WHERE FID=?;";
    private final String insertRelationshipSQL = "INSERT INTO CourseFacultyRelationship (fid, cid) VALUES" +
            "(?,?);";

    /**
     * @brief select list of cids by fids
     * @param fid
     * @return
     */
    public ArrayList<Integer> selectCourses(int fid){
        try {
            open();
            //Execute statement
            preparedStatement = connection.prepareStatement(selectCoursesByFIDSQL);
            preparedStatement.setInt(1, fid);
            resultSet = preparedStatement.executeQuery();

            //Add all course ids
            ArrayList<Integer> courseIDs = new ArrayList<>();
            while(resultSet.next()) {
                courseIDs.add(resultSet.getInt("cid"));
            }
            close();
            return courseIDs;
        } catch(SQLException ex) {
            Log.d(TAG, ex.getClass().getSimpleName());
            close();
            return new ArrayList<Integer>();
        }
    }

    /**
     * @brief select list of fids by cids
     * @param cid
     * @return
     */
    public ArrayList<Integer> selectFaculties(int cid){
        try {
            open();
            //Execute statement
            preparedStatement = connection.prepareStatement(selectFacultiesByCIDSQL);
            preparedStatement.setInt(1, cid);
            resultSet = preparedStatement.executeQuery();

            //Add all course ids
            ArrayList<Integer> courseIDs = new ArrayList<>();
            while(resultSet.next()) {
                courseIDs.add(resultSet.getInt("fid"));
            }
            close();
            return courseIDs;
        } catch(SQLException ex) {
            Log.d(TAG, ex.getClass().getSimpleName());
            close();
            return new ArrayList<Integer>();
        }
    }

    /**
     * @brief delete course faculty relationship by course
     * @param cid
     * @return
     */
    public boolean deleteByCourse(int cid){
        try {
            open();
            preparedStatement = connection.prepareStatement(deleteByCIDSQL);
            preparedStatement.setInt(1, cid);

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
     * @brief delete course faculty relationship by faculty from db
     * @param fid
     * @return
     */
    public boolean deleteByFaculty(int fid){
        try {
            open();
            preparedStatement = connection.prepareStatement(deleteByFIDSQL);
            preparedStatement.setInt(1, fid);

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
     * @brief insert one course faculty relationship into db
     * @param cid
     * @param fid
     * @return
     */
    public boolean insertRelationship(int cid, int fid){
        try {
            open();
            preparedStatement = connection.prepareStatement(insertRelationshipSQL);
            preparedStatement.setInt(1, cid);
            preparedStatement.setInt(2, fid);

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
