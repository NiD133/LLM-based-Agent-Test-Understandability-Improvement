package org.apache.commons.collections4.bloomfilter;

import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

public class SimpleBloomFilterTest_testEmptyAfterMergeWithNothing {

    private SimpleBloomFilter createEmptyFilter(final Shape shape) {
        return new SimpleBloomFilter(shape);
    }

    private Shape getTestShape() {
        return Shape.fromKM(17, 72);
    }

    /**
     * Verifies that merging an empty IndexExtractor into an empty filter
     * leaves the filter in an empty state.
     *
     * When merge(IndexExtractor) is called, SimpleBloomFilter sets its cached
     * cardinality to -1 to signal "needs recompute". isEmpty() must still return
     * true by inspecting the underlying bit map, which remains all-zero because
     * no indices were actually merged.
     */
    @Test
    void testEmptyAfterMergeWithNothing() {
        final BloomFilter bf = createEmptyFilter(getTestShape());

        // Merge an extractor that produces zero indices — equivalent to merging nothing
        bf.merge(IndexExtractor.fromIndexArray());

        // The filter must still be considered empty even though a merge was performed
        assertTrue(bf.isEmpty());
    }
}
