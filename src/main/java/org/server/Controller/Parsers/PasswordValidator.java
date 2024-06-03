package org.server.Controller.Parsers;

public class PasswordValidator {
    public static boolean containsNumsAndChars(String password) {
        char[] characters = password.toCharArray();
        boolean hasNum = false;
        boolean hasChar = false;

        for (char character : characters){
            if (Character.isDigit(character)){
                hasNum = true;
            }
            if (Character.isLetter(character)){
                hasChar = true;
            }
        }

        return hasNum && hasChar;
    }

    public static boolean checkPasswordLength(String password) {
        return password.length() >= 8;
    }

    public static boolean isSamePassword(String password, String reEnter){
        return password.equals(reEnter);
    }
}
