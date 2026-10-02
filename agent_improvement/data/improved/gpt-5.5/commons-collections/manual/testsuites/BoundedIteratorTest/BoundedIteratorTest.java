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
 * @param <E> the type of elements tested by this iterator.
 */
public class BoundedIteratorTest<E> extends AbstractIteratorTest<E> {

    /** Test array of size 7. */
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
    public void setUp()
        throws Exception {
        testList = Arrays.asList((E[]) testArray);
    }

    @Test
    void testBounded() {
        final Iterator<E> iter = new BoundedIterator<>(testList.iterator(), 2, 4);

        assertNextElements(iter, "c", "d", "e", "f");
        assertExhausted(iter, "Expected NoSuchElementException.");
    }

    @Test
    void testEmptyBounded() {
        final Iterator<E> iter = new BoundedIterator<>(testList.iterator(), 3, 0);

        assertExhausted(iter);
    }

    @Test
    void testMaxGreaterThanSize() {
        final Iterator<E> iter = new BoundedIterator<>(testList.iterator(), 1, 10);

        assertNextElements(iter, "b", "c", "d", "e", "f", "g");
        assertExhausted(iter);
    }

    @Test
    void testNegativeMax() {
        final IllegalArgumentException thrown = assertThrows(
                IllegalArgumentException.class,
                () -> new BoundedIterator<>(testList.iterator(), 3, -1));
        assertEquals("Max parameter must not be negative.", thrown.getMessage());
    }

    @Test
    void testNegativeOffset() {
        final IllegalArgumentException thrown = assertThrows(
                IllegalArgumentException.class,
                () -> new BoundedIterator<>(testList.iterator(), -1, 4));
        assertEquals("Offset parameter must not be negative.", thrown.getMessage());
    }

    @Test
    void testOffsetGreaterThanSize() {
        final Iterator<E> iter = new BoundedIterator<>(testList.iterator(), 10, 4);

        assertExhausted(iter);
    }

    @Test
    void testRemoveCalledTwice() {
        final List<E> testListCopy = new ArrayList<>(testList);
        final Iterator<E> iter = new BoundedIterator<>(testListCopy.iterator(), 1, 5);

        assertNextElements(iter, "b");
        iter.remove();

        assertThrows(IllegalStateException.class, () -> iter.remove());
    }

    @Test
    void testRemoveFirst() {
        final List<E> testListCopy = new ArrayList<>(testList);
        final Iterator<E> iter = new BoundedIterator<>(testListCopy.iterator(), 1, 5);

        assertNextElements(iter, "b");

        iter.remove();
        assertFalse(testListCopy.contains("b"));

        assertNextElements(iter, "c", "d", "e", "f");
        assertExhausted(iter);
    }

    @Test
    void testRemoveLast() {
        final List<E> testListCopy = new ArrayList<>(testList);
        final Iterator<E> iter = new BoundedIterator<>(testListCopy.iterator(), 1, 5);

        assertNextElements(iter, "b", "c", "d", "e", "f");
        assertFalse(iter.hasNext());

        final NoSuchElementException thrown = assertThrows(NoSuchElementException.class, () -> iter.next());
        assertNull(thrown.getMessage());

        iter.remove();
        assertFalse(testListCopy.contains("f"));

        assertFalse(iter.hasNext());

        final NoSuchElementException thrown1 = assertThrows(NoSuchElementException.class, () -> iter.next());
        assertNull(thrown1.getMessage());
    }

    @Test
    void testRemoveMiddle() {
        final List<E> testListCopy = new ArrayList<>(testList);
        final Iterator<E> iter = new BoundedIterator<>(testListCopy.iterator(), 1, 5);

        assertNextElements(iter, "b", "c", "d");

        iter.remove();
        assertFalse(testListCopy.contains("d"));

        assertNextElements(iter, "e", "f");
        assertExhausted(iter);
    }

    @Test
    void testRemoveUnsupported() {
        final Iterator<E> mockIterator = new AbstractIteratorDecorator<E>(testList.iterator()) {
            @Override
            public void remove() {
                throw new UnsupportedOperationException();
            }
        };

        final Iterator<E> iter = new BoundedIterator<>(mockIterator, 1, 5);
        assertNextElements(iter, "b");

        final UnsupportedOperationException thrown = assertThrows(UnsupportedOperationException.class, () -> iter.remove());
        assertNull(thrown.getMessage());
    }

    @Test
    void testRemoveWithoutCallingNext() {
        final List<E> testListCopy = new ArrayList<>(testList);
        final Iterator<E> iter = new BoundedIterator<>(testListCopy.iterator(), 1, 5);

        final IllegalStateException thrown = assertThrows(IllegalStateException.class, () -> iter.remove());
        assertEquals("remove() cannot be called before calling next()", thrown.getMessage());
    }

    @Test
    void testSameAsDecorated() {
        final Iterator<E> iter = new BoundedIterator<>(testList.iterator(), 0, testList.size());

        assertNextElements(iter, "a", "b", "c", "d", "e", "f", "g");
        assertExhausted(iter);
    }

    private void assertNextElements(final Iterator<E> iterator, final String... expectedElements) {
        for (final String expectedElement : expectedElements) {
            assertTrue(iterator.hasNext());
            assertEquals(expectedElement, iterator.next());
        }
    }

    private void assertExhausted(final Iterator<E> iterator) {
        assertFalse(iterator.hasNext());
        assertThrows(NoSuchElementException.class, () -> iterator.next());
    }

    private void assertExhausted(final Iterator<E> iterator, final String message) {
        assertFalse(iterator.hasNext());
        assertThrows(NoSuchElementException.class, () -> iterator.next(), message);
    }
}
