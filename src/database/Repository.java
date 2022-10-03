package database;

import resource.DBNode;
import resource.data.Row;

import java.util.List;

public interface Repository {

    DBNode getSchema();

    List<Row> get(String from);

	List<Row> test(String tableName, String arg1, String arg2, String arg3);
}
