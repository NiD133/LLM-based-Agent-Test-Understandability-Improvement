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
public class SparseBloomFilter_ESTest_test05 extends SparseBloomFilter_ESTest_scaffolding {

    /**
     * Verifies that merging an IndexExtractor whose index equals the number of bits
     * (i.e. one past the last valid index) throws IllegalArgumentException.
     *
     * Shape.fromKM(31, 31) produces a filter with 31 bits, so valid indices are 0–30.
     * Supplying index 31 must be rejected with the message
     * "Value in list 31 is greater than maximum value (30)".
     */
    @Test(timeout = 4000)
    public void test05() throws Throwable {
        // A shape with 31 hash functions and 31 bits; valid bit indices are 0..30
        Shape shape = Shape.fromKM(31, 31);
        SparseBloomFilter filter = new SparseBloomFilter(shape);

        // Index 31 is exactly one beyond the highest valid index (30)
        int outOfBoundsIndex = 31;
        IndexExtractor extractor = IndexExtractor.fromIndexArray(new int[] { outOfBoundsIndex });

        // Merging an out-of-bounds index must throw with a descriptive message
        try {
            filter.merge(extractor);
            fail("Expecting exception: IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            //
            // Value in list 31 is greater than maximum value (30)
            //
            verifyException("org.apache.commons.collections4.bloomfilter.SparseBloomFilter", e);
        }
    }
}
