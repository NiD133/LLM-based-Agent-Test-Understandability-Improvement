package org.apache.commons.cli;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class OptionsTest_testToString {

    @Test
    void testToString() {
        final Options options = new Options();
        options.addOption("f", "foo", true, "Foo");
        options.addOption("b", "bar", false, "Bar");

        final String result = options.toString();

        assertNotNull(result, "null string returned");
        assertTrue(result.toLowerCase().contains("foo"), "foo option missing");
        assertTrue(result.toLowerCase().contains("bar"), "bar option missing");
    }
}
