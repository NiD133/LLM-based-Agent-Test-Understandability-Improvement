package org.apache.commons.lang3;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Iterator;

import org.junit.jupiter.api.Test;

/**
 * Verifies that the iterator returned by CharRange does not support element removal,
 * since CharRange instances are immutable.
 */
public class CharRangeTest_testIteratorRemove extends AbstractLangTest {

    @Test
    void testIteratorRemove() {
        // CharRange is immutable, so its iterator must reject remove() calls
        final CharRange singleCharRange = CharRange.is('a');
        final Iterator<Character> iterator = singleCharRange.iterator();

        assertThrows(UnsupportedOperationException.class, iterator::remove);
    }
}
