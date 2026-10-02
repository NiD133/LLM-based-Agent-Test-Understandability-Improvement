package org.apache.commons.collections4.bloomfilter;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class SparseBloomFilterTest_testContains {

    private static final Shape TEST_SHAPE = Shape.fromKM(17, 72);

    protected SparseBloomFilter createEmptyFilter(final Shape shape) {
        return new SparseBloomFilter(shape);
    }

    protected final BloomFilter createFilter(final Shape shape, final Hasher hasher) {
        final SparseBloomFilter bf = createEmptyFilter(shape);
        bf.merge(hasher);
        return bf;
    }

    protected final BloomFilter createFilter(final Shape shape, final IndexExtractor extractor) {
        final SparseBloomFilter bf = createEmptyFilter(shape);
        bf.merge(extractor);
        return bf;
    }

    protected final BloomFilter createFilter(final Shape shape, final BitMapExtractor extractor) {
        final SparseBloomFilter bf = createEmptyFilter(shape);
        bf.merge(extractor);
        return bf;
    }

    @Test
    final void testContains() {
        // --- BloomFilter vs BloomFilter containment ---
        // bf1 contains only FROM1 bits; bf2 contains FROM1 and FROM11 bits
        final BloomFilter bf1 = createFilter(TEST_SHAPE, TestingHashers.FROM1);
        final BloomFilter bf2 = TestingHashers.populateFromHashersFrom1AndFrom11(createEmptyFilter(TEST_SHAPE));

        assertTrue(bf1.contains(bf1),  "BF1 Should contain itself");
        assertTrue(bf2.contains(bf2),  "BF2 Should contain itself");
        assertFalse(bf1.contains(bf2), "BF1 should not contain BF2");
        assertTrue(bf2.contains(bf1),  "BF2 should contain BF1");

        // --- BloomFilter.contains(Hasher) ---
        assertTrue(bf2.contains(new IncrementingHasher(1, 1)),
                "BF2 Should contain this hasher");
        assertFalse(bf2.contains(new IncrementingHasher(1, 3)),
                "BF2 Should not contain this hasher");

        // --- BloomFilter.contains(IndexExtractor) ---
        final IndexExtractor presentIndexExtractor = new IncrementingHasher(1, 1).indices(TEST_SHAPE);
        assertTrue(bf2.contains(presentIndexExtractor),
                "BF2 Should contain this hasher");

        final IndexExtractor absentIndexExtractor = new IncrementingHasher(1, 3).indices(TEST_SHAPE);
        assertFalse(bf2.contains(absentIndexExtractor),
                "BF2 Should not contain this hasher");

        // --- BloomFilter.contains(BitMapExtractor) ---
        final BitMapExtractor presentBitMapExtractor =
                BitMapExtractor.fromIndexExtractor(new IncrementingHasher(1, 1).indices(TEST_SHAPE),
                        TEST_SHAPE.getNumberOfBits());
        assertTrue(bf2.contains(presentBitMapExtractor),
                "BF2 Should contain this hasher");

        final BitMapExtractor absentBitMapExtractor =
                BitMapExtractor.fromIndexExtractor(new IncrementingHasher(1, 3).indices(TEST_SHAPE),
                        TEST_SHAPE.getNumberOfBits());
        assertFalse(bf2.contains(absentBitMapExtractor),
                "BF2 Should not contain this hasher");

        // --- Containment across filters with different bit-lengths ---
        // Both shapes use the same k (hash functions) but different m (number of bits).
        // FROM1 produces indices that fit in both shapes, so each filter contains the other.
        final BloomFilter bf1SameHashFns = createFilter(TEST_SHAPE, TestingHashers.FROM1);
        final Shape smallerShape = Shape.fromKM(TEST_SHAPE.getNumberOfHashFunctions(), Long.SIZE - 1);
        final BloomFilter bf3SmallerShape = createFilter(smallerShape, TestingHashers.FROM1);

        assertTrue(bf1SameHashFns.contains(bf3SmallerShape));
        assertTrue(bf3SmallerShape.contains(bf1SameHashFns));

        // bf4 occupies bits 1..11+k in the smaller shape — a superset of bf1's bits
        final int rangeEnd = 11 + TEST_SHAPE.getNumberOfHashFunctions();
        final BloomFilter bf4SupersetOfBf1 =
                TestingHashers.populateRange(createEmptyFilter(smallerShape), 1, rangeEnd);

        assertFalse(bf1SameHashFns.contains(bf4SupersetOfBf1));
        assertTrue(bf4SupersetOfBf1.contains(bf1SameHashFns));
    }
}
