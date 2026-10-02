package org.apache.commons.collections4.bloomfilter;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class SimpleBloomFilter_ESTest_test05 extends SimpleBloomFilter_ESTest_scaffolding {

    /**
     * Merging an (empty) SparseBloomFilter that shares the same Shape into a
     * SimpleBloomFilter should succeed and report success by returning true.
     */
    @Test(timeout = 4000)
    public void mergeSparseFilterWithSameShapeReturnsTrue() throws Throwable {
        Shape sharedShape = Shape.fromKM(3157, 3157);
        SimpleBloomFilter targetFilter = new SimpleBloomFilter(sharedShape);
        SparseBloomFilter filterToMerge = new SparseBloomFilter(sharedShape);

        boolean mergeSucceeded = targetFilter.merge((BloomFilter<?>) filterToMerge);

        assertTrue(mergeSucceeded);
    }
}
