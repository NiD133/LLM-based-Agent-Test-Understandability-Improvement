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

public class SimpleBloomFilterTest_testContains {

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

    @Test
    final void testContains() {
        BloomFilter bf1 = createFilter(getTestShape(), TestingHashers.FROM1);
        final BloomFilter bf2 = TestingHashers.populateFromHashersFrom1AndFrom11(createEmptyFilter(getTestShape()));
        assertTrue(bf1.contains(bf1), "BF1 Should contain itself");
        assertTrue(bf2.contains(bf2), "BF2 Should contain itself");
        assertFalse(bf1.contains(bf2), "BF1 should not contain BF2");
        assertTrue(bf2.contains(bf1), "BF2 should contain BF1");
        assertTrue(bf2.contains(new IncrementingHasher(1, 1)), "BF2 Should contain this hasher");
        assertFalse(bf2.contains(new IncrementingHasher(1, 3)), "BF2 Should not contain this hasher");
        IndexExtractor indexExtractor = new IncrementingHasher(1, 1).indices(getTestShape());
        assertTrue(bf2.contains(indexExtractor), "BF2 Should contain this hasher");
        indexExtractor = new IncrementingHasher(1, 3).indices(getTestShape());
        assertFalse(bf2.contains(indexExtractor), "BF2 Should not contain this hasher");
        BitMapExtractor bitMapExtractor = BitMapExtractor.fromIndexExtractor(new IncrementingHasher(1, 1).indices(getTestShape()), getTestShape().getNumberOfBits());
        assertTrue(bf2.contains(bitMapExtractor), "BF2 Should contain this hasher");
        bitMapExtractor = BitMapExtractor.fromIndexExtractor(new IncrementingHasher(1, 3).indices(getTestShape()), getTestShape().getNumberOfBits());
        assertFalse(bf2.contains(bitMapExtractor), "BF2 Should not contain this hasher");
        // Test different lengths
        bf1 = createFilter(getTestShape(), TestingHashers.FROM1);
        final BloomFilter bf3 = createFilter(Shape.fromKM(getTestShape().getNumberOfHashFunctions(), Long.SIZE - 1), TestingHashers.FROM1);
        assertTrue(bf1.contains(bf3));
        assertTrue(bf3.contains(bf1));
        final BloomFilter bf4 = TestingHashers.populateRange(createEmptyFilter(Shape.fromKM(getTestShape().getNumberOfHashFunctions(), Long.SIZE - 1)), 1, 11 + getTestShape().getNumberOfHashFunctions());
        assertFalse(bf1.contains(bf4));
        assertTrue(bf4.contains(bf1));
    }
}
