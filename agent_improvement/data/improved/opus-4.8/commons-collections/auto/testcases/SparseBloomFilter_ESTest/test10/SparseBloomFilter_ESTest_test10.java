package org.apache.commons.collections4.bloomfilter;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class SparseBloomFilter_ESTest_test10 extends SparseBloomFilter_ESTest_scaffolding {

    /**
     * An empty filter is considered to contain another empty filter: with no
     * required indices to look up, {@code contains} trivially succeeds.
     */
    @Test(timeout = 4000)
    public void emptyFilterContainsItselfAsBitMapExtractor() throws Throwable {
        Shape shape = Shape.fromNM(8, 4135);
        SparseBloomFilter emptyFilter = new SparseBloomFilter(shape);

        boolean contained = emptyFilter.contains((BitMapExtractor) emptyFilter);

        assertTrue(contained);
    }
}
