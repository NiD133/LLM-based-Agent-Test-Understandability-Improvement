package org.apache.commons.collections4.bloomfilter;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import org.junit.jupiter.api.Test;

public class SparseBloomFilterTest_testCopy {

    protected SparseBloomFilter createEmptyFilter(final Shape shape) {
        return new SparseBloomFilter(shape);
    }

    protected final SparseBloomFilter createFilter(final Shape shape, final Hasher hasher) {
        final SparseBloomFilter bf = createEmptyFilter(shape);
        bf.merge(hasher);
        return bf;
    }

    protected Shape getTestShape() {
        return Shape.fromKM(17, 72);
    }

    /**
     * Verifies that {@link SparseBloomFilter#copy()} produces a distinct object that is
     * equal to the original in every observable property: bit-map contents, index contents,
     * cardinality, characteristics, estimated population, shape, emptiness, and fullness.
     * When {@code assertClass} is {@code true} the copy is also required to be the same
     * runtime type as the original.
     */
    protected void testCopy(final boolean assertClass) {
        final BloomFilter bf1 = createFilter(getTestShape(), TestingHashers.FROM1);
        assertNotEquals(0, bf1.cardinality());

        final BloomFilter copy = bf1.copy();

        assertNotSame(bf1, copy);
        assertArrayEquals(bf1.asBitMapArray(), copy.asBitMapArray());
        assertArrayEquals(bf1.asIndexArray(), copy.asIndexArray());
        assertEquals(bf1.cardinality(), copy.cardinality());
        assertEquals(bf1.characteristics(), copy.characteristics());
        assertEquals(bf1.estimateN(), copy.estimateN());
        if (assertClass) {
            assertEquals(bf1.getClass(), copy.getClass());
        }
        assertEquals(bf1.getShape(), copy.getShape());
        assertEquals(bf1.isEmpty(), copy.isEmpty());
        assertEquals(bf1.isFull(), copy.isFull());
    }

    @Test
    void testCopy() {
        testCopy(true);
    }
}
