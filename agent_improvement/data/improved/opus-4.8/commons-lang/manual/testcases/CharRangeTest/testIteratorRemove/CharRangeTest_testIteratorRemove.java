package org.apache.commons.lang3;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Iterator;

import org.junit.jupiter.api.Test;

public class CharRangeTest_testIteratorRemove extends AbstractLangTest {

    /**
     * A {@link CharRange} iterator is read-only, so calling {@code remove()}
     * must always fail with an {@link UnsupportedOperationException}.
     */
    @Test
    void testIteratorRemove() {
        final CharRange singleCharRange = CharRange.is('a');
        final Iterator<Character> iterator = singleCharRange.iterator();

        assertThrows(UnsupportedOperationException.class, iterator::remove);
    }
}
