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
     * Merging a filter with a larger shape into a smaller one should throw
     * IllegalArgumentException because the source produces more bit maps than
     * the destination can accommodate.
     *
     * Shape.fromNM(1097, 1097) → 1097 bits → 18 long bit maps
     * Shape.fromKM(1097, 5335) → 5335 bits → 84 long bit maps
     * Writing 84 maps into an 18-slot array causes IndexOutOfBoundsException,
     * which SimpleBloomFilter.merge(BitMapExtractor) wraps as IllegalArgumentException.
     */
    @Test(timeout = 4000)
    public void test00() throws Throwable {
        // Small filter: 1097 bits, backed by 18 long bit maps
        Shape smallShape = Shape.fromNM(1097, 1097);
        SimpleBloomFilter smallFilter = new SimpleBloomFilter(smallShape);

        // Large filter: 5335 bits, backed by 84 long bit maps
        Shape largeShape = Shape.fromKM(1097, 5335);
        SimpleBloomFilter largeFilter = new SimpleBloomFilter(largeShape);

        // Merging the large filter (as BitMapExtractor) into the small filter must fail:
        // the large filter produces 84 bit maps but the small filter only allocated 18 slots.
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
