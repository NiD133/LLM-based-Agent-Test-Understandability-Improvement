package org.threeten.extra;

import static org.junit.Assert.fail;
import static org.evosuite.runtime.EvoAssertions.verifyException;

import java.time.temporal.TemporalField;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class DayOfYear_ESTest_test12 extends DayOfYear_ESTest_scaffolding {

    /**
     * Calling getLong with a null field should fail fast with a
     * NullPointerException thrown from within DayOfYear itself.
     */
    @Test(timeout = 4000)
    public void getLong_withNullField_throwsNullPointerException() throws Throwable {
        DayOfYear today = DayOfYear.now();

        try {
            today.getLong((TemporalField) null);
            fail("Expecting exception: NullPointerException");
        } catch (NullPointerException e) {
            // No message is set on the exception (getMessage() returns null).
            verifyException("org.threeten.extra.DayOfYear", e);
        }
    }
}
