package org.apache.commons.cli;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

// tests some deprecated classes
@SuppressWarnings("deprecation")
public class OptionsTest_testGetMatchingOpts {

    private static final String VERSION_OPTION = "version";
    private static final String VERBOSE_OPTION = "verbose";

    private void addLongOption(final Options options, final String longOption) {
        OptionBuilder.withLongOpt(longOption);
        options.addOption(OptionBuilder.create());
    }

    private void assertNoMatches(final Options options, final String optionPrefix) {
        assertTrue(options.getMatchingOptions(optionPrefix).isEmpty());
    }

    private void assertMatchCount(final Options options, final String optionPrefix, final int expectedMatches) {
        assertEquals(expectedMatches, options.getMatchingOptions(optionPrefix).size());
    }

    private void assertToStrings(final Option option) {
        assertNotNull(option.toString());
        assertNotNull(option.toDeprecatedString());
    }

    @Test
    void testGetMatchingOpts() {
        final Options options = new Options();
        addLongOption(options, VERSION_OPTION);
        addLongOption(options, VERBOSE_OPTION);

        assertNoMatches(options, "foo");
        assertMatchCount(options, VERSION_OPTION, 1);
        assertMatchCount(options, "ver", 2);

        assertToStrings(options.getOption(VERSION_OPTION));
        assertToStrings(options.getOption(VERBOSE_OPTION));
    }
}
