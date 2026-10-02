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
        final Shape shape = Shape.fromNM(1082, 1082);
        final SimpleBloomFilter bloomFilter = new SimpleBloomFilter(shape);
        final long[] emptyBitMaps = new long[0];
        final BitMapExtractor emptyExtractor = BitMapExtractor.fromBitMapArray(emptyBitMaps);
        final LongBiPredicate rejectEveryPair = mock(LongBiPredicate.class, new ViolatedAssumptionAnswer());
        doReturn(false).when(rejectEveryPair).test(anyLong(), anyLong());

        final boolean processedAllPairs = bloomFilter.processBitMapPairs(emptyExtractor, rejectEveryPair);

        assertFalse(processedAllPairs);
    }
}
