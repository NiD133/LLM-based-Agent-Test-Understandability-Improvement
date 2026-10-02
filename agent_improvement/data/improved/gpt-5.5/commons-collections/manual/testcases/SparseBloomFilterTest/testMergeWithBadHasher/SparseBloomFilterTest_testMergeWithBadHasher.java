package org.apache.commons.collections4.bloomfilter;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

public class SparseBloomFilterTest_testMergeWithBadHasher {

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

    private SparseBloomFilter createEmptyFilter(final Shape shape) {
        return new SparseBloomFilter(shape);
    }

    private Shape getTestShape() {
        return Shape.fromKM(17, 72);
    }

    private void assertBadHasherRejected(final int badIndex) {
        final BloomFilter<?> filter = createEmptyFilter(getTestShape());

        assertThrows(IllegalArgumentException.class, () -> filter.merge(new BadHasher(badIndex)));
    }

    @Test
    void testMergeWithBadHasher() {
        assertBadHasherRejected(getTestShape().getNumberOfBits());
        assertBadHasherRejected(-1);
    }
}
