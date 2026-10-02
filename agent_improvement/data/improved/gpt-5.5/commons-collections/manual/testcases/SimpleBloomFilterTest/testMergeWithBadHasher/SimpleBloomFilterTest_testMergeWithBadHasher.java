package org.apache.commons.collections4.bloomfilter;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

public class SimpleBloomFilterTest_testMergeWithBadHasher {

    private static final class BadHasher implements Hasher {
        private final int index;

        private BadHasher(final int index) {
            this.index = index;
        }

        @Override
        public IndexExtractor indices(final Shape shape) {
            return IndexExtractor.fromIndexArray(index);
        }
    }

    protected SimpleBloomFilter createEmptyFilter(final Shape shape) {
        return new SimpleBloomFilter(shape);
    }

    protected Shape getTestShape() {
        return Shape.fromKM(17, 72);
    }

    @Test
    void testMergeWithBadHasher() {
        final BloomFilter f = createEmptyFilter(getTestShape());
        assertThrows(IllegalArgumentException.class, () -> f.merge(new BadHasher(getTestShape().getNumberOfBits())));

        final BloomFilter f2 = createEmptyFilter(getTestShape());
        assertThrows(IllegalArgumentException.class, () -> f2.merge(new BadHasher(-1)));
    }
}
