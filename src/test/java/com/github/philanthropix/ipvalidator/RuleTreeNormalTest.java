package com.github.philanthropix.ipvalidator;

import org.junit.Before;
import org.junit.Test;

import java.util.TreeSet;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 *
 */
public class RuleTreeNormalTest {
    private RuleTree ruleBase;
    private TreeSet<TreeEntry> entrySet = new TreeSet<TreeEntry>();

    private static final TreeEntry ROOT_ENTRY = new TreeEntry(0, 0, false);
    private static final TreeEntry ENTRY1 = new TreeEntry(0x0A3A0703L, 0xFFFFFFC0L, true);
    private static final TreeEntry ENTRY2 = new TreeEntry(0x0AF619F9L, 0xFFFFFFC0L, true);
    private static final TreeEntry ENTRY3 = new TreeEntry(0x0AF61979L, 0xFFFFFFC0L, false);
    private static final TreeEntry ENTRY4 = new TreeEntry(0x0AF619B9L, 0xFFFFFFC0L, false);
    private static final TreeEntry ENTRY5 = new TreeEntry(0x0AF619B9L, 0xFFFFFF00L, true);
    private static final TreeEntry ENTRY6 = new TreeEntry(0x0AF618B9L, 0xFFFFFF00L, false);
    private static final TreeEntry ENTRY7 = new TreeEntry(0x0AF61721L, 0xFFFFFF00L, true);
    private static final TreeEntry ENTRY8 = new TreeEntry(0x0AF61621L, 0xFFFFFF00L, true);
    private static final TreeEntry ENTRY9 = new TreeEntry(0x0AF61521L, 0xFFFFFF00L, false);
    private static final TreeEntry ENTRY10 = new TreeEntry(0x0AF61021L, 0xFFFF0000L, true);
    private static final TreeEntry ENTRY11 = new TreeEntry(0x0AE71025L, 0xFFFF0000L, true);
    private static final TreeEntry ENTRY12 = new TreeEntry(0x0A061025L, 0xFFFF0000L, true);

    private void prepareEntrySet() {
        entrySet.add(ENTRY7);
        entrySet.add(ENTRY1);
        entrySet.add(ENTRY11);
        entrySet.add(ENTRY4);
        entrySet.add(ENTRY9);
        entrySet.add(ENTRY5);
        entrySet.add(ENTRY6);
        entrySet.add(ROOT_ENTRY);
        entrySet.add(ENTRY8);
        entrySet.add(ENTRY10);
        entrySet.add(ENTRY2);
        entrySet.add(ENTRY12);
        entrySet.add(ENTRY3);
    }

    @Before
    public void setUp() throws Exception {
        prepareEntrySet();
        ruleBase = new RuleTree(entrySet);
    }

    @Test
    public void testInRuleEntry() throws Exception {
        assertTrue(ruleBase.validateIp(0x0A3A0708L));
        assertTrue(ruleBase.validateIp(0x0AF619FAL));
        assertFalse(ruleBase.validateIp(0x0AF61970L));
        assertFalse(ruleBase.validateIp(0x0AF619B2L));
        assertFalse(ruleBase.validateIp(0x0AF618B0L));
        assertTrue(ruleBase.validateIp(0x0AF61722L));
        assertTrue(ruleBase.validateIp(0x0AF61622L));
        assertFalse(ruleBase.validateIp(0x0AF61522L));
        assertTrue(ruleBase.validateIp(0x0AE71125L));
        assertTrue(ruleBase.validateIp(0x0A061125L));
        assertTrue(ruleBase.validateIp(0x0AF61901L));
        assertTrue(ruleBase.validateIp(0x0AF61121L));
    }

    @Test
    public void testOutRuleEntry() throws Exception {
        assertFalse(ruleBase.validateIp(0x0A051025L));
        assertFalse(ruleBase.validateIp(0x0AE81025L));
        assertFalse(ruleBase.validateIp(0x0AF11020L));
        assertTrue(ruleBase.validateIp(0x0AF61409L));
        assertTrue(ruleBase.validateIp(0x0AF61908L));
        assertFalse(ruleBase.validateIp(0x0A3A0789L));
    }
}
