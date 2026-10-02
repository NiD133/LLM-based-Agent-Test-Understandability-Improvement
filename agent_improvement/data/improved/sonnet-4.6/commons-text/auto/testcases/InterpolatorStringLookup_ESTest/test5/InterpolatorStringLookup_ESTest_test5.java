package org.apache.commons.text.lookup;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.shaded.org.mockito.Mockito.*;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.evosuite.runtime.ViolatedAssumptionAnswer;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class InterpolatorStringLookup_ESTest_test5 extends InterpolatorStringLookup_ESTest_scaffolding {

    // Verifies that the no-arg constructor successfully creates an instance
    // using only the built-in, stateless default lookups (no user-supplied properties needed).
    @Test(timeout = 4000)
    public void test5() throws Throwable {
        InterpolatorStringLookup interpolatorStringLookup0 = new InterpolatorStringLookup();
        assertNotNull("Default constructor should produce a non-null instance", interpolatorStringLookup0);
    }
}
