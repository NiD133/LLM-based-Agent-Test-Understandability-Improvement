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
package org.apache.commons.collections4.bloomfilter;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

/**
 * Tests for the {@link SparseBloomFilter}.
 *
 * <p>The test shape used throughout is {@code Shape.fromKM(17, 72)}, which spans
 * two 64-bit bitmap longs (bits 0-63 in long[0], bits 64-71 in long[1]).
 */
class SparseBloomFilterTest extends AbstractBloomFilterTest<SparseBloomFilter> {

    /**
     * Indices that span both bitmap longs: 1-9 fall in long[0], 65-71 fall in long[1].
     * Used to exercise the multi-bitmap code path in {@code processBitMaps}.
     */
    private static final int[] INDICES_SPANNING_TWO_BITMAPS =
            {1, 2, 3, 4, 5, 6, 7, 8, 9, 65, 66, 67, 68, 69, 70, 71};

    /**
     * Indices that all fall within long[0], leaving long[1] as an all-zero trailing block.
     * Used to verify that {@code processBitMaps} emits the mandatory trailing zero block.
     */
    private static final int[] INDICES_IN_FIRST_BITMAP_ONLY = {1, 2, 3, 4};

    @Override
    protected SparseBloomFilter createEmptyFilter(final Shape shape) {
        return new SparseBloomFilter(shape);
    }

    /**
     * Verifies that {@code processBitMaps} stops immediately when the predicate returns
     * {@code false} on the very first bitmap block, and that exactly one block was visited.
     */
    @Test
    void testProcessBitMaps_earlyExitOnFirstBlock() {
        final BloomFilter bf = createFilter(getTestShape(),
                IndexExtractor.fromIndexArray(INDICES_SPANNING_TWO_BITMAPS));

        final int[] callCount = new int[1];
        assertFalse(bf.processBitMaps(bitmap -> {
            callCount[0]++;
            return false;
        }));
        assertEquals(1, callCount[0]);
    }

    /**
     * Verifies that {@code processBitMaps} stops at the bitmap-block boundary when the
     * predicate returns {@code false} after accepting the first block.
     * Exactly one invocation should have occurred (the accepted first block).
     */
    @Test
    void testProcessBitMaps_earlyExitAtBitmapBoundary() {
        final BloomFilter bf = createFilter(getTestShape(),
                IndexExtractor.fromIndexArray(INDICES_SPANNING_TWO_BITMAPS));

        final int[] callCount = new int[1];
        assertFalse(bf.processBitMaps(bitmap -> {
            final boolean acceptMore = callCount[0] == 0;
            if (acceptMore) {
                callCount[0]++;
            }
            return acceptMore;
        }));
        assertEquals(1, callCount[0]);
    }

    /**
     * Verifies that when all set bits fall within long[0], {@code processBitMaps} still
     * delivers the required trailing all-zero block for long[1], for a total of two calls.
     */
    @Test
    void testProcessBitMaps_trailingZeroBlockEmittedForSingleBitmapFilter() {
        final BloomFilter bf = createFilter(getTestShape(),
                IndexExtractor.fromIndexArray(INDICES_IN_FIRST_BITMAP_ONLY));

        final int[] callCount = new int[1];
        assertTrue(bf.processBitMaps(bitmap -> {
            callCount[0]++;
            return true;
        }));
        assertEquals(2, callCount[0]);
    }

    /**
     * Verifies that when all set bits fall within long[0] and the predicate rejects
     * the trailing zero block, {@code processBitMaps} returns {@code false} after
     * exactly one accepted invocation (the first non-zero block was accepted; the
     * trailing zero block caused the early exit).
     */
    @Test
    void testProcessBitMaps_earlyExitOnTrailingZeroBlock() {
        final BloomFilter bf = createFilter(getTestShape(),
                IndexExtractor.fromIndexArray(INDICES_IN_FIRST_BITMAP_ONLY));

        final int[] callCount = new int[1];
        assertFalse(bf.processBitMaps(bitmap -> {
            final boolean acceptMore = callCount[0] == 0;
            if (acceptMore) {
                callCount[0]++;
            }
            return acceptMore;
        }));
        assertEquals(1, callCount[0]);
    }

    /**
     * Verifies that merging a non-sparse {@link SimpleBloomFilter} into a sparse filter
     * produces a result with identical bitmap contents to the source filter.
     */
    @Test
    void testMergeFromNonSparseFilter_preservesBitmapContents() {
        final BloomFilter sparseFilter = createEmptyFilter(getTestShape());
        final BloomFilter simpleFilter = new SimpleBloomFilter(getTestShape());
        simpleFilter.merge(TestingHashers.FROM1);

        sparseFilter.merge(simpleFilter);

        assertTrue(simpleFilter.processBitMapPairs(sparseFilter, (x, y) -> x == y));
    }
}
