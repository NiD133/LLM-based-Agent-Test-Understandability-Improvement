package org.apache.commons.collections4.bloomfilter;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.shaded.org.mockito.Mockito.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.apache.commons.collections4.bloomfilter.BitMapExtractor;
import org.apache.commons.collections4.bloomfilter.BloomFilter;
import org.apache.commons.collections4.bloomfilter.CountingLongPredicate;
import org.apache.commons.collections4.bloomfilter.EnhancedDoubleHasher;
import org.apache.commons.collections4.bloomfilter.Hasher;
import org.apache.commons.collections4.bloomfilter.IndexExtractor;
import org.apache.commons.collections4.bloomfilter.LayerManager;
import org.apache.commons.collections4.bloomfilter.LayeredBloomFilter;
import org.apache.commons.collections4.bloomfilter.LongBiPredicate;
import org.apache.commons.collections4.bloomfilter.Shape;
import org.apache.commons.collections4.bloomfilter.SparseBloomFilter;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.evosuite.runtime.ViolatedAssumptionAnswer;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true,
        resetStaticState = true, separateClassLoader = false)
public class SparseBloomFilter_ESTest extends SparseBloomFilter_ESTest_scaffolding {

    /**
     * processBitMaps stops and returns false when the underlying LongBiPredicate
     * returns false after the first call. The filter being processed (shape 2x775)
     * is larger than the bitmap source array taken from the smaller filter (shape 8x8),
     * so the CountingLongPredicate wraps the short array and eventually returns false.
     */
    @Test(timeout = 4000)
    public void test00_processBitMaps_returnsFalseWhenPredicateFailsAfterFirstCall() throws Throwable {
        Shape smallShape = Shape.fromNM(8, 8);
        Shape largeShape = Shape.fromNM(2, 775);
        SparseBloomFilter smallFilter = new SparseBloomFilter(smallShape);
        SparseBloomFilter largeFilter = new SparseBloomFilter(largeShape);

        // Bitmap array from the small (empty) filter — one long of zeros
        long[] bitmapArray = smallFilter.asBitMapArray();

        // Predicate returns true on first test, then false — causing early termination
        LongBiPredicate biPredicate = mock(LongBiPredicate.class, new ViolatedAssumptionAnswer());
        doReturn(true, false).when(biPredicate).test(anyLong(), anyLong());

        CountingLongPredicate countingPredicate = new CountingLongPredicate(bitmapArray, biPredicate);
        boolean result = largeFilter.processBitMaps(countingPredicate);

        assertFalse("processBitMaps should return false when predicate fails", result);
    }

    /**
     * processBitMaps returns false immediately when the predicate always returns false.
     */
    @Test(timeout = 4000)
    public void test01_processBitMaps_returnsFalseWhenPredicateAlwaysFails() throws Throwable {
        Shape shape = Shape.fromNM(8, 8);
        SparseBloomFilter filter = new SparseBloomFilter(shape);

        long[] bitmapArray = filter.asBitMapArray();

        LongBiPredicate biPredicate = mock(LongBiPredicate.class, new ViolatedAssumptionAnswer());
        doReturn(false).when(biPredicate).test(anyLong(), anyLong());

        CountingLongPredicate countingPredicate = new CountingLongPredicate(bitmapArray, biPredicate);
        boolean result = filter.processBitMaps(countingPredicate);

        assertFalse("processBitMaps should return false when predicate immediately rejects", result);
    }

    /**
     * After merging a hasher into a filter, processBitMaps stops early when the predicate
     * fails on a bitmap element that does not match the single-element reference array.
     */
    @Test(timeout = 4000)
    public void test02_processBitMaps_returnsFalseAfterMergeWhenPredicateFails() throws Throwable {
        Shape shape = Shape.fromNM(8, 4135);
        EnhancedDoubleHasher hasher = new EnhancedDoubleHasher(new byte[7]);
        SparseBloomFilter filter = new SparseBloomFilter(shape);

        boolean mergeResult = filter.merge((Hasher) hasher);

        // Single-element array of zeros used as the reference bitmap
        long[] singleZeroArray = new long[1];
        LongBiPredicate biPredicate = mock(LongBiPredicate.class, new ViolatedAssumptionAnswer());
        doReturn(true, false).when(biPredicate).test(anyLong(), anyLong());

        CountingLongPredicate countingPredicate = new CountingLongPredicate(singleZeroArray, biPredicate);
        boolean processResult = filter.processBitMaps(countingPredicate);

        assertFalse("processBitMaps should return false when predicate fails", processResult);
        assertFalse("processBitMaps result should differ from successful merge", processResult == mergeResult);
    }

    /**
     * Merging a filter with itself via BitMapExtractor succeeds and both merge operations
     * return true.
     */
    @Test(timeout = 4000)
    public void test03_merge_withSelfAsBitMapExtractor_returnsTrue() throws Throwable {
        Shape shape = Shape.fromKM(833, 833);
        SparseBloomFilter filter = new SparseBloomFilter(shape);
        EnhancedDoubleHasher hasher = new EnhancedDoubleHasher(-782L, 3371L);

        boolean firstMerge = filter.merge((Hasher) hasher);
        boolean secondMerge = filter.merge((BitMapExtractor) filter);

        assertTrue("First merge with hasher should succeed", firstMerge);
        assertTrue("Merging filter with itself should succeed", secondMerge);
        assertTrue("Both merge results should be equal", secondMerge == firstMerge);
    }

