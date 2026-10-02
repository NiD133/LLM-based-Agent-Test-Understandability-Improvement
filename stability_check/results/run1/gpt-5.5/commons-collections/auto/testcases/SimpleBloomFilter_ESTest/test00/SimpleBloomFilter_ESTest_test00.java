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

    private static final int SMALL_FILTER_NUMBER_OF_ITEMS = 1097;
    private static final int SMALL_FILTER_NUMBER_OF_BITS = 1097;
    private static final int LARGE_FILTER_NUMBER_OF_HASH_FUNCTIONS = 1097;
    private static final int LARGE_FILTER_NUMBER_OF_BITS = 5335;

    @Test(timeout = 4000)
    public void test00() throws Throwable {
        Shape smallFilterShape = Shape.fromNM(SMALL_FILTER_NUMBER_OF_ITEMS, SMALL_FILTER_NUMBER_OF_BITS);
        SimpleBloomFilter smallFilter = new SimpleBloomFilter(smallFilterShape);
        Shape largerBitmapShape = Shape.fromKM(LARGE_FILTER_NUMBER_OF_HASH_FUNCTIONS, LARGE_FILTER_NUMBER_OF_BITS);
        SimpleBloomFilter largerBitmapFilter = new SimpleBloomFilter(largerBitmapShape);

        try {
            smallFilter.merge((BitMapExtractor) largerBitmapFilter);
            fail("Expecting exception: IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            //
            // BitMapExtractor should send at most 18 maps
            //
            verifyException("org.apache.commons.collections4.bloomfilter.SimpleBloomFilter", e);
        }
    }
}
