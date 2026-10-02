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

    @Test(timeout = 4000)
    public void test02() throws Throwable {
        Shape shape = Shape.fromNM(8, 4135);
        byte[] hashBytes = new byte[7];
        EnhancedDoubleHasher hasher = new EnhancedDoubleHasher(hashBytes);

        SparseBloomFilter sparseBloomFilter = new SparseBloomFilter(shape);
        boolean mergeResult = sparseBloomFilter.merge((Hasher) hasher);

        long[] bitmapCount = new long[1];
        LongBiPredicate bitmapPredicate = mock(LongBiPredicate.class, new ViolatedAssumptionAnswer());
        doReturn(true, false).when(bitmapPredicate).test(anyLong(), anyLong());
        CountingLongPredicate countingPredicate = new CountingLongPredicate(bitmapCount, bitmapPredicate);

        boolean processResult = sparseBloomFilter.processBitMaps(countingPredicate);

        assertFalse(processResult == mergeResult);
        assertFalse(processResult);
    }
}
