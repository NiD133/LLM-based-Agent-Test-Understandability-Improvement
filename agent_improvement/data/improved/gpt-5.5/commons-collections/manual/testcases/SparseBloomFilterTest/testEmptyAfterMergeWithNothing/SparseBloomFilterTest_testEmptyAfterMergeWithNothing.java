package org.apache.commons.collections4.bloomfilter;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class SparseBloomFilterTest_testEmptyAfterMergeWithNothing {

    private static final Shape TEST_SHAPE = Shape.fromKM(17, 72);

    protected SparseBloomFilter createEmptyFilter(final Shape shape) {
        return new SparseBloomFilter(shape);
    }

    protected Shape getTestShape() {
        return TEST_SHAPE;
    }

    @Test
    void testEmptyAfterMergeWithNothing() {
        final BloomFilter bf = createEmptyFilter(getTestShape());

        bf.merge(IndexExtractor.fromIndexArray());

        assertTrue(bf.isEmpty());
    }
}
