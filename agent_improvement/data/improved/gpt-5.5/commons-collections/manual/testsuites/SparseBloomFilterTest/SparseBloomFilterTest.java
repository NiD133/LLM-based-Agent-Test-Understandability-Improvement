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

import java.util.function.LongPredicate;

import org.junit.jupiter.api.Test;

/**
 * Tests for the {@link SparseBloomFilter}.
 */
class SparseBloomFilterTest extends AbstractBloomFilterTest<SparseBloomFilter> {
    private static final int[] INDICES_SPANNING_TWO_BITMAPS = {
        1, 2, 3, 4, 5, 6, 7, 8, 9, 65, 66, 67, 68, 69, 70, 71
    };

    private static final int[] INDICES_IN_FIRST_BITMAP = {1, 2, 3, 4};

    @Override
    protected SparseBloomFilter createEmptyFilter(final Shape shape) {
        return new SparseBloomFilter(shape);
    }

    @Test
    void testBitMapExtractorEdgeCases() {
        BloomFilter bf = createFilter(getTestShape(), IndexExtractor.fromIndexArray(INDICES_SPANNING_TWO_BITMAPS));

        final int[] passes = new int[1];
        assertFalse(bf.processBitMaps(stopAfterFirstBitmap(passes)));
        assertEquals(1, passes[0]);

        bf = createFilter(getTestShape(), IndexExtractor.fromIndexArray(INDICES_SPANNING_TWO_BITMAPS));
        passes[0] = 0;
        assertFalse(bf.processBitMaps(acceptOnlyFirstBitmap(passes)));
        assertEquals(1, passes[0]);

        bf = createFilter(getTestShape(), IndexExtractor.fromIndexArray(INDICES_IN_FIRST_BITMAP));
        passes[0] = 0;
        assertTrue(bf.processBitMaps(acceptEveryBitmap(passes)));
        assertEquals(2, passes[0]);

        bf = createFilter(getTestShape(), IndexExtractor.fromIndexArray(INDICES_IN_FIRST_BITMAP));
        passes[0] = 0;
        assertFalse(bf.processBitMaps(acceptOnlyFirstBitmap(passes)));
        assertEquals(1, passes[0]);
    }

    @Test
    void testBloomFilterBasedMergeEdgeCases() {
        final BloomFilter bf1 = createEmptyFilter(getTestShape());
        final BloomFilter bf2 = new SimpleBloomFilter(getTestShape());
        bf2.merge(TestingHashers.FROM1);
        bf1.merge(bf2);
        assertTrue(bf2.processBitMapPairs(bf1, (x, y) -> x == y));
    }

    private LongPredicate acceptEveryBitmap(final int[] passes) {
        return l -> {
            passes[0]++;
            return true;
        };
    }

    private LongPredicate acceptOnlyFirstBitmap(final int[] passes) {
        return l -> {
            final boolean result = passes[0] == 0;
            if (result) {
                passes[0]++;
            }
            return result;
        };
    }

    private LongPredicate stopAfterFirstBitmap(final int[] passes) {
        return l -> {
            passes[0]++;
            return false;
        };
    }
}
