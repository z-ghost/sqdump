package ru.zvv.sqldump;

import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Created by V.Zubchevskiy on 12.04.2016.
 */
public class DbMetaData implements IMetaData {

    private final ArrayList<IColumn> columns;

    public DbMetaData(ResultSetMetaData metaData) {

        columns = new ArrayList<IColumn>();
        try {
            for (int i = 1; i <= metaData.getColumnCount(); i++) {
                columns.add(new DbColumn(metaData.getColumnName(i)));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public Iterable<IColumn> getColumns() {
        return columns;
    }
}
