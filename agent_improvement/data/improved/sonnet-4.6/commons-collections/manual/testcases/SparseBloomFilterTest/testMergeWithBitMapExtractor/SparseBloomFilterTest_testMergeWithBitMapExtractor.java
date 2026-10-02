package org.apache.commons.collections4.bloomfilter;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;

/**
 * Tests that {@link SparseBloomFilter#merge(BitMapExtractor)} correctly
 * populates the filter and rejects bit-maps whose set bits exceed the
 * filter's declared shape.
 */
public class SparseBloomFilterTest_testMergeWithBitMapExtractor {

    // Shape used across all tests: k=17 hash functions, m=72 bits.
    private static final Shape TEST_SHAPE = Shape.fromKM(17, 72);

    // Number of independent round-trip verifications to perform.
    private static final int ROUND_TRIP_ROUNDS = 5;

    private SparseBloomFilter createEmptyFilter(final Shape shape) {
        return new SparseBloomFilter(shape);
    }

    private SparseBloomFilter createFilter(final Shape shape, final BitMapExtractor extractor) {
        final SparseBloomFilter bf = createEmptyFilter(shape);
        bf.merge(extractor);
        return bf;
    }

    /**
     * Verifies three things:
     * <ol>
     *   <li>A filter built from a {@link BitMapExtractor} emits exactly the same
     *       long words via {@code processBitMaps} as were supplied to the extractor
     *       (round-trip correctness).</li>
     *   <li>Merging a bit-map where every word is {@code Long.MAX_VALUE} — which
     *       sets bits beyond the filter's declared bit count — throws
     *       {@link IllegalArgumentException}.</li>
     *   <li>Merging a single-long bit-map with the most-significant bit set into a
     *       32-bit shape also throws {@link IllegalArgumentException}, even though
     *       both bitmaps have the same array length.</li>
     * </ol>
     */
    @Test
    void testMergeWithBitMapExtractor() {
        final int bitMapCount = BitMaps.numberOfBitMaps(TEST_SHAPE);

        // --- Round-trip check ---
        // Build a filter from randomly-populated bitmaps and confirm that
        // processBitMaps emits every long word that was originally supplied.
        for (int round = 0; round < ROUND_TRIP_ROUNDS; round++) {
            final long[] sourceBitmap = new long[bitMapCount];
            for (final int idx : DefaultIndexExtractorTest.generateIntArray(
                    TEST_SHAPE.getNumberOfHashFunctions(), TEST_SHAPE.getNumberOfBits())) {
                BitMaps.set(sourceBitmap, idx);
            }

            final BloomFilter filter = createFilter(TEST_SHAPE, BitMapExtractor.fromBitMapArray(sourceBitmap));

            // Collect every long from the source, then remove each one that
            // processBitMaps reports. The list must be empty when done.
            final List<Long> unmatched = new ArrayList<>();
            for (final long word : sourceBitmap) {
                unmatched.add(word);
            }
            assertTrue(filter.processBitMaps(word -> unmatched.remove(Long.valueOf(word))),
                    "processBitMaps should complete successfully (return true)");
            assertTrue(unmatched.isEmpty(),
                    "processBitMaps should emit exactly the same words that were merged in");
        }

        // --- Reject all-Long.MAX_VALUE bitmap (bits exceed shape bounds) ---
        // Long.MAX_VALUE sets bits 0-62 of each 64-bit word; many of those
        // positions lie outside the 72-bit shape's valid range.
        final long[] oversizedBitmap = new long[bitMapCount];
        Arrays.fill(oversizedBitmap, Long.MAX_VALUE);
        final BitMapExtractor oversizedExtractor = BitMapExtractor.fromBitMapArray(oversizedBitmap);
        final BloomFilter filterForOversized = createEmptyFilter(TEST_SHAPE);
        assertThrows(IllegalArgumentException.class,
                () -> filterForOversized.merge(oversizedExtractor),
                "Merging bit-maps that set bits beyond the shape's numberOfBits should throw");

        // --- Reject MSB-only bitmap when shape is only 32 bits wide ---
        // The single long 0x8000_0000_0000_0000L sets only bit 63, which is
        // outside a 32-bit shape even though both bitmaps have length 1.
        final BitMapExtractor msbExtractor =
                BitMapExtractor.fromBitMapArray(0x80_00_00_00_00_00_00_00L);
        final BloomFilter narrowFilter = createEmptyFilter(Shape.fromKM(3, 32));
        assertThrows(IllegalArgumentException.class,
                () -> narrowFilter.merge(msbExtractor),
                "Merging a bit-map whose MSB is set into a 32-bit shape should throw");
    }
}
