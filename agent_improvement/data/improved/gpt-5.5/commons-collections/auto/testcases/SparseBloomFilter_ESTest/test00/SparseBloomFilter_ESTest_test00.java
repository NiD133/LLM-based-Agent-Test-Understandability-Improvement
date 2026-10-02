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
        Shape sourceShape = Shape.fromNM(8, 8);
        Shape processedShape = Shape.fromNM(2, 775);
        SparseBloomFilter sourceFilter = new SparseBloomFilter(sourceShape);
        SparseBloomFilter processedFilter = new SparseBloomFilter(processedShape);
        long[] sourceBitMaps = sourceFilter.asBitMapArray();

        LongBiPredicate comparisonPredicate = mock(LongBiPredicate.class, new ViolatedAssumptionAnswer());
        doReturn(true, false).when(comparisonPredicate).test(anyLong(), anyLong());
        CountingLongPredicate bitmapConsumer = new CountingLongPredicate(sourceBitMaps, comparisonPredicate);

        boolean processedAllBitMaps = processedFilter.processBitMaps(bitmapConsumer);

        assertFalse(processedAllBitMaps);
    }
}
