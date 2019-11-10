import java.util.Random;

public class Utils {


    public static int randomInt(int min, int max) {
        Random r = new Random();
        return r.nextInt((max - min) + 1) + min;
    }

    public static Double randomDouble(Double min, Double max) {
        Random r = new Random();
        return min + (max - min) * r.nextDouble();
    }

    public static String convertDoubleToString(Double d) {
        return Long.toBinaryString(Double.doubleToLongBits(d));
    }

    public static Double convertStringToDouble(String s) {
        return Double.longBitsToDouble(Long.parseLong(s, 2));
    }
}
