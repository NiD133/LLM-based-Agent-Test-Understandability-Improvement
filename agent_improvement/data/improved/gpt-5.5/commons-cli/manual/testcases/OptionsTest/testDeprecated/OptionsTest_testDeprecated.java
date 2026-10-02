package org.apache.commons.cli;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

// Tests deprecated option display strings.
@SuppressWarnings("deprecation")
public class OptionsTest_testDeprecated {

    private static final String ACTIVE_OPTION = "a";
    private static final String SIMPLE_DEPRECATED_OPTION = "b";
    private static final String DETAILED_DEPRECATED_OPTION = "c";
    private static final String LONG_DEPRECATED_OPTION = "d";

    private void assertToStrings(final Option option) {
        // Should never throw and should return non-null strings.
        assertNotNull(option.toString());
        assertNotNull(option.toDeprecatedString());
    }

    private Options createOptionsWithDeprecatedExamples() {
        final Options options = new Options();
        options.addOption(Option.builder().option(ACTIVE_OPTION).get());
        options.addOption(Option.builder().option(SIMPLE_DEPRECATED_OPTION).deprecated().get());
        options.addOption(Option.builder().option(DETAILED_DEPRECATED_OPTION)
                .deprecated(DeprecatedAttributes.builder().setForRemoval(true).setSince("2.0").setDescription("Use X.").get())
                .get());
        options.addOption(Option.builder().option(LONG_DEPRECATED_OPTION).deprecated().longOpt("longD").hasArgs().get());
        return options;
    }

    private void assertStandardToStringPrefixes(final Options options) {
        assertTrue(options.getOption(ACTIVE_OPTION).toString().startsWith("[ Option a"));
        assertTrue(options.getOption(SIMPLE_DEPRECATED_OPTION).toString().startsWith("[ Option b"));
        assertTrue(options.getOption(DETAILED_DEPRECATED_OPTION).toString().startsWith("[ Option c"));
    }

    private void assertDeprecatedDescriptions(final Options options) {
        assertFalse(options.getOption(ACTIVE_OPTION).toDeprecatedString().startsWith("Option a"));
        assertEquals("Option 'b': Deprecated", options.getOption(SIMPLE_DEPRECATED_OPTION).toDeprecatedString());
        assertEquals("Option 'c': Deprecated for removal since 2.0: Use X.", options.getOption(DETAILED_DEPRECATED_OPTION).toDeprecatedString());
    }

    private void assertAllOptionsHaveDisplayStrings(final Options options) {
        assertToStrings(options.getOption(ACTIVE_OPTION));
        assertToStrings(options.getOption(SIMPLE_DEPRECATED_OPTION));
        assertToStrings(options.getOption(DETAILED_DEPRECATED_OPTION));
        assertToStrings(options.getOption(LONG_DEPRECATED_OPTION));
    }

    @Test
    void testDeprecated() {
        final Options options = createOptionsWithDeprecatedExamples();

        assertStandardToStringPrefixes(options);
        assertDeprecatedDescriptions(options);
        assertAllOptionsHaveDisplayStrings(options);
    }
}
