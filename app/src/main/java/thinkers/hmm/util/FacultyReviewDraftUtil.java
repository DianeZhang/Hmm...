package thinkers.hmm.util;

import android.util.Log;

import java.sql.SQLException;
import java.util.ArrayList;

import thinkers.hmm.model.CourseReviewDraft;
import thinkers.hmm.model.Faculty;
import thinkers.hmm.model.FacultyReviewDraft;

/**
 * Created by chaoli on 7/18/15.
 */
public class FacultyReviewDraftUtil extends DatabaseConnector {
    //Debug TAG
    private final String TAG = "Fac_review_draft_util";

    //SQL Statements
    private final String selectDraftSQL = "SELECT * FROM FacultyReviewDrafts WHERE id=?;";
    private final String selectDraftByUidSQL = "SELECT * FROM FacultyReviewDrafts WHERE uid=?;";
    private final String updateDraftSQL = "UPDATE TABLE FacultyReviewDrafts SET fid=?, uid=?, " +
            "title=?, content=? WHERE id=?;";
    private final String insertDraftSQL = "INSERT INTO FacultyReviewDrafts(fid,uid,title,content) VALUES" +
            "(?,?,?,?);";
    private final String deleteDraftSQL = "DELETE FROM FacultyReviewDrafts WHERE id=?;";

    public FacultyReviewDraft selectDraft(int draftId){
        FacultyReviewDraft draft = null;
        try {
            open();
            preparedStatement = connection.prepareStatement(selectDraftSQL);
            preparedStatement.setInt(1, draftId);

            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                int id = resultSet.getInt("id");
                int fid = resultSet.getInt("fid");
                int uid = resultSet.getInt("uid");
                String title = resultSet.getString("title");
                String content = resultSet.getString("content");

                draft = new FacultyReviewDraft(id, uid, fid, title, content);
            }
        } catch(SQLException ex) {
            Log.d(TAG, ex.getClass().getSimpleName());
        } finally {
            close();
        }

        return draft;
    }

    public ArrayList<FacultyReviewDraft> selectDraftByUid(int uid){
        ArrayList<FacultyReviewDraft> facultyReviewDrafts = new ArrayList<FacultyReviewDraft>();
        FacultyReviewDraft draft = null;
        try {
            open();
            preparedStatement = connection.prepareStatement(selectDraftSQL);
            preparedStatement.setInt(1, uid);

            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                int id = resultSet.getInt("id");
                int fid = resultSet.getInt("fid");
                String title = resultSet.getString("title");
                String content = resultSet.getString("content");

                draft = new FacultyReviewDraft(id, uid, fid, title, content);
                facultyReviewDrafts.add(draft);
            }
        } catch(SQLException ex) {
            Log.d(TAG, ex.getClass().getSimpleName());
        } finally {
            close();
        }

        return facultyReviewDrafts;
    }

    public boolean updateDraft(int draftId, FacultyReviewDraft draft){
        try {
            open();
            preparedStatement = connection.prepareStatement(updateDraftSQL);
            preparedStatement.setInt(1, draft.getFid());
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

    public boolean insertFacultyReviewDraft(FacultyReviewDraft draft){
        try {
            open();
            preparedStatement = connection.prepareStatement(insertDraftSQL);
            preparedStatement.setInt(1, draft.getFid());
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
