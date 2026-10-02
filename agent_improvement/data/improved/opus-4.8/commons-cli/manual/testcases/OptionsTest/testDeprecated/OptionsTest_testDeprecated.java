package org.apache.commons.cli;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

/**
 * Verifies how {@link Options} exposes deprecation information through the two
 * string renderings of an {@link Option}:
 * <ul>
 *   <li>{@link Option#toString()} – a debug dump that always begins with
 *       {@code "[ Option <name>"}, regardless of deprecation.</li>
 *   <li>{@link Option#toDeprecatedString()} – a human-readable deprecation
 *       notice that is only meaningful for options marked as deprecated.</li>
 * </ul>
 */
@SuppressWarnings("deprecation")
public class OptionsTest_testDeprecated {

    /**
     * Asserts that both string renderings of the given option are safe to call:
     * they must never throw and must never return {@code null}.
     */
    private void assertStringRenderingsAreNonNull(final Option option) {
        assertNotNull(option.toString());
        assertNotNull(option.toDeprecatedString());
    }

    @Test
    void testDeprecated() {
        // An option that is NOT deprecated.
        final Option notDeprecated = Option.builder().option("a").get();

        // An option deprecated with no extra detail.
        final Option deprecatedPlain = Option.builder().option("b").deprecated().get();

        // An option deprecated with full attributes: marked for removal, a
        // "since" version and a replacement hint.
        final Option deprecatedWithDetails = Option.builder()
                .option("c")
                .deprecated(DeprecatedAttributes.builder()
                        .setForRemoval(true)
                        .setSince("2.0")
                        .setDescription("Use X.")
                        .get())
                .get();

        // A deprecated option that also carries a long name and arguments.
        final Option deprecatedWithLongOpt = Option.builder()
                .option("d")
                .deprecated()
                .longOpt("longD")
                .hasArgs()
                .get();

        final Options options = new Options();
        options.addOption(notDeprecated);
        options.addOption(deprecatedPlain);
        options.addOption(deprecatedWithDetails);
        options.addOption(deprecatedWithLongOpt);

        // toString() is the debug dump and always starts with "[ Option <name>".
        assertTrue(options.getOption("a").toString().startsWith("[ Option a"));
        assertTrue(options.getOption("b").toString().startsWith("[ Option b"));
        assertTrue(options.getOption("c").toString().startsWith("[ Option c"));

        // toDeprecatedString() describes the deprecation.
        // A non-deprecated option does not produce an "Option a" style notice.
        assertFalse(options.getOption("a").toDeprecatedString().startsWith("Option a"));
        // A plainly deprecated option reports only that it is deprecated.
        assertEquals("Option 'b': Deprecated", options.getOption("b").toDeprecatedString());
        // A fully-detailed deprecation reports removal, version and description.
        assertEquals("Option 'c': Deprecated for removal since 2.0: Use X.",
                options.getOption("c").toDeprecatedString());

        // Both renderings must be non-null for every option, deprecated or not.
        assertStringRenderingsAreNonNull(options.getOption("a"));
        assertStringRenderingsAreNonNull(options.getOption("b"));
        assertStringRenderingsAreNonNull(options.getOption("c"));
        assertStringRenderingsAreNonNull(options.getOption("d"));
    }
}
