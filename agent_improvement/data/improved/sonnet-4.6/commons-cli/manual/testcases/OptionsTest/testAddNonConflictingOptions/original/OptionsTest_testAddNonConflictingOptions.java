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
public class OptionsTest_testAddNonConflictingOptions {

    private void assertToStrings(final Option option) {
        // Should never throw.
        // Should return a String, not null.
        assertNotNull(option.toString());
        assertNotNull(option.toDeprecatedString());
    }

    @Test
    void testAddNonConflictingOptions() {
        final Options options1 = new Options();
        final OptionGroup optionGroup1 = new OptionGroup();
        optionGroup1.addOption(Option.builder("a").get());
        optionGroup1.addOption(Option.builder("b").get());
        options1.addOptionGroup(optionGroup1);
        options1.addOption(Option.builder("x").get());
        options1.addOption(Option.builder("y").get());
        final Options options2 = new Options();
        final OptionGroup group2 = new OptionGroup();
        group2.addOption(Option.builder("c").type(Integer.class).get());
        group2.addOption(Option.builder("d").type(Integer.class).get());
        options2.addOptionGroup(group2);
        options1.addOption(Option.builder("e").get());
        options1.addOption(Option.builder("f").get());
        final Options underTest = new Options();
        underTest.addOptions(options1);
        underTest.addOptions(options2);
        final List<OptionGroup> expected = Arrays.asList(optionGroup1, group2);
        assertTrue(expected.size() == underTest.getOptionGroups().size() && expected.containsAll(underTest.getOptionGroups()));
        final Set<Option> expectOpt = new HashSet<>(options1.getOptions());
        expectOpt.addAll(options2.getOptions());
        assertEquals(8, expectOpt.size());
        assertTrue(expectOpt.size() == underTest.getOptions().size() && expectOpt.containsAll(underTest.getOptions()));
    }
}
