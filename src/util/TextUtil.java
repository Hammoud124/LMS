package util;

public final class TextUtil {

    private TextUtil() {}

    public static boolean isEmpty(String str) {
        return str == null || str.trim().isEmpty();
    }

}
