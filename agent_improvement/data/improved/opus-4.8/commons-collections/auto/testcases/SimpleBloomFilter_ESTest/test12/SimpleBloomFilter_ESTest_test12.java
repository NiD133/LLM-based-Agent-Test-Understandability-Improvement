package org.apache.commons.collections4.bloomfilter;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class SimpleBloomFilter_ESTest_test12 extends SimpleBloomFilter_ESTest_scaffolding {

    /**
     * Merging a hasher into a freshly created (empty) Bloom filter should
     * report success and leave the filter in a non-empty state.
     */
    @Test(timeout = 4000)
    public void mergeHasherMakesFilterNonEmpty() throws Throwable {
        Shape shape = Shape.fromNM(2490, 2490);
        SimpleBloomFilter filter = new SimpleBloomFilter(shape);
        EnhancedDoubleHasher hasher = new EnhancedDoubleHasher(2490, 2490);

        boolean mergeSucceeded = filter.merge((Hasher) hasher);
        boolean filterIsEmpty = filter.isEmpty();

        // merge() returns true, while the now-populated filter is not empty.
        assertTrue(mergeSucceeded);
        assertFalse(filterIsEmpty);
        assertNotEquals(filterIsEmpty, mergeSucceeded);
    }
}
