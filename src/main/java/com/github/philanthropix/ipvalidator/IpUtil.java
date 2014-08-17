package com.github.philanthropix.ipvalidator;


import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 *
 */
public class IpUtil {

    private IpUtil() {
        throw new AssertionError();
    }

    /**
     * Converts <IP>/<CIDR> format ie. 10.0.3.55/25, to an array of long with two elements
     *
     * @param networkCidr
     * @return long[] - an array of long the address being element 0 and the CIDR being element 1
     * @throws java.net.UnknownHostException
     */
    public static long[] ipv4CidrToLong(String networkCidr) throws UnknownHostException {
        String[] strNets = networkCidr.split("/");
        return new long[]{ipV4AddressToLong(strNets[0]), convertStrCidrToLong(strNets[1])};
    }

    /**
     * Converts an IP address string like 192.168.0.1 to an internal long value
     *
     * @param ipAddress
     * @return long the numerical representation of the address
     * @throws UnknownHostException if the address is not a valid IPv4 address
     */
    public static long ipV4AddressToLong(String ipAddress) throws UnknownHostException {

        byte[] bytes = InetAddress.getByName(ipAddress).getAddress();

        return convertByteToLong(bytes);
    }

    /**
     * Converts the numerical representation of the IP address and CIDR
     * to the String representation IP/CIDR, ie. 10.0.2.55/24
     *
     * @param ipAddress - the numerical representation of the IP Address
     * @param cidr      - the numerical representation of the CIDR
     * @return String - the IP/CIDR notation
     */
    public static String bitsToIpv4Cidr(long ipAddress, long cidr) throws UnknownHostException {

        StringBuffer strNet = new StringBuffer(20);
        strNet.append(converLongToIpString(ipAddress));
        strNet.append("/");
        strNet.append(convertLongToCidr(cidr));

        return strNet.toString();
    }


    private static long convertByteToLong(byte[] ipBytes) {

        long unsignedInt = 0;

        for (int i = 0; i < ipBytes.length; i++) {
            unsignedInt |= ((0x000000FF & ((int) ipBytes[i])) << ((ipBytes.length - i - 1) * 8));
        }

        return unsignedInt & 0xFFFFFFFFL;
    }

    private static byte[] convertLongToBytes(final long unsignedIntIp) {

        byte[] bytes = new byte[4];
        bytes[0] = (byte) ((unsignedIntIp & 0xFF000000L) >>> 24);
        bytes[1] = (byte) ((unsignedIntIp & 0x00FF0000L) >> 16);
        bytes[2] = (byte) ((unsignedIntIp & 0x0000FF00L) >> 8);
        bytes[3] = (byte) (unsignedIntIp & 0x000000FFL);

        return bytes;
    }

    public static long convertCidrToLong(int cidr) {

        if (cidr > 32 || cidr < 0)
            throw new IllegalArgumentException("[" + cidr + "]" + " is invalid, CIDR must be greater that 0 and less than 32");

        return ((0xFFFFFFFFL << (32 - cidr)) & 0xFFFFFFFFL);
    }

    public static int convertLongToCidr(long unsignedIntCidr) {

        for (int i = 32; i >= 0; i--) {
            if ((unsignedIntCidr & 0x00000001) == 1) return i;
            unsignedIntCidr >>>= 1;
        }

        return 0;
    }

    private static String converLongToIpString(long unsignedIntIp) throws UnknownHostException {
        return InetAddress.getByAddress(convertLongToBytes(unsignedIntIp)).getHostAddress();
    }

    private static long convertStrCidrToLong(String strCidr) {
        int intCidr = new Integer(strCidr);
        return convertCidrToLong(intCidr);
    }
}
