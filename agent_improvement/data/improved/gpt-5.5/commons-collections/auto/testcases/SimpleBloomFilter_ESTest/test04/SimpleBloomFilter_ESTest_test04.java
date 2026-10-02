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

    private static final int NUMBER_OF_HASH_FUNCTIONS = 295;
    private static final int NUMBER_OF_BITS = 295;
    private static final int INDEX_ARRAY_LENGTH = 7;
    private static final int FIRST_OUT_OF_RANGE_INDEX = 295;

    @Test(timeout = 4000)
    public void test04() throws Throwable {
        Shape shape = Shape.fromKM(NUMBER_OF_HASH_FUNCTIONS, NUMBER_OF_BITS);
        SimpleBloomFilter bloomFilter = new SimpleBloomFilter(shape);

        int[] indexes = new int[INDEX_ARRAY_LENGTH];
        indexes[0] = FIRST_OUT_OF_RANGE_INDEX;
        IndexExtractor outOfRangeIndexExtractor = IndexExtractor.fromIndexArray(indexes);

        try {
            bloomFilter.merge(outOfRangeIndexExtractor);
            fail("Expecting exception: IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            //
            // IndexExtractor should only send values in the range[0,295)
            //
            verifyException("org.apache.commons.collections4.bloomfilter.SimpleBloomFilter", e);
        }
    }
}
