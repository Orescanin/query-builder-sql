package database;

import resource.DBNode;
import resource.data.Row;

import java.util.ArrayList;
import java.util.List;

public interface Database{

    DBNode loadResource();

    List<Row> readDataFromTable(String tableName);
    List<Row> test(String tableName,String arg1,String arg2,String arg3);

    
    void create(String tableNamee,ArrayList<String> values);

}
