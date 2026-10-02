package org.apache.commons.cli;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import java.util.Properties;
import org.junit.jupiter.api.Test;

public class CommandLineTest_testGetOptionPropertiesWithOption {

    @Test
    void testGetOptionPropertiesWithOption() throws Exception {
        final String[] args = { "-Dparam1=value1", "-Dparam2=value2", "-Dparam3", "-Dparam4=value4", "-D", "--property", "foo=bar" };
        final Options options = new Options();
        final Option optionD = Option.builder("D").valueSeparator().numberOfArgs(2).optionalArg(true).get();
        final Option optionProperty = Option.builder().valueSeparator().numberOfArgs(2).longOpt("property").get();
        options.addOption(optionD);
        options.addOption(optionProperty);
        final Parser parser = new GnuParser();
        final CommandLine cl = parser.parse(options, args);

        final Properties props = cl.getOptionProperties(optionD);
        assertNotNull(props, "null properties");
        assertEquals(4, props.size(), "number of properties in " + props);
        assertEquals("value1", props.getProperty("param1"), "property 1");
        assertEquals("value2", props.getProperty("param2"), "property 2");
        assertEquals("true", props.getProperty("param3"), "property 3");
        assertEquals("value4", props.getProperty("param4"), "property 4");

        assertEquals("bar", cl.getOptionProperties(optionProperty).getProperty("foo"), "property with long format");
    }
}
