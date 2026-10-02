package org.apache.commons.lang3;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import java.security.SecureRandom;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class RandomUtils_ESTest_test21 extends RandomUtils_ESTest_scaffolding {

    /**
     * Verifies that the no-argument {@link RandomUtils#nextFloat()} returns a value
     * within its documented range: 0 (inclusive) up to Float.MAX_VALUE (exclusive).
     *
     * The exact value is non-deterministic, so the original generated test left its
     * value-specific assertion commented out as "unstable". Here we instead assert the
     * stable range contract, which holds for every generated float.
     */
    @Test(timeout = 4000)
    public void testNextFloatIsWithinDocumentedRange() throws Throwable {
        float randomFloat = RandomUtils.nextFloat();

        assertTrue("nextFloat() must be non-negative", randomFloat >= 0.0F);
        assertTrue("nextFloat() must be below Float.MAX_VALUE", randomFloat < Float.MAX_VALUE);
    }
}
