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
        // Define a shape with 3157 hash functions and 3157 bits
        Shape shape = Shape.fromKM(3157, 3157);

        // Create two empty filters sharing the same shape
        SimpleBloomFilter simpleFilter = new SimpleBloomFilter(shape);
        SparseBloomFilter sparseFilter = new SparseBloomFilter(shape);

        // Merging an empty SparseBloomFilter into a SimpleBloomFilter should succeed
        boolean mergeSucceeded = simpleFilter.merge((BloomFilter<?>) sparseFilter);
        assertTrue(mergeSucceeded);
    }
}
