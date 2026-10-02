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
public class SimpleBloomFilter_ESTest_test02 extends SimpleBloomFilter_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test02() throws Throwable {
        Shape shape0 = Shape.fromKM(3157, 3157);
        SimpleBloomFilter simpleBloomFilter0 = new SimpleBloomFilter(shape0);
        boolean boolean0 = simpleBloomFilter0.contains((BloomFilter<?>) simpleBloomFilter0);
        assertTrue(boolean0);
        assertEquals(0, simpleBloomFilter0.characteristics());
    }
}
