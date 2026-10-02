package org.apache.commons.cli;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

// tests some deprecated classes
@SuppressWarnings("deprecation")
public class OptionsTest_testAddOptions {

    @Test
    void testAddOptions() {
        // Build a source Options containing a group of mutually-exclusive options
        // plus two standalone options that will all be copied via addOptions().
        final Options sourceOptions = new Options();

        final OptionGroup exclusiveGroup = new OptionGroup();
        exclusiveGroup.addOption(Option.builder("a").get());
        exclusiveGroup.addOption(Option.builder("b").get());
        sourceOptions.addOptionGroup(exclusiveGroup);

        sourceOptions.addOption(Option.builder("X").get());
        sourceOptions.addOption(Option.builder("y").get());

        // Copy all options into a fresh Options instance and verify the result is identical.
        final Options destinationOptions = new Options();
        destinationOptions.addOptions(sourceOptions);

        assertEquals(sourceOptions.getOptionGroups(), destinationOptions.getOptionGroups());
        assertArrayEquals(sourceOptions.getOptions().toArray(), destinationOptions.getOptions().toArray());
    }
}
