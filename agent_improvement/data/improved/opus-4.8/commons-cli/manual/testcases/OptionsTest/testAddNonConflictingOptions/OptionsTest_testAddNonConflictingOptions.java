package org.apache.commons.cli;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.Test;

/**
 * Tests {@link Options#addOptions(Options)} when the two {@link Options} instances being merged share no
 * option keys, i.e. their options do not conflict.
 */
// tests some deprecated classes
@SuppressWarnings("deprecation")
public class OptionsTest_testAddNonConflictingOptions {

    @Test
    void testAddNonConflictingOptions() {
        // Arrange: first source holds one option group (a, b) plus standalone options x, y, e, f.
        final OptionGroup groupAB = new OptionGroup();
        groupAB.addOption(Option.builder("a").get());
        groupAB.addOption(Option.builder("b").get());

        final Options firstSource = new Options();
        firstSource.addOptionGroup(groupAB);
        firstSource.addOption(Option.builder("x").get());
        firstSource.addOption(Option.builder("y").get());
        firstSource.addOption(Option.builder("e").get());
        firstSource.addOption(Option.builder("f").get());

        // Arrange: second source holds a single option group (c, d), with no keys overlapping the first source.
        final OptionGroup groupCD = new OptionGroup();
        groupCD.addOption(Option.builder("c").type(Integer.class).get());
        groupCD.addOption(Option.builder("d").type(Integer.class).get());

        final Options secondSource = new Options();
        secondSource.addOptionGroup(groupCD);

        // Act: merge both sources into a fresh Options instance.
        final Options merged = new Options();
        merged.addOptions(firstSource);
        merged.addOptions(secondSource);

        // Assert: the merged instance contains exactly the two original option groups.
        final List<OptionGroup> expectedGroups = Arrays.asList(groupAB, groupCD);
        assertEquals(expectedGroups.size(), merged.getOptionGroups().size());
        assertTrue(merged.getOptionGroups().containsAll(expectedGroups));

        // Assert: the merged instance contains exactly the union of both sources' options (8 distinct options).
        final Set<Option> expectedOptions = new HashSet<>(firstSource.getOptions());
        expectedOptions.addAll(secondSource.getOptions());
        assertEquals(8, expectedOptions.size());
        assertEquals(expectedOptions.size(), merged.getOptions().size());
        assertTrue(expectedOptions.containsAll(merged.getOptions()));
    }
}
