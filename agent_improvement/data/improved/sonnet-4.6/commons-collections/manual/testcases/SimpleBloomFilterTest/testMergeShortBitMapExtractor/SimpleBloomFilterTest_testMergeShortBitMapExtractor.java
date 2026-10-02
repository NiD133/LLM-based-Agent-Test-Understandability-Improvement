package org.apache.commons.collections4.bloomfilter;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

public class SimpleBloomFilterTest_testMergeShortBitMapExtractor {

    protected SimpleBloomFilter createEmptyFilter(final Shape shape) {
        return new SimpleBloomFilter(shape);
    }

    // Shape: 17 hash functions, 72 bits — requires ceil(72/64) = 2 longs
    protected Shape getTestShape() {
        return Shape.fromKM(17, 72);
    }

    @Test
    void testMergeShortBitMapExtractor() {
        final SimpleBloomFilter filter = createEmptyFilter(getTestShape());

        // Provide only 1 long instead of the 2 that the shape requires.
        // 2L = 0b10 in binary: only bit 1 is set.
        final BitMapExtractor shortExtractor = p -> p.test(2L);

        // Merging fewer bitmaps than the shape expects should still succeed
        assertTrue(filter.merge(shortExtractor));

        // Only bit 1 is set (from 2L), so cardinality must be exactly 1
        assertEquals(1, filter.cardinality());
    }
}
