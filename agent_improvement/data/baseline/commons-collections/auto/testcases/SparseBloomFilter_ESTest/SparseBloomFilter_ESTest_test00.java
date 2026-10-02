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

    @Test(timeout = 4000)
    public void test00() throws Throwable {
        Shape shape0 = Shape.fromNM(8, 8);
        Shape shape1 = Shape.fromNM(2, 775);
        SparseBloomFilter sparseBloomFilter0 = new SparseBloomFilter(shape0);
        SparseBloomFilter sparseBloomFilter1 = new SparseBloomFilter(shape1);
        long[] longArray0 = sparseBloomFilter0.asBitMapArray();
        LongBiPredicate longBiPredicate0 = mock(LongBiPredicate.class, new ViolatedAssumptionAnswer());
        doReturn(true, false).when(longBiPredicate0).test(anyLong(), anyLong());
        CountingLongPredicate countingLongPredicate0 = new CountingLongPredicate(longArray0, longBiPredicate0);
        boolean boolean0 = sparseBloomFilter1.processBitMaps(countingLongPredicate0);
        assertFalse(boolean0);
    }
}
