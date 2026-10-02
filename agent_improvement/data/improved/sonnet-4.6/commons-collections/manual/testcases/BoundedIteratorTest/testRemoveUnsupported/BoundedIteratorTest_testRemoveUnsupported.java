package org.apache.commons.collections4.iterators;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class BoundedIteratorTest_testRemoveUnsupported {

    private static final String[] TEST_ELEMENTS = { "a", "b", "c", "d", "e", "f", "g" };

    private List<String> testList;

    @BeforeEach
    public void setUp() {
        testList = Arrays.asList(TEST_ELEMENTS);
    }

    /**
     * Verifies that when the underlying iterator does not support {@code remove()},
     * the {@code UnsupportedOperationException} is propagated through {@code BoundedIterator}.
     *
     * Setup: BoundedIterator wraps a non-removable decorator at offset=1, max=5,
     * so the first element returned is "b" (index 1 of the test list).
     */
    @Test
    void testRemoveUnsupported() {
        // Wrap the test list in a decorator that forbids removal
        final Iterator<String> nonRemovableIterator = new AbstractIteratorDecorator<String>(testList.iterator()) {
            @Override
            public void remove() {
                throw new UnsupportedOperationException();
            }
        };

        // BoundedIterator with offset=1 (skip "a") and max=5
        final Iterator<String> boundedIterator = new BoundedIterator<>(nonRemovableIterator, 1, 5);

        // The iterator should be positioned at "b" and ready to advance
        assertTrue(boundedIterator.hasNext());
        assertEquals("b", boundedIterator.next());

        // Calling remove() should propagate the UnsupportedOperationException from the delegate
        final UnsupportedOperationException thrown =
                assertThrows(UnsupportedOperationException.class, () -> boundedIterator.remove());
        assertNull(thrown.getMessage());
    }
}
