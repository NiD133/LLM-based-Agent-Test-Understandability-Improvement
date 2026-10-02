package org.apache.commons.collections4.bloomfilter;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

/**
 * Tests the {@code merge} operations of {@link SimpleBloomFilter}.
 *
 * <p>The shape used throughout is k=17 hash functions over m=72 bits.</p>
 */
public class SimpleBloomFilterTest_testMerge {

    /**
     * A {@link Hasher} that always reports a single index equal to the value it
     * was constructed with. Used to feed out-of-range indices into a filter so
     * that {@code merge} is expected to reject them.
     */
    private static final class BadHasher implements Hasher {

        private final IndexExtractor extractor;

        BadHasher(final int value) {
            this.extractor = IndexExtractor.fromIndexArray(value);
        }

        @Override
        public IndexExtractor indices(final Shape shape) {
            return extractor;
        }
    }

    /**
     * The shape of the Bloom filters used for testing.
     * <ul>
     *  <li>Hash functions (k) = 17</li>
     *  <li>Number of bits (m) = 72</li>
     * </ul>
     */
    private Shape getTestShape() {
        return Shape.fromKM(17, 72);
    }

    /** Creates an empty filter of the implementation under test. */
    private SimpleBloomFilter createEmptyFilter(final Shape shape) {
        return new SimpleBloomFilter(shape);
    }

    /** Creates a filter for the given shape and populates it from the supplied hasher. */
    private SimpleBloomFilter createFilter(final Shape shape, final Hasher hasher) {
        final SimpleBloomFilter filter = createEmptyFilter(shape);
        filter.merge(hasher);
        return filter;
    }

    /**
     * Tests that merging Bloom filters works as expected for the BloomFilter,
     * Hasher and out-of-range error cases.
     */
    @Test
    final void testMerge() {
        final BloomFilter bf1 = createFilter(getTestShape(), TestingHashers.FROM1);
        final BloomFilter bf2 = createFilter(getTestShape(), TestingHashers.FROM11);

        // bf3 = bf1 OR bf2, built via copy + merge, kept as an independent reference.
        final BloomFilter bf3 = bf1.copy();
        bf3.merge(bf2);

        // Merging another BloomFilter must OR the bit maps together.
        final long[] expectedBitMaps = bf1.asBitMapArray();
        final long[] bf2BitMaps = bf2.asBitMapArray();
        for (int i = 0; i < expectedBitMaps.length; i++) {
            expectedBitMaps[i] |= bf2BitMaps[i];
        }
        bf1.merge(bf2);
        final long[] actualBitMaps = bf1.asBitMapArray();
        for (int i = 0; i < expectedBitMaps.length; i++) {
            assertEquals(expectedBitMaps[i], actualBitMaps[i], "Bad value at " + i);
        }
        assertTrue(bf1.contains(bf2), "Should contain bf2");
        assertTrue(bf1.contains(bf3), "Should contain bf3");

        // Merging a Hasher must yield the same membership as merging the filters.
        final BloomFilter bf4 = createFilter(getTestShape(), TestingHashers.FROM1);
        bf4.merge(TestingHashers.FROM11);
        assertTrue(bf4.contains(bf2), "Should contain Bf2");
        assertTrue(bf4.contains(bf3), "Should contain Bf3");

        // A hasher producing indices outside [0, numberOfBits) must be rejected.
        assertThrows(IllegalArgumentException.class,
                () -> bf1.merge(new BadHasher(bf1.getShape().getNumberOfBits())));
        assertThrows(IllegalArgumentException.class,
                () -> bf1.merge(new BadHasher(-1)));

        // A larger filter whose set bits exceed bf1's shape must be rejected on merge,
        // for both the dense (SimpleBloomFilter) and sparse (SparseBloomFilter) cases.
        final Shape largerShape = Shape.fromKM(
                getTestShape().getNumberOfHashFunctions(),
                getTestShape().getNumberOfBits() * 3);
        final Hasher outOfRangeHasher = new IncrementingHasher(getTestShape().getNumberOfBits() * 2, 1);

        final BloomFilter denseOutOfRange = new SimpleBloomFilter(largerShape);
        denseOutOfRange.merge(outOfRangeHasher);
        assertThrows(IllegalArgumentException.class, () -> bf1.merge(denseOutOfRange));

        final BloomFilter sparseOutOfRange = new SparseBloomFilter(largerShape);
        sparseOutOfRange.merge(outOfRangeHasher);
        assertThrows(IllegalArgumentException.class, () -> bf1.merge(sparseOutOfRange));
    }
}
