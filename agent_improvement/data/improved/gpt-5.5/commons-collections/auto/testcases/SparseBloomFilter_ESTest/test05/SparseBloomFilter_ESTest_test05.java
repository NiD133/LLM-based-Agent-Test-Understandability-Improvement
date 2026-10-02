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

    @Test(timeout = 4000)
    public void test05() throws Throwable {
        Shape shapeWithHighestValidIndex30 = Shape.fromKM(31, 31);
        SparseBloomFilter filter = new SparseBloomFilter(shapeWithHighestValidIndex30);

        int[] outOfRangeIndex = new int[1];
        outOfRangeIndex[0] = 31;
        IndexExtractor extractorWithIndex31 = IndexExtractor.fromIndexArray(outOfRangeIndex);

        try {
            filter.merge(extractorWithIndex31);
            fail("Expecting exception: IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            //
            // Value in list 31 is greater than maximum value (30)
            //
            verifyException("org.apache.commons.collections4.bloomfilter.SparseBloomFilter", e);
        }
    }
}
