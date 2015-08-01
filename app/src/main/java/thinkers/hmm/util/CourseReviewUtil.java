package thinkers.hmm.util;

import android.util.Log;
import android.widget.ArrayAdapter;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;

import thinkers.hmm.model.CourseReview;

public class CourseReviewUtil extends DatabaseConnector{
    //Debug TAG
    private final String TAG = "CourseReviewUtil";

    //SQL Statements
    private final String selectReviewByRID = "SELECT * FROM CourseReviews WHERE id=?;";
    private final String selectReviewsByCID = "SELECT * FROM CourseReviews WHERE cid=?;";
    private final String selectReviewsByUID = "SELECT * FROM CourseReviews WHERE uid=?";
    private final String updateReviewSQL = "UPDATE TABLE CourseReviews SET cid=?, uid=?, " +
            "likes=?, dislikes=?, title=?, content=?, location=? WHERE id=?;";
    private final String insertReviewSQL = "INSERT INTO CourseReviews(cid,uid,likes,dislikes,title,content,location) VALUES" +
            "(?,?,?,?,?,?,?);";
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
            preparedStatement.setString(7, review.getLocation());

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

    public CourseReview selectCourseReview(int rid) {
        CourseReview courseReview = null;
        try {
            open();
            //Execute statement
            preparedStatement = connection.prepareStatement(selectReviewByRID);
            preparedStatement.setInt(1, rid);
            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                int id = resultSet.getInt("id");
                int cid = resultSet.getInt("cid");
                int uid = resultSet.getInt("uid");
                int likes = resultSet.getInt("likes");
                int dislikes = resultSet.getInt("dislikes");
                String title = resultSet.getString("title");
                String content = resultSet.getString("content");
                Date created = resultSet.getTimestamp("created");
                String location = resultSet.getString("location");

                courseReview = new CourseReview(id, cid, uid, likes, dislikes, title, content, location, created);
            }
        } catch(SQLException ex) {
            Log.d(TAG, ex.getClass().getSimpleName());
        } finally {
            close();
        }
        return courseReview;
    }

    public ArrayList<CourseReview> selectCourseReviewsByCourseId(int cid) {
        ArrayList<CourseReview> reviewList = new ArrayList<CourseReview>();
        try {
            open();
            //Execute statement
            preparedStatement = connection.prepareStatement(selectReviewsByCID);
            preparedStatement.setInt(1, cid);
            resultSet = preparedStatement.executeQuery();

            //Go through all the course reviews
            while(resultSet.next()) {
                int id = resultSet.getInt("id");
                int uid = resultSet.getInt("uid");
                int likes = resultSet.getInt("likes");
                int dislikes = resultSet.getInt("dislikes");
                String title = resultSet.getString("title");
                String content = resultSet.getString("content");
                Date created = resultSet.getTimestamp("created");
                String location = resultSet.getString("location");

                CourseReview review = new CourseReview(id, cid, uid, title, content, location, created);
                review.setLike(likes);
                review.setDislike(dislikes);
                reviewList.add(review);
            }
        } catch(SQLException ex) {
            Log.d(TAG, ex.getClass().getSimpleName());
        } finally {
            close();
        }

        return reviewList;
    }

    public ArrayList<CourseReview> selectCourseReviewsByUserId(int uid) {
        ArrayList<CourseReview> reviewList = new ArrayList<CourseReview>();
        try {
            open();
            //Execute statement
            preparedStatement = connection.prepareStatement(selectReviewsByUID);
            preparedStatement.setInt(1, uid);
            resultSet = preparedStatement.executeQuery();

            //Go through all the course reviews
            while(resultSet.next()) {
                int id = resultSet.getInt("id");
                int cid = resultSet.getInt("cid");
                int likes = resultSet.getInt("likes");
                int dislikes = resultSet.getInt("dislikes");
                String title = resultSet.getString("title");
                String content = resultSet.getString("content");
                Date created = resultSet.getTimestamp("created");
                String location = resultSet.getString("location");

                CourseReview review = new CourseReview(id, cid, uid, title, content, location, created);
                review.setLike(likes);
                review.setDislike(dislikes);
                reviewList.add(review);
            }
        } catch(SQLException ex) {
            Log.d(TAG, ex.getClass().getSimpleName());
        } finally {
            close();
        }

        return reviewList;
    }

    /*
    public ArrayAdapter<CourseReview> selectCourseReview(String courseName) {

    }
    */

    public boolean updateCourseReview(int id, CourseReview review) {
        try {
            open();
            preparedStatement = connection.prepareStatement(updateReviewSQL);
            preparedStatement.setInt(1, review.getCid());
            preparedStatement.setInt(2, review.getUid());
            preparedStatement.setInt(3, review.getLike());
            preparedStatement.setInt(4, review.getDislike());
            preparedStatement.setString(5, review.getTitle());
            preparedStatement.setString(6, review.getContent());
            preparedStatement.setString(7, review.getLocation());
            preparedStatement.setInt(8, review.getId());

            // execute the java preparedStatement
            preparedStatement.executeUpdate();
            return true;
        } catch(SQLException ex) {
            Log.d(TAG, ex.getClass().getSimpleName());
        } finally {
            close();
        }
        return false;
    }

    public boolean deleteCourseReview(int id) {
        try {
            open();
            preparedStatement = connection.prepareStatement(deleteReviewSQL);
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
