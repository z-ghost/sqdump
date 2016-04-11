package ru.zvv.sqldump;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.util.Date;

/**
 * Created by V.Zubchevskiy on 12.04.2016.
 */
public class SqlDataExtractor {

    private final DataSource ds;

    public SqlDataExtractor(DataSource ds) {
        this.ds = ds;
    }

    public String getSql(IDataSet dataSet) {
        Connection connection = null;
        StringBuilder sb = new StringBuilder();
        try {
            connection = ds.getConnection();
            for (ITable table : dataSet.getTables()) {
                appendTable(connection, sb, table);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            if (connection != null)
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
        }


        return sb.toString();
    }

    private void appendTable(Connection connection, StringBuilder sb, ITable table) throws SQLException {


        Statement stmt = null;
        ResultSet rs = null;
        try {
            stmt = connection.createStatement();
            rs = stmt.executeQuery("select * from " + getQualifiedName(table.getSchema(), table.getTableName()));
            IMetaData metaData = table.getMetaData(rs);
            Iterable<IColumn> columns = metaData.getColumns();
            while (rs.next())
                appendInsert(sb, metaData, table, columns, rs);

        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            if (rs != null )
                try {
                    rs.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            if (stmt != null )
                try {
                    stmt.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
        }

    }

    private void appendInsert(StringBuilder sb, IMetaData metaData, ITable table, Iterable<IColumn> columns, ResultSet rs) throws SQLException {
        sb.append("insert into ");
        sb.append(getQualifiedName(table.getSchema(), table.getTableName()));

        sb.append(" (");
        String columnSeparator = "";
        for (IColumn column : columns) {
            if (!ignore(column)) {
                String columnName = column.getColumnName();

                sb.append(columnSeparator);
                sb.append(columnName);
                columnSeparator = ", ";
            }

        }

        sb.append(") values (");
        String valueSeparator = "";
        for (IColumn column : columns) {
            if (!ignore(column)) {
                sb.append(valueSeparator);
                final String val = getValue(rs.getObject(column.getColumnName()));
                sb.append(val);
                valueSeparator = ", ";
            }
        }
        sb.append(");");
    }

    private boolean ignore(IColumn column) {
        return false;
    }

    private String getQualifiedName(String schema, String tableName) {
        return schema == null || schema.isEmpty() ? tableName : schema + "." + tableName;
    }

    //TODO: replace by ValuePrinter
    private String getValue(Object val) {
        if (val == null)
            return "null";
        if (val instanceof Number)
            return "" + val;
        else if (val instanceof byte[])
            return printBytes((byte[]) val);
        else if (val instanceof Date)
            return "'" + DateFormat.getDateTimeInstance().format((Date) val) + "'";
        else
            return "'" + val + "'";
    }

    private String printBytes(byte[] bytes) {
        final StringBuilder sb = new StringBuilder();
        for (byte b : bytes) {
            sb.append(String.format("%02X",b));
        }
        return "0x" + sb.toString();
    }
}
