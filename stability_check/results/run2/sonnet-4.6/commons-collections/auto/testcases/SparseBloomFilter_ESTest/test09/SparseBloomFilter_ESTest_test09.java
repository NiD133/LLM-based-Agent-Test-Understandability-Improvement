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
public class SparseBloomFilter_ESTest_test09 extends SparseBloomFilter_ESTest_scaffolding {

    /**
     * Verifies that a newly constructed SparseBloomFilter with no elements added reports isEmpty() as true.
     */
    @Test(timeout = 4000)
    public void test_newSparseBloomFilter_isEmpty() throws Throwable {
        // Create a shape with 833 hash functions and 833 bits
        Shape shape = Shape.fromKM(833, 833);

        // Construct a filter with the given shape but add no elements
        SparseBloomFilter emptyFilter = new SparseBloomFilter(shape);

        // A freshly constructed filter should contain no bit indices
        boolean filterIsEmpty = emptyFilter.isEmpty();
        assertTrue(filterIsEmpty);
    }
}
