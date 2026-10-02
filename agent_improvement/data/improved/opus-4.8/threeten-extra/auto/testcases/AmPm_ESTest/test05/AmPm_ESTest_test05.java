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
     * Calling {@link AmPm#get(TemporalField)} with a null field must throw a
     * NullPointerException, since the method has no null-handling and immediately
     * dereferences the field.
     */
    @Test(timeout = 4000)
    public void get_withNullField_throwsNullPointerException() throws Throwable {
        AmPm am = AmPm.AM;

        try {
            am.get((TemporalField) null);
            fail("Expecting exception: NullPointerException");
        } catch (NullPointerException e) {
            // The exception carries no message and originates from AmPm.get(...).
            verifyException("org.threeten.extra.AmPm", e);
        }
    }
}
