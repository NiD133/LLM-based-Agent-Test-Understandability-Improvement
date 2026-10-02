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

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Test class for {@link CartesianProductIterator}.
 */
class CartesianProductIteratorTest extends AbstractIteratorTest<List<Character>> {

    /** Three letters: 'A', 'B', 'C'. */
    private List<Character> letters;

    /** Three digits: '1', '2', '3'. */
    private List<Character> numbers;

    /** Two symbols: '!', '?'. */
    private List<Character> symbols;

    /** An always-empty list, reused to model "missing" input dimensions. */
    private List<Character> emptyList;

    @Override
    public CartesianProductIterator<Character> makeEmptyIterator() {
        return new CartesianProductIterator<>();
    }

    @Override
    public CartesianProductIterator<Character> makeObject() {
        return new CartesianProductIterator<>(letters, numbers, symbols);
    }

    @BeforeEach
    public void setUp() {
        letters = Arrays.asList('A', 'B', 'C');
        numbers = Arrays.asList('1', '2', '3');
        symbols = Arrays.asList('!', '?');
        emptyList = Collections.emptyList();
    }

    @Override
    public boolean supportsRemove() {
        return false;
    }

    /**
     * Drains the iterator, collecting every tuple as a {@code Character[]} in
     * iteration order, then asserts the iterator is exhausted by checking that a
     * further call to {@code next()} throws {@link NoSuchElementException}.
     *
     * @param it the iterator to consume completely
     * @return the tuples produced, in the order they were returned
     */
    private List<Character[]> collectAllTuples(final CartesianProductIterator<Character> it) {
        final List<Character[]> tuples = new ArrayList<>();
        while (it.hasNext()) {
            final List<Character> tuple = it.next();
            tuples.add(tuple.toArray(new Character[0]));
        }
        assertThrows(NoSuchElementException.class, it::next);
        return tuples;
    }

    /**
     * Asserts that {@code actualTuples} contains exactly the Cartesian product
     * {@code first x second x third}, in the nested-loop order documented by
     * {@link CartesianProductIterator} (the last list varies fastest).
     *
     * @param actualTuples the tuples produced by the iterator
     * @param first        the first (slowest-varying) dimension
     * @param second       the second dimension
     * @param third        the third (fastest-varying) dimension
     */
    private void assertMatchesCartesianProduct(final List<Character[]> actualTuples,
            final List<Character> first, final List<Character> second, final List<Character> third) {
        assertEquals(first.size() * second.size() * third.size(), actualTuples.size());
        final Iterator<Character[]> actual = actualTuples.iterator();
        for (final Character a : first) {
            for (final Character b : second) {
                for (final Character c : third) {
                    assertArrayEquals(new Character[] {a, b, c}, actual.next());
                }
            }
        }
    }

    @Test
    void testEmptyCollection() {
        final CartesianProductIterator<Character> it = new CartesianProductIterator<>(letters, Collections.emptyList());
        assertFalse(it.hasNext());
        assertThrows(NoSuchElementException.class, it::next);
    }

    /**
     * Every tuple of {@code letters x numbers x symbols} is returned, in order.
     */
    @Test
    void testExhaustivity() {
        final List<Character[]> tuples = collectAllTuples(makeObject());
        assertMatchesCartesianProduct(tuples, letters, numbers, symbols);
    }

    /**
     * No tuples are returned when all input lists are empty.
     */
    @Test
    void testExhaustivityWithAllEmptyLists() {
        final List<Character[]> tuples =
                collectAllTuples(new CartesianProductIterator<>(emptyList, emptyList, emptyList));
        assertEquals(0, tuples.size());
    }

    /**
     * No tuples are returned when the first input list is empty.
     */
    @Test
    void testExhaustivityWithEmptyFirstList() {
        final List<Character[]> tuples =
                collectAllTuples(new CartesianProductIterator<>(emptyList, numbers, symbols));
        assertEquals(0, tuples.size());
    }

    /**
     * No tuples are returned when the last input list is empty.
     */
    @Test
    void testExhaustivityWithEmptyLastList() {
        final List<Character[]> tuples =
                collectAllTuples(new CartesianProductIterator<>(letters, numbers, emptyList));
        assertEquals(0, tuples.size());
    }

    /**
     * No tuples are returned when any input list (here the middle one) is empty.
     */
    @Test
    void testExhaustivityWithEmptyList() {
        final List<Character[]> tuples =
                collectAllTuples(new CartesianProductIterator<>(letters, emptyList, symbols));
        assertEquals(0, tuples.size());
    }

    /**
     * All tuples are returned when the same list is passed for every dimension.
     */
    @Test
    void testExhaustivityWithSameList() {
        final List<Character[]> tuples =
                collectAllTuples(new CartesianProductIterator<>(letters, letters, letters));
        assertMatchesCartesianProduct(tuples, letters, letters, letters);
    }

    /**
     * {@code forEachRemaining} hands every tuple to the consumer, in order.
     */
    @Override
    @Test
    void testForEachRemaining() {
        final List<Character[]> tuples = new ArrayList<>();
        final CartesianProductIterator<Character> it = makeObject();
        it.forEachRemaining(tuple -> tuples.add(tuple.toArray(new Character[0])));
        assertMatchesCartesianProduct(tuples, letters, numbers, symbols);
    }

    @Test
    void testRemoveThrows() {
        final CartesianProductIterator<Character> it = makeObject();
        assertThrows(UnsupportedOperationException.class, it::remove);
    }
}
