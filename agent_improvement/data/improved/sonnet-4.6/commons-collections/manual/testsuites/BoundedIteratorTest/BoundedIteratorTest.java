/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.apache.commons.collections4.iterators;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * Unit tests for {@link BoundedIterator}, which decorates another iterator
 * to return only a contiguous sub-range of its elements, controlled by an
 * {@code offset} (first element index to include) and a {@code max} (maximum
 * number of elements to return).
 *
 * <p>The shared test list is {@code ["a","b","c","d","e","f","g"]} (7 elements),
 * giving enough room to exercise offsets and max-counts both within and beyond
 * the list's bounds.</p>
 *
 * @param <E> the type of elements tested by this iterator.
 */
public class BoundedIteratorTest<E> extends AbstractIteratorTest<E> {

    /** Seven-element source used across all tests: indices 0–6 map to "a"–"g". */
    private final String[] testArray = {
        "a", "b", "c", "d", "e", "f", "g"
    };

    private List<E> testList;

    @Override
    public Iterator<E> makeEmptyIterator() {
        return new BoundedIterator<>(Collections.<E>emptyList().iterator(), 0, 10);
    }

    @Override
    public Iterator<E> makeObject() {
        return new BoundedIterator<>(new ArrayList<>(testList).iterator(), 1, testList.size() - 1);
    }

    @SuppressWarnings("unchecked")
    @BeforeEach
    public void setUp() throws Exception {
        testList = Arrays.asList((E[]) testArray);
    }

    /**
     * A BoundedIterator whose offset and max both fall strictly within the
     * decorated iterator's range should return only those elements.
     *
     * <p>With offset=2 and max=4 the iterator covers indices 2–5 ("c","d","e","f")
     * and must report {@code hasNext() == false} immediately after "f".</p>
     */
    @Test
    @DisplayName("offset and max both within range — returns only the bounded sub-sequence")
    void testBounded() {
        final int offset = 2;
        final int maxElements = 4;
        final Iterator<E> iter = new BoundedIterator<>(testList.iterator(), offset, maxElements);

        assertTrue(iter.hasNext(), "iterator should have elements within bounds");
        assertEquals("c", iter.next(), "1st element (index 2) should be 'c'");
        assertTrue(iter.hasNext(), "iterator should still have elements");
        assertEquals("d", iter.next(), "2nd element (index 3) should be 'd'");
        assertTrue(iter.hasNext(), "iterator should still have elements");
        assertEquals("e", iter.next(), "3rd element (index 4) should be 'e'");
        assertTrue(iter.hasNext(), "iterator should still have elements");
        assertEquals("f", iter.next(), "4th element (index 5) should be 'f'");

        assertFalse(iter.hasNext(), "iterator should be exhausted after max elements");
        assertThrows(NoSuchElementException.class, () -> iter.next(),
                "next() past the bound should throw NoSuchElementException");
    }

    /**
     * A BoundedIterator with max=0 must behave like an empty iterator,
     * regardless of the underlying source.
     */
    @Test
    @DisplayName("max=0 — iterator is empty even when underlying source has elements")
    void testEmptyBounded() {
        final int offset = 3;
        final int maxElements = 0;
        final Iterator<E> iter = new BoundedIterator<>(testList.iterator(), offset, maxElements);

        assertFalse(iter.hasNext(), "max=0 means no elements should be returned");
        assertThrows(NoSuchElementException.class, () -> iter.next(),
                "next() on empty bounded iterator should throw NoSuchElementException");
    }

    /**
     * When max exceeds the number of remaining elements in the underlying
     * iterator, the BoundedIterator should stop at the last available element
     * rather than throwing an exception.
     *
     * <p>With offset=1 and max=10 the iterator covers indices 1–6 ("b"–"g"),
     * all six remaining elements, and then reports exhaustion.</p>
     */
    @Test
    @DisplayName("max larger than remaining elements — stops at last element of underlying iterator")
    void testMaxGreaterThanSize() {
        final int offset = 1;
        final int maxLargerThanSource = 10;
        final Iterator<E> iter = new BoundedIterator<>(testList.iterator(), offset, maxLargerThanSource);

        assertTrue(iter.hasNext(), "iterator should have elements");
        assertEquals("b", iter.next(), "1st element (index 1) should be 'b'");
        assertTrue(iter.hasNext(), "iterator should still have elements");
        assertEquals("c", iter.next(), "2nd element (index 2) should be 'c'");
        assertTrue(iter.hasNext(), "iterator should still have elements");
        assertEquals("d", iter.next(), "3rd element (index 3) should be 'd'");
        assertTrue(iter.hasNext(), "iterator should still have elements");
        assertEquals("e", iter.next(), "4th element (index 4) should be 'e'");
        assertTrue(iter.hasNext(), "iterator should still have elements");
        assertEquals("f", iter.next(), "5th element (index 5) should be 'f'");
        assertTrue(iter.hasNext(), "iterator should still have elements");
        assertEquals("g", iter.next(), "6th element (index 6) should be 'g'");

        assertFalse(iter.hasNext(), "iterator should be exhausted after last source element");
        assertThrows(NoSuchElementException.class, () -> iter.next(),
                "next() past source end should throw NoSuchElementException");
    }

