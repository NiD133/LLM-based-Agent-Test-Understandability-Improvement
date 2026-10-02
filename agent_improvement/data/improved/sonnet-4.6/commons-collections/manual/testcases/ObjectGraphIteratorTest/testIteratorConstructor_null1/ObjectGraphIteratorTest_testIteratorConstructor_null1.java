package org.apache.commons.collections4.iterators;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Iterator;
import java.util.NoSuchElementException;

import org.junit.jupiter.api.Test;

/**
 * Tests that constructing an {@link ObjectGraphIterator} with a {@code null} root iterator
 * produces an empty, non-iterable iterator that throws the correct exceptions.
 */
public class ObjectGraphIteratorTest_testIteratorConstructor_null1 {

    /**
     * When constructed with {@code null}, the iterator should:
     * <ul>
     *   <li>report no elements ({@code hasNext()} == false)</li>
     *   <li>throw {@link NoSuchElementException} on {@code next()}</li>
     *   <li>throw {@link IllegalStateException} on {@code remove()} (no element was retrieved)</li>
     * </ul>
     */
    @Test
    void testIteratorConstructor_null1() {
        final Iterator<Object> it = new ObjectGraphIterator<>(null);

        assertFalse(it.hasNext(), "A null-constructed iterator should have no elements");
        assertThrows(NoSuchElementException.class, it::next,
                "Calling next() on an empty iterator should throw NoSuchElementException");
        assertThrows(IllegalStateException.class, it::remove,
                "Calling remove() before any next() call should throw IllegalStateException");
    }
}
