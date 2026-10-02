package org.apache.commons.collections4.bloomfilter;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

/**
 * Tests that merging a {@link SparseBloomFilter} with a {@link Hasher} that yields
 * out-of-range indices is rejected.
 */
public class SparseBloomFilterTest_testMergeWithBadHasher {

    /**
     * The shape of the Bloom filters under test: 17 hash functions (k) over 72 bits (m).
     */
    private static final Shape TEST_SHAPE = Shape.fromKM(17, 72);

    /** Creates an empty filter using the test shape. */
    private SparseBloomFilter createEmptyFilter() {
        return new SparseBloomFilter(TEST_SHAPE);
    }

    /**
     * A {@link Hasher} that always yields a single, caller-supplied index, ignoring the
     * filter shape. Used to feed deliberately out-of-range indices into a merge.
     */
    private static final class BadHasher implements Hasher {

        private final IndexExtractor extractor;

        BadHasher(final int value) {
            this.extractor = IndexExtractor.fromIndexArray(value);
        }

        @Override
        public IndexExtractor indices(final Shape shape) {
            return extractor;
        }
    }

    @Test
    void testMergeWithBadHasher() {
        // A hasher that produces an index equal to the bit count is out of range
        // (valid indices are 0..numberOfBits-1), so merging must be rejected.
        final BloomFilter filterForTooLargeIndex = createEmptyFilter();
        final int tooLargeIndex = TEST_SHAPE.getNumberOfBits();
        assertThrows(IllegalArgumentException.class,
                () -> filterForTooLargeIndex.merge(new BadHasher(tooLargeIndex)));

        // A hasher that produces a negative index is also out of range.
        final BloomFilter filterForNegativeIndex = createEmptyFilter();
        assertThrows(IllegalArgumentException.class,
                () -> filterForNegativeIndex.merge(new BadHasher(-1)));
    }
}
