package org.apache.commons.cli;

import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.Test;

// tests some deprecated classes
@SuppressWarnings("deprecation")
public class OptionsTest_testAddConflictingOptions {

    @Test
    void testAddConflictingOptions() {
        // options1 contains standalone "x", "y" and a group with "a", "b"
        final Options options1 = new Options();
        final OptionGroup optionGroup1 = new OptionGroup();
        optionGroup1.addOption(Option.builder("a").get());
        optionGroup1.addOption(Option.builder("b").get());
        options1.addOptionGroup(optionGroup1);
        options1.addOption(Option.builder("x").get());
        options1.addOption(Option.builder("y").get());

        // options2 shares keys "x" and "b" with options1, making addOptions() a conflict
        final Options options2 = new Options();
        final OptionGroup optionGroup2 = new OptionGroup();
        optionGroup2.addOption(Option.builder("x").type(Integer.class).get());
        optionGroup2.addOption(Option.builder("b").type(Integer.class).get());
        options2.addOptionGroup(optionGroup2);
        options2.addOption(Option.builder("c").get());

        // merging options2 into options1 must fail because "x" and "b" already exist
        assertThrows(IllegalArgumentException.class, () -> options1.addOptions(options2));
    }
}