    /**
     * Merging an IndexExtractor containing a negative index throws
     * IllegalArgumentException because negative bit indices are invalid.
     */
    @Test(timeout = 4000)
    public void test04_merge_withNegativeIndex_throwsIllegalArgumentException() throws Throwable {
        Shape shape = Shape.fromNMK(31, Integer.MAX_VALUE, 31);
        SparseBloomFilter filter = new SparseBloomFilter(shape);

        int[] indicesWithNegative = new int[6];
        indicesWithNegative[0] = -2047;
        IndexExtractor indexExtractor = IndexExtractor.fromIndexArray(indicesWithNegative);

        try {
            filter.merge(indexExtractor);
            fail("Expecting exception: IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            verifyException("org.apache.commons.collections4.bloomfilter.SparseBloomFilter", e);
        }
    }

    /**
     * Merging an IndexExtractor whose index equals the number of bits in the shape
     * throws IllegalArgumentException because valid indices are 0..numberOfBits-1.
     */
    @Test(timeout = 4000)
    public void test05_merge_withIndexEqualToNumberOfBits_throwsIllegalArgumentException() throws Throwable {
        Shape shape = Shape.fromKM(31, 31);
        SparseBloomFilter filter = new SparseBloomFilter(shape);

        // Shape has 31 bits (indices 0–30); index 31 is out of range
        int[] indicesOutOfRange = new int[]{31};
        IndexExtractor indexExtractor = IndexExtractor.fromIndexArray(indicesOutOfRange);

        try {
            filter.merge(indexExtractor);
            fail("Expecting exception: IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            verifyException("org.apache.commons.collections4.bloomfilter.SparseBloomFilter", e);
        }
    }

    /**
     * Merging a mocked LayeredBloomFilter into a SparseBloomFilter succeeds
     * even when the layer manager's processBloomFilters returns false.
     */
    @Test(timeout = 4000)
    public void test06_merge_withLayeredBloomFilter_returnsTrue() throws Throwable {
        Shape shape = Shape.fromNM(8, 8);

        LayerManager<SparseBloomFilter> layerManager = (LayerManager<SparseBloomFilter>)
                mock(LayerManager.class, new ViolatedAssumptionAnswer());
        doReturn(false).when(layerManager).processBloomFilters(any(java.util.function.Predicate.class));

        LayeredBloomFilter<SparseBloomFilter> layeredFilter = new LayeredBloomFilter<>(shape, layerManager);
        SparseBloomFilter filter = new SparseBloomFilter(shape);

        boolean result = filter.merge((BloomFilter<?>) layeredFilter);

        assertTrue("Merging a LayeredBloomFilter should succeed", result);
    }

    /**
     * After merging a hasher into a filter shaped for 833 bits across 14 longs,
     * asBitMapArray returns an array of exactly 14 longs.
     */
    @Test(timeout = 4000)
    public void test07_asBitMapArray_afterHasherMerge_hasCorrectLength() throws Throwable {
        Shape shape = Shape.fromKM(833, 833);
        SparseBloomFilter filter = new SparseBloomFilter(shape);
        EnhancedDoubleHasher hasher = new EnhancedDoubleHasher(-782L, 3371L);

        filter.merge((Hasher) hasher);
        long[] bitmapArray = filter.asBitMapArray();

        assertEquals("Bitmap array should span 14 longs for an 833-bit shape", 14, bitmapArray.length);
    }

    /**
     * Calling clear() on a SparseBloomFilter resets it; characteristics() still
     * returns SPARSE (value 1) because the type does not change after clearing.
     */
    @Test(timeout = 4000)
    public void test08_clear_doesNotChangeCharacteristics() throws Throwable {
        Shape shape = Shape.fromKM(31, 31);
        SparseBloomFilter filter = new SparseBloomFilter(shape);

        filter.clear();

        assertEquals("Characteristics should remain SPARSE (1) after clear", 1, filter.characteristics());
    }

    /**
     * A newly created SparseBloomFilter with no elements merged is empty.
     */
    @Test(timeout = 4000)
    public void test09_isEmpty_onNewFilter_returnsTrue() throws Throwable {
        Shape shape = Shape.fromKM(833, 833);
        SparseBloomFilter filter = new SparseBloomFilter(shape);

        assertTrue("A newly created filter should be empty", filter.isEmpty());
    }

    /**
     * An empty SparseBloomFilter trivially contains itself because no bits need
     * to be present in the other filter.
     */
    @Test(timeout = 4000)
    public void test10_contains_emptyFilterContainsItself_returnsTrue() throws Throwable {
        Shape shape = Shape.fromNM(8, 4135);
        SparseBloomFilter filter = new SparseBloomFilter(shape);

        assertTrue("An empty filter should contain itself", filter.contains((BitMapExtractor) filter));
    }

    /**
     * estimateIntersection does not alter the filter's type; characteristics()
     * still returns SPARSE (value 1) after the call.
     */
    @Test(timeout = 4000)
    public void test11_estimateIntersection_doesNotChangeCharacteristics() throws Throwable {
        Shape shape = Shape.fromKM(833, 833);
        SparseBloomFilter filter = new SparseBloomFilter(shape);

        filter.estimateIntersection(filter);

        assertEquals("Characteristics should remain SPARSE (1) after estimateIntersection", 1, filter.characteristics());
    }
}
