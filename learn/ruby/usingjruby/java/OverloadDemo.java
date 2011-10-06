import java.util.List;
public class OverloadDemo {
    public static String whatTypeIs(long value) {
        return "long";
    }
    public static String whatTypeIs(String value) {
        return "string";
    }
    public static String whatTypeIs(Object value) {
        return "object";
    }

    public int neededFor(int i) {
        return 32;
    }
    public int neededFor(long l) {
        return 64;
    }
}