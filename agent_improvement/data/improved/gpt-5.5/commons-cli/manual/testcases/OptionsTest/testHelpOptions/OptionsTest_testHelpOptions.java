package org.apache.commons.cli;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.Collection;

import org.junit.jupiter.api.Test;

// Tests deprecated OptionBuilder usage.
@SuppressWarnings("deprecation")
public class OptionsTest_testHelpOptions {

    @Test
    void testHelpOptions() {
        OptionBuilder.withLongOpt("long-only1");
        final Option longOnlyOption1 = OptionBuilder.create();
        OptionBuilder.withLongOpt("long-only2");
        final Option longOnlyOption2 = OptionBuilder.create();
        final Option shortOnlyOption1 = OptionBuilder.create("1");
        final Option shortOnlyOption2 = OptionBuilder.create("2");
        OptionBuilder.withLongOpt("bothA");
        final Option shortAndLongOptionA = OptionBuilder.create("a");
        OptionBuilder.withLongOpt("bothB");
        final Option shortAndLongOptionB = OptionBuilder.create("b");

        final Options options = new Options();
        options.addOption(longOnlyOption1);
        options.addOption(longOnlyOption2);
        options.addOption(shortOnlyOption1);
        options.addOption(shortOnlyOption2);
        options.addOption(shortAndLongOptionA);
        options.addOption(shortAndLongOptionB);

        final Collection<Option> expectedHelpOptions = new ArrayList<>();
        expectedHelpOptions.add(longOnlyOption1);
        expectedHelpOptions.add(longOnlyOption2);
        expectedHelpOptions.add(shortOnlyOption1);
        expectedHelpOptions.add(shortOnlyOption2);
        expectedHelpOptions.add(shortAndLongOptionA);
        expectedHelpOptions.add(shortAndLongOptionB);

        final Collection<Option> actualHelpOptions = options.helpOptions();
        assertTrue(actualHelpOptions.containsAll(expectedHelpOptions), "Everything in all should be in help");
        assertTrue(expectedHelpOptions.containsAll(actualHelpOptions), "Everything in help should be in all");
    }
}
