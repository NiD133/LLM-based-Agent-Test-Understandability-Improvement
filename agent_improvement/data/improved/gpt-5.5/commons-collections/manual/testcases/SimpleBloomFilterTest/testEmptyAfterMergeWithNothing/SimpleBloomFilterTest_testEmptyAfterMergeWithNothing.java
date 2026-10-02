package org.apache.commons.collections4.bloomfilter;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class SimpleBloomFilterTest_testEmptyAfterMergeWithNothing {

    protected SimpleBloomFilter createEmptyFilter(final Shape shape) {
        return new SimpleBloomFilter(shape);
    }

    protected Shape getTestShape() {
        return Shape.fromKM(17, 72);
    }

    @Test
    void testEmptyAfterMergeWithNothing() {
        final BloomFilter bf = createEmptyFilter(getTestShape());

        bf.merge(IndexExtractor.fromIndexArray());

        assertTrue(bf.isEmpty());
    }
}
