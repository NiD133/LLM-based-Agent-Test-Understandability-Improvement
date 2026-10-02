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
        Shape shape0 = Shape.fromNM(8, 4135);
        byte[] byteArray0 = new byte[7];
        EnhancedDoubleHasher enhancedDoubleHasher0 = new EnhancedDoubleHasher(byteArray0);
        SparseBloomFilter sparseBloomFilter0 = new SparseBloomFilter(shape0);
        boolean boolean0 = sparseBloomFilter0.merge((Hasher) enhancedDoubleHasher0);
        long[] longArray0 = new long[1];
        LongBiPredicate longBiPredicate0 = mock(LongBiPredicate.class, new ViolatedAssumptionAnswer());
        doReturn(true, false).when(longBiPredicate0).test(anyLong(), anyLong());
        CountingLongPredicate countingLongPredicate0 = new CountingLongPredicate(longArray0, longBiPredicate0);
        boolean boolean1 = sparseBloomFilter0.processBitMaps(countingLongPredicate0);
        assertFalse(boolean1 == boolean0);
        assertFalse(boolean1);
    }
}
