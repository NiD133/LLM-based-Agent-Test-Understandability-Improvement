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

    // The filter shape uses 833 bits; ceil(833 / 64) = 14 longs are needed to represent the bit map.
    private static final int NUMBER_OF_HASH_FUNCTIONS = 833;
    private static final int NUMBER_OF_BITS = 833;
    private static final int EXPECTED_BIT_MAP_ARRAY_LENGTH = 14;

    @Test(timeout = 4000)
    public void test07() throws Throwable {
        // Build a shape where both the number of hash functions and the number of bits are 833.
        Shape shape = Shape.fromKM(NUMBER_OF_HASH_FUNCTIONS, NUMBER_OF_BITS);

        // Create an empty sparse bloom filter backed by that shape.
        SparseBloomFilter sparseBloomFilter = new SparseBloomFilter(shape);

        // Create a hasher with a specific initial value and increment, then populate the filter.
        EnhancedDoubleHasher hasher = new EnhancedDoubleHasher(-782L, 3371L);
        sparseBloomFilter.merge((Hasher) hasher);

        // The bit-map array packs the 833 bit positions into 64-bit longs: ceil(833/64) = 14.
        long[] bitMapArray = sparseBloomFilter.asBitMapArray();
        assertEquals(EXPECTED_BIT_MAP_ARRAY_LENGTH, bitMapArray.length);
    }
}
