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
     * Merging a larger filter (viewed as a {@link BitMapExtractor}) into a smaller
     * one must fail: the source emits more bit maps than the target's backing array
     * can hold, so {@link SimpleBloomFilter#merge(BitMapExtractor)} rejects it with
     * an {@link IllegalArgumentException}.
     */
    @Test(timeout = 4000)
    public void test00() throws Throwable {
        // Target filter: shape sized for 1097 bits -> backing array holds only 18 bit maps.
        Shape smallShape = Shape.fromNM(1097, 1097);
        SimpleBloomFilter smallFilter = new SimpleBloomFilter(smallShape);

        // Source filter: shape sized for 5335 bits -> emits far more than 18 bit maps.
        Shape largeShape = Shape.fromKM(1097, 5335);
        SimpleBloomFilter largeFilter = new SimpleBloomFilter(largeShape);

        try {
            smallFilter.merge((BitMapExtractor) largeFilter);
            fail("Expecting exception: IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // The target filter accepts at most 18 bit maps for its shape.
            // Message: "BitMapExtractor should send at most 18 maps"
            verifyException("org.apache.commons.collections4.bloomfilter.SimpleBloomFilter", e);
        }
    }
}
