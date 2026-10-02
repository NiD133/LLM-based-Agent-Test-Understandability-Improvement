package org.apache.commons.cli;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.Properties;

import org.junit.jupiter.api.Test;

public class CommandLineTest_testGetOptionPropertiesWithOption {

    @Test
    void testGetOptionPropertiesWithOption() throws Exception {
        // A "-D" property option (key=value pairs) plus a "--property" long-form equivalent.
        final Option optionD = Option.builder("D").valueSeparator().numberOfArgs(2).optionalArg(true).get();
        final Option optionProperty = Option.builder().valueSeparator().numberOfArgs(2).longOpt("property").get();
        final Options options = new Options();
        options.addOption(optionD);
        options.addOption(optionProperty);

        // param3 has no value, so it becomes a boolean flag ("true"); the final pair uses the long form.
        final String[] args = { "-Dparam1=value1", "-Dparam2=value2", "-Dparam3", "-Dparam4=value4", "-D", "--property", "foo=bar" };
        final CommandLine commandLine = new GnuParser().parse(options, args);

        // Properties collected for the "-D" option.
        final Properties dProperties = commandLine.getOptionProperties(optionD);
        assertNotNull(dProperties, "null properties");
        assertEquals(4, dProperties.size(), "number of properties in " + dProperties);
        assertEquals("value1", dProperties.getProperty("param1"), "property 1");
        assertEquals("value2", dProperties.getProperty("param2"), "property 2");
        assertEquals("true", dProperties.getProperty("param3"), "property 3");
        assertEquals("value4", dProperties.getProperty("param4"), "property 4");

        // Properties collected for the long-form "--property" option.
        assertEquals("bar", commandLine.getOptionProperties(optionProperty).getProperty("foo"), "property with long format");
    }
}
