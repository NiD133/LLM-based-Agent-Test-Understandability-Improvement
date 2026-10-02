package org.apache.commons.text.diff;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;

/**
 * Verifies that {@link StringsComparator#getScript()} reports the expected number of
 * edit modifications (inserts plus deletes) needed to turn the {@code left} string into
 * the {@code right} string.
 */
public class StringsComparatorTest_testLength {

    /**
     * A single comparison scenario: the two strings to diff and the number of
     * modifications the comparator is expected to report for them.
     */
    private static final class ModificationCase {

        private final String left;
        private final String right;
        private final int expectedModifications;

        ModificationCase(final String left, final String right, final int expectedModifications) {
            this.left = left;
            this.right = right;
            this.expectedModifications = expectedModifications;
        }
    }

    private static final List<ModificationCase> MODIFICATION_CASES = Arrays.asList(
            new ModificationCase("bottle", "noodle", 6),
            new ModificationCase("nematode knowledge", "empty bottle", 16),
            new ModificationCase("", "", 0),
            new ModificationCase("aa", "C", 3),
            new ModificationCase("prefixed string", "prefix", 9),
            new ModificationCase("ABCABBA", "CBABAC", 5),
            new ModificationCase("glop glop", "pas glop pas glop", 8),
            new ModificationCase("coq", "ane", 6),
            new ModificationCase("spider-man", "klingon", 13));

    @Test
    void testLength() {
        for (final ModificationCase testCase : MODIFICATION_CASES) {
            final StringsComparator comparator = new StringsComparator(testCase.left, testCase.right);

            assertEquals(testCase.expectedModifications, comparator.getScript().getModifications());
        }
    }
}
