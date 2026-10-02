/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package org.apache.commons.compress.harmony.unpack200;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class SegmentConstantPoolArrayCacheTest {

    private SegmentConstantPoolArrayCache cache;

    @BeforeEach
    void setUp() {
        cache = new SegmentConstantPoolArrayCache();
    }

    /**
     * A key that appears exactly once in a simple array should return a
     * single-element index list pointing to the correct position.
     */
    @Test
    void testSingleSimpleArray_uniqueKey_returnsOneIndex() {
        final String[] array = { "Zero", "One", "Two", "Three", "Four" };

        final List<Integer> indexes = cache.indexesForArrayKey(array, "Three");

        assertEquals(1, indexes.size(), "Exactly one occurrence of 'Three' expected");
        assertEquals(3, indexes.get(0).intValue(), "'Three' is at index 3");
    }

    /**
     * A key that appears at multiple positions in an array should return all
     * of those positions in ascending order.
     * The element value "OneThreeFour" is chosen to reflect its positions (1, 3, 4).
     */
    @Test
    void testSingleArray_keyWithMultipleOccurrences_returnsAllIndexes() {
        final String[] array = { "Zero", "OneThreeFour", "Two", "OneThreeFour", "OneThreeFour" };

        final List<Integer> indexes = cache.indexesForArrayKey(array, "OneThreeFour");

        assertEquals(3, indexes.size(), "Three occurrences expected");
        assertEquals(1, indexes.get(0).intValue(), "First occurrence at index 1");
        assertEquals(3, indexes.get(1).intValue(), "Second occurrence at index 3");
        assertEquals(4, indexes.get(2).intValue(), "Third occurrence at index 4");
    }

    /**
     * The cache must handle multiple distinct arrays independently: lookups on one
     * array must not affect or corrupt lookups on another, and both arrays must
     * reflect their own element distributions after repeated queries.
     *
     * This test also verifies:
     * - looking up a key that appears only once after the cache is warm
     * - looking up a key that is absent returns an empty list
     */
    @Test
    void testMultipleArrays_independentCaching_returnsCorrectIndexesPerArray() {
        // arrayOne: "Shared" is at positions 1, 3, 4; "Two" is at position 2
        final String[] arrayOne = { "Zero", "Shared", "Two", "Shared", "Shared" };
        // arrayTwo: "Shared" is at positions 0, 2, 3, 4
        final String[] arrayTwo = { "Shared", "One", "Shared", "Shared", "Shared" };

        // Phase 1: prime the internal cache for both arrays by querying "Shared" in each.
        cache.indexesForArrayKey(arrayOne, "Shared");
        cache.indexesForArrayKey(arrayTwo, "Shared");

        // Phase 2: query different keys to exercise cached vs. fresh lookup paths.
        // Switching to a different key in arrayOne forces a new lookup in the already-cached array.
        final List<Integer> indexesOfTwoInArrayOne = cache.indexesForArrayKey(arrayOne, "Two");
        // Re-querying the same key in arrayTwo exercises the "last result" fast path in the cache.
        final List<Integer> indexesOfSharedInArrayTwo = cache.indexesForArrayKey(arrayTwo, "Shared");

        // "Two" appears exactly once in arrayOne, at index 2.
        assertEquals(1, indexesOfTwoInArrayOne.size());
        assertEquals(2, indexesOfTwoInArrayOne.get(0).intValue());

        // Phase 3: look up "Shared" in arrayOne to verify the cache was not corrupted
        // by the previous cross-array query sequence.
        final List<Integer> indexesOfSharedInArrayOne = cache.indexesForArrayKey(arrayOne, "Shared");
        assertEquals(3, indexesOfSharedInArrayOne.size());
        assertEquals(1, indexesOfSharedInArrayOne.get(0).intValue());
        assertEquals(3, indexesOfSharedInArrayOne.get(1).intValue());
        assertEquals(4, indexesOfSharedInArrayOne.get(2).intValue());

        // "Shared" appears at four positions in arrayTwo: 0, 2, 3, 4.
        assertEquals(4, indexesOfSharedInArrayTwo.size());
        assertEquals(0, indexesOfSharedInArrayTwo.get(0).intValue());
        assertEquals(2, indexesOfSharedInArrayTwo.get(1).intValue());
        assertEquals(3, indexesOfSharedInArrayTwo.get(2).intValue());
        assertEquals(4, indexesOfSharedInArrayTwo.get(3).intValue());

        // A key that is absent in the array must return an empty list, not null.
        final List<Integer> indexesOfAbsentKey = cache.indexesForArrayKey(arrayOne, "Not found");
        assertEquals(0, indexesOfAbsentKey.size());
    }
}
