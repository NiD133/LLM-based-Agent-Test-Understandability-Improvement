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
public class SparseBloomFilter_ESTest_test11 extends SparseBloomFilter_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test11() throws Throwable {
        final int hashFunctionCount = 833;
        final int numberOfBits = 833;
        final int sparseCharacteristic = 1;

        Shape sparseFilterShape = Shape.fromKM(hashFunctionCount, numberOfBits);
        SparseBloomFilter sparseBloomFilter = new SparseBloomFilter(sparseFilterShape);

        sparseBloomFilter.estimateIntersection(sparseBloomFilter);

        assertEquals(sparseCharacteristic, sparseBloomFilter.characteristics());
    }
}
