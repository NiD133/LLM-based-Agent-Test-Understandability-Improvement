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
public class SimpleBloomFilter_ESTest_test05 extends SimpleBloomFilter_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test05() throws Throwable {
        final int numberOfHashFunctions = 3157;
        final int numberOfBits = 3157;
        final Shape sharedShape = Shape.fromKM(numberOfHashFunctions, numberOfBits);

        final SimpleBloomFilter targetFilter = new SimpleBloomFilter(sharedShape);
        final SparseBloomFilter emptySparseFilter = new SparseBloomFilter(sharedShape);

        final boolean merged = targetFilter.merge((BloomFilter<?>) emptySparseFilter);

        assertTrue(merged);
    }
}
