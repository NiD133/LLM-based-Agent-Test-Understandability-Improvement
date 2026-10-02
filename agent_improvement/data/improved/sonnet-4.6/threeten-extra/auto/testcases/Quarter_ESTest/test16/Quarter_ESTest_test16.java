package org.threeten.extra;

import org.junit.Test;
import static org.junit.Assert.*;
import java.time.temporal.TemporalField;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Quarter_ESTest_test16 extends Quarter_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void isSupported_withNullField_returnsFalse() throws Throwable {
        // Quarter.isSupported(null) must return false, as null is not a supported field
        Quarter quarter = Quarter.Q2;
        boolean result = quarter.isSupported((TemporalField) null);
        assertFalse(result);
    }
}
