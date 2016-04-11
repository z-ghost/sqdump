package ru.zvv.sqldump;

/**
 * Created by V.Zubchevskiy on 12.04.2016.
 */
public class DbColumn implements IColumn {

    private final String columnName;

    public DbColumn(String columnName) {
        this.columnName = columnName;
    }

    @Override
    public String getColumnName() {
        return columnName;
    }
}
