package org.apache.commons.collections4.bloomfilter;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class SparseBloomFilter_ESTest_test07 extends SparseBloomFilter_ESTest_scaffolding {

    /**
     * Verifies that the bit map array produced by a SparseBloomFilter is sized
     * according to the filter's shape, regardless of how many bits are actually set.
     *
     * The shape declares 833 bits. Bits are packed into 64-bit longs, so the
     * backing array needs ceil(833 / 64) = 14 longs to hold them all.
     */
    @Test(timeout = 4000)
    public void test07() throws Throwable {
        // A shape with 833 hash functions and 833 bits.
        Shape shape = Shape.fromKM(833, 833);
        SparseBloomFilter filter = new SparseBloomFilter(shape);

        // Merge some indices into the filter using a hasher.
        EnhancedDoubleHasher hasher = new EnhancedDoubleHasher(-782L, 3371L);
        filter.merge((Hasher) hasher);

        // The bit map array must span all 833 bits: ceil(833 / 64) = 14 longs.
        long[] bitMapArray = filter.asBitMapArray();
        assertEquals(14, bitMapArray.length);
    }
}
