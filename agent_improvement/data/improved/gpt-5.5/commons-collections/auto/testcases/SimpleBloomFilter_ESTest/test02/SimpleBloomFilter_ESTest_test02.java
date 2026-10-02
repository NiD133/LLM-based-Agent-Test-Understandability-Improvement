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

    private static final int NUMBER_OF_HASH_FUNCTIONS = 3157;
    private static final int NUMBER_OF_BITS = 3157;

    @Test(timeout = 4000)
    public void test02() throws Throwable {
        Shape shape = Shape.fromKM(NUMBER_OF_HASH_FUNCTIONS, NUMBER_OF_BITS);
        SimpleBloomFilter emptyFilter = new SimpleBloomFilter(shape);

        boolean containsItself = emptyFilter.contains((BloomFilter<?>) emptyFilter);

        assertTrue(containsItself);
        assertEquals(0, emptyFilter.characteristics());
    }
}
