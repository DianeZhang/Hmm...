package thinkers.hmm.util;

import android.util.Log;

import java.sql.SQLException;
import java.util.ArrayList;

import thinkers.hmm.model.Faculty;

/**
 * Created by Yao on 7/18/15.
 */
public class FacultyUtil extends DatabaseConnector{
    //Debug TAG
    private final String TAG = "Faculty util";
    private final String insertFacultySQL = "INSERT INTO Faculties (name) VALUES (?) ;";
    private final String selectFacultiesSQL = "SELECT * FROM Faculties WHERE name = ?;";
    private final String selectFacultySQL = "SELECT * FROM Faculties WHERE id = ?;";
    private final String deleteFacultyByIdSQL = "DELETE FROM Faculties WHERE id=?;";
    private final String selectAllFacultiesSQL = "SELECT * FROM Faculties;";

    public boolean insertFaculty(Faculty faculty) {
        try {
            open();

            preparedStatement = connection.prepareStatement(insertFacultySQL);
            preparedStatement.setString(1, faculty.getName());
            preparedStatement.executeUpdate();

            close();
            return true;
        } catch(SQLException ex) {
            Log.d(TAG, ex.getClass().getSimpleName());
            close();
            return false;
        }
    }

    public ArrayList<Faculty> selectFaculty(String name) {
        ArrayList<Faculty> faculties = new ArrayList<Faculty>();
        String facultyName;
        int facultyId;


        try {
            open();

            preparedStatement = connection.prepareStatement(selectFacultiesSQL);
            preparedStatement.setString(1, name);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                facultyId = resultSet.getInt("id");
                facultyName = resultSet.getString("name");
                Faculty faculty = new Faculty(facultyId,facultyName);
                faculties.add(faculty);
            }

            close();
            return faculties;
        } catch(SQLException ex) {
            Log.d(TAG, ex.getClass().getSimpleName());
            close();
            faculties.clear();
            return faculties;
        }
    }

    public Faculty selectFaculty(int id) {
        String facultyName;
        int facultyId;


        try {
            open();

            preparedStatement = connection.prepareStatement(selectFacultySQL);
            preparedStatement.setInt(1, id);
            resultSet = preparedStatement.executeQuery();

            facultyId = resultSet.getInt("id");
            facultyName = resultSet.getString("name");
            Faculty faculty = new Faculty(facultyId, facultyName);


            close();
            return faculty;
        } catch(SQLException ex) {
            Log.d(TAG, ex.getClass().getSimpleName());
            close();
            return null;
        }
    }

    public boolean deleteFaculty(int id) {
        try {
            open();
            preparedStatement = connection.prepareStatement(deleteFacultyByIdSQL);
            preparedStatement.setInt(1, id);

            // execute the java preparedStatement
            preparedStatement.executeUpdate();
            close();
            return true;
        } catch(SQLException ex) {
            close();
            Log.d(TAG, ex.getClass().getSimpleName());
            return false;
        }

    }

    public ArrayList<Faculty> getAllFaculties() {
        ArrayList<Faculty> faculties = new ArrayList<Faculty>();
        String facultyName;
        int facultyId;

        try {
            open();

            preparedStatement = connection.prepareStatement(selectAllFacultiesSQL);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                facultyId = resultSet.getInt("id");
                facultyName = resultSet.getString("name");
                Faculty faculty = new Faculty(facultyId,facultyName);
                faculties.add(faculty);
            }

            close();
            return faculties;
        } catch(SQLException ex) {
            Log.d(TAG, ex.getClass().getSimpleName());
            close();
            faculties.clear();
            return faculties;
        }
    }
}
