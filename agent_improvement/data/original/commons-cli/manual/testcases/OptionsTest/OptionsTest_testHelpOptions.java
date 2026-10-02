package org.apache.commons.cli;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.junit.jupiter.api.Test;

// tests some deprecated classes
@SuppressWarnings("deprecation")
public class OptionsTest_testHelpOptions {

    private void assertToStrings(final Option option) {
        // Should never throw.
        // Should return a String, not null.
        assertNotNull(option.toString());
        assertNotNull(option.toDeprecatedString());
    }

    @Test
    void testHelpOptions() {
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
        final Options options = new Options();
        options.addOption(longOnly1);
        options.addOption(longOnly2);
        options.addOption(shortOnly1);
        options.addOption(shortOnly2);
        options.addOption(bothA);
        options.addOption(bothB);
        final Collection<Option> allOptions = new ArrayList<>();
        allOptions.add(longOnly1);
        allOptions.add(longOnly2);
        allOptions.add(shortOnly1);
        allOptions.add(shortOnly2);
        allOptions.add(bothA);
        allOptions.add(bothB);
        final Collection<Option> helpOptions = options.helpOptions();
        assertTrue(helpOptions.containsAll(allOptions), "Everything in all should be in help");
        assertTrue(allOptions.containsAll(helpOptions), "Everything in help should be in all");
    }
}
