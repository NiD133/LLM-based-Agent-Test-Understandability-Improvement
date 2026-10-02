package org.apache.commons.collections4.bloomfilter;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

/**
 * Tests for {@link SimpleBloomFilter#estimateN()}, which estimates the number
 * of distinct items that have been merged into a filter based on its cardinality
 * and the filter's shape parameters.
 */
public class SimpleBloomFilterTest_testEstimateN {

    /**
     * The test shape uses k=17 hash functions and m=72 bits, which gives
     * enough resolution to distinguish small item counts in the assertions below.
     */
    private static final Shape TEST_SHAPE = Shape.fromKM(17, 72);

    /** Creates an empty filter with the standard test shape. */
    private SimpleBloomFilter createEmptyFilter(final Shape shape) {
        return new SimpleBloomFilter(shape);
    }

    /** Creates a filter with the standard test shape pre-populated from a hasher. */
    private SimpleBloomFilter createFilter(final Shape shape, final Hasher hasher) {
        final SimpleBloomFilter filter = createEmptyFilter(shape);
        filter.merge(hasher);
        return filter;
    }

    /**
     * Verifies that {@code estimateN()} returns the correct rounded estimate as
     * items are incrementally merged, and returns {@code Integer.MAX_VALUE} when
     * every bit in the filter is set (i.e. the filter is saturated).
     *
     * <p>The test uses {@link IncrementingHasher} instances whose starting indices
     * are chosen so that:
     * <ul>
     *   <li>Merging FROM1 (starts at bit 1) and IncrementingHasher(4,1) (starts at
     *       bit 4) causes enough bit overlap that the estimate stays at 1 instead of
     *       rising to 2.</li>
     *   <li>Adding IncrementingHasher(17,1) (starts at bit 17) finally separates
     *       the occupied bit ranges enough for the estimate to reach 3.</li>
     * </ul>
     */
    @Test
    final void testEstimateN() {
        // Single item merged: estimate should be 1.
        BloomFilter filter1 = createFilter(TEST_SHAPE, TestingHashers.FROM1);
        assertEquals(1, filter1.estimateN());

        // Merging a second hasher whose bits heavily overlap with FROM1 does not
        // raise the estimate; it remains 1 because the cardinality increase is
        // too small relative to the shape's probability model.
        filter1.merge(new IncrementingHasher(4, 1));
        assertEquals(1, filter1.estimateN());

        // Adding a third hasher with a distinct bit range finally pushes the
        // cardinality high enough for the estimate to round up to 3.
        filter1.merge(new IncrementingHasher(17, 1));
        assertEquals(3, filter1.estimateN());

        // A fully saturated filter (all bits set) cannot be distinguished from
        // infinitely many items; the method returns Integer.MAX_VALUE.
        filter1 = TestingHashers.populateEntireFilter(createEmptyFilter(TEST_SHAPE));
        assertEquals(Integer.MAX_VALUE, filter1.estimateN());
    }
}
