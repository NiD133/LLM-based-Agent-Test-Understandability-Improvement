package org.apache.commons.cli;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.Properties;

import org.junit.jupiter.api.Test;

/**
 * Verifies {@link CommandLine#getOptionProperties(String)}, which collects an option's
 * arguments into a {@link Properties} map. Arguments are read in key/value pairs; a lone
 * trailing key (with no following value) is mapped to {@code "true"}.
 */
public class CommandLineTest_testGetOptionProperties {

    @Test
    void testGetOptionProperties() throws Exception {
        // "-D" takes up to two args (key[=value]); "--property" takes exactly two (key value).
        final String[] args = {
            "-Dparam1=value1", // key + value
            "-Dparam2=value2", // key + value
            "-Dparam3",        // key only -> mapped to "true"
            "-Dparam4=value4", // key + value
            "-D",              // bare option, contributes nothing
            "--property", "foo=bar" // long-form option with key=value
        };

        final Options options = new Options();
        options.addOption(Option.builder("D").valueSeparator().optionalArg(true).numberOfArgs(2).get());
        options.addOption(Option.builder().valueSeparator().numberOfArgs(2).longOpt("property").get());

        final Parser parser = new GnuParser();
        final CommandLine cl = parser.parse(options, args);

        // Properties gathered for the short option "-D".
        final Properties props = cl.getOptionProperties("D");
        assertNotNull(props, "null properties");
        assertEquals(4, props.size(), "number of properties in " + props);
        assertEquals("value1", props.getProperty("param1"), "property 1");
        assertEquals("value2", props.getProperty("param2"), "property 2");
        assertEquals("true", props.getProperty("param3"), "property 3"); // key without value -> "true"
        assertEquals("value4", props.getProperty("param4"), "property 4");

        // Properties gathered for the long option "--property".
        assertEquals("bar", cl.getOptionProperties("property").getProperty("foo"), "property with long format");
    }
}
