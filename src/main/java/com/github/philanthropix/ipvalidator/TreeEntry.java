package com.github.philanthropix.ipvalidator;

import java.net.UnknownHostException;

public class TreeEntry implements Comparable<TreeEntry> {

    private final long ipAddress;

    private final long cidr;

    private final boolean allowed;

    public TreeEntry(long ipAddress, long cidr, boolean allowed) {
        this.ipAddress = ipAddress;
        this.cidr = cidr;
        this.allowed = allowed;
    }

    public int compareTo(TreeEntry entry) {
        if (getCidr() < entry.getCidr()) return -1;
        if (getCidr() > entry.getCidr()) return 1;
        if (getIpAddress() < entry.getIpAddress()) return -1;
        if (getIpAddress() > entry.getIpAddress()) return 1;
        return 0;
    }

    public long getIpAddress() {
        return ipAddress;
    }

    public long getCidr() {
        return cidr;
    }

    public boolean isAllowed() {
        return allowed;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TreeEntry entry = (TreeEntry) o;

        if (cidr != entry.cidr) return false;
        if (ipAddress != entry.ipAddress) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = (int) (ipAddress ^ (ipAddress >>> 32));
        result = 31 * result + (int) (cidr ^ (cidr >>> 32));
        return result;
    }

    @Override
    public String toString() {
        try {
            return "TreeEntry{" + IpUtil.bitsToIpv4Cidr(ipAddress, cidr) + " " + allowed + '}';
        } catch (UnknownHostException e) {
            throw new RuntimeException(e);
        }
    }
}
