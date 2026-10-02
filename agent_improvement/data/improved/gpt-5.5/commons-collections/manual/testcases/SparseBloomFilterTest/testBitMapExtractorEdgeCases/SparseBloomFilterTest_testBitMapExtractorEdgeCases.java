package org.apache.commons.collections4.bloomfilter;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class SparseBloomFilterTest_testBitMapExtractorEdgeCases {

    private Shape getTestShape() {
        return Shape.fromKM(17, 72);
    }

    private SparseBloomFilter createFilter(final Shape shape, final IndexExtractor extractor) {
        final SparseBloomFilter bloomFilter = new SparseBloomFilter(shape);
        bloomFilter.merge(extractor);
        return bloomFilter;
    }

    @Test
    void testBitMapExtractorEdgeCases() {
        final int[] valuesAcrossBitmapBoundary = { 1, 2, 3, 4, 5, 6, 7, 8, 9, 65, 66, 67, 68, 69, 70, 71 };
        BloomFilter<?> bloomFilter = createFilter(getTestShape(), IndexExtractor.fromIndexArray(valuesAcrossBitmapBoundary));

        final int[] passes = new int[1];
        assertFalse(bloomFilter.processBitMaps(bitMap -> {
            passes[0]++;
            return false;
        }));
        assertEquals(1, passes[0]);

        bloomFilter = createFilter(getTestShape(), IndexExtractor.fromIndexArray(valuesAcrossBitmapBoundary));
        passes[0] = 0;
        assertFalse(bloomFilter.processBitMaps(bitMap -> {
            final boolean keepProcessing = passes[0] == 0;
            if (keepProcessing) {
                passes[0]++;
            }
            return keepProcessing;
        }));
        assertEquals(1, passes[0]);

        final int[] valuesOnlyInFirstBitmap = { 1, 2, 3, 4 };
        bloomFilter = createFilter(getTestShape(), IndexExtractor.fromIndexArray(valuesOnlyInFirstBitmap));
        passes[0] = 0;
        assertTrue(bloomFilter.processBitMaps(bitMap -> {
            passes[0]++;
            return true;
        }));
        assertEquals(2, passes[0]);

        bloomFilter = createFilter(getTestShape(), IndexExtractor.fromIndexArray(valuesOnlyInFirstBitmap));
        passes[0] = 0;
        assertFalse(bloomFilter.processBitMaps(bitMap -> {
            final boolean keepProcessing = passes[0] == 0;
            if (keepProcessing) {
                passes[0]++;
            }
            return keepProcessing;
        }));
        assertEquals(1, passes[0]);
    }
}
