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
public class SimpleBloomFilter_ESTest_test11 extends SimpleBloomFilter_ESTest_scaffolding {

    /**
     * Verifies that after calling clear(), characteristics() still returns 0.
     * SimpleBloomFilter.characteristics() always returns 0 (non-sparse), so
     * clear() must not alter that contract.
     */
    @Test(timeout = 4000)
    public void test_clearDoesNotAffectCharacteristics() throws Throwable {
        Shape shape = Shape.fromNM(1110, 1110);
        SimpleBloomFilter filter = new SimpleBloomFilter(shape);

        filter.clear();

        assertEquals(0, filter.characteristics());
    }
}
