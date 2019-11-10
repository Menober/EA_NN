import org.junit.Test;


public class Tests {

//    @Test
//    public void mutationTest() {
//        Individual i = new Individual();
//        i.weights = new Double[]{1.0, 2.0};
//        for (int j = 0; j++ < 100; ) {
//            i.mutation(1000);
//            System.out.println(i.weights[0] + " " + i.weights[1]);
//        }
//    }

    @Test
    public void test() {
        Double x = Utils.randomDouble(Double.MIN_VALUE, Double.MAX_VALUE);
        System.out.println(x);
        System.out.println(convertDoubleToString(x));
        System.out.println(convertStringToDouble(convertDoubleToString(x)));
        System.out.println();
        String string = convertDoubleToString(x);
        System.out.println(string);
        char[] bytes = string.toCharArray();
        char[] bytes2 = bytes.clone();
        if (bytes[0] == '1')
            bytes[0] = '0';
        else
            bytes[0] = '1';
        for (char b : bytes) {
            System.out.print(b);
        }
        System.out.println();
        System.out.println(convertStringToDouble(String.valueOf(bytes2)));
        System.out.println(convertStringToDouble(String.valueOf(bytes)));
    }


    public String convertDoubleToString(Double d) {
        return Long.toBinaryString(Double.doubleToLongBits(d));
    }

    public Double convertStringToDouble(String s) {
        return Double.longBitsToDouble(Long.parseLong(s, 2));
    }
}
