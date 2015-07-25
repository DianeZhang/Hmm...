package thinkers.hmm.util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * Created by Yao on 7/18/15.
 */
public abstract class DatabaseConnectorBase {
    //Database Objects
    Connection connection = null;
    PreparedStatement preparedStatement = null;
    ResultSet resultSet = null;

    abstract public void open();
    abstract public void close();

}
