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

    @Test(timeout = 4000)
    public void test03() throws Throwable {
        final int numberOfHashFunctions = 2490;
        final int numberOfBits = 2490;
        final int bitmapWordCount = 6;
        final long firstBitmapWord = 2490L;

        Shape shape = Shape.fromNM(numberOfHashFunctions, numberOfBits);
        SimpleBloomFilter emptyFilter = new SimpleBloomFilter(shape);

        long[] bitmapWords = new long[bitmapWordCount];
        bitmapWords[0] = firstBitmapWord;
        BitMapExtractor requestedBits = BitMapExtractor.fromBitMapArray(bitmapWords);

        boolean containsRequestedBits = emptyFilter.contains(requestedBits);

        assertFalse(containsRequestedBits);
    }
}
