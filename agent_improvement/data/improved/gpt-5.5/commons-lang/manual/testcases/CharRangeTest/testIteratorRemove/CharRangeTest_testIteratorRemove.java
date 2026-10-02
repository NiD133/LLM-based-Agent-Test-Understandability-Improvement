package org.apache.commons.lang3;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Iterator;

import org.junit.jupiter.api.Test;

public class CharRangeTest_testIteratorRemove extends AbstractLangTest {

    @Test
    void testIteratorRemove() {
        final CharRange singleCharacterRange = CharRange.is('a');
        final Iterator<Character> iterator = singleCharacterRange.iterator();

        assertThrows(UnsupportedOperationException.class, iterator::remove);
    }
}
