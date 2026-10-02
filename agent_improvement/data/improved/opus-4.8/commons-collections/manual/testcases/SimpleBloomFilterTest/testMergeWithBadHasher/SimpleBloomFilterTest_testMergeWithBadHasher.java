package org.apache.commons.collections4.bloomfilter;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

/**
 * Verifies that {@link SimpleBloomFilter#merge(Hasher)} rejects hashers that
 * produce out-of-range bit indices.
 */
public class SimpleBloomFilterTest_testMergeWithBadHasher {

    /** Shape used for testing: 17 hash functions (k) over 72 bits (m). */
    private static final Shape TEST_SHAPE = Shape.fromKM(17, 72);

    private SimpleBloomFilter createEmptyFilter() {
        return new SimpleBloomFilter(TEST_SHAPE);
    }

    /**
     * A {@link Hasher} that always yields a single, fixed bit index regardless of
     * the shape. Used here to feed out-of-range indices into a filter merge.
     */
    private static final class BadHasher implements Hasher {

        private final IndexExtractor fixedIndex;

        BadHasher(final int value) {
            this.fixedIndex = IndexExtractor.fromIndexArray(value);
        }

        @Override
        public IndexExtractor indices(final Shape shape) {
            return fixedIndex;
        }
    }

    @Test
    void testMergeWithBadHasher() {
        final int numberOfBits = TEST_SHAPE.getNumberOfBits();

        // A hasher whose index equals the bit count is one past the valid range [0, numberOfBits).
        final BloomFilter indexTooLarge = createEmptyFilter();
        assertThrows(IllegalArgumentException.class,
                () -> indexTooLarge.merge(new BadHasher(numberOfBits)));

        // A hasher producing a negative index is also outside the valid range.
        final BloomFilter indexNegative = createEmptyFilter();
        assertThrows(IllegalArgumentException.class,
                () -> indexNegative.merge(new BadHasher(-1)));
    }
}
