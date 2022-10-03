package database;

//import lombok.AllArgsConstructor;
//import lombok.Data;
import resource.DBNode;
import resource.data.Row;

import java.util.ArrayList;
import java.util.List;

//@Data
//@AllArgsConstructor
public class DatabaseImplementation implements Database {

    private Repository repository;

    
    
    
    public DatabaseImplementation(Repository repository) {
		super();
		this.repository = repository;
	}

	@Override
    public DBNode loadResource() {
		
        return repository.getSchema();
    }

    @Override
    public List<Row> readDataFromTable(String tableName) {
        return repository.get(tableName);
    }

	@Override
	public void create(String tableNamee, ArrayList<String> values) {
		
		
	}

	@Override
	public List<Row> test(String tableName, String arg1, String arg2, String arg3) {
		// TODO Auto-generated method stub
		return repository.test(tableName,arg1,arg2,arg3);
	}
    
    
}
