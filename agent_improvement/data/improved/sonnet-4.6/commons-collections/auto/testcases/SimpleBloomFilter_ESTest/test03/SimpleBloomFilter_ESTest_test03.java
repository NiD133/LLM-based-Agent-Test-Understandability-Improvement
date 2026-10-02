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
public class SimpleBloomFilter_ESTest_test03 extends SimpleBloomFilter_ESTest_scaffolding {

    // Verifies that an empty SimpleBloomFilter does not report containing
    // bits drawn from an external BitMapExtractor.
    @Test(timeout = 4000)
    public void test03() throws Throwable {
        Shape shape = Shape.fromNM(2490, 2490);
        SimpleBloomFilter emptyFilter = new SimpleBloomFilter(shape);

        // Construct a BitMapExtractor whose first 64-bit word has value 2490,
        // meaning several low-order bits are set in that word.
        long[] bitMapWords = new long[6];
        bitMapWords[0] = (long) 2490;
        BitMapExtractor bitMapExtractor = BitMapExtractor.fromBitMapArray(bitMapWords);

        // An empty filter has no bits set, so it cannot contain the extractor's bits.
        boolean contained = emptyFilter.contains(bitMapExtractor);
        assertFalse(contained);
    }
}
