package org.apache.commons.collections4.bloomfilter;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

/**
 * Tests for {@link SparseBloomFilter#estimateN()}, which estimates the number
 * of distinct items stored in a Bloom filter.  The estimate is derived from
 * the number of set bits (cardinality) and the filter's shape parameters
 * (k hash functions, m total bits) using the standard approximation formula.
 */
public class SparseBloomFilterTest_testEstimateN {

    /** Shape used across all tests: k=17 hash functions, m=72 bits. */
    private Shape getTestShape() {
        return Shape.fromKM(17, 72);
    }

    /** Creates an empty {@link SparseBloomFilter} with the given shape. */
    private SparseBloomFilter createEmptyFilter(final Shape shape) {
        return new SparseBloomFilter(shape);
    }

    /** Creates a {@link SparseBloomFilter} pre-populated via the given hasher. */
    private BloomFilter createFilter(final Shape shape, final Hasher hasher) {
        final SparseBloomFilter bf = createEmptyFilter(shape);
        bf.merge(hasher);
        return bf;
    }

    /**
     * Verifies that {@code estimateN()} returns the correct population estimate
     * as items are added to the filter, including the saturation sentinel.
     *
     * <p>Key behaviours exercised:
     * <ul>
     *   <li>A single item produces estimate 1.</li>
     *   <li>A second item whose bits heavily overlap with the first does not
     *       raise the estimate — the combined bit count is still consistent
     *       with one item according to the formula.</li>
     *   <li>A third item placed in a fresh bit region raises the estimate to 3.</li>
     *   <li>When all bits are set the filter is fully saturated and
     *       {@code estimateN()} returns {@link Integer#MAX_VALUE}.</li>
     * </ul>
     */
    @Test
    void testEstimateN() {
        final Shape shape = getTestShape();

        // One item inserted via FROM1; the 17 bits it sets map back to n=1.
        BloomFilter filter = createFilter(shape, TestingHashers.FROM1);
        assertEquals(1, filter.estimateN(),
                "A single-item filter should estimate n=1");

        // Merging a second item (IncrementingHasher starting at bit 4, step 1)
        // produces heavy bit overlap with the first item.  The cardinality
        // increase is too small for the formula to push the estimate above 1.
        filter.merge(new IncrementingHasher(4, 1));
        assertEquals(1, filter.estimateN(),
                "Heavy bit overlap with the first item keeps the estimate at 1");

        // A third item (starting at bit 17, step 1) occupies a less-overlapping
        // region; the additional set bits push the formula's result to 3.
        filter.merge(new IncrementingHasher(17, 1));
        assertEquals(3, filter.estimateN(),
                "Three items with sufficient bit spread should estimate n=3");

        // Populating every bit saturates the filter.  estimateN() signals full
        // saturation with Integer.MAX_VALUE rather than an unreliable numeric guess.
        final BloomFilter fullFilter =
                TestingHashers.populateEntireFilter(createEmptyFilter(shape));
        assertEquals(Integer.MAX_VALUE, fullFilter.estimateN(),
                "A fully saturated filter should return Integer.MAX_VALUE");
    }
}
