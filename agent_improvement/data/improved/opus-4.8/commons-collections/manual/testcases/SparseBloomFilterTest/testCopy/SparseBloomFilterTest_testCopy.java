package org.apache.commons.collections4.bloomfilter;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotSame;

import org.junit.jupiter.api.Test;

/**
 * Verifies that {@link SparseBloomFilter#copy()} produces an independent instance
 * that is equivalent to the original in every observable property.
 */
public class SparseBloomFilterTest_testCopy {

    /**
     * The shape used for the filters under test: 17 hash functions over 72 bits.
     */
    private static final Shape TEST_SHAPE = Shape.fromKM(17, 72);

    /**
     * Builds a non-empty filter by merging the given hasher into a fresh filter.
     */
    private static SparseBloomFilter createFilter(final Shape shape, final Hasher hasher) {
        final SparseBloomFilter filter = new SparseBloomFilter(shape);
        filter.merge(hasher);
        return filter;
    }

    @Test
    void testCopy() {
        final BloomFilter original = createFilter(TEST_SHAPE, TestingHashers.FROM1);
        assertNotEquals(0, original.cardinality(), "Original filter should not be empty");

        final BloomFilter copy = original.copy();

        // The copy must be a distinct object...
        assertNotSame(original, copy, "copy() must return a new instance");

        // ...that is otherwise indistinguishable from the original.
        assertArrayEquals(original.asBitMapArray(), copy.asBitMapArray());
        assertArrayEquals(original.asIndexArray(), copy.asIndexArray());
        assertEquals(original.cardinality(), copy.cardinality());
        assertEquals(original.characteristics(), copy.characteristics());
        assertEquals(original.estimateN(), copy.estimateN());
        assertEquals(original.getClass(), copy.getClass());
        assertEquals(original.getShape(), copy.getShape());
        assertEquals(original.isEmpty(), copy.isEmpty());
        assertEquals(original.isFull(), copy.isFull());
    }
}
