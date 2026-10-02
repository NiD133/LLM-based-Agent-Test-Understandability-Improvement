package org.apache.commons.collections4.bloomfilter;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class SparseBloomFilter_ESTest_test11 extends SparseBloomFilter_ESTest_scaffolding {

    /**
     * Estimating the intersection of an empty filter with itself should not alter
     * the filter, which still reports the SPARSE characteristic (value 1).
     */
    @Test(timeout = 4000)
    public void estimateIntersectionWithSelfKeepsSparseCharacteristic() throws Throwable {
        Shape shape = Shape.fromKM(833, 833);
        SparseBloomFilter filter = new SparseBloomFilter(shape);

        filter.estimateIntersection(filter);

        int expectedSparseCharacteristic = 1;
        assertEquals(expectedSparseCharacteristic, filter.characteristics());
    }
}
