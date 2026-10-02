package org.apache.commons.text;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

/**
 * Tests {@link StringSubstitutor#replace(String, int, int)} when no replacement
 * can happen.
 *
 * <p>A {@link StringSubstitutor} created with the no-argument constructor has no
 * variable source, so any {@code ${...}} variables in the input are left
 * untouched. This test verifies that replacing only a sub-region of the template
 * returns exactly that sub-region, with its variable markers preserved.</p>
 */
public class StringSubstitutorTest_testReplacePartialString_noReplace {

    /** Template containing two variables that have no values defined. */
    private static final String TEMPLATE = "The ${animal} jumps over the ${target}.";

    /** Start of the sub-region to process: the first character after "The ". */
    private static final int REGION_OFFSET = 4;

    /** Length of the sub-region to process: covers "${animal} jumps". */
    private static final int REGION_LENGTH = 15;

    /** The sub-region, returned unchanged because no variable source exists. */
    private static final String EXPECTED_UNCHANGED_REGION = "${animal} jumps";

    @Test
    void testReplacePartialString_noReplace() {
        final StringSubstitutor substitutorWithoutValues = new StringSubstitutor();

        final String result = substitutorWithoutValues.replace(TEMPLATE, REGION_OFFSET, REGION_LENGTH);

        assertEquals(EXPECTED_UNCHANGED_REGION, result);
    }
}
