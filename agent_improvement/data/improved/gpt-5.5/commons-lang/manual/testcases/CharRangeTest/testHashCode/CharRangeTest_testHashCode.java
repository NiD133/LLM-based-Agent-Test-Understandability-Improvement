package org.apache.commons.lang3;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import org.junit.jupiter.api.Test;

public class CharRangeTest_testHashCode extends AbstractLangTest {

    private static final char SINGLE_CHARACTER = 'a';
    private static final char FIRST_RANGE_START = 'a';
    private static final char FIRST_RANGE_END = 'e';
    private static final char SECOND_RANGE_START = 'b';
    private static final char SECOND_RANGE_END = 'f';

    @Test
    void testHashCode() {
        final CharRange singleCharacterRange = CharRange.is(SINGLE_CHARACTER);
        final CharRange firstMultiCharacterRange = CharRange.isIn(FIRST_RANGE_START, FIRST_RANGE_END);
        final CharRange secondMultiCharacterRange = CharRange.isIn(SECOND_RANGE_START, SECOND_RANGE_END);

        assertHashCodeIsStable(singleCharacterRange, CharRange.is(SINGLE_CHARACTER));
        assertHashCodeIsStable(firstMultiCharacterRange, CharRange.isIn(FIRST_RANGE_START, FIRST_RANGE_END));
        assertHashCodeIsStable(secondMultiCharacterRange, CharRange.isIn(SECOND_RANGE_START, SECOND_RANGE_END));

        assertHashCodesDiffer(singleCharacterRange, firstMultiCharacterRange);
        assertHashCodesDiffer(singleCharacterRange, secondMultiCharacterRange);
        assertHashCodesDiffer(firstMultiCharacterRange, singleCharacterRange);
        assertHashCodesDiffer(firstMultiCharacterRange, secondMultiCharacterRange);
        assertHashCodesDiffer(secondMultiCharacterRange, singleCharacterRange);
        assertHashCodesDiffer(secondMultiCharacterRange, firstMultiCharacterRange);
    }

    private static void assertHashCodeIsStable(final CharRange range, final CharRange equivalentRange) {
        assertEquals(range.hashCode(), range.hashCode());
        assertEquals(range.hashCode(), equivalentRange.hashCode());
    }

    private static void assertHashCodesDiffer(final CharRange firstRange, final CharRange secondRange) {
        assertNotEquals(firstRange.hashCode(), secondRange.hashCode());
    }
}
