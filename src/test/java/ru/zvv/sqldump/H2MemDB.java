package ru.zvv.sqldump;

import org.h2.jdbcx.JdbcDataSource;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Created by V.Zubchevskiy on 12.04.2016.
 */
public class H2MemDB {

    private final JdbcDataSource ds;

    public H2MemDB() {
        ds = new JdbcDataSource();
        ds.setUrl("jdbc:h2:mem:db1;DB_CLOSE_DELAY=-1");
        ds.setUser("sa");
        ds.setPassword("");
    }

    public void execute(String sql) {
        Connection connection = null;
        Statement stmt = null;
        try {
            Class.forName("org.h2.Driver");
            connection = ds.getConnection();
            stmt = connection.createStatement();
            stmt.execute(sql);
        }
        catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (stmt != null)
                try {
                    stmt.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            if (connection != null)
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
        }

    }

    public DataSource getDataSource() {
        return ds;
    }
}
