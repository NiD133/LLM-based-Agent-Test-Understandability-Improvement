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
public class SimpleBloomFilter_ESTest_test10 extends SimpleBloomFilter_ESTest_scaffolding {

    /**
     * Verifies that asBitMapArray() returns an array whose length equals the
     * number of 64-bit longs needed to hold all bits defined by the filter's shape.
     *
     * Shape.fromNM(1110, 1110) produces a shape with 1110 total bits.
     * Storing 1110 bits in 64-bit longs requires ceil(1110 / 64) = 18 longs.
     */
    @Test(timeout = 4000)
    public void test10() throws Throwable {
        // A shape with 1110 items and 1110 bits needs 18 longs to hold all bits
        // because ceil(1110 / 64) = 18.
        int numberOfItems = 1110;
        int numberOfBits  = 1110;
        Shape shape = Shape.fromNM(numberOfItems, numberOfBits);

        SimpleBloomFilter filter = new SimpleBloomFilter(shape);

        long[] bitMapArray = filter.asBitMapArray();

        int expectedArrayLength = 18; // ceil(1110 / 64) = 18
        assertEquals(expectedArrayLength, bitMapArray.length);
    }
}
