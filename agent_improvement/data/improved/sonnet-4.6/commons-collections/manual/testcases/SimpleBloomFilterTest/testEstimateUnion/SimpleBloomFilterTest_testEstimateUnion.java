package org.apache.commons.collections4.bloomfilter;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

/**
 * Tests {@link SimpleBloomFilter#estimateUnion(BloomFilter)}.
 *
 * <p>The test shape is Shape.fromKM(17, 72). Each hasher (FROM1, FROM11)
 * populates a distinct set of bits representing one logical item, so:
 * <ul>
 *   <li>union of two distinct single-item filters → estimate 2</li>
 *   <li>union of a single-item filter with an empty filter → estimate 1</li>
 * </ul>
 */
public class SimpleBloomFilterTest_testEstimateUnion {

    private static final Shape TEST_SHAPE = Shape.fromKM(17, 72);

    private SimpleBloomFilter createEmptyFilter() {
        return new SimpleBloomFilter(TEST_SHAPE);
    }

    private SimpleBloomFilter createFilterFromHasher(final Hasher hasher) {
        final SimpleBloomFilter bf = createEmptyFilter();
        bf.merge(hasher);
        return bf;
    }

    /**
     * Tests that estimateUnion returns the correct estimated item count for both
     * the two-distinct-items case and the one-item-plus-empty case, and that the
     * operation is symmetric.
     */
    @Test
    void testEstimateUnion() {
        final SimpleBloomFilter filterA = createFilterFromHasher(TestingHashers.FROM1);
        final SimpleBloomFilter filterB = createFilterFromHasher(TestingHashers.FROM11);

        // Two distinct single-item filters: the union should estimate 2 items.
        assertEquals(2, filterA.estimateUnion(filterB),
                "Union of two distinct single-item filters should estimate 2");
        assertEquals(2, filterB.estimateUnion(filterA),
                "estimateUnion should be symmetric");

        // Union of a populated filter with an empty filter should estimate 1 item.
        final SimpleBloomFilter emptyFilter = createEmptyFilter();
        assertEquals(1, filterA.estimateUnion(emptyFilter),
                "Union of a single-item filter with an empty filter should estimate 1");
        assertEquals(1, emptyFilter.estimateUnion(filterA),
                "estimateUnion with empty filter should be symmetric");
    }
}
