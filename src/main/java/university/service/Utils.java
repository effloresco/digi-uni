package university.service;

import java.util.List;
import java.util.Random;

public final class Utils {
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_CYAN = "\u001B[36m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_BOLD = "\u001B[1m";
    public static final int WIDTH = 40;
    public static final String OPT0 = "[0] Вихід";

    public enum Mt {
        Success, Error, Warning
    }

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
            if (!Character.isLetter(c) && !Character.isSpaceChar(c)) {
                return true;
            }
        }
        return false;
    }

    public static int getRandomNumber() {
        int min = 0;
        int max = 999999999;
        Random random = new Random();
        Integer randomNumber = random.nextInt(max - min + 1) + min;
        return randomNumber;
    }

    public static void printHeader(String s) {
        System.out.println("\n" + ANSI_CYAN + "=".repeat(WIDTH) + ANSI_RESET);
        System.out.println(ANSI_BOLD + " " + s + ANSI_RESET);
        System.out.println(ANSI_CYAN + "=".repeat(WIDTH) + ANSI_RESET);
    }

    public static void printPrompt(String s) {
        System.out.println(ANSI_CYAN + "-".repeat(WIDTH) + ANSI_RESET);
        System.out.print(s + " ");
    }

    public static void printMessage(Mt mt, String message) {
        String color = "";
        String nl = "";

        switch (mt) {
            case Error -> {
                color = ANSI_RED;
                nl = "\n";
            }
            case Success -> color = ANSI_GREEN;
            case Warning -> color = ANSI_YELLOW;
        }
        System.out.println(color + " " + message + ANSI_RESET + nl);
    }

    public static void printMessage(Mt mt, String message, String s) {
        printMessage(mt, message);
        if (!s.isEmpty())
            System.out.println(" " + s);
    }

    public static void printMessage(Mt mt, String message, String s, Boolean addRow) {
        if (addRow)
            System.out.println();
        printMessage(mt, message, s);
    }

    public static void printMenu(String header, List<String> items) {
        printHeader(header);
        items.forEach(item -> System.out.println(" " + item));
        printPrompt(">");
    }

}