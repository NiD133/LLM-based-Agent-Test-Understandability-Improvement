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
        Shape shape = Shape.fromNM(8, 8);
        SparseBloomFilter emptyFilter = new SparseBloomFilter(shape);
        long[] emptyFilterBitMaps = emptyFilter.asBitMapArray();

        LongBiPredicate rejectingBitMapComparison = mock(LongBiPredicate.class, new ViolatedAssumptionAnswer());
        doReturn(false).when(rejectingBitMapComparison).test(anyLong(), anyLong());

        CountingLongPredicate countingPredicate = new CountingLongPredicate(emptyFilterBitMaps, rejectingBitMapComparison);
        boolean processedAllBitMaps = emptyFilter.processBitMaps(countingPredicate);

        assertFalse(processedAllBitMaps);
    }
}
