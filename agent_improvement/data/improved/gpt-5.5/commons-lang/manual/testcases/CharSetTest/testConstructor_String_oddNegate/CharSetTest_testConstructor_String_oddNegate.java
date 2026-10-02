package org.apache.commons.lang3;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Set;

import org.junit.jupiter.api.Test;

public class CharSetTest_testConstructor_String_oddNegate extends AbstractLangTest {

    @Test
    void testConstructor_String_oddNegate() {
        assertCharSetRanges("^", CharRange.is('^'));
        assertCharSetRanges("^^", CharRange.isNot('^'));
        assertCharSetRanges("^^^", CharRange.isNot('^'), CharRange.is('^'));
        assertCharSetRanges("^^^^", CharRange.isNot('^'));
        assertCharSetRanges("a^", CharRange.is('a'), CharRange.is('^'));
        assertCharSetRanges("^a-", CharRange.isNot('a'), CharRange.is('-'));
        assertCharSetRanges("^^-c", CharRange.isNotIn('^', 'c'));
        assertCharSetRanges("^c-^", CharRange.isNotIn('c', '^'));
        assertCharSetRanges("^c-^d", CharRange.isNotIn('c', '^'), CharRange.is('d'));
        assertCharSetRanges("^^-", CharRange.isNot('^'), CharRange.is('-'));
    }

    private static void assertCharSetRanges(final String definition, final CharRange... expectedRanges) {
        final CharSet set = CharSet.getInstance(definition);
        final Set<CharRange> actualRanges = set.getCharRanges();

        assertEquals(expectedRanges.length, actualRanges.size());
        for (final CharRange expectedRange : expectedRanges) {
            assertTrue(actualRanges.contains(expectedRange));
        }
    }
}
