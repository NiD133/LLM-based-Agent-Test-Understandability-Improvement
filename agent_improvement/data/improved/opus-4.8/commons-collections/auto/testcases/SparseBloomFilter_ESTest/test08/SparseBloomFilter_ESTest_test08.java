package org.apache.commons.collections4.bloomfilter;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class SparseBloomFilter_ESTest_test08 extends SparseBloomFilter_ESTest_scaffolding {

    /**
     * Clearing a SparseBloomFilter must not change its characteristics: a sparse
     * filter always reports the SPARSE characteristic (value 1), regardless of its
     * current contents.
     */
    @Test(timeout = 4000)
    public void clearKeepsSparseCharacteristic() throws Throwable {
        Shape shape = Shape.fromKM(31, 31);
        SparseBloomFilter filter = new SparseBloomFilter(shape);

        filter.clear();

        int expectedSparseCharacteristic = 1;
        assertEquals(expectedSparseCharacteristic, filter.characteristics());
    }
}
