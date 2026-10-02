package org.apache.commons.collections4.iterators;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

/**
 * Verifies the constructor behaviour of {@link LoopingListIterator}.
 */
public class LoopingListIteratorTest_testConstructorEx {

    /**
     * The constructor rejects a {@code null} list, because it cannot wrap a
     * non-existent collection. It must throw a {@link NullPointerException}.
     */
    @Test
    void constructorRejectsNullList() {
        assertThrows(NullPointerException.class, () -> new LoopingListIterator<>(null));
    }
}
