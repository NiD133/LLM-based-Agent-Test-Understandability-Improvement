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
import org.junit.jupiter.api.Test;

/**
 * A unit test to test the basic functions of {@link BoundedIterator}.
 *
 * <p>{@link BoundedIterator} decorates another iterator so that only the
 * elements in the range {@code [offset, offset + max)} are returned. These
 * tests exercise that windowing behaviour together with the {@code remove()}
 * contract and the constructor's argument validation.</p>
 *
 * @param <E> the type of elements tested by this iterator.
 */
public class BoundedIteratorTest<E> extends AbstractIteratorTest<E> {

    /** The seven elements (indexes 0..6) wrapped by the iterators under test. */
    private final String[] testArray = {
        "a", "b", "c", "d", "e", "f", "g"
    };

    /** The {@link #testArray} exposed as a {@code List<E>}; rebuilt before each test. */
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
    public void setUp()
        throws Exception {
        testList = Arrays.asList((E[]) testArray);
    }

    /**
     * Asserts that the iterator reports another element and that {@link Iterator#next()}
     * returns the {@code expected} value.
     *
     * @param iter  the iterator to advance
     * @param expected  the value the next element is expected to equal
     */
    private void assertNextElement(final Iterator<E> iter, final String expected) {
        assertTrue(iter.hasNext(), "iterator should have another element");
        assertEquals(expected, iter.next());
    }

    /**
     * Asserts that the iterator is exhausted: {@link Iterator#hasNext()} is
     * {@code false} and {@link Iterator#next()} throws {@link NoSuchElementException}.
     *
     * @param iter  the iterator expected to be exhausted
     */
    private void assertExhausted(final Iterator<E> iter) {
        assertFalse(iter.hasNext(), "iterator should be exhausted");
        assertThrows(NoSuchElementException.class, () -> iter.next(),
                "Expected NoSuchElementException.");
    }

    /**
     * Test a decorated iterator bounded such that the first element returned is
     * at an index greater its first element, and the last element returned is
     * at an index less than its last element.
     */
    @Test
    void testBounded() {
        // Window [offset=2, max=4) -> indexes 2,3,4,5 -> "c","d","e","f".
        final Iterator<E> iter = new BoundedIterator<>(testList.iterator(), 2, 4);

        assertNextElement(iter, "c");
        assertNextElement(iter, "d");
        assertNextElement(iter, "e");
        assertNextElement(iter, "f");

        assertExhausted(iter);
    }

    /**
     * Test a decorated iterator bounded to a {@code max} of 0. The
     * BoundedIterator should behave as if there are no more elements to return,
     * since it is technically an empty iterator.
     */
    @Test
    void testEmptyBounded() {
        // A max of 0 means no elements are in range, regardless of offset.
        final Iterator<E> iter = new BoundedIterator<>(testList.iterator(), 3, 0);

        assertExhausted(iter);
    }

    /**
     * Test the case if the {@code max} passed to the constructor is
     * greater than the size of the decorated iterator. The last element
     * returned should be the same as the last element of the decorated
     * iterator.
     */
    @Test
    void testMaxGreaterThanSize() {
        // max=10 exceeds the 6 remaining elements, so iteration stops at the
        // decorated iterator's end: indexes 1..6 -> "b".."g".
        final Iterator<E> iter = new BoundedIterator<>(testList.iterator(), 1, 10);

        assertNextElement(iter, "b");
        assertNextElement(iter, "c");
        assertNextElement(iter, "d");
        assertNextElement(iter, "e");
        assertNextElement(iter, "f");
        assertNextElement(iter, "g");

        assertExhausted(iter);
    }

