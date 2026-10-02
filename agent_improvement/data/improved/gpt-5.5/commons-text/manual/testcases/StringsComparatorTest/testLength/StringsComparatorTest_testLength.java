package org.apache.commons.text.diff;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;

public class StringsComparatorTest_testLength {

    private static final List<ComparisonCase> COMPARISON_CASES = Arrays.asList(
            new ComparisonCase("bottle", "noodle", 6),
            new ComparisonCase("nematode knowledge", "empty bottle", 16),
            new ComparisonCase("", "", 0),
            new ComparisonCase("aa", "C", 3),
            new ComparisonCase("prefixed string", "prefix", 9),
            new ComparisonCase("ABCABBA", "CBABAC", 5),
            new ComparisonCase("glop glop", "pas glop pas glop", 8),
            new ComparisonCase("coq", "ane", 6),
            new ComparisonCase("spider-man", "klingon", 13));

    @Test
    void testLength() {
        for (final ComparisonCase comparisonCase : COMPARISON_CASES) {
            assertModificationCount(comparisonCase);
        }
    }

    private void assertModificationCount(final ComparisonCase comparisonCase) {
        final StringsComparator comparator = new StringsComparator(comparisonCase.before, comparisonCase.after);
        assertEquals(comparisonCase.expectedModifications, comparator.getScript().getModifications());
    }

    private static final class ComparisonCase {

        private final String before;
        private final String after;
        private final int expectedModifications;

        private ComparisonCase(final String before, final String after, final int expectedModifications) {
            this.before = before;
            this.after = after;
            this.expectedModifications = expectedModifications;
        }
    }
}
