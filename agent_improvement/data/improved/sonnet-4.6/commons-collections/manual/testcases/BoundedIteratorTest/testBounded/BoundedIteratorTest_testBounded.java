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
 * Tests BoundedIterator when offset and max constrain both ends of the source list.
 *
 * Source list: ["a", "b", "c", "d", "e", "f", "g"] (indices 0–6)
 * BoundedIterator(offset=2, max=4) → should return elements at indices 2, 3, 4, 5: "c", "d", "e", "f"
 */
public class BoundedIteratorTest_testBounded {

    // Source data: 7-element list used as the backing iterator
    private static final String[] SOURCE_ARRAY = { "a", "b", "c", "d", "e", "f", "g" };

    private List<String> sourceList;

    @BeforeEach
    public void setUp() {
        sourceList = Arrays.asList(SOURCE_ARRAY);
    }

    /**
     * A BoundedIterator with offset=2 and max=4 should skip the first two elements
     * and yield exactly four elements ("c", "d", "e", "f"), then report exhaustion.
     */
    @Test
    void testBounded() {
        // offset=2 skips "a","b"; max=4 limits to four elements: "c","d","e","f"
        final Iterator<String> iter = new BoundedIterator<>(sourceList.iterator(), 2, 4);

        assertTrue(iter.hasNext());
        assertEquals("c", iter.next());

        assertTrue(iter.hasNext());
        assertEquals("d", iter.next());

        assertTrue(iter.hasNext());
        assertEquals("e", iter.next());

        assertTrue(iter.hasNext());
        assertEquals("f", iter.next());

        // Iterator is now exhausted — no more elements within the bounded range
        assertFalse(iter.hasNext());
        assertThrows(NoSuchElementException.class, () -> iter.next(),
                "Expected NoSuchElementException when calling next() on an exhausted BoundedIterator.");
    }
}
