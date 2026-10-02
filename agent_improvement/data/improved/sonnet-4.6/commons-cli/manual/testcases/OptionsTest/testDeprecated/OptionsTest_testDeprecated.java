package org.apache.commons.cli;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

// Suppressed because Option.toDeprecatedString() is itself a deprecated API
@SuppressWarnings("deprecation")
public class OptionsTest_testDeprecated {

    /**
     * Verifies that both toString() and toDeprecatedString() return non-null strings.
     * Neither method should throw under any deprecation configuration.
     */
    private void assertToStrings(final Option option) {
        assertNotNull(option.toString());
        assertNotNull(option.toDeprecatedString());
    }

    @Test
    void testDeprecated() {
        // Build four options covering the main deprecation scenarios:
        //   a) active (not deprecated)
        //   b) deprecated with no extra attributes
        //   c) deprecated with forRemoval, since-version, and description
        //   d) deprecated with a long name and multiple arguments
        final Options options = new Options();

        final Option activeOption = Option.builder().option("a").get();
        final Option simplyDeprecated = Option.builder().option("b").deprecated().get();
        final Option deprecatedForRemoval = Option.builder().option("c")
                .deprecated(DeprecatedAttributes.builder()
                        .setForRemoval(true)
                        .setSince("2.0")
                        .setDescription("Use X.")
                        .get())
                .get();
        final Option deprecatedWithLongOpt = Option.builder().option("d").deprecated().longOpt("longD").hasArgs().get();

        options.addOption(activeOption);
        options.addOption(simplyDeprecated);
        options.addOption(deprecatedForRemoval);
        options.addOption(deprecatedWithLongOpt);

        // toString() always starts with "[ Option <key>" regardless of deprecation status
        assertTrue(options.getOption("a").toString().startsWith("[ Option a"));
        assertTrue(options.getOption("b").toString().startsWith("[ Option b"));
        assertTrue(options.getOption("c").toString().startsWith("[ Option c"));

        // toDeprecatedString() is empty-ish for active options: it must NOT start with "Option a"
        assertFalse(options.getOption("a").toDeprecatedString().startsWith("Option a"));

        // Simply deprecated option produces the minimal deprecation notice
        assertEquals("Option 'b': Deprecated",
                options.getOption("b").toDeprecatedString());

        // Deprecated-for-removal option includes version and replacement hint
        assertEquals("Option 'c': Deprecated for removal since 2.0: Use X.",
                options.getOption("c").toDeprecatedString());

        // Both methods must not throw or return null for all options
        assertToStrings(options.getOption("a"));
        assertToStrings(options.getOption("b"));
        assertToStrings(options.getOption("c"));
        assertToStrings(options.getOption("d"));
    }
}
