package org.apache.commons.collections4.bloomfilter;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SimpleBloomFilterTest_testMergeWithBitMapExtractor {

    // Shape used across all tests: 17 hash functions, 72 bits
    private static final Shape TEST_SHAPE = Shape.fromKM(17, 72);

    private SimpleBloomFilter createEmptyFilter(final Shape shape) {
        return new SimpleBloomFilter(shape);
    }

    private BloomFilter createFilter(final Shape shape, final BitMapExtractor extractor) {
        final SimpleBloomFilter bf = createEmptyFilter(shape);
        bf.merge(extractor);
        return bf;
    }

    @Test
    void testMergeWithBitMapExtractor() {
        final int bitMapCount = BitMaps.numberOfBitMaps(TEST_SHAPE);

        // Verify that merging valid bit maps produces a filter with exactly the same bit patterns
        for (int i = 0; i < 5; i++) {
            final long[] bitMaps = new long[bitMapCount];
            for (final int idx : DefaultIndexExtractorTest.generateIntArray(
                    TEST_SHAPE.getNumberOfHashFunctions(), TEST_SHAPE.getNumberOfBits())) {
                BitMaps.set(bitMaps, idx);
            }
            final BloomFilter filter = createFilter(TEST_SHAPE, BitMapExtractor.fromBitMapArray(bitMaps));

            // Collect expected bit map words, then verify the filter reports exactly those words
            final List<Long> expectedBitMaps = new ArrayList<>();
            for (final long word : bitMaps) {
                expectedBitMaps.add(word);
            }
            assertTrue(filter.processBitMaps(word -> expectedBitMaps.remove(Long.valueOf(word))));
            assertTrue(expectedBitMaps.isEmpty());
        }

        // Merging an extractor whose bit maps have all bits set must fail because
        // the highest bits exceed the shape's numberOfBits limit
        final long[] allBitsSet = new long[bitMapCount];
        Arrays.fill(allBitsSet, Long.MAX_VALUE);
        final BitMapExtractor oversizedExtractor = BitMapExtractor.fromBitMapArray(allBitsSet);
        final BloomFilter filterForOversized = createEmptyFilter(TEST_SHAPE);
        assertThrows(IllegalArgumentException.class, () -> filterForOversized.merge(oversizedExtractor));

        // Merging a single bit map with only bit 63 set must fail for a 32-bit shape
        // because bit 63 is beyond the shape's numberOfBits (32), even though the
        // extractor and filter both produce exactly one bitmap word
        final BitMapExtractor highBitExtractor = BitMapExtractor.fromBitMapArray(0x80_00_00_00_00_00_00_00L);
        final BloomFilter filterFor32BitShape = createEmptyFilter(Shape.fromKM(3, 32));
        assertThrows(IllegalArgumentException.class, () -> filterFor32BitShape.merge(highBitExtractor));
    }
}
