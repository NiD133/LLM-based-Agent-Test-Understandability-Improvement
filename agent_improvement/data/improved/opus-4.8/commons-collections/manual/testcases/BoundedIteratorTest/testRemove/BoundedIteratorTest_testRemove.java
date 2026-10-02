package org.apache.commons.collections4.iterators;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Tests {@link BoundedIterator#remove()}.
 */
public class BoundedIteratorTest_testRemove {

    /** Backing data: seven elements, so the bounded view below covers indices 1..6. */
    private final String[] testArray = { "a", "b", "c", "d", "e", "f", "g" };

    private List<String> testList;

    @BeforeEach
    public void setUp() {
        testList = Arrays.asList(testArray);
    }

    /**
     * Creates a {@link BoundedIterator} over a mutable copy of {@link #testList},
     * skipping the first element (offset = 1) and exposing the remaining elements
     * (max = size - 1). The copy is mutable so that {@code remove()} is supported.
     */
    private Iterator<String> makeBoundedIterator() {
        final List<String> mutableSource = new ArrayList<>(testList);
        return new BoundedIterator<>(mutableSource.iterator(), 1, testList.size() - 1);
    }

    @Test
    void testRemove() {
        final Iterator<String> it = makeBoundedIterator();

        // remove() before any next() is illegal
        assertThrows(IllegalStateException.class, it::remove);

        // remove() right after a successful next() is allowed
        it.next();
        it.remove();

        // a second remove() without an intervening next() is illegal again
        assertThrows(IllegalStateException.class, it::remove);
    }
}
