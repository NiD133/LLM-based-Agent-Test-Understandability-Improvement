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
public class SimpleBloomFilter_ESTest_test14 extends SimpleBloomFilter_ESTest_scaffolding {

    /**
     * An empty Bloom filter has no enabled bits, so checking whether it contains
     * its own indices is vacuously true: there are no indices to look up, and
     * {@code contains} reports {@code true} when every queried index is present.
     */
    @Test(timeout = 4000)
    public void emptyFilterContainsItsOwnIndices() throws Throwable {
        Shape shape = Shape.fromKM(3093, 3093);
        SimpleBloomFilter emptyFilter = new SimpleBloomFilter(shape);

        boolean containsOwnIndices = emptyFilter.contains((IndexExtractor) emptyFilter);

        assertTrue(containsOwnIndices);
    }
}
