package org.apache.commons.text.lookup;

import static org.junit.Assert.assertNotNull;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class InterpolatorStringLookup_ESTest_test5 extends InterpolatorStringLookup_ESTest_scaffolding {

    /**
     * The no-argument constructor should build an instance successfully,
     * relying solely on the stateless default lookups.
     */
    @Test(timeout = 4000)
    public void noArgConstructorCreatesInstance() throws Throwable {
        InterpolatorStringLookup lookup = new InterpolatorStringLookup();

        assertNotNull(lookup);
    }
}
