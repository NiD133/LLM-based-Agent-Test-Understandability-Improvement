package org.apache.commons.collections4.bloomfilter;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class SimpleBloomFilter_ESTest_test03 extends SimpleBloomFilter_ESTest_scaffolding {

    /**
     * An empty Bloom filter should not contain the bits described by a non-empty
     * BitMapExtractor, since none of those bits have been merged into it.
     */
    @Test(timeout = 4000)
    public void emptyFilterDoesNotContainBitsFromExtractor() throws Throwable {
        // Build an empty filter over a shape sized for 2490 items / 2490 bits.
        Shape shape = Shape.fromNM(2490, 2490);
        SimpleBloomFilter emptyFilter = new SimpleBloomFilter(shape);

        // A bit map array whose first word has some bits set (value 2490).
        long[] bitMaps = new long[6];
        bitMaps[0] = 2490L;
        BitMapExtractor extractorWithBitsSet = BitMapExtractor.fromBitMapArray(bitMaps);

        boolean containsBits = emptyFilter.contains(extractorWithBitsSet);

        assertFalse(containsBits);
    }
}
