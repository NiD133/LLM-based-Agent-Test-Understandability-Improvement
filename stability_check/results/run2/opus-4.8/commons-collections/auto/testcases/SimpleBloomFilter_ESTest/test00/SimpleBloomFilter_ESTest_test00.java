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

    /**
     * Merging a larger filter (whose bit map array is longer) into a smaller
     * one must fail: the source supplies more bit maps than the destination's
     * backing array can hold, so the destination reports how many maps it can
     * accept via an IllegalArgumentException.
     */
    @Test(timeout = 4000)
    public void test00() throws Throwable {
        // Destination: a small filter sized from a modest number of bits.
        Shape smallShape = Shape.fromNM(1097, 1097);
        SimpleBloomFilter smallFilter = new SimpleBloomFilter(smallShape);

        // Source: a much larger filter (5335 bits) that produces more bit maps.
        Shape largeShape = Shape.fromKM(1097, 5335);
        SimpleBloomFilter largeFilter = new SimpleBloomFilter(largeShape);

        try {
            smallFilter.merge((BitMapExtractor) largeFilter);
            fail("Expecting exception: IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // Rejected because the source sends more bit maps than the
            // destination's array can hold ("should send at most 18 maps").
            verifyException("org.apache.commons.collections4.bloomfilter.SimpleBloomFilter", e);
        }
    }
}
