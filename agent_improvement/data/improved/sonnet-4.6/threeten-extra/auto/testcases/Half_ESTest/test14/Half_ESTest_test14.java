package org.threeten.extra;

import org.junit.Test;
import static org.junit.Assert.*;
import java.time.temporal.TemporalField;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Half_ESTest_test14 extends Half_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test_isSupported_returnsfalse_whenFieldIsNull() throws Throwable {
        // Passing null to isSupported should return false without throwing
        boolean result = Half.H2.isSupported((TemporalField) null);
        assertFalse(result);
    }
}
