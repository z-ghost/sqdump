package ru.zvv.sqldump;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by V.Zubchevskiy on 12.04.2016.
 */
public class DbTable implements ITable {

    private final String name;

    public DbTable(String name) {
        this.name = name;
    }

    @Override
    public IMetaData getMetaData(ResultSet resultSet) {
        try {
            return new DbMetaData(resultSet.getMetaData());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String getSchema() {
        return null;
    }

    @Override
    public String getTableName() {
        return name;
    }
}
