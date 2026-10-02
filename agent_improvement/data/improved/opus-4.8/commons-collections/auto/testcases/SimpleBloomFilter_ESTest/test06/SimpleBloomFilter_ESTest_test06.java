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
public class SimpleBloomFilter_ESTest_test06 extends SimpleBloomFilter_ESTest_scaffolding {

    /**
     * Merging a BitMapExtractor that sets a bit beyond the shape's bit limit must fail.
     *
     * The shape allows 295 bits, so any bit set above index 295 is illegal. We build a
     * BitMapExtractor from an index array whose first index (295) lands exactly on the
     * out-of-range boundary bit, and verify that merge() rejects it with an
     * IllegalArgumentException.
     */
    @Test(timeout = 4000)
    public void test06() throws Throwable {
        final int numberOfBits = 295;

        // A shape that permits 295 bits (k=295 hash functions, m=295 bits).
        Shape shape = Shape.fromKM(numberOfBits, numberOfBits);
        SimpleBloomFilter bloomFilter = new SimpleBloomFilter(shape);

        // An index array whose first entry (295) is the boundary bit that exceeds the shape.
        int[] indices = new int[7];
        indices[0] = numberOfBits;
        IndexExtractor indexExtractor = IndexExtractor.fromIndexArray(indices);

        // Convert the indices to bit maps covering 295 bits, so the offending bit shows up
        // as excess above the shape's limit when merged.
        BitMapExtractor bitMapExtractor = BitMapExtractor.fromIndexExtractor(indexExtractor, numberOfBits);

        try {
            bloomFilter.merge(bitMapExtractor);
            fail("Expecting exception: IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // "BitMapExtractor set a bit higher than the limit for the shape: 295"
            verifyException("org.apache.commons.collections4.bloomfilter.SimpleBloomFilter", e);
        }
    }
}
