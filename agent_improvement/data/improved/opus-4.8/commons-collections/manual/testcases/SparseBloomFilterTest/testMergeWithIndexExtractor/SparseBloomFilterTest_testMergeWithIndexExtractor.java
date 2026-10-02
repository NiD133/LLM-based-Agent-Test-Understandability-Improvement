package org.apache.commons.collections4.bloomfilter;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.BitSet;

import org.junit.jupiter.api.Test;

/**
 * Verifies {@link SparseBloomFilter#merge(IndexExtractor)}: a successful merge enables exactly
 * the (deduplicated) indices supplied, and out-of-range indices are rejected.
 */
public class SparseBloomFilterTest_testMergeWithIndexExtractor {

    /**
     * The shape used for every filter in this test: 17 hash functions over 72 bits,
     * so the only valid bit indices are 0..71.
     */
    private Shape getTestShape() {
        return Shape.fromKM(17, 72);
    }

    /** Creates an empty filter of the shape under test. */
    private SparseBloomFilter createEmptyFilter(final Shape shape) {
        return new SparseBloomFilter(shape);
    }

    /** Creates an empty filter and merges the given indices into it. */
    private BloomFilter createFilterFromIndices(final Shape shape, final IndexExtractor indices) {
        final SparseBloomFilter filter = createEmptyFilter(shape);
        filter.merge(indices);
        return filter;
    }

    @Test
    void testMergeWithIndexExtractor() {
        final Shape shape = getTestShape();

        // Over several random index sets, merging must enable exactly the distinct indices supplied.
        for (int i = 0; i < 5; i++) {
            final int[] values = DefaultIndexExtractorTest.generateIntArray(
                    shape.getNumberOfHashFunctions(), shape.getNumberOfBits());
            final BloomFilter filter = createFilterFromIndices(shape, IndexExtractor.fromIndexArray(values));

            // The set of distinct indices we expect the filter to contain.
            final BitSet expectedIndices = DefaultIndexExtractorTest.uniqueSet(values);

            // Walk the filter's indices: each must be expected; remove it as it is visited.
            // processIndices returns true only if every index passes the predicate.
            assertTrue(filter.processIndices(idx -> {
                final boolean wasExpected = expectedIndices.get(idx);
                expectedIndices.clear(idx);
                return wasExpected;
            }));

            // Having removed every visited index, nothing expected should remain unseen.
            assertTrue(expectedIndices.isEmpty());
        }

        // An index equal to the number of bits is one past the valid range (0..numberOfBits-1).
        final BloomFilter tooLargeIndex = createEmptyFilter(shape);
        assertThrows(IllegalArgumentException.class,
                () -> tooLargeIndex.merge(IndexExtractor.fromIndexArray(shape.getNumberOfBits())));

        // A negative index is also out of range.
        final BloomFilter negativeIndex = createEmptyFilter(shape);
        assertThrows(IllegalArgumentException.class,
                () -> negativeIndex.merge(IndexExtractor.fromIndexArray(-1)));
    }
}
