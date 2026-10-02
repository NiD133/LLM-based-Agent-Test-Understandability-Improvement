package org.apache.commons.collections4.iterators;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Tests that BoundedIterator handles a {@code max} value larger than the
 * remaining elements in the decorated iterator: iteration should stop
 * naturally at the end of the underlying data, not at the max limit.
 */
public class BoundedIteratorTest_testMaxGreaterThanSize {

    // Seven-element list used as the decorated iterator's source
    private static final String[] SOURCE_ARRAY = { "a", "b", "c", "d", "e", "f", "g" };

    private List<String> sourceList;

    @BeforeEach
    public void setUp() {
        sourceList = Arrays.asList(SOURCE_ARRAY);
    }

    /**
     * When {@code max} (10) exceeds the number of elements available after the
     * offset (1), the iterator must return every remaining element ("b"–"g")
     * and then report exhaustion rather than throwing prematurely.
     */
    @Test
    void testMaxGreaterThanSize() {
        // offset=1 skips "a"; max=10 is intentionally larger than the 6 remaining elements
        final Iterator<String> iter = new BoundedIterator<>(sourceList.iterator(), 1, 10);

        assertTrue(iter.hasNext());
        assertEquals("b", iter.next());

        assertTrue(iter.hasNext());
        assertEquals("c", iter.next());

        assertTrue(iter.hasNext());
        assertEquals("d", iter.next());

        assertTrue(iter.hasNext());
        assertEquals("e", iter.next());

        assertTrue(iter.hasNext());
        assertEquals("f", iter.next());

        assertTrue(iter.hasNext());
        assertEquals("g", iter.next());

        // All source elements consumed — iterator must be exhausted
        assertFalse(iter.hasNext());
        assertThrows(NoSuchElementException.class, iter::next);
    }
}
