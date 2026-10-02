/*
 * Refactored for understandability from EvoSuite-generated test.
 */

package org.apache.commons.collections4.bloomfilter;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.shaded.org.mockito.Mockito.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.apache.commons.collections4.bloomfilter.BitMapExtractor;
import org.apache.commons.collections4.bloomfilter.BloomFilter;
import org.apache.commons.collections4.bloomfilter.EnhancedDoubleHasher;
import org.apache.commons.collections4.bloomfilter.Hasher;
import org.apache.commons.collections4.bloomfilter.IndexExtractor;
import org.apache.commons.collections4.bloomfilter.LongBiPredicate;
import org.apache.commons.collections4.bloomfilter.Shape;
import org.apache.commons.collections4.bloomfilter.SimpleBloomFilter;
import org.apache.commons.collections4.bloomfilter.SparseBloomFilter;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.evosuite.runtime.ViolatedAssumptionAnswer;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class) @EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class SimpleBloomFilter_ESTest extends SimpleBloomFilter_ESTest_scaffolding {

  @Test(timeout = 4000)
  public void testMerge_BitMapExtractor_throwsWhenExtractorExceedsFilterBitMapCount()  throws Throwable  {
      Shape smallShape = Shape.fromNM(1097, 1097);
      SimpleBloomFilter smallFilter = new SimpleBloomFilter(smallShape);
      Shape largerShape = Shape.fromKM(1097, 5335);
      SimpleBloomFilter largerFilter = new SimpleBloomFilter(largerShape);
      // Undeclared exception!
      try {
        smallFilter.merge((BitMapExtractor) largerFilter);
        fail("Expecting exception: IllegalArgumentException");

      } catch(IllegalArgumentException e) {
         //
         // BitMapExtractor should send at most 18 maps
         //
         verifyException("org.apache.commons.collections4.bloomfilter.SimpleBloomFilter", e);
      }
  }

  @Test(timeout = 4000)
  public void testProcessBitMapPairs_returnsFalseWhenPredicateReturnsFalse()  throws Throwable  {
      Shape shape = Shape.fromNM(1082, 1082);
      SimpleBloomFilter filter = new SimpleBloomFilter(shape);
      long[] emptyBitMap = new long[0];
      BitMapExtractor emptyExtractor = BitMapExtractor.fromBitMapArray(emptyBitMap);
      LongBiPredicate alwaysFalsePredicate = mock(LongBiPredicate.class, new ViolatedAssumptionAnswer());
      doReturn(false).when(alwaysFalsePredicate).test(anyLong() , anyLong());
      boolean result = filter.processBitMapPairs(emptyExtractor, alwaysFalsePredicate);
      assertFalse(result);
  }

  @Test(timeout = 4000)
  public void testContains_BloomFilter_emptyFilterContainsItself()  throws Throwable  {
      Shape shape = Shape.fromKM(3157, 3157);
      SimpleBloomFilter filter = new SimpleBloomFilter(shape);
      boolean containsSelf = filter.contains((BloomFilter<?>) filter);
      assertTrue(containsSelf);
      assertEquals(0, filter.characteristics());
  }

  @Test(timeout = 4000)
  public void testContains_BitMapExtractor_returnsFalseWhenExtractorHasBitsNotInFilter()  throws Throwable  {
      Shape shape = Shape.fromNM(2490, 2490);
      SimpleBloomFilter filter = new SimpleBloomFilter(shape);
      long[] bitMapWithSetBit = new long[6];
      bitMapWithSetBit[0] = (long) 2490;
      BitMapExtractor extractor = BitMapExtractor.fromBitMapArray(bitMapWithSetBit);
      boolean result = filter.contains(extractor);
      assertFalse(result);
  }

  @Test(timeout = 4000)
  public void testMerge_IndexExtractor_throwsWhenIndexIsOutOfRange()  throws Throwable  {
      Shape shape = Shape.fromKM(295, 295);
      SimpleBloomFilter filter = new SimpleBloomFilter(shape);
      int[] indicesWithOutOfRangeValue = new int[7];
      indicesWithOutOfRangeValue[0] = 295;
      IndexExtractor indexExtractor = IndexExtractor.fromIndexArray(indicesWithOutOfRangeValue);
      // Undeclared exception!
      try {
        filter.merge(indexExtractor);
        fail("Expecting exception: IllegalArgumentException");

      } catch(IllegalArgumentException e) {
         //
         // IndexExtractor should only send values in the range[0,295)
         //
         verifyException("org.apache.commons.collections4.bloomfilter.SimpleBloomFilter", e);
      }
  }

  @Test(timeout = 4000)
  public void testMerge_BloomFilter_mergeWithSparseBloomFilterSucceeds()  throws Throwable  {
      Shape shape = Shape.fromKM(3157, 3157);
      SimpleBloomFilter filter = new SimpleBloomFilter(shape);
      SparseBloomFilter sparseFilter = new SparseBloomFilter(shape);
      boolean result = filter.merge((BloomFilter<?>) sparseFilter);
      assertTrue(result);
  }

  @Test(timeout = 4000)
  public void testMerge_BitMapExtractor_throwsWhenExtractorBitsExceedShapeLimit()  throws Throwable  {
      Shape shape = Shape.fromKM(295, 295);
      SimpleBloomFilter filter = new SimpleBloomFilter(shape);
      int[] indicesWithOutOfRangeValue = new int[7];
      indicesWithOutOfRangeValue[0] = 295;
      IndexExtractor outOfRangeIndexExtractor = IndexExtractor.fromIndexArray(indicesWithOutOfRangeValue);
      BitMapExtractor extractor = BitMapExtractor.fromIndexExtractor(outOfRangeIndexExtractor, 295);
      // Undeclared exception!
      try {
        filter.merge(extractor);
        fail("Expecting exception: IllegalArgumentException");

      } catch(IllegalArgumentException e) {
         //
         // BitMapExtractor set a bit higher than the limit for the shape: 295
         //
         verifyException("org.apache.commons.collections4.bloomfilter.SimpleBloomFilter", e);
      }
  }

  @Test(timeout = 4000)
  public void testMerge_BitMapExtractor_selfMergeSucceeds()  throws Throwable  {
      Shape shape = Shape.fromKM(2496, 2496);
      SimpleBloomFilter filter = new SimpleBloomFilter(shape);
      boolean result = filter.merge((BitMapExtractor) filter);
      assertTrue(result);
  }

  @Test(timeout = 4000)
  public void testMerge_IndexExtractor_selfMergeOnEmptyFilterLeavesFilterEmpty()  throws Throwable  {
      Shape shape = Shape.fromNM(2490, 2490);
      SimpleBloomFilter filter = new SimpleBloomFilter(shape);
      boolean mergeResult = filter.merge((IndexExtractor) filter);
      boolean empty = filter.isEmpty();
      assertTrue(empty == mergeResult);
      assertTrue(empty);
  }

  @Test(timeout = 4000)
  public void testIsEmpty_returnsTrueForNewlyCreatedFilter()  throws Throwable  {
      Shape shape = Shape.fromNM(2490, 2490);
      SimpleBloomFilter filter = new SimpleBloomFilter(shape);
      boolean empty = filter.isEmpty();
      assertTrue(empty);
  }

  @Test(timeout = 4000)
  public void testAsBitMapArray_returnsArrayWithCorrectLengthForShape()  throws Throwable  {
      Shape shape = Shape.fromNM(1110, 1110);
      SimpleBloomFilter filter = new SimpleBloomFilter(shape);
      long[] bitMapArray = filter.asBitMapArray();
      assertEquals(18, bitMapArray.length);
  }

  @Test(timeout = 4000)
  public void testClear_resetsFilterState()  throws Throwable  {
      Shape shape = Shape.fromNM(1110, 1110);
      SimpleBloomFilter filter = new SimpleBloomFilter(shape);
      filter.clear();
      assertEquals(0, filter.characteristics());
  }

  @Test(timeout = 4000)
  public void testMerge_Hasher_filterIsNoLongerEmptyAfterMerge()  throws Throwable  {
      Shape shape = Shape.fromNM(2490, 2490);
      SimpleBloomFilter filter = new SimpleBloomFilter(shape);
      EnhancedDoubleHasher hasher = new EnhancedDoubleHasher(2490, 2490);
      boolean mergeResult = filter.merge((Hasher) hasher);
      boolean empty = filter.isEmpty();
      assertFalse(empty == mergeResult);
      assertFalse(empty);
  }

  @Test(timeout = 4000)
  public void testEstimateIntersection_withSelf_doesNotThrow()  throws Throwable  {
      Shape shape = Shape.fromKM(3132, 3132);
      SimpleBloomFilter filter = new SimpleBloomFilter(shape);
      filter.estimateIntersection(filter);
      assertEquals(0, filter.characteristics());
  }

  @Test(timeout = 4000)
  public void testContains_IndexExtractor_emptyFilterContainsItsOwnIndexExtractor()  throws Throwable  {
      Shape shape = Shape.fromKM(3093, 3093);
      SimpleBloomFilter filter = new SimpleBloomFilter(shape);
      boolean result = filter.contains((IndexExtractor) filter);
      assertTrue(result);
  }
}
