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
    public void test00_mergeLargerFilterIntoSmallerFilterThrowsIllegalArgumentException() throws Throwable {
        // Create a small filter: fromNM(n=1097, m=1097) gives a shape with 1097 bits (~18 long bitmaps)
        Shape smallShape = Shape.fromNM(1097, 1097);
        SimpleBloomFilter smallFilter = new SimpleBloomFilter(smallShape);

        // Create a large filter: fromKM(k=1097, m=5335) gives a shape with 5335 bits (~84 long bitmaps)
        Shape largeShape = Shape.fromKM(1097, 5335);
        SimpleBloomFilter largeFilter = new SimpleBloomFilter(largeShape);

        // Merging largeFilter (84 bitmaps) into smallFilter (18 bitmaps) must fail:
        // the BitMapExtractor sends more maps than the target filter can hold.
        try {
            smallFilter.merge((BitMapExtractor) largeFilter);
            fail("Expecting exception: IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            //
            // BitMapExtractor should send at most 18 maps
            //
            verifyException("org.apache.commons.collections4.bloomfilter.SimpleBloomFilter", e);
        }
    }
}