    /**
     * Passing a negative max to the constructor must throw
     * {@link IllegalArgumentException} with a message that names the
     * offending parameter.
     */
    @Test
    @DisplayName("negative max — constructor throws IllegalArgumentException")
    void testNegativeMax() {
        final IllegalArgumentException thrown = assertThrows(
                IllegalArgumentException.class,
                () -> new BoundedIterator<>(testList.iterator(), 3, -1),
                "negative max should be rejected at construction time");
        assertEquals("Max parameter must not be negative.", thrown.getMessage());
    }

    /**
     * Passing a negative offset to the constructor must throw
     * {@link IllegalArgumentException} with a message that names the
     * offending parameter.
     */
    @Test
    @DisplayName("negative offset — constructor throws IllegalArgumentException")
    void testNegativeOffset() {
        final IllegalArgumentException thrown = assertThrows(
                IllegalArgumentException.class,
                () -> new BoundedIterator<>(testList.iterator(), -1, 4),
                "negative offset should be rejected at construction time");
        assertEquals("Offset parameter must not be negative.", thrown.getMessage());
    }

    /**
     * When the offset is beyond the end of the underlying iterator, the
     * BoundedIterator must be empty from the start.
     */
    @Test
    @DisplayName("offset beyond source size — iterator is immediately exhausted")
    void testOffsetGreaterThanSize() {
        final int offsetPastEnd = 10;
        final Iterator<E> iter = new BoundedIterator<>(testList.iterator(), offsetPastEnd, 4);

        assertFalse(iter.hasNext(), "offset past source end means no elements available");
        assertThrows(NoSuchElementException.class, () -> iter.next(),
                "next() with offset past source end should throw NoSuchElementException");
    }

    /**
     * Calling {@code remove()} twice in a row (without an intervening
     * {@code next()}) must throw {@link IllegalStateException} on the second call.
     */
    @Test
    @DisplayName("remove() called twice without next() in between — second call throws IllegalStateException")
    void testRemoveCalledTwice() {
        final List<E> testListCopy = new ArrayList<>(testList);
        final Iterator<E> iter = new BoundedIterator<>(testListCopy.iterator(), 1, 5);

        assertTrue(iter.hasNext(), "iterator should have elements before first remove");
        assertEquals("b", iter.next(), "first element after offset=1 should be 'b'");
        iter.remove();

        assertThrows(IllegalStateException.class, () -> iter.remove(),
                "second remove() without next() should throw IllegalStateException");
    }

    /**
     * Removing the first element returned by the iterator must eliminate it
     * from the backing collection while leaving subsequent elements intact.
     */
    @Test
    @DisplayName("remove() on first element — element removed from backing collection, remaining elements unaffected")
    void testRemoveFirst() {
        final List<E> testListCopy = new ArrayList<>(testList);
        final Iterator<E> iter = new BoundedIterator<>(testListCopy.iterator(), 1, 5);

        assertTrue(iter.hasNext(), "iterator should have elements");
        assertEquals("b", iter.next(), "first element after offset=1 should be 'b'");

        iter.remove();
        assertFalse(testListCopy.contains("b"), "'b' should have been removed from the backing list");

        assertTrue(iter.hasNext(), "remaining elements should still be accessible");
        assertEquals("c", iter.next(), "next element after removing 'b' should be 'c'");
        assertTrue(iter.hasNext());
        assertEquals("d", iter.next());
        assertTrue(iter.hasNext());
        assertEquals("e", iter.next());
        assertTrue(iter.hasNext());
        assertEquals("f", iter.next());

        assertFalse(iter.hasNext(), "iterator should be exhausted after 5 elements (max=5)");
        assertThrows(NoSuchElementException.class, () -> iter.next(),
                "next() past max should throw NoSuchElementException");
    }

    /**
     * Removing the last element returned by the iterator (after the iterator
     * reports exhaustion) must eliminate it from the backing collection.
     *
     * <p>The {@link NoSuchElementException} thrown by {@code next()} after
     * exhaustion must have no message, both before and after the remove.</p>
     */
    @Test
    @DisplayName("remove() on last element — element removed from backing collection after iterator exhaustion")
    void testRemoveLast() {
        final List<E> testListCopy = new ArrayList<>(testList);
        final Iterator<E> iter = new BoundedIterator<>(testListCopy.iterator(), 1, 5);

        assertTrue(iter.hasNext());
        assertEquals("b", iter.next());
        assertTrue(iter.hasNext());
        assertEquals("c", iter.next());
        assertTrue(iter.hasNext());
        assertEquals("d", iter.next());
        assertTrue(iter.hasNext());
        assertEquals("e", iter.next());
        assertTrue(iter.hasNext());
        assertEquals("f", iter.next(), "last element within max=5 should be 'f'");

        assertFalse(iter.hasNext(), "iterator should be exhausted after max elements");

        final NoSuchElementException thrownBeforeRemove =
                assertThrows(NoSuchElementException.class, () -> iter.next(),
                        "next() on exhausted iterator should throw NoSuchElementException");
        assertNull(thrownBeforeRemove.getMessage(),
                "NoSuchElementException should carry no message");

        iter.remove();
        assertFalse(testListCopy.contains("f"), "'f' should have been removed from the backing list");

        assertFalse(iter.hasNext(), "iterator should remain exhausted after remove");

        final NoSuchElementException thrownAfterRemove =
                assertThrows(NoSuchElementException.class, () -> iter.next(),
                        "next() should still throw NoSuchElementException after remove");
        assertNull(thrownAfterRemove.getMessage(),
                "NoSuchElementException should carry no message after remove");
    }

