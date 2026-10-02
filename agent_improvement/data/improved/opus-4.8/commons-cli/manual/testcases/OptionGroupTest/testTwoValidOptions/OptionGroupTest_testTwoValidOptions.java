package org.apache.commons.cli;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Verifies that when two non-conflicting options are supplied on the command
 * line, both are parsed successfully while the unused options stay unset.
 *
 * <p>The options under test are arranged into three mutually exclusive groups
 * plus one standalone option:</p>
 * <ul>
 *   <li>Group 1: {@code -f/--file} vs. {@code -d/--directory}</li>
 *   <li>Group 2: {@code -s/--section} vs. {@code -c/--chapter}</li>
 *   <li>Group 3: {@code --import} vs. {@code --export}</li>
 *   <li>Standalone: {@code -r/--revision}</li>
 * </ul>
 *
 * <p>Because {@code -r} is standalone and {@code -f} belongs to a different
 * group than any other supplied option, both can be selected at the same time
 * without triggering a mutual-exclusion error.</p>
 */
// tests some deprecated classes
@SuppressWarnings("deprecation")
public class OptionGroupTest_testTwoValidOptions {

    /** Whether an option takes an argument value; these flags take none. */
    private static final boolean NO_ARGUMENT = false;

    private Options options;

    private final Parser parser = new PosixParser();

    /**
     * Builds an {@link Options} set containing three mutually exclusive groups
     * and a single standalone option.
     */
    @BeforeEach
    public void setUp() {
        options = new Options();
        options.addOptionGroup(mutuallyExclusiveGroup(
                new Option("f", "file", NO_ARGUMENT, "file to process"),
                new Option("d", "directory", NO_ARGUMENT, "directory to process")));
        options.addOptionGroup(mutuallyExclusiveGroup(
                new Option("s", "section", NO_ARGUMENT, "section to process"),
                new Option("c", "chapter", NO_ARGUMENT, "chapter to process")));
        options.addOptionGroup(mutuallyExclusiveGroup(
                new Option(null, "import", NO_ARGUMENT, "section to process"),
                new Option(null, "export", NO_ARGUMENT, "chapter to process")));
        options.addOption("r", "revision", NO_ARGUMENT, "revision number");
    }

    /**
     * Bundles two options into a single mutually exclusive group.
     */
    private static OptionGroup mutuallyExclusiveGroup(final Option first, final Option second) {
        return new OptionGroup().addOption(first).addOption(second);
    }

    @Test
    void testTwoValidOptions() throws Exception {
        // Supply the standalone -r together with -f from the first group.
        final String[] args = { "-r", "-f" };

        final CommandLine cl = parser.parse(options, args);

        assertTrue(cl.hasOption("r"), "Confirm -r is set");
        assertTrue(cl.hasOption("f"), "Confirm -f is set");
        assertFalse(cl.hasOption("d"), "Confirm -d is NOT set");
        assertFalse(cl.hasOption("s"), "Confirm -s is NOT set");
        assertFalse(cl.hasOption("c"), "Confirm -c is NOT set");
        assertTrue(cl.getArgList().isEmpty(), "Confirm no extra args");
    }
}
