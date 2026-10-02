package org.apache.commons.cli;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.junit.jupiter.api.Test;

/**
 * Verifies that {@link Options#helpOptions()} exposes exactly the options that
 * were registered, regardless of whether each option has a short name, a long
 * name, or both.
 */
// tests some deprecated classes
@SuppressWarnings("deprecation")
public class OptionsTest_testHelpOptions {

    @Test
    void testHelpOptions() {
        // Two options that only have a long name.
        OptionBuilder.withLongOpt("long-only1");
        final Option longOnly1 = OptionBuilder.create();
        OptionBuilder.withLongOpt("long-only2");
        final Option longOnly2 = OptionBuilder.create();

        // Two options that only have a short name.
        final Option shortOnly1 = OptionBuilder.create("1");
        final Option shortOnly2 = OptionBuilder.create("2");

        // Two options that have both a short name and a long name.
        OptionBuilder.withLongOpt("bothA");
        final Option bothA = OptionBuilder.create("a");
        OptionBuilder.withLongOpt("bothB");
        final Option bothB = OptionBuilder.create("b");

        // Register every option with the Options container.
        final Options options = new Options();
        options.addOption(longOnly1);
        options.addOption(longOnly2);
        options.addOption(shortOnly1);
        options.addOption(shortOnly2);
        options.addOption(bothA);
        options.addOption(bothB);

        // The full set of options we expect helpOptions() to report back.
        final Collection<Option> expectedOptions =
                Arrays.asList(longOnly1, longOnly2, shortOnly1, shortOnly2, bothA, bothB);

        final Collection<Option> helpOptions = options.helpOptions();

        // helpOptions() must contain the same options as those registered, no more and no less.
        assertTrue(helpOptions.containsAll(expectedOptions), "Every registered option should be in help");
        assertTrue(expectedOptions.containsAll(helpOptions), "Every help option should be a registered option");
    }
}
