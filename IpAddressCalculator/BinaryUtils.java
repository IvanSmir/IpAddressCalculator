package IpAddressCalculator;


public class BinaryUtils {

    public static String decimalToBinary(int decimal) {
        String binary = Integer.toBinaryString(decimal);
        while (binary.length() < 8) {
            binary = "0" + binary;
        }
        return binary;
    }

    public static boolean isValidBinaryPattern(String binary) {
        int index = binary.indexOf('0');
        return index == -1 || binary.substring(index).indexOf('1') == -1;
    }
}
