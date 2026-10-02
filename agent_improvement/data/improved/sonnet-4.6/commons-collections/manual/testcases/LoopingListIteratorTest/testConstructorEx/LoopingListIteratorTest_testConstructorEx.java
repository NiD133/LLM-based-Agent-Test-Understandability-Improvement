package org.apache.commons.collections4.iterators;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

public class LoopingListIteratorTest_testConstructorEx {

    /**
     * Verifies that constructing a {@link LoopingListIterator} with a {@code null}
     * list argument throws a {@link NullPointerException}, as the iterator requires
     * a non-null list to loop over.
     */
    @Test
    void testConstructorThrowsNullPointerExceptionWhenListIsNull() {
        assertThrows(NullPointerException.class, () -> new LoopingListIterator<>(null));
    }
}
