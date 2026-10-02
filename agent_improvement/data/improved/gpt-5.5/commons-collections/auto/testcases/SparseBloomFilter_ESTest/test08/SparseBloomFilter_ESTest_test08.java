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
public class SparseBloomFilter_ESTest_test08 extends SparseBloomFilter_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test08() throws Throwable {
        Shape filterShape = Shape.fromKM(31, 31);
        SparseBloomFilter emptyFilter = new SparseBloomFilter(filterShape);

        emptyFilter.clear();

        assertEquals("Clearing a sparse filter must keep the sparse characteristic flag",
                1, emptyFilter.characteristics());
    }
}
