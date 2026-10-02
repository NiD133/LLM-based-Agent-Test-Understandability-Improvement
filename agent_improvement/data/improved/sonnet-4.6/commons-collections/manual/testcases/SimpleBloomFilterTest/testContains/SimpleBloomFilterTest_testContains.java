package org.apache.commons.collections4.bloomfilter;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

public class SimpleBloomFilterTest_testContains {

    // Shape with k=17 hash functions and m=72 bits, used across all test scenarios.
    private static final Shape TEST_SHAPE = Shape.fromKM(17, 72);

    private SimpleBloomFilter createEmptyFilter(final Shape shape) {
        return new SimpleBloomFilter(shape);
    }

    private BloomFilter createFilter(final Shape shape, final Hasher hasher) {
        final SimpleBloomFilter bf = createEmptyFilter(shape);
        bf.merge(hasher);
        return bf;
    }

    @Test
    void testContains() {
        // bf1 encodes FROM1 (indices 1..17); bf2 encodes both FROM1 and FROM11 (indices 1..27),
        // making bf2 a strict superset of bf1.
        BloomFilter bf1 = createFilter(TEST_SHAPE, TestingHashers.FROM1);
        final BloomFilter bf2 = TestingHashers.populateFromHashersFrom1AndFrom11(createEmptyFilter(TEST_SHAPE));

        // A filter always contains itself.
        assertTrue(bf1.contains(bf1), "BF1 Should contain itself");
        assertTrue(bf2.contains(bf2), "BF2 Should contain itself");

        // bf2 is a superset of bf1: bf1 cannot contain bf2, but bf2 can contain bf1.
        assertFalse(bf1.contains(bf2), "BF1 should not contain BF2");
        assertTrue(bf2.contains(bf1), "BF2 should contain BF1");

        // contains(Hasher): hasher(1,1) maps entirely within bf2; hasher(1,3) produces
        // indices that fall outside the bits set in bf2.
        assertTrue(bf2.contains(new IncrementingHasher(1, 1)), "BF2 Should contain this hasher");
        assertFalse(bf2.contains(new IncrementingHasher(1, 3)), "BF2 Should not contain this hasher");

        // contains(IndexExtractor): same containment checks expressed as index extractors.
        IndexExtractor indexExtractor = new IncrementingHasher(1, 1).indices(TEST_SHAPE);
        assertTrue(bf2.contains(indexExtractor), "BF2 Should contain this hasher");
        indexExtractor = new IncrementingHasher(1, 3).indices(TEST_SHAPE);
        assertFalse(bf2.contains(indexExtractor), "BF2 Should not contain this hasher");

        // contains(BitMapExtractor): same containment checks expressed as bit map extractors.
        BitMapExtractor bitMapExtractor = BitMapExtractor.fromIndexExtractor(
                new IncrementingHasher(1, 1).indices(TEST_SHAPE), TEST_SHAPE.getNumberOfBits());
        assertTrue(bf2.contains(bitMapExtractor), "BF2 Should contain this hasher");
        bitMapExtractor = BitMapExtractor.fromIndexExtractor(
                new IncrementingHasher(1, 3).indices(TEST_SHAPE), TEST_SHAPE.getNumberOfBits());
        assertFalse(bf2.contains(bitMapExtractor), "BF2 Should not contain this hasher");

        // Containment across filters with different bit-array lengths (different shapes).
        // bf1 uses shape(k=17, m=72); bf3 uses shape(k=17, m=63). Both encode the same FROM1
        // hasher, so they mutually contain each other despite different backing array sizes.
        bf1 = createFilter(TEST_SHAPE, TestingHashers.FROM1);
        final Shape smallerShape = Shape.fromKM(TEST_SHAPE.getNumberOfHashFunctions(), Long.SIZE - 1);
        final BloomFilter bf3 = createFilter(smallerShape, TestingHashers.FROM1);
        assertTrue(bf1.contains(bf3));
        assertTrue(bf3.contains(bf1));

        // bf4 populates a wider bit range (1..28) in the smaller shape.
        // bf1 does not cover all of bf4's bits, but bf4 covers all of bf1's bits.
        final BloomFilter bf4 = TestingHashers.populateRange(
                createEmptyFilter(smallerShape), 1, 11 + TEST_SHAPE.getNumberOfHashFunctions());
        assertFalse(bf1.contains(bf4));
        assertTrue(bf4.contains(bf1));
    }
}
