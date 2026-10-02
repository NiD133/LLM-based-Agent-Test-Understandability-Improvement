package org.apache.commons.collections4.bloomfilter;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

/**
 * Tests for {@link SimpleBloomFilter#merge} with BloomFilter, Hasher, and out-of-range inputs.
 */
public class SimpleBloomFilterTest_testMerge {

    /** Shape used across all tests: k=17 hash functions, m=72 bits. */
    private static final Shape TEST_SHAPE = Shape.fromKM(17, 72);

    /** Creates an empty {@link SimpleBloomFilter} with the standard test shape. */
    private SimpleBloomFilter createEmptyFilter() {
        return new SimpleBloomFilter(TEST_SHAPE);
    }

    /** Creates a {@link SimpleBloomFilter} pre-populated via the given {@link Hasher}. */
    private SimpleBloomFilter createFilter(final Hasher hasher) {
        final SimpleBloomFilter bf = createEmptyFilter();
        bf.merge(hasher);
        return bf;
    }

    /**
     * Verifies that merging two Bloom filters (via BloomFilter, then via Hasher) produces the
     * correct union of bits, and that out-of-range inputs are rejected.
     *
     * <p>Steps:
     * <ol>
     *   <li>Build bf1 (FROM1) and bf2 (FROM11); record the expected union bitmap manually.</li>
     *   <li>Merge bf2 into bf1 and assert every bitmap word matches the expected union.</li>
     *   <li>Assert bf1 contains both bf2 and the pre-merge snapshot (bf3).</li>
     *   <li>Repeat via Hasher: build bf4, merge FROM11 as a Hasher, assert containment.</li>
     *   <li>Assert that hashers producing indices outside [0, numberOfBits) throw.</li>
     *   <li>Assert that merging a larger-shape BloomFilter (dense and sparse) throws.</li>
     * </ol>
     */
    @Test
    void testMerge() {
        // --- Step 1: create base filters and a pre-merge snapshot ---
        final BloomFilter bf1 = createFilter(TestingHashers.FROM1);
        final BloomFilter bf2 = createFilter(TestingHashers.FROM11);
        // bf3 is a copy of bf1 BEFORE the merge, used later to confirm bf1 still contains it
        final BloomFilter bf3 = bf1.copy();

        // --- Step 2: compute expected union bitmap, then actually merge ---
        final long[] bf1Bitmap = bf1.asBitMapArray();
        final long[] bf2Bitmap = bf2.asBitMapArray();
        // expected[i] = bf1[i] | bf2[i]
        for (int i = 0; i < bf1Bitmap.length; i++) {
            bf1Bitmap[i] |= bf2Bitmap[i];
        }
        bf1.merge(bf2);
        final long[] mergedBitmap = bf1.asBitMapArray();
        for (int i = 0; i < bf1Bitmap.length; i++) {
            assertEquals(bf1Bitmap[i], mergedBitmap[i], "Bad value at word index " + i);
        }

        // --- Step 3: containment after BloomFilter merge ---
        assertTrue(bf1.contains(bf2), "Merged filter should contain bf2");
        assertTrue(bf1.contains(bf3), "Merged filter should contain bf3 (pre-merge snapshot)");

        // --- Step 4: merge via Hasher produces the same result ---
        final BloomFilter bf4 = createFilter(TestingHashers.FROM1);
        bf4.merge(TestingHashers.FROM11);
        assertTrue(bf4.contains(bf2), "Hasher-merged filter should contain bf2");
        assertTrue(bf4.contains(bf3), "Hasher-merged filter should contain bf3");

        // --- Step 5: out-of-range hasher indices must throw ---
        assertThrows(IllegalArgumentException.class,
                () -> bf1.merge(new BadHasher(bf1.getShape().getNumberOfBits())),
                "Index equal to numberOfBits should be rejected");
        assertThrows(IllegalArgumentException.class,
                () -> bf1.merge(new BadHasher(-1)),
                "Negative index should be rejected");

        // --- Step 6: merging a filter built for a larger shape must throw ---
        final Shape largerShape = Shape.fromKM(
                TEST_SHAPE.getNumberOfHashFunctions(),
                TEST_SHAPE.getNumberOfBits() * 3);
        final Hasher outOfRangeHasher = new IncrementingHasher(TEST_SHAPE.getNumberOfBits() * 2, 1);

        final BloomFilter bf5 = new SimpleBloomFilter(largerShape);
        bf5.merge(outOfRangeHasher);
        assertThrows(IllegalArgumentException.class,
                () -> bf1.merge(bf5),
                "Dense filter with bits beyond bf1's shape should be rejected");

        final BloomFilter bf6 = new SparseBloomFilter(largerShape);
        bf6.merge(outOfRangeHasher);
        assertThrows(IllegalArgumentException.class,
                () -> bf1.merge(bf6),
                "Sparse filter with bits beyond bf1's shape should be rejected");
    }
}
