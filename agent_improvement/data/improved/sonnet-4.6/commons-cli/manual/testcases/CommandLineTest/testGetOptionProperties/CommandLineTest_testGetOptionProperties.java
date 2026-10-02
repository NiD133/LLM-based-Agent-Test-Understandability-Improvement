package org.apache.commons.cli;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import java.util.Properties;
import org.junit.jupiter.api.Test;

public class CommandLineTest_testGetOptionProperties {

    @Test
    void testGetOptionProperties() throws Exception {
        // Command line with multiple -D key=value pairs (and a bare -D) plus a long --property option
        final String[] args = { "-Dparam1=value1", "-Dparam2=value2", "-Dparam3", "-Dparam4=value4", "-D", "--property", "foo=bar" };

        final Options options = new Options();
        options.addOption(Option.builder("D").valueSeparator().optionalArg(true).numberOfArgs(2).get());
        options.addOption(Option.builder().valueSeparator().numberOfArgs(2).longOpt("property").get());

        final Parser parser = new GnuParser();
        final CommandLine cl = parser.parse(options, args);

        // Verify all -D key=value pairs are extracted; a bare key (param3) maps to "true"
        final Properties props = cl.getOptionProperties("D");
        assertNotNull(props, "null properties");
        assertEquals(4, props.size(), "number of properties in " + props);
        assertEquals("value1", props.getProperty("param1"), "property 1");
        assertEquals("value2", props.getProperty("param2"), "property 2");
        assertEquals("true",   props.getProperty("param3"), "property 3");
        assertEquals("value4", props.getProperty("param4"), "property 4");

        // Verify the long-form --property option also produces correct key-value mapping
        assertEquals("bar", cl.getOptionProperties("property").getProperty("foo"), "property with long format");
    }
}
