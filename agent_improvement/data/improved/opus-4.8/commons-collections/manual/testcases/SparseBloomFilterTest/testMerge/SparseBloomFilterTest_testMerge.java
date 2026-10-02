package org.apache.commons.collections4.bloomfilter;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

/**
 * Tests {@link SparseBloomFilter#merge}.
 *
 * <p>The shared test shape has {@code k = 17} hash functions over {@code m = 72} bits.</p>
 */
public class SparseBloomFilterTest_testMerge {

    /**
     * A hasher that always produces a single fixed index, used to feed out-of-range values
     * (e.g. {@code numberOfBits} or {@code -1}) into a filter so the merge is rejected.
     */
    private static final class BadHasher implements Hasher {

        private final IndexExtractor extractor;

        BadHasher(final int value) {
            this.extractor = IndexExtractor.fromIndexArray(new int[] {value});
        }

        @Override
        public IndexExtractor indices(final Shape shape) {
            return extractor;
        }
    }

    /** The shape used by every filter in these tests: 17 hash functions over 72 bits. */
    private Shape getTestShape() {
        return Shape.fromKM(17, 72);
    }

    /**
     * Builds a {@link SparseBloomFilter} of the given shape and merges the hasher into it.
     *
     * @param shape  the shape of the filter.
     * @param hasher the hasher whose indices are merged into the new filter.
     * @return the populated filter.
     */
    private SparseBloomFilter createFilter(final Shape shape, final Hasher hasher) {
        final SparseBloomFilter filter = new SparseBloomFilter(shape);
        filter.merge(hasher);
        return filter;
    }

    /**
     * Verifies that merging one Bloom filter into another behaves like a bitwise OR of their
     * bit maps, that the result contains both source filters, that merging works equally well
     * via a hasher, and that out-of-range or shape-incompatible merges are rejected.
     */
    @Test
    final void testMerge() {
        final Shape shape = getTestShape();

        final BloomFilter filterFrom1 = createFilter(shape, TestingHashers.FROM1);
        final BloomFilter filterFrom11 = createFilter(shape, TestingHashers.FROM11);

        // A snapshot of filterFrom1 that we also merge filterFrom11 into, so it should end up
        // holding the union of both filters.
        final BloomFilter unionCopy = filterFrom1.copy();
        unionCopy.merge(filterFrom11);

        // The expected bit map of the merged filter is the bitwise OR of the two source filters.
        final long[] expectedBits = filterFrom1.asBitMapArray();
        final long[] from11Bits = filterFrom11.asBitMapArray();
        for (int i = 0; i < expectedBits.length; i++) {
            expectedBits[i] |= from11Bits[i];
        }

        // Merging another BloomFilter must OR its bits into the target.
        filterFrom1.merge(filterFrom11);
        final long[] actualBits = filterFrom1.asBitMapArray();
        for (int i = 0; i < expectedBits.length; i++) {
            assertEquals(expectedBits[i], actualBits[i], "Bad value at " + i);
        }
        assertTrue(filterFrom1.contains(filterFrom11), "Should contain filterFrom11");
        assertTrue(filterFrom1.contains(unionCopy), "Should contain unionCopy");

        // Merging via a hasher must give the same union as merging the filter directly.
        final BloomFilter mergedViaHasher = createFilter(shape, TestingHashers.FROM1);
        mergedViaHasher.merge(TestingHashers.FROM11);
        assertTrue(mergedViaHasher.contains(filterFrom11), "Should contain filterFrom11");
        assertTrue(mergedViaHasher.contains(unionCopy), "Should contain unionCopy");

        // A hasher that produces indices outside [0, m) must be rejected.
        assertThrows(IllegalArgumentException.class,
                () -> filterFrom1.merge(new BadHasher(filterFrom1.getShape().getNumberOfBits())));
        assertThrows(IllegalArgumentException.class,
                () -> filterFrom1.merge(new BadHasher(-1)));

        // A filter built on a larger shape can hold indices beyond this shape's range; merging
        // it in must be rejected regardless of the source filter's implementation.
        final Shape largerShape = Shape.fromKM(shape.getNumberOfHashFunctions(), shape.getNumberOfBits() * 3);
        final Hasher outOfRangeHasher = new IncrementingHasher(shape.getNumberOfBits() * 2, 1);

        final BloomFilter simpleOutOfRange = new SimpleBloomFilter(largerShape);
        simpleOutOfRange.merge(outOfRangeHasher);
        assertThrows(IllegalArgumentException.class, () -> filterFrom1.merge(simpleOutOfRange));

        final BloomFilter sparseOutOfRange = new SparseBloomFilter(largerShape);
        sparseOutOfRange.merge(outOfRangeHasher);
        assertThrows(IllegalArgumentException.class, () -> filterFrom1.merge(sparseOutOfRange));
    }
}
