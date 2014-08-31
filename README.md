ip-validator
============

A simple java library for Blacklisting / Whiltelisting IP addresses based upon a hierarchical JSON configuration

Getting ip-validator
====================

The latest release of ip-validator is in maven central

Adding to your pom.xml

```xml
<dependency>
    <groupId>com.github.philanthropix</groupId>
    <artifactId>ip-validator</artifactId>
    <version>0.0.3</version>
</dependency>
```


Building ip-validator
=====================

This project should build with JDK 6 and above, and Maven 3 

No special processes required, just standard a Maven build command:

```
mvn clean package
```

Dependencies
============

ip-validator uses [jackson](http://jackson.codehaus.org/) to parse the JSON configuration file


To use
======

This library is very simple to use.

1. First create a JSON file with the CIDR addresses you want to whitelist or blacklist
2. Create an instance of the RuleTree class using the RuleTreeFactory class like so:
```java

InputStream is; //get an input stream for the JSON config file you created 

RuleTree ruleTree = RuleTreeFactory.createRuleTree(is);

``` 

3. Validate IP addresses against the whitelist/blacklist
```java
if (ruleTree.validateIp("10.1.1.33")) {
	//do some cool stuff
}
```

JSON config file
================

The config file uses the followng format

```json
{
 "entries" : [
   {
     "network" : "10.1.1.55",
     "cidr" : "24",
     "allowed" : true
   }
 ]
}
```
