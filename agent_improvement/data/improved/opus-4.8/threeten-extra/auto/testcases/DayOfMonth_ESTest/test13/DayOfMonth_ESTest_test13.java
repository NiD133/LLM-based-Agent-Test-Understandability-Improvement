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
public class DayOfMonth_ESTest_test13 extends DayOfMonth_ESTest_scaffolding {

    /**
     * Calling {@link DayOfMonth#getLong(TemporalField)} with a {@code null} field
     * must fail with a {@link NullPointerException} raised inside {@code DayOfMonth}.
     */
    @Test(timeout = 4000)
    public void getLong_withNullField_throwsNullPointerException() throws Throwable {
        DayOfMonth dayOfMonth = DayOfMonth.now();

        try {
            dayOfMonth.getLong((TemporalField) null);
            fail("Expected a NullPointerException when the field is null");
        } catch (NullPointerException expected) {
            // The exception carries no message and originates in DayOfMonth.
            verifyException("org.threeten.extra.DayOfMonth", expected);
        }
    }
}
