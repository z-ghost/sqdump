package ru.zvv.sqldump;

import java.util.Collections;
import java.util.List;

/**
 * Created by V.Zubchevskiy on 12.04.2016.
 */
public class TableDataSet implements IDataSet {

    private final List<ITable> tables;

    public TableDataSet(String name) {
        tables = Collections.<ITable>singletonList(new DbTable(name));
    }

    @Override
    public Iterable<ITable> getTables() {
        return tables;
    }
}
