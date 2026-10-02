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
     * Verifies that merging a BitMapExtractor whose encoded bits exceed the shape's
     * bit-count limit throws an IllegalArgumentException.
     *
     * The shape is configured with 295 bits (valid index range: 0–294).
     * The index array contains the value 295, which maps to a bit position
     * exactly at the boundary (bit 295 is beyond the last valid index 294).
     * When converted to a BitMapExtractor and merged into the filter, the filter
     * detects that a bit above the shape limit was set and rejects the operation.
     */
    @Test(timeout = 4000)
    public void test06() throws Throwable {
        // Shape with 295 bits: valid bit indices are 0 through 294
        Shape shape = Shape.fromKM(295, 295);
        SimpleBloomFilter filter = new SimpleBloomFilter(shape);

        // Index 295 is one past the last valid index (294), exceeding the shape limit
        int[] indices = new int[7];
        indices[0] = 295;

        IndexExtractor indexExtractor = IndexExtractor.fromIndexArray(indices);
        // Convert to a BitMapExtractor bounded to 295 bits; bit 295 will overflow the shape
        BitMapExtractor bitMapExtractor = BitMapExtractor.fromIndexExtractor(indexExtractor, 295);

        // Merging should fail because bit 295 exceeds the shape's 295-bit capacity
        try {
            filter.merge(bitMapExtractor);
            fail("Expecting exception: IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            //
            // BitMapExtractor set a bit higher than the limit for the shape: 295
            //
            verifyException("org.apache.commons.collections4.bloomfilter.SimpleBloomFilter", e);
        }
    }
}
