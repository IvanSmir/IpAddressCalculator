package IpAddressCalculator;

import java.util.Scanner;
import IpAddressCalculator.BinaryUtils;
import IpAddressCalculator.IpUtils;


public class Calculator {

    public static void runCalculator() {
        Scanner scanner = new Scanner(System.in);
        char choice = 'n';

        while (choice != 's') {
            String[] partes = IpUtils.pedirIp();

            if (partes != null && partes.length == 2) {
                String ipString = partes[0];
                String maskString = partes[1];

                if (IpUtils.isValidIp(ipString) && IpUtils.isValidMask(maskString)) {
                    System.out.println("IP y mascara son correctos.");
                    printResults(partes);
                } else {
                    System.out.println("Ip o mascara incorrecto");
                }
            }

            System.out.print("Ingrese s para salir: ");
            try {
                choice = scanner.nextLine().charAt(0);
            } catch (StringIndexOutOfBoundsException e) {
                choice = 'n'; 
            }
        }

        scanner.close();
    }

    private static void printResults(String[] partes) {
        String ipString = partes[0];
        String maskString = partes[1];
    
        System.out.println("IP y máscara: " + ipString + "/" + maskString);
    
        String[] ipNumbers = ipString.split("\\.");
        String[] binaryIpNumbers = convertToBinary(ipNumbers);
    
        String[] mask = IpUtils.getMaskParts(maskString);
        String[] binaryMask = convertToBinary(mask);
    
        String[] network = IpUtils.getNetwork(partes);
        String[] binaryNetwork = convertToBinary(network);
    
        String[] inverseMask = IpUtils.getInverseMask(mask);
        String[] binaryInverseMask = convertToBinary(inverseMask);
    
        String[] broadcast = IpUtils.getBroadcast(partes);
        String[] binaryBroadcast = convertToBinary(broadcast);
    
        printData("Decimal Ip", ipNumbers);
        printData("Binary    ", binaryIpNumbers);
        printData("Mask      ", binaryMask);
        System.out.println("Se realiza el and");
        printData("Network   ", binaryNetwork);
        printData("Network   ", network);
        System.out.println("\n\n");
        printData("Decimal   ", ipNumbers);
        printData("Binary    ", binaryIpNumbers);
        printData("Inverse Mask", binaryInverseMask);
        System.out.println("Se realiza el or");
        printData("Broadcast   ", binaryBroadcast);
        printData("Broadcast   ", broadcast);
    }
    
    private static String[] convertToBinary(String[] numbers) {
        String[] binaryNumbers = new String[numbers.length];
        for (int i = 0; i < numbers.length; i++) {
            binaryNumbers[i] = BinaryUtils.decimalToBinary(Integer.parseInt(numbers[i]));
        }
        return binaryNumbers;
    }
    
    private static void printData(String label, String[] data) {
        System.out.printf("%s:\t%-16s%-16s%-16s%-16s\n", label, data[0], data[1], data[2], data[3]);
    }
    
}
