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
public class SimpleBloomFilter_ESTest_test01 extends SimpleBloomFilter_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test01() throws Throwable {
        Shape shape0 = Shape.fromNM(1082, 1082);
        SimpleBloomFilter simpleBloomFilter0 = new SimpleBloomFilter(shape0);
        long[] longArray0 = new long[0];
        BitMapExtractor bitMapExtractor0 = BitMapExtractor.fromBitMapArray(longArray0);
        LongBiPredicate longBiPredicate0 = mock(LongBiPredicate.class, new ViolatedAssumptionAnswer());
        doReturn(false).when(longBiPredicate0).test(anyLong(), anyLong());
        boolean boolean0 = simpleBloomFilter0.processBitMapPairs(bitMapExtractor0, longBiPredicate0);
        assertFalse(boolean0);
    }
}
