package org.apache.commons.collections4.bloomfilter;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.Test;

/**
 * Tests {@link BloomFilter#estimateIntersection(BloomFilter)} using a
 * {@link SparseBloomFilter} as the concrete implementation.
 *
 * <p>The test shape uses k=17 hash functions and m=72 bits.</p>
 */
public class SparseBloomFilterTest_testEstimateIntersection {

    /** Shape shared by all filters in this test class (k=17, m=72). */
    private Shape testShape() {
        return Shape.fromKM(17, 72);
    }

    /** Returns a new, empty {@link SparseBloomFilter} with the test shape. */
    private SparseBloomFilter emptyFilter() {
        return new SparseBloomFilter(testShape());
    }

    /** Returns a filter pre-populated by the given hasher. */
    private BloomFilter filterFrom(final Hasher hasher) {
        final SparseBloomFilter bf = emptyFilter();
        bf.merge(hasher);
        return bf;
    }

    /**
     * Verifies that {@code estimateIntersection} produces correct counts across a
     * representative set of filter combinations:
     *
     * <ul>
     *   <li>Overlapping filters with a known shared item count.</li>
     *   <li>One filter is fully populated ("infinite").</li>
     *   <li>One filter is empty (intersection must be 0).</li>
     *   <li>Disjoint bit-range filters (must throw {@link IllegalArgumentException}).</li>
     *   <li>Two fully-populated filters (must return {@link Integer#MAX_VALUE}).</li>
     * </ul>
     */
    @Test
    void testEstimateIntersection() {
        // singleItemFilter: populated with FROM1 — represents exactly 1 distinct item.
        final BloomFilter singleItemFilter = filterFrom(TestingHashers.FROM1);

        // twoItemFilter: populated with FROM1 and FROM11 — represents exactly 2 distinct items,
        // one of which (FROM1) is shared with singleItemFilter.
        final BloomFilter twoItemFilter =
                TestingHashers.populateFromHashersFrom1AndFrom11(emptyFilter());

        // fullFilter: every bit is set — represents a conceptually "infinite" population.
        final BloomFilter fullFilter =
                TestingHashers.populateEntireFilter(emptyFilter());

        // --- Overlapping filters ---
        // singleItemFilter ∩ twoItemFilter → 1 shared item (FROM1).
        assertEquals(1, singleItemFilter.estimateIntersection(twoItemFilter));
        // Intersection is symmetric.
        assertEquals(1, twoItemFilter.estimateIntersection(singleItemFilter));

        // singleItemFilter (1 item) ∩ fullFilter (all bits set) → 1.
        assertEquals(1, singleItemFilter.estimateIntersection(fullFilter));

        // Verify symmetry once more: twoItemFilter ∩ singleItemFilter → 1.
        assertEquals(1, twoItemFilter.estimateIntersection(singleItemFilter));

        // fullFilter ∩ twoItemFilter → 2 (fullFilter "contains" every item).
        assertEquals(2, fullFilter.estimateIntersection(twoItemFilter));

        // --- Empty filter ---
        // Anything ∩ empty filter → 0.
        final BloomFilter emptyFilter = emptyFilter();
        assertEquals(0, singleItemFilter.estimateIntersection(emptyFilter));
        assertEquals(0, emptyFilter.estimateIntersection(singleItemFilter));

        // --- Disjoint bit ranges ---
        // When no bits overlap the Jaccard-based estimate cannot be computed;
        // estimateIntersection must signal this via IllegalArgumentException.
        final int midPoint = testShape().getNumberOfBits() / 2;
        final BloomFilter lowerHalfFilter =
                TestingHashers.populateRange(emptyFilter(), 0, midPoint);
        final BloomFilter upperHalfFilter =
                TestingHashers.populateRange(emptyFilter(), midPoint + 1,
                        testShape().getNumberOfBits() - 1);
        assertThrows(IllegalArgumentException.class,
                () -> lowerHalfFilter.estimateIntersection(upperHalfFilter));

        // --- Infinite ∩ infinite ---
        // Two fully-populated filters produce an unbounded estimate → Integer.MAX_VALUE.
        assertEquals(Integer.MAX_VALUE, fullFilter.estimateIntersection(fullFilter));
    }
}
