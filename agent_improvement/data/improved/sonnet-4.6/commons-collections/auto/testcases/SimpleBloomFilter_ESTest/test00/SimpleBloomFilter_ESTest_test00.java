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
     * Verifies that merging a larger filter (with more bit maps) into a smaller filter
     * throws an IllegalArgumentException because the source BitMapExtractor sends more
     * bitmap segments than the target filter's shape can accommodate.
     *
     * shape0 has 1097 bits (fromNM: 1097 items, 1097 bits) → requires 18 longs.
     * shape1 has 5335 bits (fromKM: 1097 hash functions, 5335 bits) → requires 84 longs.
     * Merging shape1 into shape0 overflows shape0's bitmap array.
     */
    @Test(timeout = 4000)
    public void test00() throws Throwable {
        // Target filter: small shape derived from (numberOfItems=1097, numberOfBits=1097)
        Shape smallShape = Shape.fromNM(1097, 1097);
        SimpleBloomFilter smallFilter = new SimpleBloomFilter(smallShape);

        // Source filter: larger shape derived from (numberOfHashFunctions=1097, numberOfBits=5335)
        // This produces many more bitmap longs than smallFilter can hold.
        Shape largeShape = Shape.fromKM(1097, 5335);
        SimpleBloomFilter largeFilter = new SimpleBloomFilter(largeShape);

        // Merging a larger BitMapExtractor into a smaller filter must be rejected
        // because the extractor sends more bitmap segments than the target allows.
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
