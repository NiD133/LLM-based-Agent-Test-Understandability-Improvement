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
public class SparseBloomFilter_ESTest_test02 extends SparseBloomFilter_ESTest_scaffolding {

    /**
     * Verifies that processBitMaps returns false when its predicate returns false
     * partway through iteration, even though merge always returns true.
     *
     * The mock LongBiPredicate is configured to return true on the first invocation
     * and false on the second, causing processBitMaps to short-circuit and return false.
     */
    @Test(timeout = 4000)
    public void test02() throws Throwable {
        // Arrange: create a filter with 8 hash functions across 4135 bits
        Shape filterShape = Shape.fromNM(8, 4135);
        byte[] zeroBytes = new byte[7];
        EnhancedDoubleHasher hasher = new EnhancedDoubleHasher(zeroBytes);
        SparseBloomFilter filter = new SparseBloomFilter(filterShape);

        // Act: merge the hasher into the filter; merge always returns true
        boolean mergeSucceeded = filter.merge((Hasher) hasher);

        // Arrange the predicate that will short-circuit on the second bitmap
        long[] referenceBitMaps = new long[1];
        LongBiPredicate shortCircuitBiPredicate = mock(LongBiPredicate.class, new ViolatedAssumptionAnswer());
        doReturn(true, false).when(shortCircuitBiPredicate).test(anyLong(), anyLong());
        CountingLongPredicate countingPredicate = new CountingLongPredicate(referenceBitMaps, shortCircuitBiPredicate);

        // Act: iterate over the filter's bitmaps; should return false when the predicate refuses
        boolean processBitMapsCompleted = filter.processBitMaps(countingPredicate);

        // Assert: processBitMaps returned false (predicate short-circuited), unlike the merge result
        assertFalse("processBitMaps should have been interrupted by the predicate returning false",
                processBitMapsCompleted);
        assertFalse("processBitMaps result must differ from merge result (true vs false)",
                processBitMapsCompleted == mergeSucceeded);
    }
}
