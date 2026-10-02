package org.apache.commons.collections4.bloomfilter;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.BitSet;

import org.junit.jupiter.api.Test;

/**
 * Tests {@link SimpleBloomFilter#merge(IndexExtractor)}.
 */
public class SimpleBloomFilterTest_testMergeWithIndexExtractor {

    /** Number of random merge iterations to exercise in the happy-path check. */
    private static final int RANDOM_MERGE_ITERATIONS = 5;

    /**
     * The shape used for every filter in this test: 17 hash functions over 72 bits.
     */
    private Shape getTestShape() {
        return Shape.fromKM(17, 72);
    }

    /** Creates an empty filter with the test shape. */
    private SimpleBloomFilter createEmptyFilter(final Shape shape) {
        return new SimpleBloomFilter(shape);
    }

    /** Creates a filter populated by merging the given index extractor. */
    private SimpleBloomFilter createFilter(final Shape shape, final IndexExtractor extractor) {
        final SimpleBloomFilter filter = createEmptyFilter(shape);
        filter.merge(extractor);
        return filter;
    }

    @Test
    void testMergeWithIndexExtractor() {
        final Shape shape = getTestShape();

        // Happy path: merging a set of random in-range indices yields a filter
        // whose enabled bits are exactly the unique input indices.
        for (int i = 0; i < RANDOM_MERGE_ITERATIONS; i++) {
            final int[] values = DefaultIndexExtractorTest.generateIntArray(
                    shape.getNumberOfHashFunctions(), shape.getNumberOfBits());
            final BloomFilter filter = createFilter(shape, IndexExtractor.fromIndexArray(values));

            // The set of distinct indices we expect to find in the filter.
            final BitSet expectedIndices = DefaultIndexExtractorTest.uniqueSet(values);

            // Each produced index must be expected; clearing as we go lets us
            // confirm afterwards that every expected index was produced exactly once.
            assertTrue(filter.processIndices(idx -> {
                final boolean wasExpected = expectedIndices.get(idx);
                expectedIndices.clear(idx);
                return wasExpected;
            }));
            assertTrue(expectedIndices.isEmpty());
        }

        // An index equal to the number of bits is out of range (valid range is [0, numberOfBits)).
        final BloomFilter tooLargeIndexFilter = createEmptyFilter(shape);
        assertThrows(IllegalArgumentException.class,
                () -> tooLargeIndexFilter.merge(IndexExtractor.fromIndexArray(shape.getNumberOfBits())));

        // A negative index is out of range.
        final BloomFilter negativeIndexFilter = createEmptyFilter(shape);
        assertThrows(IllegalArgumentException.class,
                () -> negativeIndexFilter.merge(IndexExtractor.fromIndexArray(-1)));
    }
}
