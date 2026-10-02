package org.apache.commons.collections4.iterators;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Iterator;
import java.util.NoSuchElementException;

import org.junit.jupiter.api.Test;

/**
 * Tests ObjectGraphIterator when constructed with a non-Iterator root and a null transformer.
 *
 * When no transformer is supplied, the iterator yields the root object exactly once
 * without applying any traversal logic, then reports exhaustion.
 */
public class ObjectGraphIteratorTest_testIteration_RootNoTransformer {

    /** Minimal stand-in for a domain root object (not an Iterator). */
    static class Forest {
    }

    /**
     * Verifies that an ObjectGraphIterator with a non-Iterator root and a null transformer:
     * <ul>
     *   <li>reports hasNext() == true before the first call</li>
     *   <li>returns the exact root object on next()</li>
     *   <li>reports hasNext() == false after the root is consumed</li>
     *   <li>throws NoSuchElementException on a subsequent next() call</li>
     * </ul>
     */
    @Test
    void testIteration_RootNoTransformer() {
        final Forest forest = new Forest();
        final Iterator<Object> it = new ObjectGraphIterator<>(forest, null);

        assertTrue(it.hasNext(), "iterator should have the root element");
        assertSame(forest, it.next(), "next() should return the root object itself");
        assertFalse(it.hasNext(), "iterator should be exhausted after returning root");
        assertThrows(NoSuchElementException.class, it::next,
                "next() on exhausted iterator should throw NoSuchElementException");
    }
}
