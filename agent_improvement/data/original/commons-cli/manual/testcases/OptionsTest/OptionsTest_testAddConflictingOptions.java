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
public class OptionsTest_testAddConflictingOptions {

    private void assertToStrings(final Option option) {
        // Should never throw.
        // Should return a String, not null.
        assertNotNull(option.toString());
        assertNotNull(option.toDeprecatedString());
    }

    @Test
    void testAddConflictingOptions() {
        final Options options1 = new Options();
        final OptionGroup optionGroup1 = new OptionGroup();
        optionGroup1.addOption(Option.builder("a").get());
        optionGroup1.addOption(Option.builder("b").get());
        options1.addOptionGroup(optionGroup1);
        options1.addOption(Option.builder("x").get());
        options1.addOption(Option.builder("y").get());
        final Options options2 = new Options();
        final OptionGroup optionGroup2 = new OptionGroup();
        optionGroup2.addOption(Option.builder("x").type(Integer.class).get());
        optionGroup2.addOption(Option.builder("b").type(Integer.class).get());
        options2.addOptionGroup(optionGroup2);
        options2.addOption(Option.builder("c").get());
        assertThrows(IllegalArgumentException.class, () -> options1.addOptions(options2));
    }
}
