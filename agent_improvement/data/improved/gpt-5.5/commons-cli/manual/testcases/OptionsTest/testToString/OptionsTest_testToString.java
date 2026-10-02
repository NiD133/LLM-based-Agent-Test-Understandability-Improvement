package org.apache.commons.cli;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class OptionsTest_testToString {

    @Test
    void testToString() {
        final Options options = new Options();
        options.addOption("f", "foo", true, "Foo");
        options.addOption("b", "bar", false, "Bar");

        final String optionsDescription = options.toString();

        assertNotNull(optionsDescription, "null string returned");
        assertTrue(optionsDescription.toLowerCase().contains("foo"), "foo option missing");
        assertTrue(optionsDescription.toLowerCase().contains("bar"), "bar option missing");
    }
}
