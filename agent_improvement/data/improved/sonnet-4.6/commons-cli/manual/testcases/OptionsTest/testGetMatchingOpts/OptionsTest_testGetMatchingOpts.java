package org.apache.commons.cli;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

// tests some deprecated classes
@SuppressWarnings("deprecation")
public class OptionsTest_testGetMatchingOpts {

    /**
     * Asserts that both string representations of an option are non-null.
     * Calling these methods should never throw, and must always yield a String.
     */
    private void assertToStrings(final Option option) {
        assertNotNull(option.toString());
        assertNotNull(option.toDeprecatedString());
    }

    @Test
    void testGetMatchingOpts() {
        // Register two long options that share the common prefix "ver": "version" and "verbose".
        final Options options = new Options();

        OptionBuilder.withLongOpt("version");
        options.addOption(OptionBuilder.create());

        OptionBuilder.withLongOpt("verbose");
        options.addOption(OptionBuilder.create());

        // "foo" does not match any registered long option, so the result must be empty.
        assertTrue(options.getMatchingOptions("foo").isEmpty(),
                "No option starts with 'foo'; expected an empty match list");

        // "version" is an exact long-option name, so only that single option matches.
        assertEquals(1, options.getMatchingOptions("version").size(),
                "Exact match on 'version' should return exactly one option");

        // "ver" is a prefix of both "version" and "verbose", so both must be returned.
        assertEquals(2, options.getMatchingOptions("ver").size(),
                "Prefix 'ver' should match both 'version' and 'verbose'");

        // Both registered options must produce valid (non-null) string representations.
        assertToStrings(options.getOption("version"));
        assertToStrings(options.getOption("verbose"));
    }
}
