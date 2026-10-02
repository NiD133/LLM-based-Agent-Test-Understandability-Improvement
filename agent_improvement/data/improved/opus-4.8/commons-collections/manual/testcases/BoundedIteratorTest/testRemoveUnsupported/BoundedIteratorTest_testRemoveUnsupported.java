package org.apache.commons.collections4.iterators;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import org.junit.jupiter.api.Test;

/**
 * Tests that {@link BoundedIterator} propagates an
 * {@link UnsupportedOperationException} from the decorated iterator when that
 * iterator does not support {@code remove()}.
 */
public class BoundedIteratorTest_testRemoveUnsupported {

    /** Elements wrapped by the iterator under test. */
    private final List<String> elements = Arrays.asList("a", "b", "c", "d", "e", "f", "g");

    @Test
    void testRemoveUnsupported() {
        // A decorated iterator whose remove() is not supported.
        final Iterator<String> removeRejectingIterator =
            new AbstractIteratorDecorator<String>(elements.iterator()) {
                @Override
                public void remove() {
                    throw new UnsupportedOperationException();
                }
            };

        // Bound the iterator to the range [offset=1, max=5), so the first
        // returned element is "b".
        final Iterator<String> boundedIterator =
            new BoundedIterator<>(removeRejectingIterator, 1, 5);

        assertTrue(boundedIterator.hasNext());
        assertEquals("b", boundedIterator.next());

        // remove() must delegate to the decorated iterator, which rejects it.
        final UnsupportedOperationException thrown =
            assertThrows(UnsupportedOperationException.class, () -> boundedIterator.remove());
        assertNull(thrown.getMessage());
    }
}
