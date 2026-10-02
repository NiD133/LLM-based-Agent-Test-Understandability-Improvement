package org.apache.commons.collections4.bloomfilter;

import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.Test;

/**
 * Verifies that {@link SparseBloomFilter#merge(Hasher)} rejects hashers that
 * produce indices outside the valid range {@code [0, numberOfBits)}.
 *
 * <p>The filter shape used throughout: k=17 hash functions, m=72 bits.
 */
public class SparseBloomFilterTest_testMergeWithBadHasher {

    /** Shape shared by all tests: 17 hash functions, 72-bit filter. */
    private static final Shape TEST_SHAPE = Shape.fromKM(17, 72);

    private SparseBloomFilter createEmptyFilter() {
        return new SparseBloomFilter(TEST_SHAPE);
    }

    /**
     * A {@link Hasher} that always produces a single, fixed index regardless of
     * the shape — used to inject out-of-range values into a filter under test.
     */
    private static final class BadHasher implements Hasher {
        private final int fixedIndex;

        BadHasher(final int fixedIndex) {
            this.fixedIndex = fixedIndex;
        }

        @Override
        public IndexExtractor indices(final Shape shape) {
            return IndexExtractor.fromIndexArray(fixedIndex);
        }
    }

    /**
     * Merging a hasher whose index equals {@code numberOfBits} (one past the last
     * valid bit) must throw {@link IllegalArgumentException}.
     */
    @Test
    void testMergeRejectsIndexEqualToNumberOfBits() {
        final int tooLargeIndex = TEST_SHAPE.getNumberOfBits(); // valid range is [0, numberOfBits-1]
        final SparseBloomFilter filter = createEmptyFilter();

        assertThrows(IllegalArgumentException.class,
                () -> filter.merge(new BadHasher(tooLargeIndex)));
    }

    /**
     * Merging a hasher whose index is {@code -1} (below zero) must throw
     * {@link IllegalArgumentException}.
     */
    @Test
    void testMergeRejectsNegativeIndex() {
        final SparseBloomFilter filter = createEmptyFilter();

        assertThrows(IllegalArgumentException.class,
                () -> filter.merge(new BadHasher(-1)));
    }
}
