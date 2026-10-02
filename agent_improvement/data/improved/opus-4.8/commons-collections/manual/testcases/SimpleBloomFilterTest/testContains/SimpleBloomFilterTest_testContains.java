package org.apache.commons.collections4.bloomfilter;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

/**
 * Tests {@link SimpleBloomFilter#contains}, verifying that a filter correctly
 * reports whether it contains the bits described by another filter, a hasher,
 * an {@link IndexExtractor}, or a {@link BitMapExtractor}.
 */
public class SimpleBloomFilterTest_testContains {

    /** Number of hash functions (k) used by the test shape. */
    private static final int NUMBER_OF_HASH_FUNCTIONS = 17;

    /** Number of bits (m) used by the test shape. */
    private static final int NUMBER_OF_BITS = 72;

    /**
     * The shape of the Bloom filters used for testing: 17 hash functions over 72 bits.
     */
    private Shape getTestShape() {
        return Shape.fromKM(NUMBER_OF_HASH_FUNCTIONS, NUMBER_OF_BITS);
    }

    /**
     * Creates an empty Bloom filter with the given shape.
     */
    private SimpleBloomFilter createEmptyFilter(final Shape shape) {
        return new SimpleBloomFilter(shape);
    }

    /**
     * Creates a Bloom filter with the given shape, populated from the given hasher.
     */
    private SimpleBloomFilter createFilter(final Shape shape, final Hasher hasher) {
        final SimpleBloomFilter filter = createEmptyFilter(shape);
        filter.merge(hasher);
        return filter;
    }

    @Test
    final void testContains() {
        final Shape shape = getTestShape();

        // bf1 holds the bits from a single hasher; bf2 is a superset that also
        // includes a second hasher's bits, so bf2 should contain bf1 but not vice versa.
        BloomFilter bf1 = createFilter(shape, TestingHashers.FROM1);
        final BloomFilter bf2 =
                TestingHashers.populateFromHashersFrom1AndFrom11(createEmptyFilter(shape));

        // A filter always contains itself.
        assertTrue(bf1.contains(bf1), "BF1 Should contain itself");
        assertTrue(bf2.contains(bf2), "BF2 Should contain itself");

        // Containment between the two filters is one-directional.
        assertFalse(bf1.contains(bf2), "BF1 should not contain BF2");
        assertTrue(bf2.contains(bf1), "BF2 should contain BF1");

        // contains(Hasher): the (1,1) hasher is a subset of bf2; the (1,3) hasher is not.
        assertTrue(bf2.contains(new IncrementingHasher(1, 1)), "BF2 Should contain this hasher");
        assertFalse(bf2.contains(new IncrementingHasher(1, 3)), "BF2 Should not contain this hasher");

        // contains(IndexExtractor): same expectation expressed via the hashers' indices.
        IndexExtractor indexExtractor = new IncrementingHasher(1, 1).indices(shape);
        assertTrue(bf2.contains(indexExtractor), "BF2 Should contain this hasher");
        indexExtractor = new IncrementingHasher(1, 3).indices(shape);
        assertFalse(bf2.contains(indexExtractor), "BF2 Should not contain this hasher");

        // contains(BitMapExtractor): same expectation expressed via bit maps.
        BitMapExtractor bitMapExtractor =
                BitMapExtractor.fromIndexExtractor(new IncrementingHasher(1, 1).indices(shape), shape.getNumberOfBits());
        assertTrue(bf2.contains(bitMapExtractor), "BF2 Should contain this hasher");
        bitMapExtractor =
                BitMapExtractor.fromIndexExtractor(new IncrementingHasher(1, 3).indices(shape), shape.getNumberOfBits());
        assertFalse(bf2.contains(bitMapExtractor), "BF2 Should not contain this hasher");

        // Filters of different lengths: bf3 has the same hasher but fewer bits (Long.SIZE - 1).
        // Containment still holds both ways because they share the same set bits.
        bf1 = createFilter(shape, TestingHashers.FROM1);
        final BloomFilter bf3 =
                createFilter(Shape.fromKM(shape.getNumberOfHashFunctions(), Long.SIZE - 1), TestingHashers.FROM1);
        assertTrue(bf1.contains(bf3));
        assertTrue(bf3.contains(bf1));

        // bf4 (shorter shape) is populated over a wider range, making it a superset of bf1.
        final BloomFilter bf4 = TestingHashers.populateRange(
                createEmptyFilter(Shape.fromKM(shape.getNumberOfHashFunctions(), Long.SIZE - 1)),
                1, 11 + shape.getNumberOfHashFunctions());
        assertFalse(bf1.contains(bf4));
        assertTrue(bf4.contains(bf1));
    }
}
