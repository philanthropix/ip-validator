package com.github.philanthropix.ipvalidator;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.philanthropix.ipvalidator.json.ConfigNode;
import com.github.philanthropix.ipvalidator.json.TreeConfig;

/**
 * Created by yushah on 12/8/14.
 */
public class Test {

    public static void main(String[] args) {

        ObjectMapper mapper = new ObjectMapper();

        TreeConfig treeConfig = new TreeConfig();

        String[] network = {"0.0.0.0", "10.0.0.0", "10.253.0.0", "10.253.10.0", "10.127.0.0", "10.127.2.0", "10.127.3.0", "10.8.1.0", "252.18.0.0", "252.18.10.0", "11.192.253.0"};
        int[] cidr = {0, 8, 16, 24, 16, 24, 24, 24, 16, 24, 24};
        boolean[] allowed = {false, true, false, true, false, true, true, false, true, false, true};

        for (int i = 0; i < network.length; i++) {
            ConfigNode ar = new ConfigNode(network[i], cidr[i], allowed[i]);
            treeConfig.getEntries().add(ar);
        }

        String jsonString = null;
        try {
            jsonString = mapper.writeValueAsString(treeConfig);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            System.out.println("Unable to write JSON: " + e);
        }

        System.out.println(jsonString);
    }
}
