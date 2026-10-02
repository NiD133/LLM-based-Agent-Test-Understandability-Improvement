package org.apache.commons.collections4.bloomfilter;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.BitSet;
import java.util.List;

public class SimpleBloomFilterTest_testMerge {

    protected SimpleBloomFilter createEmptyFilter(final Shape shape) {
        return new SimpleBloomFilter(shape);
    }

    private void assertFailedIndexExtractorConstructor(final Shape shape, final int[] values) {
        final IndexExtractor indices = IndexExtractor.fromIndexArray(values);
        assertThrows(IllegalArgumentException.class, () -> createFilter(shape, indices));
    }

    private void assertIndexExtractorMerge(final Shape shape, final int[] values, final int[] expected) {
        final IndexExtractor indices = IndexExtractor.fromIndexArray(values);
        final BloomFilter filter = createFilter(shape, indices);
        final List<Integer> lst = new ArrayList<>();
        filter.processIndices(x -> {
            lst.add(x);
            return true;
        });
        assertEquals(expected.length, lst.size());
        for (final int value : expected) {
            assertTrue(lst.contains(Integer.valueOf(value)), "Missing " + value);
        }
    }

    /**
     * Creates an empty version of the BloomFilter implementation we are testing.
     *
     * @param shape the shape of the filter.
     * @return a BloomFilter implementation.
     */
    private T __super_createEmptyFilter(Shape shape);

    /**
     * Creates the BloomFilter implementation we are testing.
     *
     * @param shape the shape of the filter.
     * @param extractor A BitMap extractor to build the filter with.
     * @return a BloomFilter implementation.
     */
    protected final T createFilter(final Shape shape, final BitMapExtractor extractor) {
        final T bf = createEmptyFilter(shape);
        bf.merge(extractor);
        return bf;
    }

    /**
     * Creates the BloomFilter implementation we are testing.
     *
     * @param shape the shape of the filter.
     * @param hasher the hasher to use to create the filter.
     * @return a BloomFilter implementation.
     */
    protected final T createFilter(final Shape shape, final Hasher hasher) {
        final T bf = createEmptyFilter(shape);
        bf.merge(hasher);
        return bf;
    }

    /**
     * Creates the BloomFilter implementation we are testing.
     *
     * @param shape the shape of the filter.
     * @param extractor An Index extractor to build the filter with.
     * @return a BloomFilter implementation.
     */
    protected final T createFilter(final Shape shape, final IndexExtractor extractor) {
        final T bf = createEmptyFilter(shape);
        bf.merge(extractor);
        return bf;
    }

    /**
     * The shape of the Bloom filters for testing.
     * <ul>
     *  <li>Hash functions (k) = 17
     *  <li>Number of bits (m) = 72
     * </ul>
     * @return the testing shape.
     */
    protected Shape getTestShape() {
        return Shape.fromKM(17, 72);
    }

    /**
     * Tests cardinality and isEmpty. Bloom filter must be able to accept multiple
     * IndexExtractor merges until all the bits are populated.
     *
     * @param bf The Bloom filter to test.
     */
    protected void testCardinalityAndIsEmpty(final BloomFilter bf) {
        assertTrue(bf.isEmpty());
        assertEquals(0, bf.cardinality());
        for (int i = 0; i < getTestShape().getNumberOfBits(); i++) {
            bf.merge(IndexExtractor.fromIndexArray(i));
            assertFalse(bf.isEmpty(), "Wrong value at " + i);
            assertEquals(i + 1, bf.cardinality(), "Wrong value at " + i);
        }
        // check operations in reverse order
        bf.clear();
        assertEquals(0, bf.cardinality());
        assertTrue(bf.isEmpty());
        for (int i = 0; i < getTestShape().getNumberOfBits(); i++) {
            bf.merge(IndexExtractor.fromIndexArray(i));
            assertEquals(i + 1, bf.cardinality(), "Wrong value at " + i);
            assertFalse(bf.isEmpty(), "Wrong value at " + i);
        }
    }

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

    /**
     * Tests that merging bloom filters works as expected with a generic BloomFilter.
     */
    @Test
    final void testMerge() {
        final BloomFilter bf1 = createFilter(getTestShape(), TestingHashers.FROM1);
        final BloomFilter bf2 = createFilter(getTestShape(), TestingHashers.FROM11);
        final BloomFilter bf3 = bf1.copy();
        bf3.merge(bf2);
        // test with BloomFilter
        final long[] bf1Val = bf1.asBitMapArray();
        final long[] bf2Val = bf2.asBitMapArray();
        for (int i = 0; i < bf1Val.length; i++) {
            bf1Val[i] |= bf2Val[i];
        }
        bf1.merge(bf2);
        final long[] bf1New = bf1.asBitMapArray();
        for (int i = 0; i < bf1Val.length; i++) {
            assertEquals(bf1Val[i], bf1New[i], "Bad value at " + i);
        }
        assertTrue(bf1.contains(bf2), "Should contain bf2");
        assertTrue(bf1.contains(bf3), "Should contain bf3");
        // test with hasher
        final BloomFilter bf4 = createFilter(getTestShape(), TestingHashers.FROM1);
        bf4.merge(TestingHashers.FROM11);
        assertTrue(bf4.contains(bf2), "Should contain Bf2");
        assertTrue(bf4.contains(bf3), "Should contain Bf3");
        // test with hasher returning numbers out of range
        assertThrows(IllegalArgumentException.class, () -> bf1.merge(new BadHasher(bf1.getShape().getNumberOfBits())));
        assertThrows(IllegalArgumentException.class, () -> bf1.merge(new BadHasher(-1)));
        // test error when bloom filter returns values out of range
        final Shape s = Shape.fromKM(getTestShape().getNumberOfHashFunctions(), getTestShape().getNumberOfBits() * 3);
        final Hasher h = new IncrementingHasher(getTestShape().getNumberOfBits() * 2, 1);
        final BloomFilter bf5 = new SimpleBloomFilter(s);
        bf5.merge(h);
        assertThrows(IllegalArgumentException.class, () -> bf1.merge(bf5));
        final BloomFilter bf6 = new SparseBloomFilter(s);
        bf6.merge(h);
        assertThrows(IllegalArgumentException.class, () -> bf1.merge(bf6));
    }
}
