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

    @Test(timeout = 4000)
    public void test06() throws Throwable {
        final int numberOfHashFunctions = 295;
        final int numberOfBits = 295;
        final int firstIndexOutsideShape = numberOfBits;

        Shape shape = Shape.fromKM(numberOfHashFunctions, numberOfBits);
        SimpleBloomFilter filter = new SimpleBloomFilter(shape);

        int[] indices = new int[7];
        indices[0] = firstIndexOutsideShape;
        IndexExtractor outOfRangeIndex = IndexExtractor.fromIndexArray(indices);
        BitMapExtractor outOfRangeBitmap = BitMapExtractor.fromIndexExtractor(outOfRangeIndex, numberOfBits);

        // Undeclared exception!
        try {
            filter.merge(outOfRangeBitmap);
            fail("Expecting exception: IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            //
            // BitMapExtractor set a bit higher than the limit for the shape: 295
            //
            verifyException("org.apache.commons.collections4.bloomfilter.SimpleBloomFilter", e);
        }
    }
}
