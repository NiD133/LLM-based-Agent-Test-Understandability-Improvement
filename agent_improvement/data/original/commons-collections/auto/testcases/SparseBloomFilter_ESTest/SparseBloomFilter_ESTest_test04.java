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

    @Test(timeout = 4000)
    public void test04() throws Throwable {
        Shape shape0 = Shape.fromNMK(31, Integer.MAX_VALUE, 31);
        SparseBloomFilter sparseBloomFilter0 = new SparseBloomFilter(shape0);
        int[] intArray0 = new int[6];
        intArray0[0] = (-2047);
        IndexExtractor indexExtractor0 = IndexExtractor.fromIndexArray(intArray0);
        // Undeclared exception!
        try {
            sparseBloomFilter0.merge(indexExtractor0);
            fail("Expecting exception: IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            //
            // Value in list -2047 is less than 0
            //
            verifyException("org.apache.commons.collections4.bloomfilter.SparseBloomFilter", e);
        }
    }
}
