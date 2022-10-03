package database;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.StringTokenizer;

public class Compiler {
	
	List<String>preFrom = Arrays.asList("Avg","Count","Min","Max");
	HashMap<String, String> keywordz = new HashMap<>();
	HashMap<String,Integer> redosled = new HashMap<>();
	HashMap<String,String> konacan = new HashMap<>();
	
	public String complie(String a) {
	
	int fromFlag = 0;
	String From = "";
	
	keywordz.put("Select", "");
	keywordz.put("OrderBy", "ORDER BY");
	keywordz.put("Where", "WHERE");
	keywordz.put("OrWhere", "OR");
	keywordz.put("AndWhere", "AND");
	keywordz.put("WhereBetween", "WHERE");
	keywordz.put("WhereIn", "WHERE");
	keywordz.put("WhereStartsWith", "WHERE");
	keywordz.put("WhereEndsWith", "WHERE");
	keywordz.put("WhereContains", "WHERE");
	keywordz.put("ParametarList", "IN");
	keywordz.put("OrderByDesc", "ORDER BY");
	keywordz.put("Join", "JOIN");
	keywordz.put("On", "ON");
	keywordz.put("Avg", "AVG");
	keywordz.put("Min", "MIN");
	keywordz.put("Max", "MAX");
	keywordz.put("Count", "COUNT");
	keywordz.put("GroupBy", "GROUP BY");
			
	
	redosled.put("Query", 0);
	redosled.put("SELECT", 1);
	redosled.put("AVG", 2);
	redosled.put("MIN", 3);
	redosled.put("MAX", 4);
	redosled.put("COUNT", 5);
	redosled.put("FROMM" ,6);
	redosled.put("JOIN", 7);
	redosled.put("ON", 8);
	redosled.put("WHERE", 9);
	redosled.put("OR", 10);
	redosled.put("AND", 11);
	redosled.put("GROUP BY", 12);
	redosled.put("ORDER BY", 13);
	//redosled.put("SELECT", 0);
	//redosled.put("SELECT", 0);
	//redosled.put("SELECT", 0);

	
	
	
	String result = "";
	String trenutni = "";
	String variable = "";
	String sub = "";
	String subZero = "";
	String special = "";
	int iterator = 0;
	int QueryFlagZarez =0;
	StringTokenizer str = new StringTokenizer(a," .(),\"");
	while(str.hasMoreElements()) {
		
		
		/// Query
		
		variable = "";
		if(!(keywordz.containsKey(trenutni)))
				trenutni=str.nextToken();
		
		if(trenutni.equals("Query")) {
			trenutni = str.nextToken();
			variable = trenutni;
			
			
			if(str.hasMoreElements())
				trenutni = str.nextToken();
			else
				return result+= "SELECT * FROM "+variable+" ";
			if(!(trenutni.equals("Select"))) {
				
				if(preFrom.contains(trenutni)) {
					
					konacan.put("Query", "SELECT ");
					From = variable;
					QueryFlagZarez = 1;
					
				}
				else
					konacan.put("Query", "SELECT * FROM "+variable);
				
				
				
			}
			/// select deo 
			
			
			if(trenutni.equals("Select")) {
			trenutni = str.nextToken();
			int i = 0;
			while(!(keywordz.containsKey(trenutni))  ) {
				
				if(i!=0) {
					sub += ", "+trenutni;
				}
				else {	
					sub += trenutni;
				}
				i++;
				
				if(str.hasMoreElements())
					trenutni = str.nextToken();
				else
					break;
				
			}
			if(preFrom.contains(trenutni)) {
				
				konacan.put("SELECT", "SELECT "+sub);
				From = variable;
			}
			else
				konacan.put("SELECT", "SELECT "+sub+" FROM "+variable);
			}
		}
		
		////// posle selecta
		
		
		variable = "";
		int Like = 0;
		if(keywordz.containsKey(trenutni)) {
			
			special = trenutni;
			subZero = keywordz.get(trenutni);
			trenutni=str.nextToken();
			while(!(keywordz.containsKey(trenutni))) {
				
				
				
				
				if(Like ==1) {
					variable += "'"+trenutni+"'" + " ";
					Like = 0;
					
					
				}
				else
					variable += trenutni + " ";
				
				if(trenutni.equals("like"))
					Like = 1;
				
				if(str.hasMoreElements())
					trenutni = str.nextToken();
				else
					break;
			}
			
			
			switch(special) {
			
			default:{
				konacan.put(subZero, subZero+ " "+variable);
				break;
			}
				
			case "OrderByDesc":{
				konacan.put(subZero, subZero+ " "+variable+" DESC");
				break;
			}
			
			case "WhereBetween":{
				StringTokenizer token = new StringTokenizer(variable);
				konacan.put(subZero,subZero+" "+token.nextToken() +" BETWEEN "+token.nextToken()+" AND "+token.nextToken() );
				break;
			}
			case "WhereEndsWith":{
				StringTokenizer token3 = new StringTokenizer(variable);
				konacan.put(subZero,subZero +" " +token3.nextToken()+ " LIKE "+"'%"+token3.nextToken()+"'");
				break;
			}
			case "WhereStartsWith":{
				StringTokenizer token4 = new StringTokenizer(variable);
				konacan.put(subZero,subZero +" " +token4.nextToken()+ " LIKE "+"'"+token4.nextToken()+"%'");
				break;
			}
			case "WhereContains":{
				StringTokenizer token5 = new StringTokenizer(variable);
				konacan.put(subZero,subZero +" " +token5.nextToken()+ " LIKE "+"'%"+token5.nextToken()+"%'");
				break;
			}
				
			case "ParametarList":{
				variable.replace(" ", ",");
				konacan.put(subZero,subZero+" "+"("+variable+")");
				break;
				
			}
			
			
			
			case "Avg":{
				
				StringTokenizer token6 = new StringTokenizer(variable);
				String columnName= token6.nextToken();
				String allias = null;
				if(token6.hasMoreElements()) {
					allias = token6.nextToken();
				}
				
				fromFlag = 1;
				
				if (QueryFlagZarez == 0 && allias!= null)
					konacan.put(subZero,", "+subZero+"("+columnName+")"+ " AS "+"\""+allias+"\"");
				
				else if(QueryFlagZarez == 0 && allias == null)
					konacan.put(subZero,", "+subZero+"("+columnName+")");
				
				else if (QueryFlagZarez == 1 && allias!= null)
					konacan.put(subZero,subZero+"("+columnName+")"+ " AS "+"\""+allias+"\"");
				
				else if(QueryFlagZarez == 1 && allias == null)
					konacan.put(subZero,subZero+"("+columnName+")");
				
				break;
				
				
				
			}
			
			case "Min":{		StringTokenizer token6 = new StringTokenizer(variable);
			String columnName= token6.nextToken();
			String allias = null;
			if(token6.hasMoreElements()) {
				allias = token6.nextToken();
			}
			
			fromFlag = 1;
			
			if (QueryFlagZarez == 0 && allias!= null)
				konacan.put(subZero,", "+subZero+"("+columnName+")"+ " AS "+"\""+allias+"\"");
			
			else if(QueryFlagZarez == 0 && allias == null)
				konacan.put(subZero,", "+subZero+"("+columnName+")");
			
			else if (QueryFlagZarez == 1 && allias!= null)
				konacan.put(subZero,subZero+"("+columnName+")"+ " AS "+"\""+allias+"\"");
			
			else if(QueryFlagZarez == 1 && allias == null)
				konacan.put(subZero,subZero+"("+columnName+")");
			
			break;
			}
			case "Max":{		StringTokenizer token6 = new StringTokenizer(variable);
			String columnName= token6.nextToken();
			String allias = null;
			if(token6.hasMoreElements()) {
				allias = token6.nextToken();
			}
			
			fromFlag = 1;
			
			if (QueryFlagZarez == 0 && allias!= null)
				konacan.put(subZero,", "+subZero+"("+columnName+")"+ " AS "+"\""+allias+"\"");
			
			else if(QueryFlagZarez == 0 && allias == null)
				konacan.put(subZero,", "+subZero+"("+columnName+")");
			
			else if (QueryFlagZarez == 1 && allias!= null)
				konacan.put(subZero,subZero+"("+columnName+")"+ " AS "+"\""+allias+"\"");
			
			else if(QueryFlagZarez == 1 && allias == null)
				konacan.put(subZero,subZero+"("+columnName+")");
			
			break;
			}
			
			
			case "On":{
				StringTokenizer doIt = new StringTokenizer(variable);
				String prvi = doIt.nextToken();
				String drugi = doIt.nextToken();
				String treci = doIt.nextToken();
				String cetvrti = doIt.nextToken();
				String peti = doIt.nextToken();
				
				konacan.put(subZero, subZero+" "+prvi+"."+drugi+" "+treci+" "+cetvrti+"."+peti);
				
				
				
				break;
			}
			
			
			
			case "Count":{		StringTokenizer token6 = new StringTokenizer(variable);
			String columnName= token6.nextToken();
			String allias = null;
			if(token6.hasMoreElements()) {
				allias = token6.nextToken();
			}
			
			fromFlag = 1;
			
			if (QueryFlagZarez == 0 && allias!= null)
				konacan.put(subZero,", "+subZero+"("+columnName+")"+ " AS "+"\""+allias+"\"");
			
			else if(QueryFlagZarez == 0 && allias == null)
				konacan.put(subZero,", "+subZero+"("+columnName+")");
			
			else if (QueryFlagZarez == 1 && allias!= null)
				konacan.put(subZero,subZero+"("+columnName+")"+ " AS "+"\""+allias+"\"");
			
			else if(QueryFlagZarez == 1 && allias == null)
				konacan.put(subZero,subZero+"("+columnName+")");
			
			break;
			}
			
			}
				
		}
			
	}
	
	
	
	
	result = "";
	
	String[] kraj = new String[15];
	int pozicija;
	for (HashMap.Entry<String,String> entry : konacan.entrySet() ) {
			
			pozicija = redosled.get(entry.getKey());
			
			kraj[pozicija]= entry.getValue();
			
			//System.out.println(entry.getKey()+entry.getValue());
	}
	
	int broj =0;
	int fpozition = redosled.get("FROMM");
	for (String string : kraj) {
		
		if(broj == fpozition && fromFlag == 1)
			result+= "FROM "+From+" ";
		
		else if(string != null)
			result+= string+" ";
		broj++;
	}
	
	System.out.println("ovo je resuslt :  "+result );
	return result;

	}
}
