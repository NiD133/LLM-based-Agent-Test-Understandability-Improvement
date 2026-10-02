package org.apache.commons.collections4.bloomfilter;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.BitSet;

import org.junit.jupiter.api.Test;

/**
 * Tests that SimpleBloomFilter correctly handles merging from an IndexExtractor,
 * including boundary validation for out-of-range indices.
 */
public class SimpleBloomFilterTest_testMergeWithIndexExtractor {

    /** Shape used across all tests: k=17 hash functions, m=72 bits. */
    private static final Shape TEST_SHAPE = Shape.fromKM(17, 72);

    /** Creates an empty SimpleBloomFilter with the test shape. */
    private SimpleBloomFilter createEmptyFilter() {
        return new SimpleBloomFilter(TEST_SHAPE);
    }

    /**
     * Creates a SimpleBloomFilter populated by merging the given IndexExtractor
     * into a fresh empty filter.
     */
    private SimpleBloomFilter createFilter(final IndexExtractor extractor) {
        final SimpleBloomFilter bf = createEmptyFilter();
        bf.merge(extractor);
        return bf;
    }

    /**
     * Verifies that merging an IndexExtractor into a SimpleBloomFilter sets
     * exactly the bits corresponding to the (deduplicated) source indices.
     *
     * The test runs five times with independently-generated random index arrays
     * to reduce the chance of an accidental pass.
     */
    @Test
    void testMergeWithIndexExtractor() {
        for (int i = 0; i < 5; i++) {
            // Generate a random array of valid indices (size = k, range = [0, m)).
            final int[] indices = DefaultIndexExtractorTest.generateIntArray(
                    TEST_SHAPE.getNumberOfHashFunctions(),
                    TEST_SHAPE.getNumberOfBits());

            // Build a reference set of the unique indices that must appear in the filter.
            final BitSet expectedBits = DefaultIndexExtractorTest.uniqueSet(indices);

            // Merge the indices into a new filter.
            final BloomFilter filter = createFilter(IndexExtractor.fromIndexArray(indices));

            // Walk the filter's set bits: each must be in expectedBits, and we clear
            // it so that at the end expectedBits is empty (no bit was missed).
            assertTrue(filter.processIndices(idx -> {
                final boolean present = expectedBits.get(idx);
                expectedBits.clear(idx);
                return present;
            }), "Filter contained an index not in the source array");

            assertTrue(expectedBits.isEmpty(),
                    "Filter is missing one or more indices from the source array");
        }

        // An index equal to numberOfBits is out of range [0, m) and must be rejected.
        final BloomFilter tooLarge = createEmptyFilter();
        assertThrows(IllegalArgumentException.class,
                () -> tooLarge.merge(IndexExtractor.fromIndexArray(TEST_SHAPE.getNumberOfBits())),
                "Expected exception for index == numberOfBits");

        // A negative index is always out of range and must be rejected.
        final BloomFilter negative = createEmptyFilter();
        assertThrows(IllegalArgumentException.class,
                () -> negative.merge(IndexExtractor.fromIndexArray(-1)),
                "Expected exception for negative index");
    }
}
