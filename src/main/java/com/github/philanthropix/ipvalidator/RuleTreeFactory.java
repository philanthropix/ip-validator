package com.github.philanthropix.ipvalidator;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.philanthropix.ipvalidator.json.ConfigNode;
import com.github.philanthropix.ipvalidator.json.TreeConfig;

import java.io.IOException;
import java.io.InputStream;
import java.net.UnknownHostException;
import java.util.TreeSet;

import static com.github.philanthropix.ipvalidator.IpUtil.convertCidrToLong;
import static com.github.philanthropix.ipvalidator.IpUtil.ipV4AddressToLong;

/**
 * User: philanthopix
 * Date: 17/08/2014
 * Time: 18:00
 */
public class RuleTreeFactory {

    private static ObjectMapper mapper = new ObjectMapper();

    public static RuleTree createRuleTree(InputStream config) throws IOException {

        TreeConfig treeConfig = mapper.readValue(config, TreeConfig.class);

        return parseNodes(treeConfig);
    }

    public static  RuleTree createRuleTree(TreeConfig config) throws UnknownHostException {
        return parseNodes(config);
    }

    private static RuleTree parseNodes(TreeConfig config) throws UnknownHostException {

        TreeSet<TreeEntry> treeEntries = new TreeSet<TreeEntry>();

        for (ConfigNode configNode : config.getEntries()) {
            TreeEntry treeEntry = new TreeEntry(
                    ipV4AddressToLong(configNode.getNetwork()),
                    convertCidrToLong(configNode.getCidr()),
                    configNode.isAllowed());

            treeEntries.add(treeEntry);
        }

        return new RuleTree(treeEntries);
    }
}
