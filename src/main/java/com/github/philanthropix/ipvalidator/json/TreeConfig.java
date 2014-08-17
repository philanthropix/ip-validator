package com.github.philanthropix.ipvalidator.json;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yushah on 12/8/14.
 */
public class TreeConfig {

    private List<ConfigNode> entries = new ArrayList<ConfigNode>();

    public List<ConfigNode> getEntries() {
        return entries;
    }

    public void setEntries(List<ConfigNode> entries) {
        this.entries = entries;
    }
}
