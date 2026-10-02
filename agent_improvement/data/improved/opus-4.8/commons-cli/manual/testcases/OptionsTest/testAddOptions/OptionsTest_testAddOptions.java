package org.apache.commons.cli;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

/**
 * Tests {@link Options#addOptions(Options)}, which copies all options and option
 * groups from a source {@link Options} into a target {@link Options}.
 */
// tests some deprecated classes
@SuppressWarnings("deprecation")
public class OptionsTest_testAddOptions {

    @Test
    void testAddOptions() {
        // Build a source Options containing one option group ("a"/"b")
        // plus two standalone options ("X" and "y").
        final Options source = new Options();

        final OptionGroup optionGroup = new OptionGroup();
        optionGroup.addOption(Option.builder("a").get());
        optionGroup.addOption(Option.builder("b").get());
        source.addOptionGroup(optionGroup);

        source.addOption(Option.builder("X").get());
        source.addOption(Option.builder("y").get());

        // Copy everything from the source into an initially empty target.
        final Options target = new Options();
        target.addOptions(source);

        // The target must now hold the same option groups and options as the source.
        assertEquals(source.getOptionGroups(), target.getOptionGroups());
        assertArrayEquals(source.getOptions().toArray(), target.getOptions().toArray());
    }
}
