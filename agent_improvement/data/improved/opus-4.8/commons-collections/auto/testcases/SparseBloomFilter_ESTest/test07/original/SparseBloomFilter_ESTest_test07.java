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
public class SparseBloomFilter_ESTest_test07 extends SparseBloomFilter_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test07() throws Throwable {
        Shape shape0 = Shape.fromKM(833, 833);
        SparseBloomFilter sparseBloomFilter0 = new SparseBloomFilter(shape0);
        EnhancedDoubleHasher enhancedDoubleHasher0 = new EnhancedDoubleHasher((-782L), 3371L);
        sparseBloomFilter0.merge((Hasher) enhancedDoubleHasher0);
        long[] longArray0 = sparseBloomFilter0.asBitMapArray();
        assertEquals(14, longArray0.length);
    }
}
