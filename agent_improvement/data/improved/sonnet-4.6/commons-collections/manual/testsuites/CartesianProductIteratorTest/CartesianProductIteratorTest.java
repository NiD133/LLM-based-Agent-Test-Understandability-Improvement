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

    // Three disjoint character sets used to build Cartesian products in most tests.
    private List<Character> letters;  // ['A', 'B', 'C'] – size 3
    private List<Character> numbers;  // ['1', '2', '3'] – size 3
    private List<Character> symbols;  // ['!', '?']      – size 2
    private List<Character> emptyList;

    @BeforeEach
    public void setUp() {
        letters   = Arrays.asList('A', 'B', 'C');
        numbers   = Arrays.asList('1', '2', '3');
        symbols   = Arrays.asList('!', '?');
        emptyList = Collections.emptyList();
    }

    // -----------------------------------------------------------------------
    // AbstractIteratorTest contract
    // -----------------------------------------------------------------------

    @Override
    public CartesianProductIterator<Character> makeEmptyIterator() {
        return new CartesianProductIterator<>();
    }

    /** Returns an iterator over letters × numbers × symbols (3 × 3 × 2 = 18 tuples). */
    @Override
    public CartesianProductIterator<Character> makeObject() {
        return new CartesianProductIterator<>(letters, numbers, symbols);
    }

    @Override
    public boolean supportsRemove() {
        return false;
    }

    // -----------------------------------------------------------------------
    // Helper
    // -----------------------------------------------------------------------

    /**
     * Drains {@code it} into a list of tuples (each tuple converted to a
     * {@code Character[]}), then asserts that calling {@code next()} after
     * exhaustion throws {@link NoSuchElementException}.
     *
     * @param it the iterator to drain
     * @return all tuples produced by the iterator, in encounter order
     */
    private List<Character[]> collectAllTuples(final CartesianProductIterator<Character> it) {
        final List<Character[]> tuples = new ArrayList<>();
        while (it.hasNext()) {
            tuples.add(it.next().toArray(new Character[0]));
        }
        assertThrows(NoSuchElementException.class, it::next);
        return tuples;
    }

    // -----------------------------------------------------------------------
    // Tests
    // -----------------------------------------------------------------------

    /** When one input iterable is empty, hasNext() must be false immediately. */
    @Test
    void testEmptyCollection() {
        final CartesianProductIterator<Character> it =
                new CartesianProductIterator<>(letters, Collections.emptyList());
        assertFalse(it.hasNext());
        assertThrows(NoSuchElementException.class, it::next);
    }

    /** All 18 tuples (3 × 3 × 2) must be produced in lexicographic order. */
    @Test
    void testExhaustivity() {
        final List<Character[]> actualTuples = collectAllTuples(makeObject());

        assertEquals(18, actualTuples.size()); // 3 letters × 3 numbers × 2 symbols

        final Iterator<Character[]> actual = actualTuples.iterator();
        for (final Character a : letters) {
            for (final Character b : numbers) {
                for (final Character c : symbols) {
                    assertArrayEquals(new Character[]{a, b, c}, actual.next());
                }
            }
        }
    }

    /** No tuples are produced when all input lists are empty. */
    @Test
    void testExhaustivityWithAllEmptyLists() {
        final CartesianProductIterator<Character> it =
                new CartesianProductIterator<>(emptyList, emptyList, emptyList);
        assertEquals(0, collectAllTuples(it).size());
    }

    /** No tuples are produced when the first input list is empty. */
    @Test
    void testExhaustivityWithEmptyFirstList() {
        final CartesianProductIterator<Character> it =
                new CartesianProductIterator<>(emptyList, numbers, symbols);
        assertEquals(0, collectAllTuples(it).size());
    }

    /** No tuples are produced when the last input list is empty. */
    @Test
    void testExhaustivityWithEmptyLastList() {
        final CartesianProductIterator<Character> it =
                new CartesianProductIterator<>(letters, numbers, emptyList);
        assertEquals(0, collectAllTuples(it).size());
    }

    /** No tuples are produced when a middle input list is empty. */
    @Test
    void testExhaustivityWithEmptyList() {
        final CartesianProductIterator<Character> it =
                new CartesianProductIterator<>(letters, emptyList, symbols);
        assertEquals(0, collectAllTuples(it).size());
    }

    /** Passing the same list for all dimensions must yield 3³ = 27 tuples in order. */
    @Test
    void testExhaustivityWithSameList() {
        final List<Character[]> actualTuples = collectAllTuples(
                new CartesianProductIterator<>(letters, letters, letters));

        assertEquals(27, actualTuples.size()); // 3³ = 27

        final Iterator<Character[]> actual = actualTuples.iterator();
        for (final Character a : letters) {
            for (final Character b : letters) {
                for (final Character c : letters) {
                    assertArrayEquals(new Character[]{a, b, c}, actual.next());
                }
            }
        }
    }

    /** {@code forEachRemaining} must deliver all 18 tuples to the consumer. */
    @Override
    @Test
    void testForEachRemaining() {
        final List<Character[]> actualTuples = new ArrayList<>();
        makeObject().forEachRemaining(tuple -> actualTuples.add(tuple.toArray(new Character[0])));

        assertEquals(18, actualTuples.size()); // 3 letters × 3 numbers × 2 symbols

        final Iterator<Character[]> actual = actualTuples.iterator();
        for (final Character a : letters) {
            for (final Character b : numbers) {
                for (final Character c : symbols) {
                    assertArrayEquals(new Character[]{a, b, c}, actual.next());
                }
            }
        }
    }

    /** {@code remove()} is not supported and must throw. */
    @Test
    void testRemoveThrows() {
        final CartesianProductIterator<Character> it = makeObject();
        assertThrows(UnsupportedOperationException.class, it::remove);
    }
}
