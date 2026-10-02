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
public class SparseBloomFilter_ESTest_test10 extends SparseBloomFilter_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test10() throws Throwable {
        Shape shape0 = Shape.fromNM(8, 4135);
        SparseBloomFilter sparseBloomFilter0 = new SparseBloomFilter(shape0);
        boolean boolean0 = sparseBloomFilter0.contains((BitMapExtractor) sparseBloomFilter0);
        assertTrue(boolean0);
    }
}
