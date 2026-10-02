package org.apache.commons.collections4.bloomfilter;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class SimpleBloomFilterTest_testContains {

    private static final int HASH_FUNCTION_COUNT = 17;
    private static final int BIT_COUNT = 72;
    private static final int CONTAINED_START = 1;
    private static final int CONTAINED_INCREMENT = 1;
    private static final int MISSING_INCREMENT = 3;
    private static final int POPULATED_RANGE_START = 1;
    private static final int POPULATED_RANGE_END_OFFSET = 11;

    protected SimpleBloomFilter createEmptyFilter(final Shape shape) {
        return new SimpleBloomFilter(shape);
    }

    protected final SimpleBloomFilter createFilter(final Shape shape, final Hasher hasher) {
        final SimpleBloomFilter bloomFilter = createEmptyFilter(shape);
        bloomFilter.merge(hasher);
        return bloomFilter;
    }

    protected Shape getTestShape() {
        return Shape.fromKM(HASH_FUNCTION_COUNT, BIT_COUNT);
    }

    @Test
    final void testContains() {
        BloomFilter bf1 = createFilter(getTestShape(), TestingHashers.FROM1);
        final BloomFilter bf2 = TestingHashers.populateFromHashersFrom1AndFrom11(createEmptyFilter(getTestShape()));

        assertTrue(bf1.contains(bf1), "BF1 Should contain itself");
        assertTrue(bf2.contains(bf2), "BF2 Should contain itself");
        assertFalse(bf1.contains(bf2), "BF1 should not contain BF2");
        assertTrue(bf2.contains(bf1), "BF2 should contain BF1");

        assertTrue(bf2.contains(new IncrementingHasher(CONTAINED_START, CONTAINED_INCREMENT)), "BF2 Should contain this hasher");
        assertFalse(bf2.contains(new IncrementingHasher(CONTAINED_START, MISSING_INCREMENT)), "BF2 Should not contain this hasher");

        IndexExtractor indexExtractor = new IncrementingHasher(CONTAINED_START, CONTAINED_INCREMENT).indices(getTestShape());
        assertTrue(bf2.contains(indexExtractor), "BF2 Should contain this hasher");
        indexExtractor = new IncrementingHasher(CONTAINED_START, MISSING_INCREMENT).indices(getTestShape());
        assertFalse(bf2.contains(indexExtractor), "BF2 Should not contain this hasher");

        BitMapExtractor bitMapExtractor = BitMapExtractor.fromIndexExtractor(
                new IncrementingHasher(CONTAINED_START, CONTAINED_INCREMENT).indices(getTestShape()), getTestShape().getNumberOfBits());
        assertTrue(bf2.contains(bitMapExtractor), "BF2 Should contain this hasher");
        bitMapExtractor = BitMapExtractor.fromIndexExtractor(
                new IncrementingHasher(CONTAINED_START, MISSING_INCREMENT).indices(getTestShape()), getTestShape().getNumberOfBits());
        assertFalse(bf2.contains(bitMapExtractor), "BF2 Should not contain this hasher");

        bf1 = createFilter(getTestShape(), TestingHashers.FROM1);
        final Shape smallerShape = Shape.fromKM(getTestShape().getNumberOfHashFunctions(), Long.SIZE - 1);
        final BloomFilter bf3 = createFilter(smallerShape, TestingHashers.FROM1);
        assertTrue(bf1.contains(bf3));
        assertTrue(bf3.contains(bf1));

        final BloomFilter bf4 = TestingHashers.populateRange(createEmptyFilter(smallerShape), POPULATED_RANGE_START,
                POPULATED_RANGE_END_OFFSET + getTestShape().getNumberOfHashFunctions());
        assertFalse(bf1.contains(bf4));
        assertTrue(bf4.contains(bf1));
    }
}