    /**
     * Test the case if a negative {@code max} is passed to the
     * constructor. {@link IllegalArgumentException} is expected.
     */
    @Test
    void testNegativeMax() {
        final IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class,
                () -> new BoundedIterator<>(testList.iterator(), 3, -1));
        assertEquals("Max parameter must not be negative.", thrown.getMessage());
    }

    /**
     * Test the case if a negative {@code offset} is passed to the
     * constructor. {@link IllegalArgumentException} is expected.
     */
    @Test
    void testNegativeOffset() {
        final IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class,
                () -> new BoundedIterator<>(testList.iterator(), -1, 4));
        assertEquals("Offset parameter must not be negative.", thrown.getMessage());
    }

    /**
     * Test the case if the {@code offset} passed to the constructor is
     * greater than the decorated iterator's size. The BoundedIterator should
     * behave as if there are no more elements to return.
     */
    @Test
    void testOffsetGreaterThanSize() {
        // offset=10 skips past every element, leaving nothing to return.
        final Iterator<E> iter = new BoundedIterator<>(testList.iterator(), 10, 4);

        assertExhausted(iter);
    }

    /**
     * Test the {@code remove()} method being called twice without calling
     * {@code next()} in between.
     */
    @Test
    void testRemoveCalledTwice() {
        final List<E> testListCopy = new ArrayList<>(testList);
        final Iterator<E> iter = new BoundedIterator<>(testListCopy.iterator(), 1, 5);

        assertNextElement(iter, "b");
        iter.remove();

        // A second remove() with no intervening next() is illegal.
        assertThrows(IllegalStateException.class, () -> iter.remove());
    }

    /**
     * Test removing the first element. Verify that the element is removed from
     * the underlying collection.
     */
    @Test
    void testRemoveFirst() {
        final List<E> testListCopy = new ArrayList<>(testList);
        final Iterator<E> iter = new BoundedIterator<>(testListCopy.iterator(), 1, 5);

        assertNextElement(iter, "b");

        iter.remove();
        assertFalse(testListCopy.contains("b"));

        // Removing the first in-range element does not affect the rest: "c".."f".
        assertNextElement(iter, "c");
        assertNextElement(iter, "d");
        assertNextElement(iter, "e");
        assertNextElement(iter, "f");

        assertExhausted(iter);
    }

    /**
     * Test removing the last element. Verify that the element is removed from
     * the underlying collection.
     */
    @Test
    void testRemoveLast() {
        final List<E> testListCopy = new ArrayList<>(testList);
        final Iterator<E> iter = new BoundedIterator<>(testListCopy.iterator(), 1, 5);

        assertNextElement(iter, "b");
        assertNextElement(iter, "c");
        assertNextElement(iter, "d");
        assertNextElement(iter, "e");
        assertNextElement(iter, "f");

        assertFalse(iter.hasNext());

        // next() past the end throws an unmessaged NoSuchElementException.
        final NoSuchElementException thrown = assertThrows(NoSuchElementException.class,
                () -> iter.next());
        assertNull(thrown.getMessage());

        // remove() after the last next() still removes that last element ("f").
        iter.remove();
        assertFalse(testListCopy.contains("f"));

        assertFalse(iter.hasNext());

        final NoSuchElementException thrown1 = assertThrows(NoSuchElementException.class,
                () -> iter.next());
        assertNull(thrown1.getMessage());
    }

    /**
     * Test removing an element in the middle of the iterator. Verify that the
     * element is removed from the underlying collection.
     */
    @Test
    void testRemoveMiddle() {
        final List<E> testListCopy = new ArrayList<>(testList);
        final Iterator<E> iter = new BoundedIterator<>(testListCopy.iterator(), 1, 5);

        assertNextElement(iter, "b");
        assertNextElement(iter, "c");
        assertNextElement(iter, "d");

        iter.remove();
        assertFalse(testListCopy.contains("d"));

        // Iteration continues unaffected after a mid-stream remove: "e","f".
        assertNextElement(iter, "e");
        assertNextElement(iter, "f");

        assertExhausted(iter);
    }

    /**
     * Test the case if the decorated iterator does not support the
     * {@code remove()} method and throws an {@link UnsupportedOperationException}.
     */
    @Test
    void testRemoveUnsupported() {
        // A decorated iterator whose remove() is unsupported.
        final Iterator<E> mockIterator = new AbstractIteratorDecorator<E>(testList.iterator()) {
            @Override
            public void remove() {
                throw new UnsupportedOperationException();
            }
        };

        final Iterator<E> iter = new BoundedIterator<>(mockIterator, 1, 5);
        assertNextElement(iter, "b");

        // BoundedIterator should propagate the decorated iterator's failure.
        final UnsupportedOperationException thrown = assertThrows(UnsupportedOperationException.class,
                () -> iter.remove());
        assertNull(thrown.getMessage());
    }

    /**
     * Test the {@code remove()} method being called without
     * {@code next()} being called first.
     */
    @Test
    void testRemoveWithoutCallingNext() {
        final List<E> testListCopy = new ArrayList<>(testList);
        final Iterator<E> iter = new BoundedIterator<>(testListCopy.iterator(), 1, 5);

        // remove() before any next() is illegal, even with a non-zero offset.
        final IllegalStateException thrown = assertThrows(IllegalStateException.class,
                () -> iter.remove());
        assertEquals("remove() cannot be called before calling next()", thrown.getMessage());
    }

    /**
     * Test a decorated iterator bounded such that the {@code offset} is
     * zero and the {@code max} is its size, in that the BoundedIterator
     * should return all the same elements as its decorated iterator.
     */
    @Test
    void testSameAsDecorated() {
        // Window [offset=0, max=size) covers everything: "a".."g".
        final Iterator<E> iter = new BoundedIterator<>(testList.iterator(), 0,
                                                  testList.size());

        assertNextElement(iter, "a");
        assertNextElement(iter, "b");
        assertNextElement(iter, "c");
        assertNextElement(iter, "d");
        assertNextElement(iter, "e");
        assertNextElement(iter, "f");
        assertNextElement(iter, "g");

        assertExhausted(iter);
    }

}
