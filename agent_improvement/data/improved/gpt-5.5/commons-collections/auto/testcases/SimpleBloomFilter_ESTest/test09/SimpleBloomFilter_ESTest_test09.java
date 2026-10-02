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
public class SimpleBloomFilter_ESTest_test09 extends SimpleBloomFilter_ESTest_scaffolding {

    private static final int NUMBER_OF_ITEMS = 2490;
    private static final int NUMBER_OF_BITS = 2490;

    @Test(timeout = 4000)
    public void test09() throws Throwable {
        Shape shape = Shape.fromNM(NUMBER_OF_ITEMS, NUMBER_OF_BITS);
        SimpleBloomFilter bloomFilter = new SimpleBloomFilter(shape);

        boolean isEmpty = bloomFilter.isEmpty();

        assertTrue(isEmpty);
    }
}
