package org.apache.commons.collections4.bloomfilter;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class SimpleBloomFilter_ESTest_test10 extends SimpleBloomFilter_ESTest_scaffolding {

    /**
     * A new, empty filter exposes a bit-map array whose length is the number of
     * 64-bit longs needed to hold the shape's bits. For a shape of 1110 bits,
     * that is ceil(1110 / 64) = 18 longs.
     */
    @Test(timeout = 4000)
    public void asBitMapArrayHasOneLongPerSixtyFourBits() throws Throwable {
        Shape shape = Shape.fromNM(1110, 1110);
        SimpleBloomFilter filter = new SimpleBloomFilter(shape);

        long[] bitMapArray = filter.asBitMapArray();

        int expectedLongCount = 18;
        assertEquals(expectedLongCount, bitMapArray.length);
    }
}
