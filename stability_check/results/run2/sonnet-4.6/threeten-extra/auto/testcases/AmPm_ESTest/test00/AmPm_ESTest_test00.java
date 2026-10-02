package org.threeten.extra;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import java.time.temporal.TemporalField;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class AmPm_ESTest_test00 extends AmPm_ESTest_scaffolding {

    // getLong(null) must throw NullPointerException because the implementation
    // dereferences the field before any null-check.
    @Test(timeout = 4000)
    public void test_getLong_withNullField_throwsNullPointerException() throws Throwable {
        AmPm pm = AmPm.PM;
        try {
            pm.getLong((TemporalField) null);
            fail("Expecting exception: NullPointerException");
        } catch (NullPointerException e) {
            verifyException("org.threeten.extra.AmPm", e);
        }
    }
}
