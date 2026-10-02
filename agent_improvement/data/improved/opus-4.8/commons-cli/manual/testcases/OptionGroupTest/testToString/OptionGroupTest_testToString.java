package org.apache.commons.cli;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

/**
 * Verifies {@link OptionGroup#toString()}.
 *
 * <p>An {@code OptionGroup}'s string form lists each of its options inside square
 * brackets. Each option is rendered as its short flag (e.g. {@code -f}) when one
 * exists, otherwise as its long flag (e.g. {@code --foo}), followed by the option's
 * description. Because the order in which the options are listed has historically
 * not been guaranteed, each assertion accepts either ordering.</p>
 */
// tests some deprecated classes
@SuppressWarnings("deprecation")
public class OptionGroupTest_testToString {

    @Test
    void testToString() {
        // A group whose options have only long flags (no short flag).
        final OptionGroup longFlagGroup = new OptionGroup();
        longFlagGroup.addOption(new Option(null, "foo", false, "Foo"));
        longFlagGroup.addOption(new Option(null, "bar", false, "Bar"));
        assertToStringMatchesEitherOrder(
                "[--foo Foo, --bar Bar]", "[--bar Bar, --foo Foo]", longFlagGroup);

        // A group whose options have short flags.
        final OptionGroup shortFlagGroup = new OptionGroup();
        shortFlagGroup.addOption(new Option("f", "foo", false, "Foo"));
        shortFlagGroup.addOption(new Option("b", "bar", false, "Bar"));
        assertToStringMatchesEitherOrder(
                "[-f Foo, -b Bar]", "[-b Bar, -f Foo]", shortFlagGroup);
    }

    /**
     * Asserts that the group's {@code toString()} equals one of the two accepted
     * orderings. If it already matches {@code accepted}, nothing more is checked;
     * otherwise it must equal {@code expected}.
     */
    private static void assertToStringMatchesEitherOrder(
            final String expected, final String accepted, final OptionGroup group) {
        if (!accepted.equals(group.toString())) {
            assertEquals(expected, group.toString());
        }
    }
}
