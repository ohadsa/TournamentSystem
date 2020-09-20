package Championship.util;

public class StringUtil {

	public static boolean isOnlyLettersAndSpaces(String str) {
		if (isEmptyOrNull(str)) { // cannot be empty or null
			return false;
		}

		if (str.charAt(0) == ' ') { // cannot start with a space
			return false;
		}

		for (int i = 0; i < str.length(); i++) {
			if (!Character.isLetter(str.charAt(i)) && str.charAt(i) != ' ') { // must be a letter or a space
				return false;
			}
		}
		return true;
	}

	public static String toUpperCaseFirstChar(String str) {
		if (isEmptyOrNull(str)) {
			return "";
		}

		char[] temp = str.toCharArray();
		StringBuffer res = new StringBuffer("");

		if (Character.isLetter(temp[0])) {
			temp[0] = Character.toUpperCase(temp[0]);
		}

		res.append(temp[0]);
		for (int i = 1; i < temp.length; i++) {
			if (temp[i - 1] == ' ' && Character.isLetter(temp[i])) {  // if last is space and next is a letter, capital the letter																 
				temp[i] = Character.toUpperCase(temp[i]);
			}
			res.append(temp[i]);
		}
		return res.toString();
	}

	public static boolean isEmptyOrNull(String str) {
		return str == null || str.isEmpty();
	}

}
