package org.apache.commons.cli;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;

// tests some deprecated classes
@SuppressWarnings("deprecation")
public class OptionsTest_testDuplicateLong {

    /**
     * Verifies that both string representations of an option are non-null
     * and do not throw exceptions when called.
     */
    private void assertStringRepresentationsAreNonNull(final Option option) {
        assertNotNull(option.toString());
        assertNotNull(option.toDeprecatedString());
    }

    @Test
    void testDuplicateLong() {
        final Options options = new Options();

        final String shortName = "a";
        final String longName = "--a";
        final String firstDescription = "toggle -a";
        final String secondDescription = "toggle -a*";

        // Adding two options with the same short and long name: the second should overwrite the first.
        options.addOption(shortName, longName, false, firstDescription);
        options.addOption(shortName, longName, false, secondDescription);

        final Option resolvedOption = options.getOption(shortName);

        // "last one in wins": the description from the second addOption call should be retained.
        assertEquals(secondDescription, resolvedOption.getDescription(), "last one in wins");

        // String representations of the overwritten option should be valid and non-null.
        assertStringRepresentationsAreNonNull(resolvedOption);
    }
}
