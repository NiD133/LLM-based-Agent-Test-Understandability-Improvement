package org.apache.commons.collections4.bloomfilter;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;
import org.apache.commons.collections4.bloomfilter.AbstractBloomFilterTest.BadHasher;

public class SparseBloomFilterTest_testMerge {

    // Shape used across all tests: k=17 hash functions, m=72 bits
    protected Shape getTestShape() {
        return Shape.fromKM(17, 72);
    }

    protected SparseBloomFilter createEmptyFilter(final Shape shape) {
        return new SparseBloomFilter(shape);
    }

    /**
     * Creates a SparseBloomFilter seeded with the given hasher.
     */
    protected final BloomFilter createFilter(final Shape shape, final Hasher hasher) {
        final SparseBloomFilter bf = createEmptyFilter(shape);
        bf.merge(hasher);
        return bf;
    }

    /**
     * Tests that merging bloom filters works as expected.
     *
     * <p>Covers three merge paths:
     * <ol>
     *   <li>merge(BloomFilter) – bitmap must equal the bitwise-OR of both filters</li>
     *   <li>merge(Hasher)      – result must be identical to the BloomFilter path</li>
     *   <li>error cases        – indices outside the filter's range must throw</li>
     * </ol>
     */
    @Test
    final void testMerge() {
        final BloomFilter filterFrom1  = createFilter(getTestShape(), TestingHashers.FROM1);
        final BloomFilter filterFrom11 = createFilter(getTestShape(), TestingHashers.FROM11);

        // Pre-compute the expected merged filter so we can check containment later.
        final BloomFilter expectedMerge = filterFrom1.copy();
        expectedMerge.merge(filterFrom11);

        // --- merge(BloomFilter) ---
        // Build the expected bitmap by manually ORing the two bitmaps.
        final long[] expectedBits = filterFrom1.asBitMapArray();
        final long[] from11Bits   = filterFrom11.asBitMapArray();
        for (int i = 0; i < expectedBits.length; i++) {
            expectedBits[i] |= from11Bits[i];
        }
        filterFrom1.merge(filterFrom11);
        final long[] mergedBits = filterFrom1.asBitMapArray();
        for (int i = 0; i < expectedBits.length; i++) {
            assertEquals(expectedBits[i], mergedBits[i], "Bad value at " + i);
        }
        assertTrue(filterFrom1.contains(filterFrom11), "Should contain bf2");
        assertTrue(filterFrom1.contains(expectedMerge), "Should contain bf3");

        // --- merge(Hasher) ---
        // Merging via a Hasher must produce the same result as merging via BloomFilter.
        final BloomFilter filterMergedViaHasher = createFilter(getTestShape(), TestingHashers.FROM1);
        filterMergedViaHasher.merge(TestingHashers.FROM11);
        assertTrue(filterMergedViaHasher.contains(filterFrom11), "Should contain Bf2");
        assertTrue(filterMergedViaHasher.contains(expectedMerge), "Should contain Bf3");

        // --- error: hasher produces an index equal to numberOfBits (one past the end) ---
        assertThrows(IllegalArgumentException.class,
                () -> filterFrom1.merge(new BadHasher(filterFrom1.getShape().getNumberOfBits())));
        // --- error: hasher produces a negative index ---
        assertThrows(IllegalArgumentException.class,
                () -> filterFrom1.merge(new BadHasher(-1)));

        // --- error: merging a BloomFilter whose bits exceed this filter's range ---
        // Build a larger shape (3× the bit-count) and populate it with out-of-range indices.
        final Shape largerShape = Shape.fromKM(
                getTestShape().getNumberOfHashFunctions(),
                getTestShape().getNumberOfBits() * 3);
        final Hasher outOfRangeHasher = new IncrementingHasher(getTestShape().getNumberOfBits() * 2, 1);

        // SimpleBloomFilter with out-of-range bits must be rejected.
        final BloomFilter simpleOutOfRange = new SimpleBloomFilter(largerShape);
        simpleOutOfRange.merge(outOfRangeHasher);
        assertThrows(IllegalArgumentException.class, () -> filterFrom1.merge(simpleOutOfRange));

        // SparseBloomFilter with out-of-range bits must also be rejected.
        final BloomFilter sparseOutOfRange = new SparseBloomFilter(largerShape);
        sparseOutOfRange.merge(outOfRangeHasher);
        assertThrows(IllegalArgumentException.class, () -> filterFrom1.merge(sparseOutOfRange));
    }
}
