package org.threeten.extra;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class AmPm_ESTest_test08 extends AmPm_ESTest_scaffolding {

    // AmPm.from() short-circuits when the input is already an AmPm instance,
    // returning the exact same object rather than creating a new one.
    @Test(timeout = 4000)
    public void test_fromAmPmInstance_returnsSameInstance() throws Throwable {
        AmPm am = AmPm.AM;
        AmPm result = AmPm.from(am);
        assertSame(am, result);
    }
}
