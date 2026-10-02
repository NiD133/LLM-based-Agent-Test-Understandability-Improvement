package org.apache.commons.collections4.bloomfilter;

import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.Test;

public class SimpleBloomFilterTest_testMergeWithBadHasher {

    /**
     * A hasher that always produces a single fixed index, regardless of the filter
     * shape. Used to verify that merging an out-of-range index throws an exception.
     */
    static class BadHasher implements Hasher {
        private final IndexExtractor extractor;

        BadHasher(final int value) {
            this.extractor = IndexExtractor.fromIndexArray(value);
        }

        @Override
        public IndexExtractor indices(final Shape shape) {
            return extractor;
        }
    }

    private Shape getTestShape() {
        // k=17 hash functions, m=72 bits
        return Shape.fromKM(17, 72);
    }

    private SimpleBloomFilter createEmptyFilter(final Shape shape) {
        return new SimpleBloomFilter(shape);
    }

    @Test
    void testMergeWithBadHasher() {
        final Shape shape = getTestShape();
        final int numberOfBits = shape.getNumberOfBits();

        // An index equal to numberOfBits is one past the valid range [0, numberOfBits)
        final BloomFilter filterWithOversizedIndex = createEmptyFilter(shape);
        assertThrows(IllegalArgumentException.class,
                () -> filterWithOversizedIndex.merge(new BadHasher(numberOfBits)));

        // A negative index is always out of the valid range
        final BloomFilter filterWithNegativeIndex = createEmptyFilter(shape);
        assertThrows(IllegalArgumentException.class,
                () -> filterWithNegativeIndex.merge(new BadHasher(-1)));
    }
}
