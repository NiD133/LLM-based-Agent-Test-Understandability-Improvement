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
public class SimpleBloomFilter_ESTest_test04 extends SimpleBloomFilter_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test04() throws Throwable {
        Shape shape0 = Shape.fromKM(295, 295);
        SimpleBloomFilter simpleBloomFilter0 = new SimpleBloomFilter(shape0);
        int[] intArray0 = new int[7];
        intArray0[0] = 295;
        IndexExtractor indexExtractor0 = IndexExtractor.fromIndexArray(intArray0);
        // Undeclared exception!
        try {
            simpleBloomFilter0.merge(indexExtractor0);
            fail("Expecting exception: IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            //
            // IndexExtractor should only send values in the range[0,295)
            //
            verifyException("org.apache.commons.collections4.bloomfilter.SimpleBloomFilter", e);
        }
    }
}
