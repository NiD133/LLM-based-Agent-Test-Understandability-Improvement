package org.apache.commons.collections4.bloomfilter;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class SimpleBloomFilter_ESTest_test08 extends SimpleBloomFilter_ESTest_scaffolding {

    /**
     * Merging an empty filter into itself sets no bits, so the filter stays empty.
     * The merge still reports success (returns {@code true}).
     */
    @Test(timeout = 4000)
    public void mergeEmptyFilterIntoItselfLeavesItEmpty() throws Throwable {
        Shape shape = Shape.fromNM(2490, 2490);
        SimpleBloomFilter filter = new SimpleBloomFilter(shape);

        boolean mergeSucceeded = filter.merge((IndexExtractor) filter);
        boolean stillEmpty = filter.isEmpty();

        assertTrue("merge should report success", mergeSucceeded);
        assertTrue("filter should remain empty after merging empty into empty", stillEmpty);
        assertEquals(mergeSucceeded, stillEmpty);
    }
}
