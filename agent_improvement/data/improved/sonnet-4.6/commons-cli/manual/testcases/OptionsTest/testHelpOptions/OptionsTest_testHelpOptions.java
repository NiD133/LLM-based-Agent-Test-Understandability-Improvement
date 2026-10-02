package org.apache.commons.cli;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.util.ArrayList;
import java.util.Collection;
import org.junit.jupiter.api.Test;

// tests some deprecated classes
@SuppressWarnings("deprecation")
public class OptionsTest_testHelpOptions {

    /**
     * Verifies that both toString() and toDeprecatedString() return non-null values
     * without throwing an exception.
     */
    private void assertToStrings(final Option option) {
        assertNotNull(option.toString());
        assertNotNull(option.toDeprecatedString());
    }

    /**
     * Verifies that helpOptions() returns every registered option regardless of
     * whether the option carries a short name only, a long name only, or both.
     * The check is bidirectional (mutual containsAll), which is equivalent to
     * asserting set equality without requiring a specific iteration order.
     */
    @Test
    void testHelpOptions() {
        // Arrange: build one option of each name-type combination
        OptionBuilder.withLongOpt("long-only1");
        final Option longOnly1 = OptionBuilder.create();

        OptionBuilder.withLongOpt("long-only2");
        final Option longOnly2 = OptionBuilder.create();

        final Option shortOnly1 = OptionBuilder.create("1");
        final Option shortOnly2 = OptionBuilder.create("2");

        OptionBuilder.withLongOpt("bothA");
        final Option bothA = OptionBuilder.create("a");

        OptionBuilder.withLongOpt("bothB");
        final Option bothB = OptionBuilder.create("b");

        // Arrange: register all six options
        final Options options = new Options();
        options.addOption(longOnly1);
        options.addOption(longOnly2);
        options.addOption(shortOnly1);
        options.addOption(shortOnly2);
        options.addOption(bothA);
        options.addOption(bothB);

        // Arrange: build the reference collection that helpOptions() must match
        final Collection<Option> allOptions = new ArrayList<>();
        allOptions.add(longOnly1);
        allOptions.add(longOnly2);
        allOptions.add(shortOnly1);
        allOptions.add(shortOnly2);
        allOptions.add(bothA);
        allOptions.add(bothB);

        // Act
        final Collection<Option> helpOptions = options.helpOptions();

        // Assert: helpOptions and allOptions contain exactly the same elements
        assertTrue(helpOptions.containsAll(allOptions), "Everything in all should be in help");
        assertTrue(allOptions.containsAll(helpOptions), "Everything in help should be in all");
    }
}
