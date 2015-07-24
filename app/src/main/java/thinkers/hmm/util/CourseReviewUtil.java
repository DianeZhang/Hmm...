package thinkers.hmm.util;

import android.util.Log;
import android.widget.ArrayAdapter;

import java.sql.SQLException;
import java.util.ArrayList;

import thinkers.hmm.model.CourseReview;

/**
 * Created by Yao on 7/18/15.
 */
public class CourseReviewUtil extends DatabaseConnector{
    //Debug TAG
    private final String TAG = "CourseReviewUtil";

    //SQL Statements
    private final String selectReviewSQL = "SELECT * FROM CourseReviews WHERE cid=?;";
    private final String updateReviewSQL = "UPDATE TABLE CourseReviews SET cid=?, uid=?, " +
            "likes=?, dislikes=?, title=?, content=?, created=?, location=? WHERE id=?;";
    private final String insertReviewSQL = "INSERT INTO CourseReviews(cid,uid,likes,dislikes,title,content,created,location) VALUES" +
            "(?,?,?,?,?,?,?,?);";
    private final String deleteReviewSQL = "DELETE FROM CourseReviews WHERE id=?;";

    public boolean insertCourseReview(CourseReview review) {
        try {
            open();
            preparedStatement = connection.prepareStatement(insertReviewSQL);
            preparedStatement.setInt(1, review.getCid());
            preparedStatement.setInt(2, review.getUid());
            preparedStatement.setInt(3, review.getLike());
            preparedStatement.setInt(4, review.getDislike());
            preparedStatement.setString(5, review.getTitle());
            preparedStatement.setString(6, review.getContent());
            preparedStatement.setDate(7, review.getCreatedtime());

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

    public ArrayList<CourseReview> selectCourseReview(int cid) {

    }

    /*
    public ArrayAdapter<CourseReview> selectCourseReview(String courseName) {

    }
    */

    public boolean updateCourseReview(int,CourseReview) {

    }

    public boolean deleteCourseReview(int) {

    }
}
