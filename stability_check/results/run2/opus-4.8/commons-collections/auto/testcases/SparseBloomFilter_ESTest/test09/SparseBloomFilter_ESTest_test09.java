package org.apache.commons.collections4.bloomfilter;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class SparseBloomFilter_ESTest_test09 extends SparseBloomFilter_ESTest_scaffolding {

    /**
     * A newly constructed SparseBloomFilter has no bits set, so it should report
     * itself as empty.
     */
    @Test(timeout = 4000)
    public void newlyCreatedFilterIsEmpty() throws Throwable {
        Shape shape = Shape.fromKM(833, 833);
        SparseBloomFilter filter = new SparseBloomFilter(shape);

        boolean empty = filter.isEmpty();

        assertTrue("A freshly constructed filter must be empty", empty);
    }
}
