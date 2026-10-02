package org.apache.commons.collections4.bloomfilter;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class SparseBloomFilter_ESTest_test05 extends SparseBloomFilter_ESTest_scaffolding {

    /**
     * Merging an index that is equal to (or greater than) the number of bits in
     * the filter's shape must be rejected. For a shape with 31 bits, the only
     * valid indices are 0..30, so attempting to merge index 31 should raise an
     * IllegalArgumentException.
     */
    @Test(timeout = 4000)
    public void mergeIndexAtOrAboveBitCountThrows() throws Throwable {
        // Shape with 31 hash functions and 31 bits => valid indices are 0..30.
        Shape shape = Shape.fromKM(31, 31);
        SparseBloomFilter filter = new SparseBloomFilter(shape);

        // Index 31 is out of range (one past the maximum valid index of 30).
        int outOfRangeIndex = 31;
        IndexExtractor indexExtractor = IndexExtractor.fromIndexArray(new int[] { outOfRangeIndex });

        try {
            filter.merge(indexExtractor);
            fail("Expecting exception: IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // Message: "Value in list 31 is greater than maximum value (30)"
            verifyException("org.apache.commons.collections4.bloomfilter.SparseBloomFilter", e);
        }
    }
}
