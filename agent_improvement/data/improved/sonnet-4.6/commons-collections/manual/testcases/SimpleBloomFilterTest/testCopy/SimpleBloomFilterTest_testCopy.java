package org.apache.commons.collections4.bloomfilter;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import org.junit.jupiter.api.Test;

public class SimpleBloomFilterTest_testCopy {

    // k=17 hash functions, m=72 bits — a small shape adequate for unit testing
    private static final Shape TEST_SHAPE = Shape.fromKM(17, 72);

    private SimpleBloomFilter createFilter(final Shape shape, final Hasher hasher) {
        final SimpleBloomFilter bf = new SimpleBloomFilter(shape);
        bf.merge(hasher);
        return bf;
    }

    @Test
    void testCopy() {
        // Create a non-empty source filter so the copy is meaningfully populated
        final SimpleBloomFilter source = createFilter(TEST_SHAPE, TestingHashers.FROM1);
        assertNotEquals(0, source.cardinality(), "Source filter must be non-empty for copy to be meaningful");

        // copy() must return a logically equal but physically distinct instance
        final SimpleBloomFilter copy = source.copy();
        assertNotSame(source, copy, "copy() must return a new object, not the same reference");

        // All observable properties must match between the source and its copy
        assertArrayEquals(source.asBitMapArray(), copy.asBitMapArray(), "Bit maps must be identical");
        assertArrayEquals(source.asIndexArray(), copy.asIndexArray(), "Index arrays must be identical");
        assertEquals(source.cardinality(), copy.cardinality(), "Cardinality must match");
        assertEquals(source.characteristics(), copy.characteristics(), "Characteristics must match");
        assertEquals(source.estimateN(), copy.estimateN(), "Estimated element count must match");
        assertEquals(source.getClass(), copy.getClass(), "copy() must return the same concrete type");
        assertEquals(source.getShape(), copy.getShape(), "Shape must match");
        assertEquals(source.isEmpty(), copy.isEmpty(), "isEmpty() must match");
        assertEquals(source.isFull(), copy.isFull(), "isFull() must match");
    }
}
