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
public class SparseBloomFilter_ESTest_test03 extends SparseBloomFilter_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test03() throws Throwable {
        Shape shape0 = Shape.fromKM(833, 833);
        SparseBloomFilter sparseBloomFilter0 = new SparseBloomFilter(shape0);
        EnhancedDoubleHasher enhancedDoubleHasher0 = new EnhancedDoubleHasher((-782L), 3371L);
        boolean boolean0 = sparseBloomFilter0.merge((Hasher) enhancedDoubleHasher0);
        boolean boolean1 = sparseBloomFilter0.merge((BitMapExtractor) sparseBloomFilter0);
        assertTrue(boolean1 == boolean0);
        assertTrue(boolean1);
    }
}
