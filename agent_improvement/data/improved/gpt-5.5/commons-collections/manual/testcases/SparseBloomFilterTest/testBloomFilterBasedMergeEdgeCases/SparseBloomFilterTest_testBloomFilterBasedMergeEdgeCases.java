package org.apache.commons.collections4.bloomfilter;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class SparseBloomFilterTest_testBloomFilterBasedMergeEdgeCases {

    protected SparseBloomFilter createEmptyFilter(final Shape shape) {
        return new SparseBloomFilter(shape);
    }

    protected Shape getTestShape() {
        return Shape.fromKM(17, 72);
    }

    @Test
    void testBloomFilterBasedMergeEdgeCases() {
        final BloomFilter bf1 = createEmptyFilter(getTestShape());
        final BloomFilter bf2 = new SimpleBloomFilter(getTestShape());

        bf2.merge(TestingHashers.FROM1);
        bf1.merge(bf2);

        assertTrue(bf2.processBitMapPairs(bf1, (x, y) -> x == y));
    }
}
