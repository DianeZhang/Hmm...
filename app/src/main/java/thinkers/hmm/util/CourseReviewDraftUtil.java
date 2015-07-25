package thinkers.hmm.util;

import android.util.Log;
import java.sql.SQLException;
import thinkers.hmm.model.*;


/**
 * Created by chaoli on 7/18/15.
 */
public class CourseReviewDraftUtil extends DatabaseConnector {
    //Debug TAG
    private final String TAG = "CourseReviewDraftUtil";

    //SQL Statements
    private final String selectDraftSQL = "SELECT * FROM CourseReviewDrafts WHERE id=?;";
    private final String updateDraftSQL = "UPDATE TABLE CourseReviewDrafts SET cid=?, uid=?, " +
            "title=?, content=? WHERE id=?;";
    private final String insertDraftSQL = "INSERT INTO CourseReviewDrafts(cid,uid,title,content) VALUES" +
            "(?,?,?,?);";
    private final String deleteDraftSQL = "DELETE FROM CourseReviewDrafts WHERE id=?;";

    public CourseReviewDraft selectDraft(int draftId) {
        CourseReviewDraft draft = null;
        try {
            open();
            preparedStatement = connection.prepareStatement(selectDraftSQL);
            preparedStatement.setInt(1, draftId);

            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                int id = resultSet.getInt("id");
                int cid = resultSet.getInt("cid");
                int uid = resultSet.getInt("uid");
                String title = resultSet.getString("title");
                String content = resultSet.getString("content");

                draft = new CourseReviewDraft(cid, id, uid, title, content);
            }
        } catch(SQLException ex) {
            Log.d(TAG, ex.getClass().getSimpleName());
        } finally {
            close();
        }

        return draft;
    }

    public boolean updateDraft(int draftId, CourseReviewDraft draft) {
        try {
            open();
            preparedStatement = connection.prepareStatement(updateDraftSQL);
            preparedStatement.setInt(1, draft.getCid());
            preparedStatement.setInt(2, draft.getUid());
            preparedStatement.setString(3, draft.getTitle());
            preparedStatement.setString(4, draft.getContent());
            preparedStatement.setInt(5, draftId);

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

    public boolean deleteDraft(int draftId){
        try {
            open();
            preparedStatement = connection.prepareStatement(deleteDraftSQL);
            preparedStatement.setInt(1, draftId);

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

    public boolean insertCourseReviewDraft(CourseReviewDraft draft){
        try {
            open();
            preparedStatement = connection.prepareStatement(insertDraftSQL);
            preparedStatement.setInt(1, draft.getCid());
            preparedStatement.setInt(2, draft.getUid());
            preparedStatement.setString(3, draft.getTitle());
            preparedStatement.setString(4, draft.getContent());

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
