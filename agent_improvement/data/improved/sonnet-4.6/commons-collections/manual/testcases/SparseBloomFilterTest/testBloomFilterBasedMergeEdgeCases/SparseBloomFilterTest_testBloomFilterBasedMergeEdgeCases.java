package org.apache.commons.collections4.bloomfilter;

import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

/**
 * Tests that merging a SimpleBloomFilter into a SparseBloomFilter produces
 * a result whose bit-map content is identical to the source filter.
 */
public class SparseBloomFilterTest_testBloomFilterBasedMergeEdgeCases {

    /**
     * Returns the shape used for all test filters: k=17 hash functions, m=72 bits.
     */
    private Shape getTestShape() {
        return Shape.fromKM(17, 72);
    }

    /**
     * Creates a new, empty SparseBloomFilter with the given shape.
     */
    private SparseBloomFilter createEmptyFilter(final Shape shape) {
        return new SparseBloomFilter(shape);
    }

    /**
     * Verifies that a SparseBloomFilter correctly absorbs a SimpleBloomFilter via merge.
     *
     * <p>Steps:
     * <ol>
     *   <li>Create an empty SparseBloomFilter (bf1).</li>
     *   <li>Create a SimpleBloomFilter (bf2) and populate it with FROM1 hashes.</li>
     *   <li>Merge bf2 into bf1.</li>
     *   <li>Assert that every bit-map word in bf2 equals the corresponding word in bf1,
     *       confirming that the merge copied all set bits faithfully.</li>
     * </ol>
     */
    @Test
    void testBloomFilterBasedMergeEdgeCases() {
        // bf1 starts empty; it will receive all bits from bf2 via merge
        final BloomFilter bf1 = createEmptyFilter(getTestShape());

        // bf2 is a non-sparse filter populated with a known hasher
        final BloomFilter bf2 = new SimpleBloomFilter(getTestShape());
        bf2.merge(TestingHashers.FROM1);

        // After merging bf2 into bf1, their bit-map representations must be identical
        bf1.merge(bf2);
        assertTrue(bf2.processBitMapPairs(bf1, (x, y) -> x == y));
    }
}
