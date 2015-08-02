package thinkers.hmm.util;

import android.util.Log;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;

import thinkers.hmm.model.Faculty;
import thinkers.hmm.model.FacultyReview;

/**
 * Created by Yao on 7/18/15.
 */
public class FacultyReviewUtil extends DatabaseConnector{
    //Debug TAG
    private final String TAG = "Faculty review uil";
    private final String insertFacultyReviewSQL = "INSERT INTO FacultyReviews (fid, uid, likes, " +
            "dislikes, title, content, location ) VALUES" + "(?,?,?,?,?,?,?);";
    private final String selectFacultyReviewByFacultyIdSQL = "SELECT * FROM FacultyReviews WHERE fid=?;";
    private final String selectFacultyReviewByUserIdSQL = "SELECT * FROM FacultyReviews WHERE uid=?;";
    private final String updateFacultyReviewByIdSQL = "UPDATE FacultyReviews SET fid=?, uid=?, " +
            "likes=?, dislikes=?, title=?, content=?, location=? WHERE id=?;";
    private final String deleteFacultyReviewByIdSQL = "DELETE FROM FacultyReviews WHERE Id=?;";

    public boolean insertFacultyReview(FacultyReview facultyReview) {
        try {
            open();
            preparedStatement = connection.prepareStatement(insertFacultyReviewSQL);
            preparedStatement.setInt(1, facultyReview.getFid());
            preparedStatement.setInt(2, facultyReview.getUid());
            preparedStatement.setInt(3, facultyReview.getLike());
            preparedStatement.setInt(4, facultyReview.getDislike());
            preparedStatement.setString(5, facultyReview.getTitle());
            preparedStatement.setString(6, facultyReview.getContent());
            preparedStatement.setString(7, facultyReview.getLocation());

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

    public ArrayList<FacultyReview> selectFacultyReview(int fid) {
        ArrayList<FacultyReview> facultyReviews = new ArrayList<FacultyReview>();

        try {
            open();

            preparedStatement = connection.prepareStatement(selectFacultyReviewByFacultyIdSQL);
            preparedStatement.setInt(1, fid);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                FacultyReview facultyReview = new FacultyReview(resultSet.getInt("id"),resultSet.getInt("uid"),
                        resultSet.getInt("fid"),resultSet.getString("title"),resultSet.getString("content"),
                        resultSet.getString("location"),resultSet.getTimestamp("created"));
                facultyReview.setLike(resultSet.getInt("likes"));
                facultyReview.setDislike(resultSet.getInt("dislikes"));
                facultyReviews.add(facultyReview);
            }

            close();
            return facultyReviews;
        } catch(SQLException ex) {
            Log.d(TAG, ex.getClass().getSimpleName());
            close();
            facultyReviews.clear();
            return facultyReviews;
        }

    }

    public ArrayList<FacultyReview> selectFacultyReviewByUserId(int uid) {
        ArrayList<FacultyReview> facultyReviews = new ArrayList<FacultyReview>();

        try {
            open();

            preparedStatement = connection.prepareStatement(selectFacultyReviewByFacultyIdSQL);
            preparedStatement.setInt(1, uid);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                FacultyReview facultyReview = new FacultyReview(resultSet.getInt("id"),resultSet.getInt("uid"),
                        resultSet.getInt("fid"),resultSet.getString("title"),resultSet.getString("content"),
                        resultSet.getString("location"),resultSet.getTimestamp("created"));
                facultyReview.setLike(resultSet.getInt("likes"));
                facultyReview.setDislike(resultSet.getInt("dislikes"));
                facultyReviews.add(facultyReview);
            }

            close();
            return facultyReviews;
        } catch(SQLException ex) {
            Log.d(TAG, ex.getClass().getSimpleName());
            close();
            facultyReviews.clear();
            return facultyReviews;
        }

    }

//    public ArrayList<FacultyReview> selectFacultyReview(String ) {
//
//    }

    public boolean updateFacultyReview(int id, FacultyReview review) {
        try {
            open();
            preparedStatement = connection.prepareStatement(updateFacultyReviewByIdSQL);
            preparedStatement.setInt(1, review.getFid());
            preparedStatement.setInt(2, review.getUid());
            preparedStatement.setInt(3, review.getLike());
            preparedStatement.setInt(4, review.getDislike());
            preparedStatement.setString(5, review.getTitle());
            preparedStatement.setString(6, review.getContent());
            preparedStatement.setString(7, review.getLocation());
            preparedStatement.setInt(8, review.getId());

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

    public boolean deleteFacultyReview(int id) {
        try {
            open();
            preparedStatement = connection.prepareStatement(deleteFacultyReviewByIdSQL);
            preparedStatement.setInt(1, id);

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
