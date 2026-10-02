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
public class SimpleBloomFilter_ESTest_test00 extends SimpleBloomFilter_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test00() throws Throwable {
        Shape shape0 = Shape.fromNM(1097, 1097);
        SimpleBloomFilter simpleBloomFilter0 = new SimpleBloomFilter(shape0);
        Shape shape1 = Shape.fromKM(1097, 5335);
        SimpleBloomFilter simpleBloomFilter1 = new SimpleBloomFilter(shape1);
        // Undeclared exception!
        try {
            simpleBloomFilter0.merge((BitMapExtractor) simpleBloomFilter1);
            fail("Expecting exception: IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            //
            // BitMapExtractor should send at most 18 maps
            //
            verifyException("org.apache.commons.collections4.bloomfilter.SimpleBloomFilter", e);
        }
    }
}
