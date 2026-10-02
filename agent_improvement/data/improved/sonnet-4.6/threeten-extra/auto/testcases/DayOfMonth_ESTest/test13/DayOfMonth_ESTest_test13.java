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
public class DayOfMonth_ESTest_test13 extends DayOfMonth_ESTest_scaffolding {

    /**
     * Verifies that getLong(null) throws NullPointerException.
     *
     * DayOfMonth.getLong() delegates to field.getFrom(this) for non-ChronoField values,
     * so passing null causes a NullPointerException inside DayOfMonth before any field
     * logic runs.
     */
    @Test(timeout = 4000)
    public void test_getLong_withNullField_throwsNullPointerException() throws Throwable {
        DayOfMonth today = DayOfMonth.now();

        try {
            today.getLong((TemporalField) null);
            fail("Expecting exception: NullPointerException");
        } catch (NullPointerException e) {
            verifyException("org.threeten.extra.DayOfMonth", e);
        }
    }
}
