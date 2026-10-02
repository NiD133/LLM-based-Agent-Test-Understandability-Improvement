package org.apache.commons.collections4.bloomfilter;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotSame;

import org.junit.jupiter.api.Test;

/**
 * Tests {@link SimpleBloomFilter#copy()}.
 */
public class SimpleBloomFilterTest_testCopy {

    /** Shape used for the filters under test: 17 hash functions over 72 bits. */
    private static final Shape TEST_SHAPE = Shape.fromKM(17, 72);

    /**
     * Builds a non-empty filter by merging the given hasher into a fresh filter.
     */
    private SimpleBloomFilter newFilterWith(final Hasher hasher) {
        final SimpleBloomFilter filter = new SimpleBloomFilter(TEST_SHAPE);
        filter.merge(hasher);
        return filter;
    }

    /**
     * A copy must be a distinct instance that is equal to the original in every observable
     * property: bit maps, indices, cardinality, characteristics, estimated count, type, shape,
     * and emptiness/fullness.
     */
    @Test
    void testCopy() {
        final BloomFilter original = newFilterWith(TestingHashers.FROM1);
        assertNotEquals(0, original.cardinality());

        final BloomFilter copy = original.copy();

        assertNotSame(original, copy);
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
