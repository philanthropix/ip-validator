package com.github.philanthropix.ipvalidator;

import org.junit.Test;

import static com.github.philanthropix.ipvalidator.IpUtil.bitsToIpv4Cidr;
import static com.github.philanthropix.ipvalidator.IpUtil.ipv4CidrToLong;
import static com.github.philanthropix.ipvalidator.IpUtil.ipV4AddressToLong;
import static junit.framework.Assert.assertEquals;

/**
 *
 */
public class IpUtilTest {

    @Test
    public void testConvertStrNetToLong() throws Exception {
        long[] longs = ipv4CidrToLong("10.129.0.254/24");
        assertEquals(0x0A8100FEL, longs[0]);
        assertEquals(0xFFFFFF00L, longs[1]);

        longs = ipv4CidrToLong("10.129.0.254/32");
        assertEquals(0x0A8100FEL, longs[0]);
        assertEquals(0xFFFFFFFFL, longs[1]);

        longs = ipv4CidrToLong("10.129.0.254/0");
        assertEquals(0x0A8100FEL, longs[0]);
        assertEquals(0x00000000L, longs[1]);
    }

    @Test
    public void testConvertStringIpToLong() throws Exception {
        assertEquals(0x0A8100FEL, ipV4AddressToLong("10.129.0.254"));
        assertEquals(0xFD008208L, ipV4AddressToLong("253.0.130.8"));
    }

    @Test
    public void testConvertNetworkToString() throws Exception {
        assertEquals("10.129.0.254/24", bitsToIpv4Cidr(0x0A8100FEL, 0xFFFFFF00L));
        assertEquals("10.129.0.254/32", bitsToIpv4Cidr(0x0A8100FEL, 0xFFFFFFFFL));
        assertEquals("10.129.0.254/0", bitsToIpv4Cidr(0x0A8100FEL, 0x00000000L));
    }
}
