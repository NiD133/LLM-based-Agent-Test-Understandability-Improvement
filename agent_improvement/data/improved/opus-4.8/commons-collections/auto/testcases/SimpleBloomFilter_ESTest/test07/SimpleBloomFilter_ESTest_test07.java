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
public class SimpleBloomFilter_ESTest_test07 extends SimpleBloomFilter_ESTest_scaffolding {

    /**
     * Merging a SimpleBloomFilter with itself (viewed as a BitMapExtractor)
     * should succeed and return {@code true}.
     */
    @Test(timeout = 4000)
    public void merge_withItselfAsBitMapExtractor_returnsTrue() throws Throwable {
        Shape shape = Shape.fromKM(2496, 2496);
        SimpleBloomFilter bloomFilter = new SimpleBloomFilter(shape);

        boolean mergeSucceeded = bloomFilter.merge((BitMapExtractor) bloomFilter);

        assertTrue(mergeSucceeded);
    }
}
