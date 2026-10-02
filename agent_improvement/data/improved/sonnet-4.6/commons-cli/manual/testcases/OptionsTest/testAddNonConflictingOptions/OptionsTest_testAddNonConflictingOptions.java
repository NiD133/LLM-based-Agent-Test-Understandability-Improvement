package org.apache.commons.cli;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
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

    /**
     * Verifies that addOptions() correctly merges two independent Options instances
     * — including their individual options and option groups — without conflicts.
     */
    @Test
    void testAddNonConflictingOptions() {
        // Build options1: a mutual-exclusion group {a, b} plus standalone options x, y, e, f
        final Options options1 = new Options();
        final OptionGroup optionGroup1 = new OptionGroup();
        optionGroup1.addOption(Option.builder("a").get());
        optionGroup1.addOption(Option.builder("b").get());
        options1.addOptionGroup(optionGroup1);
        options1.addOption(Option.builder("x").get());
        options1.addOption(Option.builder("y").get());
        options1.addOption(Option.builder("e").get());
        options1.addOption(Option.builder("f").get());

        // Build options2: a mutual-exclusion group {c, d} whose members carry an Integer type
        final Options options2 = new Options();
        final OptionGroup group2 = new OptionGroup();
        group2.addOption(Option.builder("c").type(Integer.class).get());
        group2.addOption(Option.builder("d").type(Integer.class).get());
        options2.addOptionGroup(group2);

        // Merge both sets into a fresh Options instance
        final Options underTest = new Options();
        underTest.addOptions(options1);
        underTest.addOptions(options2);

        // Both option groups must be present in the merged result
        final List<OptionGroup> expectedGroups = Arrays.asList(optionGroup1, group2);
        final Collection<OptionGroup> actualGroups = underTest.getOptionGroups();
        assertEquals(expectedGroups.size(), actualGroups.size(), "merged Options should contain exactly 2 option groups");
        assertTrue(actualGroups.containsAll(expectedGroups), "merged Options should contain optionGroup1 and group2");

        // All 8 individual options (a, b, x, y, e, f, c, d) must be present
        final Set<Option> expectedOptions = new HashSet<>(options1.getOptions());
        expectedOptions.addAll(options2.getOptions());
        assertEquals(8, expectedOptions.size(), "combined option set should have 8 distinct options");

        final Collection<Option> actualOptions = underTest.getOptions();
        assertEquals(expectedOptions.size(), actualOptions.size(), "merged Options should contain all 8 options");
        assertTrue(actualOptions.containsAll(expectedOptions), "merged Options should contain every option from options1 and options2");
    }
}
