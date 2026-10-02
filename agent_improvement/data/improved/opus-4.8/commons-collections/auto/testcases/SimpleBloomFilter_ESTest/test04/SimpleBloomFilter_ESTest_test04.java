package org.apache.commons.collections4.bloomfilter;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class SimpleBloomFilter_ESTest_test04 extends SimpleBloomFilter_ESTest_scaffolding {

    /**
     * Merging an index that is outside the filter's valid range must be rejected.
     *
     * <p>A shape with 295 bits accepts only indices in the range [0, 295). Attempting to
     * merge an {@link IndexExtractor} that reports index 295 (the first value past the upper
     * bound) should make {@link SimpleBloomFilter#merge(IndexExtractor)} throw an
     * {@link IllegalArgumentException}.</p>
     */
    @Test(timeout = 4000)
    public void mergeIndexEqualToNumberOfBitsThrowsIllegalArgumentException() throws Throwable {
        final int numberOfBits = 295;
        final int numberOfHashFunctions = 295;
        Shape shape = Shape.fromKM(numberOfHashFunctions, numberOfBits);
        SimpleBloomFilter bloomFilter = new SimpleBloomFilter(shape);

        // 295 is one past the highest valid index (valid range is [0, 295)).
        int outOfRangeIndex = numberOfBits;
        int[] indices = new int[7];
        indices[0] = outOfRangeIndex;
        IndexExtractor indexExtractor = IndexExtractor.fromIndexArray(indices);

        try {
            bloomFilter.merge(indexExtractor);
            fail("Expecting exception: IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // IndexExtractor should only send values in the range [0, 295)
            verifyException("org.apache.commons.collections4.bloomfilter.SimpleBloomFilter", e);
        }
    }
}
