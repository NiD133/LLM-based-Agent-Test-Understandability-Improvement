package org.apache.commons.collections4.bloomfilter;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

public class SimpleBloomFilterTest_testBitMapExtractorSize {

    // Shape with k=17 hash functions and m=72 bits, shared across all test helpers
    private static final Shape TEST_SHAPE = Shape.fromKM(17, 72);

    private SimpleBloomFilter createEmptyFilter() {
        return new SimpleBloomFilter(TEST_SHAPE);
    }

    private SimpleBloomFilter createPopulatedFilter() {
        SimpleBloomFilter filter = new SimpleBloomFilter(TEST_SHAPE);
        filter.merge(TestingHashers.FROM1);
        return filter;
    }

    /**
     * Verifies that processBitMaps iterates over exactly as many long words as the
     * shape requires, for both a populated filter and an empty one.
     *
     * A SimpleBloomFilter always allocates BitMaps.numberOfBitMaps(shape) longs
     * regardless of content, so the iterator count must equal that value.
     */
    @Test
    void testBitMapExtractorSize() {
        int expectedBitMapCount = BitMaps.numberOfBitMaps(TEST_SHAPE);

        // Populated filter: processBitMaps must visit every allocated long word
        int[] populatedBitMapCount = {0};
        createPopulatedFilter().processBitMaps(bitMap -> {
            populatedBitMapCount[0]++;
            return true;
        });
        assertEquals(expectedBitMapCount, populatedBitMapCount[0]);

        // Empty filter: same expectation — allocation is shape-driven, not content-driven
        int[] emptyBitMapCount = {0};
        createEmptyFilter().processBitMaps(bitMap -> {
            emptyBitMapCount[0]++;
            return true;
        });
        assertEquals(expectedBitMapCount, emptyBitMapCount[0]);
    }
}
