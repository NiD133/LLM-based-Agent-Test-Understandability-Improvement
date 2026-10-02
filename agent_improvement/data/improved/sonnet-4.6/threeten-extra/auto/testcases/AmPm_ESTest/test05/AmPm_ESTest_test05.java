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
public class AmPm_ESTest_test05 extends AmPm_ESTest_scaffolding {

    /**
     * Verifies that calling AmPm.get() with a null TemporalField throws NullPointerException.
     * The AmPm.get() contract requires a non-null field; passing null must be rejected immediately.
     */
    @Test(timeout = 4000)
    public void test05_getWithNullTemporalField_throwsNullPointerException() throws Throwable {
        AmPm am = AmPm.AM;
        TemporalField nullField = null;

        try {
            am.get(nullField);
            fail("Expecting exception: NullPointerException");
        } catch (NullPointerException e) {
            verifyException("org.threeten.extra.AmPm", e);
        }
    }
}
