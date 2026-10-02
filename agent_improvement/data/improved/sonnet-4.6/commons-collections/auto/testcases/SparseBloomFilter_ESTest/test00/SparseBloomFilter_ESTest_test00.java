package org.apache.commons.collections4.bloomfilter;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.shaded.org.mockito.Mockito.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.evosuite.runtime.ViolatedAssumptionAnswer;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class SparseBloomFilter_ESTest_test00 extends SparseBloomFilter_ESTest_scaffolding {

    /**
     * Tests that {@link SparseBloomFilter#processBitMaps} returns {@code false} when the
     * supplied predicate rejects a bitmap word before all words have been consumed.
     *
     * Setup:
     *  - A small reference filter (8 bits, 1 long word) provides the baseline bitmap array.
     *  - A larger empty filter (775 bits, 13 long words) is the filter under test.
     *  - A mock {@link LongBiPredicate} is configured to accept the first pair of longs
     *    ({@code true}) and reject the second pair ({@code false}).
     *  - A {@link CountingLongPredicate} wraps the reference bitmap and the mock predicate;
     *    it pairs each long emitted by the filter under test with the next element of the
     *    reference bitmap array (padding with 0 once the array is exhausted).
     *
     * Expected behaviour:
     *  When the counting predicate returns {@code false} on the second long emitted by
     *  the large filter, {@code processBitMaps} must short-circuit and return {@code false}.
     */
    @Test(timeout = 4000)
    public void test00() throws Throwable {
        // Arrange: a small shape (8 bits → 1 long word) used to produce the reference bitmap
        Shape smallShape = Shape.fromNM(8, 8);
        // Arrange: a large shape (775 bits → 13 long words) for the filter under test
        Shape largeShape = Shape.fromNM(2, 775);

        SparseBloomFilter referenceFilter = new SparseBloomFilter(smallShape);
        SparseBloomFilter filterUnderTest = new SparseBloomFilter(largeShape);

        // The reference filter is empty, so its bitmap array is [0L] (one zero long word)
        long[] referenceBitMap = referenceFilter.asBitMapArray();

        // Mock predicate: accepts the first long pair, rejects the second
        LongBiPredicate pairPredicate = mock(LongBiPredicate.class, new ViolatedAssumptionAnswer());
        doReturn(true, false).when(pairPredicate).test(anyLong(), anyLong());

        // CountingLongPredicate pairs each incoming long with the next element of referenceBitMap
        CountingLongPredicate countingPredicate = new CountingLongPredicate(referenceBitMap, pairPredicate);

        // Act: process all 13 long words of the empty large filter through the counting predicate
        boolean result = filterUnderTest.processBitMaps(countingPredicate);

        // Assert: the predicate rejected the second long, so processBitMaps must return false
        assertFalse(result);
    }
}
