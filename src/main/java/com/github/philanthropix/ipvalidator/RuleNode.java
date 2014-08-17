package com.github.philanthropix.ipvalidator;


import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 */
public class RuleNode {

    private static final Logger log = Logger.getLogger(RuleNode.class.getName());

    private long netIp;
    private long cidr;
    private long nextTopCidr;
    private boolean allowed;
    private boolean parentAllowed;
    private HashMap<Long, RuleNode> rules = new HashMap<Long, RuleNode>();

    protected RuleNode(TreeEntry newEntry) {
        this.cidr = newEntry.getCidr();
        this.allowed = newEntry.isAllowed();
        this.netIp = newEntry.getIpAddress() & newEntry.getCidr();
    }

    protected boolean validateIP(long longIp) {

        log.log(Level.FINE, "Validating [{}] in [{}]", new Object[]{longIp, this});

        if (isLeaf()) {
            log.log(Level.FINE, "Reached Net:[{}/{}] entry leaf.", new Object[]{getNetIp(), getCidr()});

            return (longIp & getCidr()) == getNetIp() ? isAllowed() : isParentAllowed();
        }
        RuleNode subTree = getRules().get(longIp & getNextTopCidr());

        return subTree == null ? isAllowed() : subTree.validateIP(longIp);
    }

    protected void addNewEntry(TreeEntry newEntry) {
        log.log(Level.FINE, "Adding new entry [{}] to rule [{}]", new Object[]{newEntry, this});
        if (isLeaf()) addEntryToLeaf(newEntry);
        else if (newEntry.getCidr() == getNextTopCidr()) addNewLeaf(newEntry);
        else if (newEntry.getCidr() > getNextTopCidr()) addEntryIntoSubLevel(newEntry);
        else if (newEntry.getCidr() < getNextTopCidr()) moveCurrentSubLevelIntoNewEntry(newEntry);
    }

    private void addEntryToLeaf(TreeEntry newEntry) {
        setNextTopCidr(newEntry.getCidr());
        addNewLeaf(newEntry);
    }

    private void addEntryIntoSubLevel(TreeEntry newEntry) {

        long netKey = newEntry.getIpAddress() & getNextTopCidr();
        RuleNode subRules = getRules().get(netKey);

        if (subRules == null) {
            addNewLeaf(newEntry);
        } else {
            subRules.addNewEntry(newEntry);
        }
    }

    private void addNewLeaf(TreeEntry newEntry) {

        RuleNode newLeaf = new RuleNode(newEntry);
        newLeaf.setParentAllowed(isAllowed());
        getRules().put(newEntry.getIpAddress() & getNextTopCidr(), newLeaf);
    }

    private void moveCurrentSubLevelIntoNewEntry(TreeEntry newEntry) {

        RuleNode newRuleNode = new RuleNode(newEntry);
        newRuleNode.setNextTopCidr(getNextTopCidr());
        setNextTopCidr(newRuleNode.getCidr());
        HashMap<Long, RuleNode> newSubRules = new HashMap<Long, RuleNode>();
        newSubRules.put(newRuleNode.getNetIp() & getNextTopCidr(), newRuleNode);

        for (RuleNode ruleNode : getRules().values()) {
            long netKey = ruleNode.getNetIp() & getNextTopCidr();

            if (newSubRules.containsKey(netKey)) {
                newRuleNode.getRules().put(ruleNode.getNetIp() & newRuleNode.getNextTopCidr(), ruleNode);
            } else {
                newSubRules.put(netKey, ruleNode);
            }
        }

        setRules(newSubRules);
    }

    protected TreeEntry convertToEntry(){
        return new TreeEntry(getNetIp(), getCidr(), isAllowed());
    }

    private boolean isLeaf() {
        return getRules().isEmpty();
    }

    protected long getCidr() {
        return cidr;
    }

    private HashMap<Long, RuleNode> getRules() {
        return rules;
    }

    private void setRules(HashMap<Long, RuleNode> rules) {
        this.rules = rules;
    }

    private boolean isAllowed() {
        return allowed;
    }

    private long getNetIp() {
        return netIp;
    }

    private long getNextTopCidr() {
        return nextTopCidr;
    }

    private void setNextTopCidr(long nextTopCidr) {
        this.nextTopCidr = nextTopCidr;
    }

    private boolean isParentAllowed() {
        return parentAllowed;
    }

    private void setParentAllowed(boolean parentAllowed) {
        this.parentAllowed = parentAllowed;
    }

    @Override
    public String toString() {
        try {
            return "RuleNode{" + IpUtil.bitsToIpv4Cidr(netIp, cidr) + " " + allowed +
                    ", nextTopCidr=" + IpUtil.convertLongToCidr(nextTopCidr) +
                    '}';
        } catch (UnknownHostException e) {
            throw new RuntimeException(e);
        }
    }
}
