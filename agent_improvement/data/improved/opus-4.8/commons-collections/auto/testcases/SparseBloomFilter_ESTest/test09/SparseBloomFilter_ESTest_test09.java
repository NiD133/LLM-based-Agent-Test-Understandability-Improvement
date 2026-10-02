package org.apache.commons.collections4.bloomfilter;

import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class SparseBloomFilter_ESTest_test09 extends SparseBloomFilter_ESTest_scaffolding {

    /**
     * A freshly constructed SparseBloomFilter has no bits enabled,
     * so isEmpty() should report true.
     */
    @Test(timeout = 4000)
    public void newFilterIsEmpty() throws Throwable {
        Shape shape = Shape.fromKM(833, 833);
        SparseBloomFilter filter = new SparseBloomFilter(shape);

        boolean empty = filter.isEmpty();

        assertTrue("A newly created filter should contain no bits", empty);
    }
}
