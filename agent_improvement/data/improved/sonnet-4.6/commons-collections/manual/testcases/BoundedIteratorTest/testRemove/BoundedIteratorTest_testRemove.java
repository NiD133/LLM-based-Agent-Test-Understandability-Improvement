package org.apache.commons.collections4.iterators;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class BoundedIteratorTest_testRemove {

    /** Elements used to back the test iterator. */
    private final String[] testArray = { "a", "b", "c", "d", "e", "f", "g" };

    private List<String> testList;

    @BeforeEach
    public void setUp() {
        testList = Arrays.asList(testArray);
    }

    /**
     * Returns a BoundedIterator that skips the first element and exposes
     * the remaining six elements of {@code testList}, backed by a mutable
     * ArrayList so that {@code remove()} is supported.
     */
    private Iterator<String> makeObject() {
        return new BoundedIterator<>(new ArrayList<>(testList).iterator(), 1, testList.size() - 1);
    }

    /** Returns true because BoundedIterator delegates remove() to the backing iterator. */
    public boolean supportsRemove() {
        return true;
    }

    /** Hook for subclass cross-verification; intentionally empty here. */
    public void verify() {
        // no-op
    }

    /**
     * Verifies the remove() contract of BoundedIterator:
     * <ul>
     *   <li>remove() before the first next() call throws IllegalStateException</li>
     *   <li>remove() after next() succeeds without throwing</li>
     *   <li>a consecutive second remove() (without an intervening next()) throws IllegalStateException</li>
     * </ul>
     */
    @Test
    void testRemove() {
        final Iterator<String> it = makeObject();

        if (!supportsRemove()) {
            assertThrows(UnsupportedOperationException.class, it::remove);
            return;
        }

        // remove() before any next() call must throw
        assertThrows(IllegalStateException.class, () -> it.remove());
        verify();

        // advance past the first element, then remove it — must succeed
        it.next();
        it.remove();

        // a second consecutive remove() (no intervening next()) must throw
        assertThrows(IllegalStateException.class, () -> it.remove());
    }
}
