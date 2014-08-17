ip-validator
============

A simple java library for Blacklisting / Whiltelisting IP addresses based upon a hierarchical JSON configuration


Building ip-validator
=====================

This project should build with JDK 6 and above, and Maven 3 

No special processes required, just standard a Maven build command:

'''
mvn clean package
'''

Dependencies
============

ip-validator uses [jackson](http://jackson.codehaus.org/) to parse the JSON configuration file


To use
======

This library is very simple to use.

# First create a JSON file with the CIDR addresses you want to whitelist or blacklist
# Create an instance of the RuleTree class using the RuleTreeFactory class like so:
'''java

InputStream is; //get an input stream for the JSON config file you created 

RuleTree ruleTree = RuleTreeFactory.createRuleTree(is);

''' 

# Validate IP addresses against the whitelist/blacklist
'''java
if (ruleTree.validateIp("10.1.1.33")) {
	//do some cool stuff
}
'''

JSON config file
================

The config file uses the followng format

'''json



'''
