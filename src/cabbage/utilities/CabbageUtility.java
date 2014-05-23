package cabbage.utilities;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class CabbageUtility {

	public static boolean checkLengthOfString(String string, int min, int max) {
		if(string == null) return false;
		if (string.length() >= min && string.length() <= max){
			return true;
		}
		return false;
	}

	public static boolean isEmailFormat(String string) {
		if(string == null) return false;
		if(!string.matches("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$")) return false;
		return true;
	}

	public static boolean containsUpper(String string){  
		if(string == null) return false;
		if (!string.matches(".*[A-Z].*")) return false;
		return true;
	}
	public static boolean containsLower(String string){   
		if(string == null) return false;
		if (!string.matches(".*[a-z].*")) return false;
		return true;
	}
	public static boolean containsNumber(String string){  
		if(string == null) return false;
		if (!string.matches(".*[0-9].*")) return false;
		return true;
	}

	public static boolean matchTwoStrings(String s1, String s2) {
		if(s1 == null) return false;
		if(s2 == null) return false;
		if(s1.toLowerCase().equals(s2.toLowerCase())) return true;
		return false;
	}

	public static boolean isAlphaNumeric(String s) {
		if(s == null) return false;

		return false;
	}

	public static boolean containsNaughtyWords(String s) {
		if(s == null) return false;

		return false;
	}

	//

	// Checks a given string is numeric.
	public static boolean isNumeric(String string) {
		if (string == null) return false;

		for (char c : string.toCharArray()) {
			if (!Character.isDigit(c)) return false;
		}
		return true;
	}

	// Checks that two chars are the same. - isCharacter
	public static boolean matchTwoChars(char c1, char c2) {
		if (c1 == 0) return false;
		if (c2 == 0) return false;

		if (c1 != c2) return false;
		return true;
	}

	// Checks that the numbers contained in a string are within a given range.
	public static boolean isInBounds(String string, int min, int max) {
		if (string == null) return false;
		if (!(isNumeric(string))) return false;

		int num = Integer.parseInt(string);
		if (! (num >= min && num <= max)) return false;
		return true;
	}

	//
	public static Map<Integer,Integer> sortTwoLists(List<Integer> keys, List<Integer> values) {
		System.out.println("\nSorting...");
		if (keys.size() != values.size()) return null;
		Map<Integer,Integer> m = new TreeMap<Integer,Integer>();
		System.out.println("keys before: " + keys);
		System.out.println("vals before: " + values);
		for (int w = 0; w < keys.size() - 1; w++){
			for (int x = 0; x < keys.size() - 1; x++){
				if (values.get(x) > values.get(x+1)) {
					int tempValue = values.get(x);
					values.set(x, values.get(x+1));
					values.set(x+1,tempValue);
					int tempKey = keys.get(x);
					keys.set(x, keys.get(x+1));
					keys.set(x+1,tempKey);
				}
			}
		}
		for (int i = 0; i < keys.size();i++) {
			m.put(keys.get(i), values.get(i));
		}
		System.out.println("keys after: " + keys);
		System.out.println("vals after: " + values);
		System.out.println("\nMap: " + m);
		return m;
	}
}
