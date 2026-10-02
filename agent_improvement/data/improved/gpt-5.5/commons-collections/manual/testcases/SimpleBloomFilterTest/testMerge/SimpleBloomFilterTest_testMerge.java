package org.apache.commons.collections4.bloomfilter;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class SimpleBloomFilterTest_testMerge {

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

    protected SimpleBloomFilter createEmptyFilter(final Shape shape) {
        return new SimpleBloomFilter(shape);
    }

    protected final SimpleBloomFilter createFilter(final Shape shape, final BloomFilter<?> extractor) {
        final SimpleBloomFilter bloomFilter = createEmptyFilter(shape);
        bloomFilter.merge(extractor);
        return bloomFilter;
    }

    protected final SimpleBloomFilter createFilter(final Shape shape, final Hasher hasher) {
        final SimpleBloomFilter bloomFilter = createEmptyFilter(shape);
        bloomFilter.merge(hasher);
        return bloomFilter;
    }

    protected Shape getTestShape() {
        return Shape.fromKM(17, 72);
    }

    /**
     * Tests that merging bloom filters works as expected with a generic BloomFilter.
     */
    @Test
    final void testMerge() {
        final BloomFilter<?> bf1 = createFilter(getTestShape(), TestingHashers.FROM1);
        final BloomFilter<?> bf2 = createFilter(getTestShape(), TestingHashers.FROM11);
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

        final BloomFilter<?> bf4 = createFilter(getTestShape(), TestingHashers.FROM1);
        bf4.merge(TestingHashers.FROM11);

        assertTrue(bf4.contains(bf2), "Should contain Bf2");
        assertTrue(bf4.contains(bf3), "Should contain Bf3");

        assertThrows(IllegalArgumentException.class, () -> bf1.merge(new BadHasher(bf1.getShape().getNumberOfBits())));
        assertThrows(IllegalArgumentException.class, () -> bf1.merge(new BadHasher(-1)));

        final Shape largerShape = Shape.fromKM(getTestShape().getNumberOfHashFunctions(), getTestShape().getNumberOfBits() * 3);
        final Hasher outOfRangeHasher = new IncrementingHasher(getTestShape().getNumberOfBits() * 2, 1);
        final BloomFilter<?> largerSimpleFilter = new SimpleBloomFilter(largerShape);
        largerSimpleFilter.merge(outOfRangeHasher);
        assertThrows(IllegalArgumentException.class, () -> bf1.merge(largerSimpleFilter));

        final BloomFilter<?> largerSparseFilter = new SparseBloomFilter(largerShape);
        largerSparseFilter.merge(outOfRangeHasher);
        assertThrows(IllegalArgumentException.class, () -> bf1.merge(largerSparseFilter));
    }
}
