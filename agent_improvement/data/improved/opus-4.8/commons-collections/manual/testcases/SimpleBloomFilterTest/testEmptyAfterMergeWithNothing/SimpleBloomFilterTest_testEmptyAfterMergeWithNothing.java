package org.apache.commons.collections4.bloomfilter;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class SimpleBloomFilterTest_testEmptyAfterMergeWithNothing {

    /** Shape used for the test: 17 hash functions (k) over 72 bits (m). */
    private static final Shape TEST_SHAPE = Shape.fromKM(17, 72);

    /**
     * Merging an empty IndexExtractor (no indices) must leave the filter empty.
     * This guards the case where the merge sets the internal cardinality to -1
     * yet no bits are actually enabled, so {@code isEmpty()} must still report true.
     */
    @Test
    void testEmptyAfterMergeWithNothing() {
        final BloomFilter bf = new SimpleBloomFilter(TEST_SHAPE);

        bf.merge(IndexExtractor.fromIndexArray());

        assertTrue(bf.isEmpty());
    }
}
