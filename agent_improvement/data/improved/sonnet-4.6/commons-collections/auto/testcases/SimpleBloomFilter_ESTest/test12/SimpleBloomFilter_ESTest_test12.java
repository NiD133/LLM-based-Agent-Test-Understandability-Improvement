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
public class SimpleBloomFilter_ESTest_test12 extends SimpleBloomFilter_ESTest_scaffolding {

    // Verifies that merging a hasher into a SimpleBloomFilter marks the filter as non-empty,
    // and that merge() returns true while isEmpty() returns false after the operation.
    @Test(timeout = 4000)
    public void test_mergeHasher_filterBecomesNonEmpty() throws Throwable {
        Shape shape = Shape.fromNM(2490, 2490);
        SimpleBloomFilter filter = new SimpleBloomFilter(shape);
        EnhancedDoubleHasher hasher = new EnhancedDoubleHasher(2490, 2490);

        boolean mergeSucceeded = filter.merge((Hasher) hasher);
        boolean filterIsEmpty = filter.isEmpty();

        assertFalse(filterIsEmpty == mergeSucceeded); // isEmpty (false) != mergeSucceeded (true)
        assertFalse(filterIsEmpty);
    }
}
