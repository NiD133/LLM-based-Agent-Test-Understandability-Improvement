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

    private List<Character> letters;
    private List<Character> numbers;
    private List<Character> symbols;
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

    @Test
    void testEmptyCollection() {
        final CartesianProductIterator<Character> it = new CartesianProductIterator<>(letters, Collections.emptyList());
        assertFalse(it.hasNext());
        assertThrows(NoSuchElementException.class, it::next);
    }

    /**
     * test checking that all the tuples are returned
     */
    @Test
    void testExhaustivity() {
        final CartesianProductIterator<Character> it = makeObject();
        final List<Character[]> resultsList = collectRemainingTuples(it);

        assertThrows(NoSuchElementException.class, it::next);
        assertEquals(18, resultsList.size());
        assertContainsCartesianProductInIterationOrder(resultsList, letters, numbers, symbols);
    }

    /**
     * test checking that no tuples are returned when all the lists are empty
     */
    @Test
    void testExhaustivityWithAllEmptyLists() {
        final CartesianProductIterator<Character> it = new CartesianProductIterator<>(emptyList, emptyList, emptyList);
        final List<Character[]> resultsList = collectRemainingTuples(it);

        assertThrows(NoSuchElementException.class, it::next);
        assertEquals(0, resultsList.size());
    }

    /**
     * test checking that no tuples are returned when first of the lists is empty
     */
    @Test
    void testExhaustivityWithEmptyFirstList() {
        final CartesianProductIterator<Character> it = new CartesianProductIterator<>(emptyList, numbers, symbols);
        final List<Character[]> resultsList = collectRemainingTuples(it);

        assertThrows(NoSuchElementException.class, it::next);
        assertEquals(0, resultsList.size());
    }

    /**
     * test checking that no tuples are returned when last of the lists is empty
     */
    @Test
    void testExhaustivityWithEmptyLastList() {
        final CartesianProductIterator<Character> it = new CartesianProductIterator<>(letters, numbers, emptyList);
        final List<Character[]> resultsList = collectRemainingTuples(it);

        assertThrows(NoSuchElementException.class, it::next);
        assertEquals(0, resultsList.size());
    }

    /**
     * test checking that no tuples are returned when at least one of the lists is empty
     */
    @Test
    void testExhaustivityWithEmptyList() {
        final CartesianProductIterator<Character> it = new CartesianProductIterator<>(letters, emptyList, symbols);
        final List<Character[]> resultsList = collectRemainingTuples(it);

        assertThrows(NoSuchElementException.class, it::next);
        assertEquals(0, resultsList.size());
    }

    /**
     * test checking that all tuples are returned when same list is passed multiple times
     */
    @Test
    void testExhaustivityWithSameList() {
        final CartesianProductIterator<Character> it = new CartesianProductIterator<>(letters, letters, letters);
        final List<Character[]> resultsList = collectRemainingTuples(it);

        assertThrows(NoSuchElementException.class, it::next);
        assertEquals(27, resultsList.size());
        assertContainsCartesianProductInIterationOrder(resultsList, letters, letters, letters);
    }

    /**
     * test that all tuples are provided to consumer
     */
    @Override
    @Test
    void testForEachRemaining() {
        final List<Character[]> resultsList = new ArrayList<>();
        final CartesianProductIterator<Character> it = makeObject();

        it.forEachRemaining(tuple -> resultsList.add(tuple.toArray(new Character[0])));

        assertEquals(18, resultsList.size());
        assertContainsCartesianProductInIterationOrder(resultsList, letters, numbers, symbols);
    }

    @Test
    void testRemoveThrows() {
        final CartesianProductIterator<Character> it = makeObject();
        assertThrows(UnsupportedOperationException.class, it::remove);
    }

    private List<Character[]> collectRemainingTuples(final CartesianProductIterator<Character> it) {
        final List<Character[]> resultsList = new ArrayList<>();
        while (it.hasNext()) {
            final List<Character> tuple = it.next();
            resultsList.add(tuple.toArray(new Character[0]));
        }
        return resultsList;
    }

    private void assertContainsCartesianProductInIterationOrder(final List<Character[]> resultsList,
            final List<Character> firstList, final List<Character> secondList, final List<Character> thirdList) {
        final Iterator<Character[]> itResults = resultsList.iterator();
        for (final Character a : firstList) {
            for (final Character b : secondList) {
                for (final Character c : thirdList) {
                    assertArrayEquals(new Character[]{a, b, c}, itResults.next());
                }
            }
        }
    }
}
