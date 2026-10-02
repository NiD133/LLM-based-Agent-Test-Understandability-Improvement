package org.apache.commons.cli;

import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.Test;

// tests some deprecated classes
@SuppressWarnings("deprecation")
public class OptionsTest_testAddOptions2X {

    /**
     * Verifies that adding an Options instance to itself throws IllegalArgumentException
     * because every key it contains is already present (duplicate key conflict).
     */
    @Test
    void testAddOptions2X() {
        // Build an Options instance with a group containing "a"/"b" and standalone "X"/"y"
        final Options options = new Options();

        final OptionGroup optionGroup1 = new OptionGroup();
        optionGroup1.addOption(Option.builder("a").get());
        optionGroup1.addOption(Option.builder("b").get());
        options.addOptionGroup(optionGroup1);

        options.addOption(Option.builder("X").get());
        options.addOption(Option.builder("y").get());

        // Passing the same instance to addOptions() must fail: all keys are already registered
        assertThrows(IllegalArgumentException.class, () -> options.addOptions(options));
    }
}
