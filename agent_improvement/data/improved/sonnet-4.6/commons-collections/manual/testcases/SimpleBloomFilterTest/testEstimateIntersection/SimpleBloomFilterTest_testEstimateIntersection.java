package org.apache.commons.collections4.bloomfilter;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.Test;

/**
 * Tests for {@link SimpleBloomFilter#estimateIntersection(BloomFilter)}.
 *
 * The test shape uses k=17 hash functions and m=72 bits, which gives
 * well-defined estimations for small populations.
 */
public class SimpleBloomFilterTest_testEstimateIntersection {

    // Shape shared across all test scenarios: 17 hash functions, 72 bits.
    private static final Shape TEST_SHAPE = Shape.fromKM(17, 72);

    private SimpleBloomFilter createEmptyFilter() {
        return new SimpleBloomFilter(TEST_SHAPE);
    }

    private SimpleBloomFilter createFilter(final Hasher hasher) {
        final SimpleBloomFilter bf = createEmptyFilter();
        bf.merge(hasher);
        return bf;
    }

    /**
     * Verifies that {@code estimateIntersection} returns the correct estimated
     * number of common elements for various filter combinations:
     * <ul>
     *   <li>overlapping single-element and two-element filters → 1</li>
     *   <li>any filter intersected with the full (all-bits-set) filter → same as its own estimate</li>
     *   <li>any filter intersected with an empty filter → 0</li>
     *   <li>two fully disjoint filters (no shared bits) → throws {@link IllegalArgumentException}</li>
     *   <li>the full filter intersected with itself → {@link Integer#MAX_VALUE} (infinite estimate)</li>
     * </ul>
     */
    @Test
    void testEstimateIntersection() {
        // A filter populated by a single hasher starting at index 1.
        final BloomFilter singleElementFilter = createFilter(TestingHashers.FROM1);

        // A filter populated by two hashers (starting at 1 and at 11), representing two elements.
        final BloomFilter twoElementFilter =
                TestingHashers.populateFromHashersFrom1AndFrom11(createEmptyFilter());

        // A filter with every bit set — represents an "infinite" or fully-saturated population.
        final BloomFilter fullFilter = TestingHashers.populateEntireFilter(createEmptyFilter());

        // --- Overlapping filters ---
        // FROM1 is contained in the two-element filter, so the intersection estimates 1 element.
        assertEquals(1, singleElementFilter.estimateIntersection(twoElementFilter));
        assertEquals(1, twoElementFilter.estimateIntersection(singleElementFilter));

        // FROM1 is also contained in the full filter, so the estimate remains 1.
        assertEquals(1, singleElementFilter.estimateIntersection(fullFilter));

        // Symmetric check: two-element ∩ single-element still estimates 1.
        assertEquals(1, twoElementFilter.estimateIntersection(singleElementFilter));

        // The full filter contains all two-element bits, so the intersection estimate equals 2.
        assertEquals(2, fullFilter.estimateIntersection(twoElementFilter));

        // --- Empty filter ---
        final BloomFilter emptyFilter = createEmptyFilter();
        assertEquals(0, singleElementFilter.estimateIntersection(emptyFilter));
        assertEquals(0, emptyFilter.estimateIntersection(singleElementFilter));

        // --- Disjoint filters (no shared bits) ---
        // When two filters share no bits, the inclusion-exclusion estimate goes negative,
        // so estimateIntersection must throw IllegalArgumentException.
        final int midPoint = TEST_SHAPE.getNumberOfBits() / 2;
        final BloomFilter lowerHalfFilter =
                TestingHashers.populateRange(createEmptyFilter(), 0, midPoint);
        final BloomFilter upperHalfFilter =
                TestingHashers.populateRange(createEmptyFilter(), midPoint + 1, TEST_SHAPE.getNumberOfBits() - 1);
        assertThrows(IllegalArgumentException.class,
                () -> lowerHalfFilter.estimateIntersection(upperHalfFilter));

        // --- Full filter intersected with itself ---
        // A saturated filter has an infinite population estimate, so the intersection
        // of two infinite estimates is represented as Integer.MAX_VALUE.
        assertEquals(Integer.MAX_VALUE, fullFilter.estimateIntersection(fullFilter));
    }
}
