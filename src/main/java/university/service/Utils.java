package university.service;

import java.util.Random;

public final class Utils {
    public static boolean containsNonDigit(String s) {
        if (s == null) {
            return false;
        }
        if (s.isEmpty())
            return false;

        for (char c : s.toCharArray()) {
            if (!Character.isDigit(c)) {
                return true;
            }
        }
        return false;
    }

    public static boolean containsNonLetter(String s) {
        if (s == null) {
            return false;
        }
        if (s.isEmpty())
            return false;

        for (char c : s.toCharArray()) {
            if (!Character.isLetter(c)) {
                return true;
            }
        }
        return false;
    }
    public static int getRandomNumber(){
        int min = 0;
        int max = 999999999;
        Random random = new Random();
        Integer randomNumber = random.nextInt(max - min + 1) + min;
    return randomNumber;
    }
}
