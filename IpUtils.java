package javaApp;

import java.util.Scanner;

public class IpUtils {

    public static String[] pedirIp() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Ingresa una direccion IP/mask ('ip/mascara' o 'ip/mascara_decimal'.): ");
        String input = scanner.nextLine();

        String[] partes = input.split("/");
        if (partes.length != 2) {
            System.out.println("Formato incorrecto. Debe ser 'ip/mascara' o 'ip/mascara_decimal'.");
        }
        return partes;
    }

    public static boolean isValidIp(String ip) {
        String[] partes = ip.split("\\.");
        if (partes.length != 4) {
            System.out.println("Formato incorrecto de Ip Debe ser 0.0.0.0 hasta 255.255.255.255");
            return false;
        } else if (!isvalidNumber(partes)) {
            System.out.println("Formato incorrecto de Ip Debe ser 0.0.0.0 hasta 255.255.255.255");
            return false;
        } else {
            return true;
        }
    }

    public static boolean isvalidNumber(String[] ips) {
        for (String partes : ips) {
            try {
                int value = Integer.parseInt(partes);
                if (value < 0 || value > 255) {
                    return false;
                }
            } catch (NumberFormatException e) {
                return false;
            }
        }
        return true;
    }

    public static boolean isValidMask(String mask) {
        if (isValidDecimalMask(mask) || isValidCidrMask(mask)) {
            return true;
        } else {
            System.out.println(
                    "Formato incorrecto de mascara Debe ser formato decimal(0.0.0.0-255.255.255.255) o Cidr(0-32)");
            return false;
        }
    }

    public static boolean isValidCidrMask(String mask) {
        try {
            int value = Integer.parseInt(mask);
            if (value < 0 || value > 32) {
                return false;
            }
        } catch (NumberFormatException e) {
            return false;
        }
        return true;
    }

    public static boolean isValidDecimalMask(String mask) {
        String[] partes = mask.split("\\.");
        if (partes.length == 4) {
            if (isValidDecimalMaskNumbers(partes)) {
                return true;
            }
        }
        return false;
    }

    public static boolean isValidDecimalMaskNumbers(String[] mask) {
        for (String part : mask) {
            try {
                int value = Integer.parseInt(part);
                if (!BinaryUtils.isValidBinaryPattern(BinaryUtils.decimalToBinary(value))) {
                    return false;
                }
    
            } catch (NumberFormatException e) {
                return false;
            }
        }
        return true;
    }
    
    

    public static String[] getMaskParts(String mask) {
        String[] maskParts;

        if (isValidCidrMask(mask)) {
            String maskDecimal = maskCidrtoDecimal(mask);
            maskParts = maskDecimal.split("\\.");
        } else {
            maskParts = mask.split("\\.");
        }

        return maskParts;
    }

    public static String[] getNetwork(String[] ip) {
        String[] ippartes = ip[0].split("\\.");
        String[] mask = getMaskParts(ip[1]);
        String[] network = new String[4];

        for (int i = 0; i < 4; i++) {
            int ipPart = Integer.parseInt(ippartes[i]);
            int maskPart = Integer.parseInt(mask[i]);
            int networkPart = ipPart & maskPart;
            network[i] = Integer.toString(networkPart);
        }

        return network;
    }

    public static String[] getBroadcast(String[] ip) {
        String[] ippartes = ip[0].split("\\.");
        String[] mask = getMaskParts(ip[1]);

        String[] broadcast = new String[4];

        for (int i = 0; i < 4; i++) {
            int ipPart = Integer.parseInt(ippartes[i]);
            int maskPart = Integer.parseInt(mask[i]);
            int broadcastPart = ipPart | (255 - maskPart);
            broadcast[i] = Integer.toString(broadcastPart);
        }

        return broadcast;
    }

    private static String maskCidrtoDecimal(String masks) {
        int mask = Integer.parseInt(masks);
        String maskaux = "";
        for (int i = 0; i < mask; i++) {
            maskaux = maskaux + "1";
        }
        for (int i = maskaux.length(); i < 32; i++) {
            maskaux = maskaux + "0";
        }
        String maskString = "";
        for (int i = 0; i < 4; i++) {
            int start = i * 8;
            int end = start + 8;
            String part = maskaux.substring(start, end);
            maskString = maskString + Integer.parseInt(part, 2);

            if (i < 3) {
                maskString = maskString + ".";
            }
        }

        return maskString;
    }

    public static String[] getInverseMask(String[] mask) {
        String[] inverseMask = new String[4];

        for (int i = 0; i < 4; i++) {
            int maskPart = Integer.parseInt(mask[i]);
            int inversePart = 255 - maskPart;
            inverseMask[i] = Integer.toString(inversePart);
        }

        return inverseMask;
    }
}
