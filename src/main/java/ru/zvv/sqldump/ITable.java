package ru.zvv.sqldump;

import java.sql.ResultSet;

/**
 * Created by V.Zubchevskiy on 12.04.2016.
 */
public interface ITable {

    IMetaData getMetaData(ResultSet resultSet);

    String getSchema();

    String getTableName();
}
