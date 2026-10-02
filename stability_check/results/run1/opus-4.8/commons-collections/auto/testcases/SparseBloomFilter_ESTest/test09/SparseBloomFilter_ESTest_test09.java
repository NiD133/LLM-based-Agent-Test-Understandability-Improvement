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
     * A newly constructed SparseBloomFilter has no bits set, so it should report itself as empty.
     */
    @Test(timeout = 4000)
    public void newFilterIsEmpty() throws Throwable {
        Shape shape = Shape.fromKM(833, 833);
        SparseBloomFilter emptyFilter = new SparseBloomFilter(shape);

        assertTrue("A freshly created filter should be empty", emptyFilter.isEmpty());
    }
}
