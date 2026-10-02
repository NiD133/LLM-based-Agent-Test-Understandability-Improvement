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

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;

/**
 * Tests {@link SegmentConstantPoolArrayCache}, which maps a value to every index at which it appears in a String array.
 *
 * <p>{@code indexesForArrayKey(array, key)} returns the (ascending) list of indexes whose element equals {@code key},
 * or an empty list when the key is absent.</p>
 */
class SegmentConstantPoolArrayCacheTest {

    /** Convenience: assert that the cache reports exactly {@code expectedIndexes} for {@code key} in {@code array}. */
    private static void assertIndexes(final SegmentConstantPoolArrayCache cache, final String[] array, final String key,
            final Integer... expectedIndexes) {
        assertEquals(Arrays.asList(expectedIndexes), cache.indexesForArrayKey(array, key));
    }

    /**
     * Exercises the cache with two distinct arrays queried in an interleaved order, so that the lookups for one array
     * are separated by lookups for the other. This confirms the cache keeps each array's index map independent and
     * keeps returning correct results no matter the access pattern.
     */
    @Test
    void testMultipleArrayMultipleHit() {
        final SegmentConstantPoolArrayCache arrayCache = new SegmentConstantPoolArrayCache();

        // "Shared" appears at indexes 1, 3, 4 in arrayOne and 0, 2, 3, 4 in arrayTwo.
        final String[] arrayOne = { "Zero", "Shared", "Two", "Shared", "Shared" };
        final String[] arrayTwo = { "Shared", "One", "Shared", "Shared", "Shared" };

        // First lookups populate the cache for each array; the interleaving keeps both arrays "live".
        arrayCache.indexesForArrayKey(arrayOne, "Shared");
        arrayCache.indexesForArrayKey(arrayTwo, "Shared");

        // "Two" is unique in arrayOne -> only index 2.
        assertIndexes(arrayCache, arrayOne, "Two", 2);

        // "Shared" in arrayOne -> indexes 1, 3, 4.
        assertIndexes(arrayCache, arrayOne, "Shared", 1, 3, 4);

        // "Shared" in arrayTwo -> indexes 0, 2, 3, 4.
        assertIndexes(arrayCache, arrayTwo, "Shared", 0, 2, 3, 4);

        // A value that never appears yields an empty list.
        assertIndexes(arrayCache, arrayOne, "Not found");
    }

    /**
     * A single array where the searched value occurs several times: the cache must return every matching index.
     */
    @Test
    void testSingleMultipleHitArray() {
        final SegmentConstantPoolArrayCache arrayCache = new SegmentConstantPoolArrayCache();
        final String[] array = { "Zero", "OneThreeFour", "Two", "OneThreeFour", "OneThreeFour" };

        // "OneThreeFour" appears at indexes 1, 3 and 4.
        assertIndexes(arrayCache, array, "OneThreeFour", 1, 3, 4);
    }

    /**
     * A single array of distinct values: the searched value occurs exactly once.
     */
    @Test
    void testSingleSimpleArray() {
        final SegmentConstantPoolArrayCache arrayCache = new SegmentConstantPoolArrayCache();
        final String[] array = { "Zero", "One", "Two", "Three", "Four" };

        // "Three" appears only at index 3.
        assertIndexes(arrayCache, array, "Three", 3);
    }

}
