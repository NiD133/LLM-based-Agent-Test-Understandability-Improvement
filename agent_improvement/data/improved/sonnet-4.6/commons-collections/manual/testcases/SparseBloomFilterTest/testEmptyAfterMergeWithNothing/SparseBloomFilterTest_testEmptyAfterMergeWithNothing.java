package org.apache.commons.collections4.bloomfilter;

import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

public class SparseBloomFilterTest_testEmptyAfterMergeWithNothing {

    private SparseBloomFilter createEmptyFilter(final Shape shape) {
        return new SparseBloomFilter(shape);
    }

    private Shape getTestShape() {
        return Shape.fromKM(17, 72);
    }

    /**
     * Verifies that a filter remains empty after merging an empty IndexExtractor.
     *
     * A SparseBloomFilter tracks set bits in a TreeSet. When merged with an
     * IndexExtractor that provides no indices, no bits are added, so the filter
     * must still report isEmpty() == true.
     */
    @Test
    void testEmptyAfterMergeWithNothing() {
        final BloomFilter bf = createEmptyFilter(getTestShape());
        bf.merge(IndexExtractor.fromIndexArray());
        assertTrue(bf.isEmpty());
    }
}
