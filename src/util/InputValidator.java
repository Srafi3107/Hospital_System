package util;

public class InputValidator {
    public static boolean isInteger(String s) {
        try { Integer.parseInt(s); return true; } catch (Exception e) { return false; }
    }

    public static boolean isDouble(String s) {
        try { Double.parseDouble(s); return true; } catch (Exception e) { return false; }
    }
}