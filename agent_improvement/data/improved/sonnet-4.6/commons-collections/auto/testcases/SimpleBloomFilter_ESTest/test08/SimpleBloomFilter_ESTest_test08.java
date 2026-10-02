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
public class SimpleBloomFilter_ESTest_test08 extends SimpleBloomFilter_ESTest_scaffolding {

    /**
     * Verifies that merging an empty SimpleBloomFilter with itself (as an IndexExtractor)
     * succeeds and leaves the filter still empty.
     *
     * An empty filter has no set indices, so merging it with itself is a no-op.
     * The merge must return true, and isEmpty() must also return true afterward.
     */
    @Test(timeout = 4000)
    public void test08() throws Throwable {
        Shape shape = Shape.fromNM(2490, 2490);
        SimpleBloomFilter emptyFilter = new SimpleBloomFilter(shape);

        boolean mergeSucceeded = emptyFilter.merge((IndexExtractor) emptyFilter);
        boolean filterIsEmpty = emptyFilter.isEmpty();

        // Merging an empty filter with itself should succeed (return true)
        // and the filter should remain empty (also true); both results must agree
        assertTrue(filterIsEmpty == mergeSucceeded);
        assertTrue(filterIsEmpty);
    }
}
