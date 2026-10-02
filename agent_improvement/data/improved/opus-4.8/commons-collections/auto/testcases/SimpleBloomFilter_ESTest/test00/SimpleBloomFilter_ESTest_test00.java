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
     * Merging a larger filter into a smaller one must fail.
     *
     * <p>The target filter is shaped for only 1097 bits (18 bit-map longs), while the
     * source filter is shaped for 5335 bits (84 bit-map longs). When the source is merged
     * as a {@link BitMapExtractor}, it feeds more bit maps than the target can hold, so the
     * target overruns its internal array and reports an {@link IllegalArgumentException}.</p>
     */
    @Test(timeout = 4000)
    public void test00() throws Throwable {
        // Target filter: 1097 items / 1097 bits -> 18 bit-map longs.
        Shape smallShape = Shape.fromNM(1097, 1097);
        SimpleBloomFilter targetFilter = new SimpleBloomFilter(smallShape);

        // Source filter: 1097 hash functions / 5335 bits -> 84 bit-map longs (more than the target).
        Shape largeShape = Shape.fromKM(1097, 5335);
        SimpleBloomFilter oversizedSourceFilter = new SimpleBloomFilter(largeShape);

        try {
            targetFilter.merge((BitMapExtractor) oversizedSourceFilter);
            fail("Expecting exception: IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // The target accepts at most 18 maps; the source sends more, triggering the failure.
            // Message: "BitMapExtractor should send at most 18 maps"
            verifyException("org.apache.commons.collections4.bloomfilter.SimpleBloomFilter", e);
        }
    }
}
