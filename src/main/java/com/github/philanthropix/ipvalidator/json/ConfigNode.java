package com.github.philanthropix.ipvalidator.json;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by yushah on 12/8/14.
 */
public class ConfigNode {

    private String network;
    private int cidr;
    private boolean allowed;

    @JsonCreator
    public ConfigNode(@JsonProperty("network") String network,
                      @JsonProperty("cidr") int cidr,
                      @JsonProperty("allowed") boolean allowed) {
        this.network = network;
        this.cidr = cidr;
        this.allowed = allowed;
    }

    public String getNetwork() {
        return network;
    }

    public void setNetwork(String network) {
        this.network = network;
    }

    public int getCidr() {
        return cidr;
    }

    public void setCidr(int cidr) {
        this.cidr = cidr;
    }

    public boolean isAllowed() {
        return allowed;
    }
}
