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

    /**
     * Calling {@link AmPm#getLong(TemporalField)} with a null field must throw a
     * NullPointerException, because the method dereferences the field before it
     * can decide how to resolve the value.
     */
    @Test(timeout = 4000)
    public void getLong_withNullField_throwsNullPointerException() throws Throwable {
        AmPm afternoon = AmPm.PM;

        try {
            afternoon.getLong((TemporalField) null);
            fail("Expecting exception: NullPointerException");
        } catch (NullPointerException e) {
            // The exception carries no message and originates from AmPm itself.
            verifyException("org.threeten.extra.AmPm", e);
        }
    }
}
