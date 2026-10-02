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
public class SimpleBloomFilter_ESTest_test04 extends SimpleBloomFilter_ESTest_scaffolding {

    /**
     * Verifies that merging an IndexExtractor whose first index equals the number of bits in the
     * shape throws an IllegalArgumentException. The shape has 295 bits, so valid indices are in
     * [0, 295). Providing an index of exactly 295 (the exclusive upper bound) is out of range and
     * must be rejected.
     */
    @Test(timeout = 4000)
    public void test04() throws Throwable {
        // Build a shape with 295 hash functions and 295 bits; valid bit indices are [0, 295).
        Shape shape = Shape.fromKM(295, 295);
        SimpleBloomFilter filter = new SimpleBloomFilter(shape);

        // Create an index array whose first element (295) equals the bit count and is therefore
        // out of range. The remaining six elements default to 0, which is valid.
        int[] indices = new int[7];
        indices[0] = 295;
        IndexExtractor indexExtractor = IndexExtractor.fromIndexArray(indices);

        // Merging an out-of-range index (295 >= 295) must throw IllegalArgumentException.
        try {
            filter.merge(indexExtractor);
            fail("Expecting exception: IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            //
            // IndexExtractor should only send values in the range[0,295)
            //
            verifyException("org.apache.commons.collections4.bloomfilter.SimpleBloomFilter", e);
        }
    }
}
