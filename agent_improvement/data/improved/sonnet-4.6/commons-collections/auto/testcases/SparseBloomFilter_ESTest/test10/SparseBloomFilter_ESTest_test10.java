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
public class SparseBloomFilter_ESTest_test10 extends SparseBloomFilter_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test10() throws Throwable {
        // An empty SparseBloomFilter vacuously contains itself when checked via the
        // BitMapExtractor interface: it has no bits set, so processIndices finds no
        // indices to verify and allMatch returns true over the empty stream.
        Shape shape = Shape.fromNM(8, 4135);
        SparseBloomFilter emptyFilter = new SparseBloomFilter(shape);

        boolean containsItself = emptyFilter.contains((BitMapExtractor) emptyFilter);

        assertTrue(containsItself);
    }
}
