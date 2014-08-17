package com.github.philanthropix.ipvalidator;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.philanthropix.ipvalidator.json.TreeConfig;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.InputStream;

import static com.github.philanthropix.ipvalidator.IpUtil.ipV4AddressToLong;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 *
 */
public class WhiteListMiniITest {

    private static TreeConfig whiteListDef;

    private RuleTree ruleTree;

    private static ObjectMapper mapper = new ObjectMapper();

    @BeforeClass
    public static void init() throws Exception {
        InputStream is = WhiteListMiniITest.class.getClassLoader().getResourceAsStream("whitelist.json");
        whiteListDef = mapper.readValue(is, TreeConfig.class);
    }

    @Before
    public void setUp() throws Exception {
        ruleTree = RuleTreeFactory.createRuleTree(whiteListDef);
    }

    @Test
    public void testInRuleEntry() throws Exception {
        assertFalse(ruleTree.validateIp(ipV4AddressToLong("12.5.8.9")));
        assertTrue(ruleTree.validateIp(ipV4AddressToLong("11.192.253.246")));
        assertFalse(ruleTree.validateIp(ipV4AddressToLong("10.8.1.234")));
        assertTrue(ruleTree.validateIp(ipV4AddressToLong("10.253.10.88")));
        assertTrue(ruleTree.validateIp(ipV4AddressToLong("10.127.2.19")));
        assertTrue(ruleTree.validateIp(ipV4AddressToLong("10.127.3.1")));
        assertFalse(ruleTree.validateIp(ipV4AddressToLong("10.127.254.91")));
        assertFalse(ruleTree.validateIp(ipV4AddressToLong("252.18.10.199")));
        assertTrue(ruleTree.validateIp(ipV4AddressToLong("252.18.6.77")));
        assertFalse(ruleTree.validateIp(ipV4AddressToLong("10.253.8.99")));
        assertTrue(ruleTree.validateIp(ipV4AddressToLong("10.155.0.9")));
    }
}
