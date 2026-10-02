package org.apache.commons.collections4.bloomfilter;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class SparseBloomFilterTest_testContains {

    private SparseBloomFilter createEmptyFilter(final Shape shape) {
        return new SparseBloomFilter(shape);
    }

    private SparseBloomFilter createFilter(final Shape shape, final Hasher hasher) {
        final SparseBloomFilter filter = createEmptyFilter(shape);
        filter.merge(hasher);
        return filter;
    }

    private Shape getTestShape() {
        return Shape.fromKM(17, 72);
    }

    @Test
    final void testContains() {
        final Shape testShape = getTestShape();
        final BloomFilter bf1 = createFilter(testShape, TestingHashers.FROM1);
        final BloomFilter bf2 = TestingHashers.populateFromHashersFrom1AndFrom11(createEmptyFilter(testShape));

        assertTrue(bf1.contains(bf1), "BF1 Should contain itself");
        assertTrue(bf2.contains(bf2), "BF2 Should contain itself");
        assertFalse(bf1.contains(bf2), "BF1 should not contain BF2");
        assertTrue(bf2.contains(bf1), "BF2 should contain BF1");

        assertContainsHasherRepresentations(bf2, testShape, new IncrementingHasher(1, 1), true);
        assertContainsHasherRepresentations(bf2, testShape, new IncrementingHasher(1, 3), false);

        final Shape singleBitMapShape = Shape.fromKM(testShape.getNumberOfHashFunctions(), Long.SIZE - 1);
        final BloomFilter bf3 = createFilter(singleBitMapShape, TestingHashers.FROM1);

        assertTrue(bf1.contains(bf3));
        assertTrue(bf3.contains(bf1));

        final BloomFilter bf4 = TestingHashers.populateRange(
                createEmptyFilter(singleBitMapShape),
                1,
                11 + testShape.getNumberOfHashFunctions());

        assertFalse(bf1.contains(bf4));
        assertTrue(bf4.contains(bf1));
    }

    private void assertContainsHasherRepresentations(
            final BloomFilter filter,
            final Shape shape,
            final Hasher hasher,
            final boolean expected) {
        final IndexExtractor indexExtractor = hasher.indices(shape);
        final BitMapExtractor bitMapExtractor = BitMapExtractor.fromIndexExtractor(
                indexExtractor,
                shape.getNumberOfBits());

        if (expected) {
            assertTrue(filter.contains(hasher), "BF2 Should contain this hasher");
            assertTrue(filter.contains(indexExtractor), "BF2 Should contain this hasher");
            assertTrue(filter.contains(bitMapExtractor), "BF2 Should contain this hasher");
        } else {
            assertFalse(filter.contains(hasher), "BF2 Should not contain this hasher");
            assertFalse(filter.contains(indexExtractor), "BF2 Should not contain this hasher");
            assertFalse(filter.contains(bitMapExtractor), "BF2 Should not contain this hasher");
        }
    }
}
