package org.apache.commons.cli;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.Properties;

import org.junit.jupiter.api.Test;

public class CommandLineTest_testGetOptionPropertiesWithOption {

    @Test
    void testGetOptionPropertiesWithOption() throws Exception {
        final String[] args = {
            "-Dparam1=value1",
            "-Dparam2=value2",
            "-Dparam3",
            "-Dparam4=value4",
            "-D",
            "--property",
            "foo=bar"
        };

        final Option defineProperty = Option.builder("D").valueSeparator().numberOfArgs(2).optionalArg(true).get();
        final Option longProperty = Option.builder().valueSeparator().numberOfArgs(2).longOpt("property").get();
        final Options options = new Options();
        options.addOption(defineProperty);
        options.addOption(longProperty);

        final Parser parser = new GnuParser();
        final CommandLine commandLine = parser.parse(options, args);

        final Properties definedProperties = commandLine.getOptionProperties(defineProperty);
        assertNotNull(definedProperties, "null properties");
        assertEquals(4, definedProperties.size(), "number of properties in " + definedProperties);
        assertEquals("value1", definedProperties.getProperty("param1"), "property 1");
        assertEquals("value2", definedProperties.getProperty("param2"), "property 2");
        assertEquals("true", definedProperties.getProperty("param3"), "property 3");
        assertEquals("value4", definedProperties.getProperty("param4"), "property 4");
        assertEquals("bar", commandLine.getOptionProperties(longProperty).getProperty("foo"), "property with long format");
    }
}
