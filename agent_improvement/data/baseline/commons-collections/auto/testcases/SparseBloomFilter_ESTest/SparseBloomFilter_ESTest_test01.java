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
public class SparseBloomFilter_ESTest_test01 extends SparseBloomFilter_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test01() throws Throwable {
        Shape shape0 = Shape.fromNM(8, 8);
        SparseBloomFilter sparseBloomFilter0 = new SparseBloomFilter(shape0);
        long[] longArray0 = sparseBloomFilter0.asBitMapArray();
        LongBiPredicate longBiPredicate0 = mock(LongBiPredicate.class, new ViolatedAssumptionAnswer());
        doReturn(false).when(longBiPredicate0).test(anyLong(), anyLong());
        CountingLongPredicate countingLongPredicate0 = new CountingLongPredicate(longArray0, longBiPredicate0);
        boolean boolean0 = sparseBloomFilter0.processBitMaps(countingLongPredicate0);
        assertFalse(boolean0);
    }
}
