package com.github.philanthropix.ipvalidator;

import java.util.TreeSet;

/**
 *
 */
public class RuleTree {

    private static RuleNode topRuleNode = new RuleNode(new TreeEntry(0, 0, false));

    public RuleTree(TreeSet<TreeEntry> rules) {
        this.topRuleNode =  buildTree(rules);
    }

    protected boolean validateIp(long longIp) {
        return getTopRuleNode().validateIP(longIp);
    }

    private RuleNode buildTree(TreeSet<TreeEntry> entrySet) {

        RuleNode newTopCidrRule = new RuleNode(new TreeEntry(0, 0, false));

        if (entrySet.first().equals(newTopCidrRule.convertToEntry())) {
            newTopCidrRule = new RuleNode(entrySet.pollFirst());
        }
        for (TreeEntry entry : entrySet) {
            newTopCidrRule.addNewEntry(entry);
        }
        return newTopCidrRule;
    }

    protected RuleNode getTopRuleNode() {
        return topRuleNode;
    }
}
