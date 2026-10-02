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
     * Verifies that calling getLong with a null TemporalField throws NullPointerException.
     * AmPm.getLong delegates to field.getFrom(this) for non-ChronoField values,
     * so passing null causes a NullPointerException before any field dispatch occurs.
     */
    @Test(timeout = 4000)
    public void test00() throws Throwable {
        AmPm pm = AmPm.PM;

        try {
            pm.getLong((TemporalField) null);
            fail("Expecting exception: NullPointerException");
        } catch (NullPointerException e) {
            verifyException("org.threeten.extra.AmPm", e);
        }
    }
}
