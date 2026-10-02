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
     * An empty Bloom filter trivially "contains" itself because it has no bits set,
     * so every index check in processIndices returns true vacuously.
     */
    @Test(timeout = 4000)
    public void test_emptyFilterContainsItselfAsIndexExtractor() throws Throwable {
        Shape shape = Shape.fromKM(3093, 3093);
        SimpleBloomFilter emptyFilter = new SimpleBloomFilter(shape);

        // Cast to IndexExtractor so contains() iterates the filter's own (empty) index set.
        boolean containsItself = emptyFilter.contains((IndexExtractor) emptyFilter);

        assertTrue(containsItself);
    }
}
