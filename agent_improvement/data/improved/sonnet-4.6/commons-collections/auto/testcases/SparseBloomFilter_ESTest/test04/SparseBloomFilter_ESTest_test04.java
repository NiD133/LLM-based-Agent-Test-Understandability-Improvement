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
public class SparseBloomFilter_ESTest_test04 extends SparseBloomFilter_ESTest_scaffolding {

    /**
     * Verifies that merging an IndexExtractor containing a negative index value
     * throws an IllegalArgumentException. Negative bit indices are invalid for
     * a Bloom filter because bit positions must be non-negative.
     */
    @Test(timeout = 4000)
    public void test04_mergeWithNegativeIndex_throwsIllegalArgumentException() throws Throwable {
        // Build a shape: 31 hash functions, up to Integer.MAX_VALUE bits, 31 expected items
        Shape shape = Shape.fromNMK(31, Integer.MAX_VALUE, 31);
        SparseBloomFilter filter = new SparseBloomFilter(shape);

        // Create an index array where the first element is a negative bit index
        int[] indices = new int[6];
        int negativeIndex = -2047;
        indices[0] = negativeIndex;
        IndexExtractor extractorWithNegativeIndex = IndexExtractor.fromIndexArray(indices);

        // Merging an extractor that yields a negative index must be rejected
        try {
            filter.merge(extractorWithNegativeIndex);
            fail("Expecting exception: IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            //
            // Value in list -2047 is less than 0
            //
            verifyException("org.apache.commons.collections4.bloomfilter.SparseBloomFilter", e);
        }
    }
}
