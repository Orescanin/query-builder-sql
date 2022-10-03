package database;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.StringTokenizer;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class Validator {

	public static boolean Validate(String query) {
		
		StringTokenizer upit = new StringTokenizer(query," \t\n");
		StringTokenizer CheckForVars = new StringTokenizer(query," \t\n");
		int numOfVars= 0;
		
		ArrayList<String> checkVarName = new ArrayList<String>(Arrays.asList("var","new","."," ","\"",",",":") );
		ArrayList<Character> badChars = new ArrayList<Character>(Arrays.asList('.',' ',':' ));
		ArrayList<String> keywordList = new ArrayList<String>();
		String kveri = query;
		String current = "";
		String errorMessage = "";
		char currChar=' ';
		int errorFlag= 0;
		String checker="";
		int i=0;
		while(CheckForVars.hasMoreElements()) {
					
					if(CheckForVars.nextToken().equals("var"))
						numOfVars ++;
				}
		if(numOfVars == 0) {
			errorMessage("lose deklarisana promenljiva");
			return false;
		}
		//System.out.println(upit.hasMoreElements());
		//while(numOfVars != 0) {
			//while(upit.hasMoreElements()) {
		
		while(upit.hasMoreElements()) {
			
			current=upit.nextToken();
			if(current.equals("var")) current=upit.nextToken();
			else {
				errorMessage += "Lose deklarisana promenljiva var \n";
				errorMessage(errorMessage);
				errorFlag=1;
				return false;
			
			}
			if(!(checkVarName.contains(current))) current=upit.nextToken();
			else {
				errorMessage += "Lose deklarisana promenljiva invalid characters \n";
				errorMessage(errorMessage);
				errorFlag=1;
				return false;
			
			}
			if(current.equals("=")) current=upit.nextToken();
			else {
				errorMessage += "Lose deklarisana promenljiva \n";
				errorMessage(errorMessage);
				errorFlag=1;
				return false;
			
			}
			if(current.equals("new")) current=upit.nextToken();
			else {
				errorMessage += "Lose deklarisana promenljiva new \n";
				errorMessage(errorMessage);
				errorFlag=1;
				return false;
			
			}
			while(i<7) {
				if(i==current.length()) {
					errorMessage += "Lose deklarisan Query \n";
					errorMessage("fali deo");
					return false;
				}
				currChar=current.charAt(i);
				checker+=currChar;
				i++;
			}
			currChar=current.charAt(i);
			
			//System.out.println(checker);
			if(checker.equals("Query(\"")) {
				
				while(currChar != '"' ) {
					
					if(i==current.length()) {
						errorMessage += "Lose deklarisan Query \n";
						errorMessage(errorMessage);
						return false;
					}
					currChar=current.charAt(i);
					i++;
				}
				break;
			
			}
			else {
				errorMessage += "Lose deklarisan Query \n";
				errorMessage(errorMessage);
				errorFlag=1;
				return false;
			}
		
		}
		
				i=0;
				currChar=' ';
				int navodnici=0;
				while(navodnici!=2) {
					currChar=kveri.charAt(i);
					i++;
					if(currChar == '\"') navodnici++;
					
					
				}
				currChar=kveri.charAt(i);
				
				
				
				if(currChar == ')') {
					
					i++;
					//System.out.println(current.charAt(i));

					if(i!=kveri.length()) currChar=kveri.charAt(i);

				}
				else {
					errorMessage += "Lose deklarisan Query \n";
					errorMessage(errorMessage);
					errorFlag=1;
					return false;
					//break;
				
				}
				
				//PROVERI DA LI JE ZAVRSEN UPIT da li je i jednak str length
				if(i== kveri.length() ) {
					goodMessage();
					return true;
				}
				
				if(currChar == '.') {
					currChar=kveri.charAt(i);
					i++;
				}
				else {
					errorMessage += "Treba tacka posle Queryija \n";
					//System.out.println("ovde puca");
					//System.out.println(current.charAt(i));
					errorMessage(errorMessage);
					errorFlag=1;
					return false;
					//break;
				
				}
				
				checker="";
				int validFlag = 0;
				//i prelazi current.length, resi posle
				for( ; i<kveri.length(); i++){
					currChar=kveri.charAt(i);
					checker+=currChar;
					//System.out.println(checker);
					
					if(badChars.contains(currChar)) {
						errorMessage(current);
						errorFlag=1;
						return false;
					}
					
					switch(checker) {
						case "Select" :
						case "OrderBy":
						case "OrderByDesc":
						case "GroupBy":
						case "Where":
						case "OrWhere":
						case "AndWhere":
						case "WhereEndsWith":
						case "WhereStartsWith":
						case "WhereContains":
						case "WhereBetween":
						case "Avg":
						case "Count":
						case "Max":
						case "Min":
							if(kveri.charAt(i+1)!='(' && checker.equals("OrderBy")) continue;
							if(kveri.charAt(i+1)!='(' && checker.equals("Where")) continue;
							
							if(keywordList.contains(checker)) {
								errorMessage += checker + " funkcija vec postoji jedna \n";
								
								errorMessage(errorMessage);
								return false;
								
							}
							
							else keywordList.add(checker);
							
							i=checkFuncArgs(kveri, checker, i);
							
							System.out.println(i);
							

							if(i==0) {
								errorMessage += checker + " funkcija je lose definisana \n";
								
								errorMessage(errorMessage);
								return false;
							}
							i++;
							System.out.println(kveri.length());
							if(i== kveri.length() ) {
								validFlag = 0;
								goodMessage();
								//break;
								return true;
							}
							currChar = kveri.charAt(i);
							if(currChar != '.') {
								errorMessage += checker + " funkcija je lose definisana \n";
								
								errorMessage(errorMessage);
								return false;
							}
							checker="";
							continue;
						
						case "Join":
							
							if(keywordList.contains(checker)) {
								errorMessage += checker + " funkcija vec postoji jedna \n";
								
								errorMessage(errorMessage);
								return false;
								
							}
							
							else keywordList.add(checker);
							
							i=checkFuncArgs(kveri, checker, i);
							
							System.out.println(i);
							

							if(i==0) {
								errorMessage += checker + " funkcija je lose definisana \n";
								
								errorMessage(errorMessage);
								return false;
							}
							
							int cnt = 0;
							checker="";
							i++;
							currChar= kveri.charAt(i);
							if(currChar != '.') {
								errorMessage += checker + " funkcija je lose definisana \n";
								
								errorMessage(errorMessage);
								return false;
							}
							i++;
							while(cnt<2) {
								if(i==kveri.length()) {
									errorMessage += "Lose deklarisan Join \n";
									errorMessage(errorMessage);
									return false;
								}
								cnt++;
								currChar=kveri.charAt(i);
								checker+=currChar;
								i++;
							}
							i--;
							if(checker.equals("On")) {
								i=checkFuncArgs(kveri, checker, i);
								if(i==0) {
									errorMessage += checker + " funkcija je lose definisana \n";
									
									errorMessage(errorMessage);
									return false;
								}
								i++;
								if(i== kveri.length() ) {
									validFlag = 0;
									goodMessage();
									//break;
									return true;
								}
								
								currChar = kveri.charAt(i);
								if(currChar != '.') {
									errorMessage += checker + " funkcija je lose definisana \n";
									
									errorMessage(errorMessage);
									return false;
								}
								checker="";
								continue;
								
								
								
							}
							
							else {
								errorMessage += "Lose deklarisan On \n";
								errorMessage(errorMessage);
								return false;
							}
							
							
						case "WhereIn":
							
							if(keywordList.contains(checker)) {
								errorMessage += checker + " funkcija vec postoji jedna \n";
								
								errorMessage(errorMessage);
								return false;
								
							}
							
							else keywordList.add(checker);
							
							i=checkFuncArgs(kveri, checker, i);
							
							System.out.println(i);
							

							if(i==0) {
								errorMessage += checker + " funkcija je lose definisana \n";
								
								errorMessage(errorMessage);
								return false;
							}
							
							int cnt1 = 0;
							checker="";
							i++;
							currChar= kveri.charAt(i);
							if(currChar != '.') {
								errorMessage += checker + " funkcija je lose definisana \n";
								
								errorMessage(errorMessage);
								return false;
							}
							i++;
							while(cnt1<13) {
								if(i==kveri.length()) {
									errorMessage += "Lose deklarisan WhereIn \n";
									errorMessage(errorMessage);
									return false;
								}
								cnt1++;
								currChar=kveri.charAt(i);
								checker+=currChar;
								i++;
							}
							i--;
							if(checker.equals("ParametarList")) {
								i=checkFuncArgs(kveri, checker, i);
								if(i==0) {
									errorMessage += checker + " funkcija je lose definisana \n";
									
									errorMessage(errorMessage);
									return false;
								}
								i++;
								if(i== kveri.length() ) {
									validFlag = 0;
									goodMessage();
									//break;
									return true;
								}
								
								currChar = kveri.charAt(i);
								if(currChar != '.') {
									errorMessage += checker + " funkcija je lose definisana \n";
									
									errorMessage(errorMessage);
									return false;
								}
								checker="";
								continue;
								
								
								
							}
							
							else {
								errorMessage += "Lose deklarisan ParametarList \n";
								errorMessage(errorMessage);
								return false;
							}
							

					}
					validFlag = 1;
					
				}
				if(validFlag == 1) {
					errorMessage += "greska u kodu od " + checker;
					errorMessage(errorMessage);
					return false;
				}
				
			//}
			
			if(errorFlag == 1) {
				//return;
			}
		//	numOfVars --;
		//}
		
		return false;
		
	}
	
	public static void errorMessage(String error) {
		
		String message = "greske: \n "+ error;
		JOptionPane.showMessageDialog(new JFrame(), message, "Error Error Error",
		        JOptionPane.ERROR_MESSAGE);
		
	}
	
	public static void goodMessage() {
			
			String message = "Unos je validan ";
			JOptionPane.showMessageDialog(new JFrame(), message, "Sve je ok!",
			        JOptionPane.INFORMATION_MESSAGE);
			
		}
	
	public static int  checkFuncArgs(String str,String keyword, int iter) {
		
		int i=iter+1;
		int intArg = 0;
		int intChar = 0;
		String intString="";
		System.out.println("keyword u funkc je "+keyword);
		
		//var hej = new Query("lelel").WhereContains("lelp","kolk")
		if(str.charAt(i)=='('){
			i++;
		}
		else return 0;

		if(str.charAt(i)=='\"'){
			i++;
		}
		else return 0;
		

		if(keyword.equals("Select") || keyword.equals("OrderBy") || keyword.equals("OrderByDesc") || keyword.equals("ParametarList") || keyword.equals("GroupBy")) {
			while(i!=str.length()) {
				System.out.println(str.charAt(i));
				//Proveri za nevalidne charove BADCHARS lista stavi da bude global ali ne za ',' i '\"'
				if(str.charAt(i)=='\"') {
					i++;
					if(str.charAt(i)==')') return i;
					if(str.charAt(i)==',') {
						i++;
						System.out.println("U zarezu sam char je " + str.charAt(i) );
						if(str.charAt(i) == ' ' || str.charAt(i) == '\n' || str.charAt(i) == '\t' ) {
							while(str.charAt(i) == ' ' || str.charAt(i) == '\n' || str.charAt(i) == '\t' ) i++;
						}
						if(str.charAt(i)=='\"') {
							i++;
							continue;
						}
						else return 0;
					}
					else return 0;
				}
				i++;
				
				//Beskonacna petlja ako ne zatvori zagradu smisli kad ce brejkujes
				
			}
		}
		
		if(keyword.equals("WhereEndsWith") || keyword.equals("WhereStartsWith") || keyword.equals("Where") || keyword.equals("OrWhere") ||	keyword.equals("AndWhere")
				|| keyword.equals("WhereContains") || keyword.equals("WhereIn") ||	keyword.equals("On") ||	keyword.equals("Join") ||	keyword.equals("WhereBetween")) {
			int countArgs = 0;
			System.out.println(keyword);
			if(keyword.equals("WhereEndsWith") || keyword.equals("WhereStartsWith") || keyword.equals("WhereContains") )  countArgs=1;
			if(keyword.equals("WhereIn") || keyword.equals("Join")  )  {
				System.out.println("usao u join if dobro");
				countArgs=2;
			}
			
			while(i!=str.length()) {
				System.out.println(str.charAt(i));
				//Proveri za nevalidne charove BADCHARS lista stavi da bude global ali ne za ',' i '\"'
				if(str.charAt(i)=='\"') {
					System.out.println(countArgs);
					i++;
					System.out.println(str.charAt(i));
					if(str.charAt(i)==')' && countArgs==2) return i;
					if(str.charAt(i)==',') {
						i++;
						if(str.charAt(i) == ' ' || str.charAt(i) == '\n' || str.charAt(i) == '\t' ) {
							while(str.charAt(i) == ' ' || str.charAt(i) == '\n' || str.charAt(i) == '\t' ) i++;
						}
						//System.out.println("usao u , ");
						//System.out.println(str);
						//System.out.println(str.charAt(i));
						if(str.charAt(i)=='\"') {
							System.out.println(str.charAt(i));
							countArgs++;
							i++;
							continue;
						}
						else return 0;
					}
					else return 0;
				}
				i++;
				
				//Beskonacna petlja ako ne zatvori zagradu smisli kad ce brejkujes
				
			}
		}
		
		if(keyword.equals("Avg") || keyword.equals("Count") || keyword.equals("Min") || keyword.equals("Max") ) {
			int countArgs = 0;
			while(i!=str.length()) {
				
				//System.out.println("ovde sam");
				System.out.println(str.charAt(i));
				//Proveri za nevalidne charove BADCHARS lista stavi da bude global ali ne za ',' i '\"'
				if(str.charAt(i)=='\"') {
					i++;
					if((str.charAt(i)==')') && (countArgs == 0 || countArgs == 1)) return i;
					if(str.charAt(i)==',') {
						System.out.println("usao u , countArgs je" + countArgs);
						i++;
						System.out.println("char koji treba da bude \" je "+ str.charAt(i));
						if(str.charAt(i) == ' ' || str.charAt(i) == '\n' || str.charAt(i) == '\t' ) {
							while(str.charAt(i) == ' ' || str.charAt(i) == '\n' || str.charAt(i) == '\t' ) i++;
						}
						if(str.charAt(i)=='\"') {
							countArgs++;
							i++;
							continue;
						}
						else return 0;
					}
					else return 0;
				}
				i++;
				
				//Beskonacna petlja ako ne zatvori zagradu smisli kad ce brejkujes
				
			}
		}
		/*
		if(keyword.equals("Where") || keyword.equals("OrWhere") || keyword.equals("AndWhere")  ) {
			int countArgs = 0;
			while(i!=str.length()) {
				
				//System.out.println("ovde sam");
				System.out.println(str.charAt(i));
				//Proveri za nevalidne charove BADCHARS lista stavi da bude global ali ne za ',' i '\"'
				if(str.charAt(i)=='\"') {
					i++;
					if((str.charAt(i)==')') && (countArgs == 2)) return i;
					if(str.charAt(i)==',') {
						System.out.println("usao u , countArgs je" + countArgs);
						i++;
						System.out.println("char koji treba da bude \" je "+ str.charAt(i));
						if(str.charAt(i) == ' ' || str.charAt(i) == '\n' || str.charAt(i) == '\t' ) {
							while(str.charAt(i) == ' ' || str.charAt(i) == '\n' || str.charAt(i) == '\t' ) i++;
						}
						
						
						if(countArgs == 1) {
							while(str.charAt(i)!=')') {
								System.out.println("ulazim tamo gde treba");
								
								if(Character.isDigit(str.charAt(i))) {
									i++;
								}
								else {
									return 0;
									
								}
								
								if(str.charAt(i) == ')') return i;
								
							}
							
						
							
						}
						if(str.charAt(i)=='\"') {
							countArgs++;
							i++;
							continue;
						}
						else return 0;
					}
					else return 0;
				}
				i++;
				
				//Beskonacna petlja ako ne zatvori zagradu smisli kad ce brejkujes
				
			}
			*/
		//}
		
		return 0;
		
	}
	
}
