package ru.zvv.sqldump;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import javax.sql.DataSource;

/**
 * Created by V.Zubchevskiy on 12.04.2016.
 */
public class SqlDataExtractorTest {

    private DataSource ds;

    @Before
    public void setUp() throws Exception {
        H2MemDB db = new H2MemDB();
        db.execute("create table test(id int primary key, name varchar(255))");
        db.execute("insert into test (ID, NAME) values (1, 'test')");
        ds = db.getDataSource();
    }

    @Test
    public void testSimple() throws Exception {

        IDataSet dataSet = new TableDataSet("test");
        String sql = new SqlDataExtractor(ds).getSql(dataSet);
        Assert.assertEquals("insert into test (ID, NAME) values (1, 'test');", sql);

    }
}