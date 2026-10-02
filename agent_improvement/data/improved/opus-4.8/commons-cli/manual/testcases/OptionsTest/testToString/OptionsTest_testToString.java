package org.apache.commons.cli;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

/**
 * Tests that {@link Options#toString()} produces a non-null, human-readable
 * dump that mentions every registered option.
 */
public class OptionsTest_testToString {

    @Test
    void testToString() {
        // Register two options: one that takes an argument ("foo") and one that does not ("bar").
        final Options options = new Options();
        options.addOption("f", "foo", true, "Foo");
        options.addOption("b", "bar", false, "Bar");

        final String rendered = options.toString();

        assertNotNull(rendered, "null string returned");
        assertTrue(rendered.toLowerCase().contains("foo"), "foo option missing");
        assertTrue(rendered.toLowerCase().contains("bar"), "bar option missing");
    }
}
