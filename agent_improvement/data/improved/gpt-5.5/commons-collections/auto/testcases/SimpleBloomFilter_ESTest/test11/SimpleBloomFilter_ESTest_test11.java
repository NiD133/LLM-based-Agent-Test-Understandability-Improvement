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
public class SimpleBloomFilter_ESTest_test11 extends SimpleBloomFilter_ESTest_scaffolding {

    private static final int EXPECTED_CHARACTERISTICS = 0;
    private static final int SHAPE_ITEM_COUNT = 1110;
    private static final int SHAPE_BIT_COUNT = 1110;

    @Test(timeout = 4000)
    public void test11() throws Throwable {
        Shape shape = Shape.fromNM(SHAPE_ITEM_COUNT, SHAPE_BIT_COUNT);
        SimpleBloomFilter bloomFilter = new SimpleBloomFilter(shape);

        bloomFilter.clear();

        assertEquals(EXPECTED_CHARACTERISTICS, bloomFilter.characteristics());
    }
}
