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
public class SparseBloomFilter_ESTest_test08 extends SparseBloomFilter_ESTest_scaffolding {

    // SparseBloomFilter.characteristics() always returns SPARSE (= 1),
    // regardless of filter state. This value comes from BloomFilter.SPARSE.
    private static final int SPARSE_CHARACTERISTIC = 1;

    @Test(timeout = 4000)
    public void testCharacteristicsReturnsSparseAfterClear() throws Throwable {
        Shape shape = Shape.fromKM(31, 31);
        SparseBloomFilter filter = new SparseBloomFilter(shape);

        filter.clear();

        assertEquals(
            "SparseBloomFilter.characteristics() must always report SPARSE",
            SPARSE_CHARACTERISTIC,
            filter.characteristics()
        );
    }
}
