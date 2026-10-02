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
public class SparseBloomFilter_ESTest_test07 extends SparseBloomFilter_ESTest_scaffolding {

    private static final int HASH_FUNCTION_COUNT = 833;
    private static final int BIT_COUNT = 833;
    private static final long INITIAL_HASH = -782L;
    private static final long INCREMENTAL_HASH = 3371L;
    private static final int EXPECTED_BITMAP_ARRAY_LENGTH = 14;

    @Test(timeout = 4000)
    public void test07() throws Throwable {
        Shape filterShape = Shape.fromKM(HASH_FUNCTION_COUNT, BIT_COUNT);
        SparseBloomFilter filter = new SparseBloomFilter(filterShape);
        EnhancedDoubleHasher hasher = new EnhancedDoubleHasher(INITIAL_HASH, INCREMENTAL_HASH);

        filter.merge((Hasher) hasher);
        long[] bitMaps = filter.asBitMapArray();

        assertEquals(EXPECTED_BITMAP_ARRAY_LENGTH, bitMaps.length);
    }
}