    /**
     * Removing a middle element must eliminate it from the backing collection
     * while the iterator continues to return subsequent elements normally.
     */
    @Test
    @DisplayName("remove() on middle element — element removed from backing collection, iteration continues")
    void testRemoveMiddle() {
        final List<E> testListCopy = new ArrayList<>(testList);
        final Iterator<E> iter = new BoundedIterator<>(testListCopy.iterator(), 1, 5);

        assertTrue(iter.hasNext());
        assertEquals("b", iter.next());
        assertTrue(iter.hasNext());
        assertEquals("c", iter.next());
        assertTrue(iter.hasNext());
        assertEquals("d", iter.next(), "element to be removed should be 'd'");

        iter.remove();
        assertFalse(testListCopy.contains("d"), "'d' should have been removed from the backing list");

        assertTrue(iter.hasNext(), "elements after the removed one should still be accessible");
        assertEquals("e", iter.next(), "element after removed 'd' should be 'e'");
        assertTrue(iter.hasNext());
        assertEquals("f", iter.next());

        assertFalse(iter.hasNext(), "iterator should be exhausted after max=5 elements");
        assertThrows(NoSuchElementException.class, () -> iter.next(),
                "next() past max should throw NoSuchElementException");
    }

    /**
     * When the underlying iterator does not support {@code remove()}, the
     * {@link UnsupportedOperationException} must propagate through
     * BoundedIterator without a message.
     */
    @Test
    @DisplayName("remove() on non-removable underlying iterator — UnsupportedOperationException propagates")
    void testRemoveUnsupported() {
        final Iterator<E> nonRemovableIterator = new AbstractIteratorDecorator<E>(testList.iterator()) {
            @Override
            public void remove() {
                throw new UnsupportedOperationException();
            }
        };

        final Iterator<E> iter = new BoundedIterator<>(nonRemovableIterator, 1, 5);
        assertTrue(iter.hasNext(), "iterator should have elements");
        assertEquals("b", iter.next(), "first element after offset=1 should be 'b'");

        final UnsupportedOperationException thrown =
                assertThrows(UnsupportedOperationException.class, () -> iter.remove(),
                        "remove() should propagate UnsupportedOperationException from underlying iterator");
        assertNull(thrown.getMessage(),
                "propagated UnsupportedOperationException should carry no message");
    }

    /**
     * Calling {@code remove()} before any call to {@code next()} must throw
     * {@link IllegalStateException} with a descriptive message.
     */
    @Test
    @DisplayName("remove() before next() — throws IllegalStateException with descriptive message")
    void testRemoveWithoutCallingNext() {
        final List<E> testListCopy = new ArrayList<>(testList);
        final Iterator<E> iter = new BoundedIterator<>(testListCopy.iterator(), 1, 5);

        final IllegalStateException thrown =
                assertThrows(IllegalStateException.class, () -> iter.remove(),
                        "remove() without prior next() should throw IllegalStateException");
        assertEquals("remove() cannot be called before calling next()", thrown.getMessage());
    }

    /**
     * A BoundedIterator with offset=0 and max equal to the source size must
     * return exactly the same elements as the decorated iterator, in order.
     */
    @Test
    @DisplayName("offset=0 and max=source size — returns all elements, identical to decorated iterator")
    void testSameAsDecorated() {
        final int offset = 0;
        final Iterator<E> iter = new BoundedIterator<>(testList.iterator(), offset, testList.size());

        assertTrue(iter.hasNext(), "iterator should have elements");
        assertEquals("a", iter.next(), "1st element should be 'a'");
        assertTrue(iter.hasNext());
        assertEquals("b", iter.next(), "2nd element should be 'b'");
        assertTrue(iter.hasNext());
        assertEquals("c", iter.next(), "3rd element should be 'c'");
        assertTrue(iter.hasNext());
        assertEquals("d", iter.next(), "4th element should be 'd'");
        assertTrue(iter.hasNext());
        assertEquals("e", iter.next(), "5th element should be 'e'");
        assertTrue(iter.hasNext());
        assertEquals("f", iter.next(), "6th element should be 'f'");
        assertTrue(iter.hasNext());
        assertEquals("g", iter.next(), "7th element should be 'g'");

        assertFalse(iter.hasNext(), "iterator should be exhausted after all 7 elements");
        assertThrows(NoSuchElementException.class, () -> iter.next(),
                "next() past source end should throw NoSuchElementException");
    }
}
