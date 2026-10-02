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
public class Half_ESTest_test10 extends Half_ESTest_scaffolding {

    /**
     * Verifies that getLong(null) throws NullPointerException.
     *
     * When the TemporalField argument is null, Half.getLong() reaches the
     * fallback branch that calls field.getFrom(this), which dereferences
     * the null reference and causes a NullPointerException.
     */
    @Test(timeout = 4000)
    public void test_getLong_withNullField_throwsNullPointerException() throws Throwable {
        Half secondHalf = Half.H2;

        try {
            secondHalf.getLong((TemporalField) null);
            fail("Expecting exception: NullPointerException");
        } catch (NullPointerException e) {
            verifyException("org.threeten.extra.Half", e);
        }
    }
}
