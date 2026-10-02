package org.apache.commons.collections4.bloomfilter;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

/**
 * Tests {@link SparseBloomFilter#contains}, exercising every overload:
 * containment of another {@link BloomFilter}, a {@link Hasher}, an
 * {@link IndexExtractor} and a {@link BitMapExtractor}.
 */
public class SparseBloomFilterTest_testContains {

    /**
     * The shape shared by the filters under test: 17 hash functions over 72 bits.
     */
    private Shape getTestShape() {
        return Shape.fromKM(17, 72);
    }

    /** Creates an empty sparse filter with the given shape. */
    private SparseBloomFilter createEmptyFilter(final Shape shape) {
        return new SparseBloomFilter(shape);
    }

    /** Creates a sparse filter populated from the given hasher. */
    private SparseBloomFilter createFilter(final Shape shape, final Hasher hasher) {
        final SparseBloomFilter filter = createEmptyFilter(shape);
        filter.merge(hasher);
        return filter;
    }

    @Test
    final void testContains() {
        // bf1 holds the bits produced by hasher FROM1.
        // bf2 holds the bits of FROM1 plus those of FROM11, so it is a superset of bf1.
        BloomFilter bf1 = createFilter(getTestShape(), TestingHashers.FROM1);
        final BloomFilter bf2 = TestingHashers.populateFromHashersFrom1AndFrom11(createEmptyFilter(getTestShape()));

        // A filter always contains itself.
        assertTrue(bf1.contains(bf1), "BF1 Should contain itself");
        assertTrue(bf2.contains(bf2), "BF2 Should contain itself");

        // Containment between the two filters: bf2 is a superset of bf1, not vice versa.
        assertFalse(bf1.contains(bf2), "BF1 should not contain BF2");
        assertTrue(bf2.contains(bf1), "BF2 should contain BF1");

        // contains(Hasher): FROM1 corresponds to IncrementingHasher(1, 1) and is present;
        // IncrementingHasher(1, 3) produces bits that are not all set in bf2.
        assertTrue(bf2.contains(new IncrementingHasher(1, 1)), "BF2 Should contain this hasher");
        assertFalse(bf2.contains(new IncrementingHasher(1, 3)), "BF2 Should not contain this hasher");

        // contains(IndexExtractor): same two hashers expressed as index extractors.
        IndexExtractor indexExtractor = new IncrementingHasher(1, 1).indices(getTestShape());
        assertTrue(bf2.contains(indexExtractor), "BF2 Should contain this hasher");
        indexExtractor = new IncrementingHasher(1, 3).indices(getTestShape());
        assertFalse(bf2.contains(indexExtractor), "BF2 Should not contain this hasher");

        // contains(BitMapExtractor): same two hashers expressed as bit-map extractors.
        BitMapExtractor bitMapExtractor = BitMapExtractor.fromIndexExtractor(
                new IncrementingHasher(1, 1).indices(getTestShape()), getTestShape().getNumberOfBits());
        assertTrue(bf2.contains(bitMapExtractor), "BF2 Should contain this hasher");
        bitMapExtractor = BitMapExtractor.fromIndexExtractor(
                new IncrementingHasher(1, 3).indices(getTestShape()), getTestShape().getNumberOfBits());
        assertFalse(bf2.contains(bitMapExtractor), "BF2 Should not contain this hasher");

        // Containment must also work across filters of different lengths (bit counts).
        bf1 = createFilter(getTestShape(), TestingHashers.FROM1);
        // bf3: same FROM1 bits but a shorter shape (Long.SIZE - 1 bits).
        final BloomFilter bf3 = createFilter(
                Shape.fromKM(getTestShape().getNumberOfHashFunctions(), Long.SIZE - 1), TestingHashers.FROM1);
        assertTrue(bf1.contains(bf3));
        assertTrue(bf3.contains(bf1));

        // bf4: a shorter shape populated with a wider range of bits, so it is a superset of bf1.
        final BloomFilter bf4 = TestingHashers.populateRange(
                createEmptyFilter(Shape.fromKM(getTestShape().getNumberOfHashFunctions(), Long.SIZE - 1)),
                1, 11 + getTestShape().getNumberOfHashFunctions());
        assertFalse(bf1.contains(bf4));
        assertTrue(bf4.contains(bf1));
    }
}
