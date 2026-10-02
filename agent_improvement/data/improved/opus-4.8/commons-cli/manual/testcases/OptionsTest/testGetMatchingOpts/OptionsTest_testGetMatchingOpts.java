package org.apache.commons.cli;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

/**
 * Verifies {@link Options#getMatchingOptions(String)}, which performs prefix
 * matching against the registered long option names.
 *
 * <p>The test registers two long options that share the prefix "ver"
 * ("version" and "verbose") and then checks how many of them match for
 * various query strings.</p>
 */
// OptionBuilder is deprecated but is the API used to build the options under test.
@SuppressWarnings("deprecation")
public class OptionsTest_testGetMatchingOpts {

    /**
     * Asserts that the option's string representations are produced without
     * error. Neither {@link Option#toString()} nor
     * {@link Option#toDeprecatedString()} should ever return {@code null}.
     */
    private void assertToStrings(final Option option) {
        assertNotNull(option.toString());
        assertNotNull(option.toDeprecatedString());
    }

    @Test
    void testGetMatchingOpts() {
        // Register two long options that both start with "ver".
        final Options options = new Options();
        OptionBuilder.withLongOpt("version");
        options.addOption(OptionBuilder.create());
        OptionBuilder.withLongOpt("verbose");
        options.addOption(OptionBuilder.create());

        // An unknown prefix matches nothing.
        assertTrue(options.getMatchingOptions("foo").isEmpty());

        // An exact long-option name matches only that one option.
        assertEquals(1, options.getMatchingOptions("version").size());

        // The shared prefix "ver" matches both "version" and "verbose".
        assertEquals(2, options.getMatchingOptions("ver").size());

        // Both registered options expose non-null string representations.
        assertToStrings(options.getOption("version"));
        assertToStrings(options.getOption("verbose"));
    }
}
