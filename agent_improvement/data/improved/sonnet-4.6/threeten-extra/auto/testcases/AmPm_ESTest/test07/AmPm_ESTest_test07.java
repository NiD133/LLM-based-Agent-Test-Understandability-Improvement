package org.threeten.extra;

import org.junit.Test;
import static org.junit.Assert.*;
import java.time.temporal.TemporalField;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class AmPm_ESTest_test07 extends AmPm_ESTest_scaffolding {

    // isSupported(null) must return false per the AmPm contract: null field is not supported
    @Test(timeout = 4000)
    public void test07() throws Throwable {
        boolean isNullFieldSupported = AmPm.AM.isSupported((TemporalField) null);
        assertFalse(isNullFieldSupported);
    }
}
