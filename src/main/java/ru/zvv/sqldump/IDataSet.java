package ru.zvv.sqldump;

/**
 * Created by V.Zubchevskiy on 12.04.2016.
 */
public interface IDataSet {
    Iterable<ITable> getTables();
}
