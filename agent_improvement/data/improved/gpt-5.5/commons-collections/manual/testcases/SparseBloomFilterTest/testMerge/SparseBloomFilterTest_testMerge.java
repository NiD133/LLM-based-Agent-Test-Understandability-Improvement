package org.apache.commons.collections4.bloomfilter;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class SparseBloomFilterTest_testMerge {

    private static final int NUMBER_OF_HASH_FUNCTIONS = 17;
    private static final int NUMBER_OF_BITS = 72;

    private SparseBloomFilter createEmptyFilter(final Shape shape) {
        return new SparseBloomFilter(shape);
    }

    private SparseBloomFilter createFilter(final Shape shape, final Hasher hasher) {
        final SparseBloomFilter bloomFilter = createEmptyFilter(shape);
        bloomFilter.merge(hasher);
        return bloomFilter;
    }

    private Shape getTestShape() {
        return Shape.fromKM(NUMBER_OF_HASH_FUNCTIONS, NUMBER_OF_BITS);
    }

    private static final class BadHasher implements Hasher {
        private final int index;

        private BadHasher(final int index) {
            this.index = index;
        }

        @Override
        public IndexExtractor indices(final Shape shape) {
            return IndexExtractor.fromIndexArray(index);
        }
    }

    /**
     * Tests that merging bloom filters works as expected with a generic BloomFilter.
     */
    @Test
    final void testMerge() {
        final Shape testShape = getTestShape();
        final BloomFilter<?> bf1 = createFilter(testShape, TestingHashers.FROM1);
        final BloomFilter<?> bf2 = createFilter(testShape, TestingHashers.FROM11);
        final BloomFilter<?> bf3 = bf1.copy();

        bf3.merge(bf2);

        final long[] expectedMergedBitMaps = bf1.asBitMapArray();
        final long[] bf2BitMaps = bf2.asBitMapArray();
        for (int i = 0; i < expectedMergedBitMaps.length; i++) {
            expectedMergedBitMaps[i] |= bf2BitMaps[i];
        }

        bf1.merge(bf2);

        final long[] actualMergedBitMaps = bf1.asBitMapArray();
        for (int i = 0; i < expectedMergedBitMaps.length; i++) {
            assertEquals(expectedMergedBitMaps[i], actualMergedBitMaps[i], "Bad value at " + i);
        }
        assertTrue(bf1.contains(bf2), "Should contain bf2");
        assertTrue(bf1.contains(bf3), "Should contain bf3");

        final BloomFilter<?> bf4 = createFilter(testShape, TestingHashers.FROM1);
        bf4.merge(TestingHashers.FROM11);
        assertTrue(bf4.contains(bf2), "Should contain Bf2");
        assertTrue(bf4.contains(bf3), "Should contain Bf3");

        assertThrows(IllegalArgumentException.class, () -> bf1.merge(new BadHasher(bf1.getShape().getNumberOfBits())));
        assertThrows(IllegalArgumentException.class, () -> bf1.merge(new BadHasher(-1)));

        final Shape largerShape = Shape.fromKM(testShape.getNumberOfHashFunctions(), testShape.getNumberOfBits() * 3);
        final Hasher outOfRangeHasher = new IncrementingHasher(testShape.getNumberOfBits() * 2, 1);

        final BloomFilter<?> simpleBloomFilter = new SimpleBloomFilter(largerShape);
        simpleBloomFilter.merge(outOfRangeHasher);
        assertThrows(IllegalArgumentException.class, () -> bf1.merge(simpleBloomFilter));

        final BloomFilter<?> sparseBloomFilter = new SparseBloomFilter(largerShape);
        sparseBloomFilter.merge(outOfRangeHasher);
        assertThrows(IllegalArgumentException.class, () -> bf1.merge(sparseBloomFilter));
    }
}
