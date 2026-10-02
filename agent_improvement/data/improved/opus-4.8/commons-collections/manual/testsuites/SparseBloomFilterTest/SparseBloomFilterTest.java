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
 * <p>A {@link SparseBloomFilter} stores its enabled bits as a sorted set of indices and only
 * materializes 64-bit "bit map" words on demand via {@link BloomFilter#processBitMaps}. Each bit
 * map word covers a contiguous block of 64 bit positions (block 0 covers bits 0-63, block 1 covers
 * bits 64-127, and so on). The edge cases below exercise how {@code processBitMaps} walks those
 * blocks and how it honours a consumer that short-circuits by returning {@code false}.</p>
 */
class SparseBloomFilterTest extends AbstractBloomFilterTest<SparseBloomFilter> {

    /**
     * Indices that populate two bit map blocks: 1-9 fall in block 0 (bits 0-63) and 65-71 fall in
     * block 1 (bits 64-127). This guarantees {@code processBitMaps} must emit two words.
     */
    private static final int[] INDICES_SPANNING_TWO_BLOCKS = {1, 2, 3, 4, 5, 6, 7, 8, 9, 65, 66, 67, 68, 69, 70, 71};

    /**
     * Indices that all fall within block 0 (bits 0-63). The filter's shape still spans two blocks,
     * so {@code processBitMaps} must emit a trailing zero word for the empty second block.
     */
    private static final int[] INDICES_IN_FIRST_BLOCK_ONLY = {1, 2, 3, 4};

    @Override
    protected SparseBloomFilter createEmptyFilter(final Shape shape) {
        return new SparseBloomFilter(shape);
    }

    /**
     * Builds a sparse filter seeded with the given indices.
     */
    private BloomFilter newFilter(final int[] indices) {
        return createFilter(getTestShape(), IndexExtractor.fromIndexArray(indices));
    }

    @Test
    void testBitMapExtractorEdgeCases() {
        // Counts how many bit map words processBitMaps offered to the consumer. A single-element
        // array is used so the count can be mutated from inside the lambda.
        final int[] wordsOffered = new int[1];

        // Case 1: indices span two blocks, but the consumer rejects the very first word.
        // Processing must stop immediately after offering that one word.
        BloomFilter bf = newFilter(INDICES_SPANNING_TWO_BLOCKS);
        wordsOffered[0] = 0;
        assertFalse(bf.processBitMaps(word -> {
            wordsOffered[0]++;
            return false;
        }));
        assertEquals(1, wordsOffered[0]);

        // Case 2: indices span two blocks; the consumer accepts the first word and rejects the
        // second (the boundary between blocks). Processing stops after the second word is offered,
        // so the counter is incremented exactly once (only on the accepted first word).
        bf = newFilter(INDICES_SPANNING_TWO_BLOCKS);
        wordsOffered[0] = 0;
        assertFalse(bf.processBitMaps(word -> {
            final boolean acceptThisWord = wordsOffered[0] == 0;
            if (acceptThisWord) {
                wordsOffered[0]++;
            }
            return acceptThisWord;
        }));
        assertEquals(1, wordsOffered[0]);

        // Case 3: all indices fit in block 0, yet the shape spans two blocks. The consumer accepts
        // every word, so it is offered the populated first word plus a trailing zero word for the
        // empty second block: two words in total.
        bf = newFilter(INDICES_IN_FIRST_BLOCK_ONLY);
        wordsOffered[0] = 0;
        assertTrue(bf.processBitMaps(word -> {
            wordsOffered[0]++;
            return true;
        }));
        assertEquals(2, wordsOffered[0]);

        // Case 4: all indices fit in block 0; the consumer accepts the first word and rejects the
        // trailing zero word for the second block. Processing stops, and the counter reflects only
        // the accepted first word.
        bf = newFilter(INDICES_IN_FIRST_BLOCK_ONLY);
        wordsOffered[0] = 0;
        assertFalse(bf.processBitMaps(word -> {
            final boolean acceptThisWord = wordsOffered[0] == 0;
            if (acceptThisWord) {
                wordsOffered[0]++;
            }
            return acceptThisWord;
        }));
        assertEquals(1, wordsOffered[0]);
    }

    @Test
    void testBloomFilterBasedMergeEdgeCases() {
        // Merge a (dense) SimpleBloomFilter into a sparse filter, then confirm both filters expose
        // identical bit map words. processBitMapPairs walks the two filters in lock-step and the
        // predicate asserts each word pair is equal.
        final BloomFilter sparseFilter = createEmptyFilter(getTestShape());
        final BloomFilter denseFilter = new SimpleBloomFilter(getTestShape());
        denseFilter.merge(TestingHashers.FROM1);
        sparseFilter.merge(denseFilter);
        assertTrue(denseFilter.processBitMapPairs(sparseFilter, (sparseWord, denseWord) -> sparseWord == denseWord));
    }
}
