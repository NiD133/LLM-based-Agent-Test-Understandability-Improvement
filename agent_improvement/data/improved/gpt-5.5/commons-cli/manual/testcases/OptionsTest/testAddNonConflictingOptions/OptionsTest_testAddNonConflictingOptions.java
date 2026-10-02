package org.apache.commons.cli;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.Test;

public class OptionsTest_testAddNonConflictingOptions {

    @Test
    void testAddNonConflictingOptions() {
        final Options firstOptions = new Options();
        final OptionGroup firstGroup = new OptionGroup();
        firstGroup.addOption(Option.builder("a").get());
        firstGroup.addOption(Option.builder("b").get());
        firstOptions.addOptionGroup(firstGroup);
        firstOptions.addOption(Option.builder("x").get());
        firstOptions.addOption(Option.builder("y").get());

        final Options secondOptions = new Options();
        final OptionGroup secondGroup = new OptionGroup();
        secondGroup.addOption(Option.builder("c").type(Integer.class).get());
        secondGroup.addOption(Option.builder("d").type(Integer.class).get());
        secondOptions.addOptionGroup(secondGroup);

        firstOptions.addOption(Option.builder("e").get());
        firstOptions.addOption(Option.builder("f").get());

        final Options underTest = new Options();
        underTest.addOptions(firstOptions);
        underTest.addOptions(secondOptions);

        final List<OptionGroup> expectedGroups = Arrays.asList(firstGroup, secondGroup);
        assertTrue(expectedGroups.size() == underTest.getOptionGroups().size()
                && expectedGroups.containsAll(underTest.getOptionGroups()));

        final Set<Option> expectedOptions = new HashSet<>(firstOptions.getOptions());
        expectedOptions.addAll(secondOptions.getOptions());
        assertEquals(8, expectedOptions.size());
        assertTrue(expectedOptions.size() == underTest.getOptions().size()
                && expectedOptions.containsAll(underTest.getOptions()));
    }
}
