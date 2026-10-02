package org.apache.commons.collections4.bloomfilter;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class SparseBloomFilter_ESTest_test04 extends SparseBloomFilter_ESTest_scaffolding {

    /**
     * Merging an IndexExtractor that yields a negative index must fail, because a
     * Bloom filter bit position can never be below 0. The filter reports the offending
     * value in the thrown IllegalArgumentException.
     */
    @Test(timeout = 4000)
    public void mergeWithNegativeIndexThrowsIllegalArgumentException() throws Throwable {
        Shape shape = Shape.fromNMK(31, Integer.MAX_VALUE, 31);
        SparseBloomFilter filter = new SparseBloomFilter(shape);

        // The first index (-2047) is invalid; the remaining entries default to 0.
        int[] indices = new int[6];
        indices[0] = -2047;
        IndexExtractor indexExtractor = IndexExtractor.fromIndexArray(indices);

        try {
            filter.merge(indexExtractor);
            fail("Expecting exception: IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // Message: "Value in list -2047 is less than 0"
            verifyException("org.apache.commons.collections4.bloomfilter.SparseBloomFilter", e);
        }
    }
}
