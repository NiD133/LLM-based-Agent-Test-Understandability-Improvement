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
public class SimpleBloomFilter_ESTest_test12 extends SimpleBloomFilter_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test12() throws Throwable {
        Shape shape0 = Shape.fromNM(2490, 2490);
        SimpleBloomFilter simpleBloomFilter0 = new SimpleBloomFilter(shape0);
        EnhancedDoubleHasher enhancedDoubleHasher0 = new EnhancedDoubleHasher(2490, 2490);
        boolean boolean0 = simpleBloomFilter0.merge((Hasher) enhancedDoubleHasher0);
        boolean boolean1 = simpleBloomFilter0.isEmpty();
        assertFalse(boolean1 == boolean0);
        assertFalse(boolean1);
    }
}
