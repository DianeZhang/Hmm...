package thinkers.hmm.util;

import android.util.Log;

import java.sql.SQLException;
import java.util.ArrayList;

import thinkers.hmm.model.Course;

/**
 * Created by Yao on 7/18/15.
 */
public class CourseUtil extends DatabaseConnector{
    //Debug TAG
    private final String TAG = "CourseUtil";

    private final String selectCourseByIDSQL = "SELECT * FROM Courses WHERE id=?;";
    private final String selectCourseByNameSQL = "SELECT * FROM Courses WHERE name=?;";
    private final String insertCourseSQL = "INSERT INTO Courses(name, code, school) VALUES(?,?,?);";
    private final String deleteCourseSQL = "DELETE FROM Courses WHERE ID=?";
    private final String selectAllCoursesSQL = "SELECT * FROM Courses;";

    public boolean insertCourse(Course course) {
        try {
            open();
            preparedStatement = connection.prepareStatement(insertCourseSQL);
            preparedStatement.setString(1, course.getName());
            preparedStatement.setString(2, course.getCode());
            preparedStatement.setString(3, course.getSchool());

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

    public ArrayList<Course> selectCourse(String courseName) {
        ArrayList<Course> courseList = new ArrayList<Course>();
        try {
            open();
            //Execute statement
            preparedStatement = connection.prepareStatement(selectCourseByNameSQL);
            preparedStatement.setString(1, courseName);
            resultSet = preparedStatement.executeQuery();

            //Go through all the course reviews
            while(resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                String code = resultSet.getString("code");
                String school = resultSet.getString("school");

                Course course = new Course(id, name, code, school);
                courseList.add(course);
            }
        } catch(SQLException ex) {
            Log.d(TAG, ex.getClass().getSimpleName());
        } finally {
            close();
        }

        return courseList;
    }

    public Course selectCourse(int id) {
        Course course = null;
        try {
            open();
            preparedStatement = connection.prepareStatement(selectCourseByIDSQL);
            preparedStatement.setInt(1, id);

            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                String name = resultSet.getString("name");
                String code = resultSet.getString("code");
                String school = resultSet.getString("school");
                course = new Course(id, name, code, school);
            }
        } catch(SQLException ex) {
            Log.d(TAG, ex.getClass().getSimpleName());
        } finally {
            close();
        }

        return course;
    }

    public boolean deleteCourse(int id) {
        try {
            open();
            preparedStatement = connection.prepareStatement(deleteCourseSQL);
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

    public ArrayList<Course> getAllCourses() {
        ArrayList<Course> courseList = new ArrayList<Course>();
        try {
            open();
            //Execute statement
            preparedStatement = connection.prepareStatement(selectAllCoursesSQL);
            resultSet = preparedStatement.executeQuery();

            //Go through all the course reviews
            while(resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                String code = resultSet.getString("code");
                String school = resultSet.getString("school");

                Course course = new Course(id, name, code, school);
                courseList.add(course);
            }
        } catch(SQLException ex) {
            Log.d(TAG, ex.getClass().getSimpleName());
        } finally {
            close();
        }

        return courseList;
    }
}
